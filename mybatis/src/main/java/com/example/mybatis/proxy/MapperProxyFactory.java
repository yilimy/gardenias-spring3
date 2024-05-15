package com.example.mybatis.proxy;

import com.example.mybatis.annotation.Param;
import com.example.mybatis.annotation.Select;
import copy.org.apache.ibatis.parsing.GenericTokenParser;
import copy.org.apache.ibatis.parsing.ParameterMapping;
import copy.org.apache.ibatis.parsing.ParameterMappingTokenHandler;
import copy.org.apache.ibatis.type.TypeHandler;
import copy.org.apache.ibatis.type.TypeHandlerRegistry;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Proxy;
import java.lang.reflect.Type;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 代理工厂里提供一些方法去获取Mapper某一个接口的代理对象的方法
 * @author caimeng
 * @date 2024/5/10 17:35
 */
@Slf4j
@SuppressWarnings("unchecked")
public class MapperProxyFactory {
    private static TypeHandlerRegistry typeHandlerRegistry;
    static {
        try {
            // 加载mysql驱动
            Class.forName("com.mysql.cj.jdbc.Driver");
            // 注册类型映射器
            typeHandlerRegistry = new TypeHandlerRegistry();
        } catch (Exception e) {
            log.error("初始化失败", e);
        }
    }

    /**
     * 封装代理方法
     * 原生jdbc实现sql执行 {@link JdbcTest#jdbcTest()}
     * @param mapper 代理对象
     * @return 方法执行结果
     * @param <T> 代理对象类型
     */
    public static <T> T getMapper(Class<T> mapper){
        // 创建一个代理对象
        Object proxyInstance = Proxy.newProxyInstance(
                // 当前应用的类加载器
                ClassLoader.getSystemClassLoader(),
                // 被代理对象的类
                new Class[]{mapper},
                // 实现方法
                ((proxy, method, args) -> {
                    // 解析sql --> 执行sql --> 结果映射
                    // JDBC 是用来执行sql的
                    Select annotation = method.getAnnotation(Select.class);
                    // select * from user where name = #{name}
                    String sql = annotation.value();
                    /*
                     * jdbc 要求的占位符是 ? 要把# --> ?
                     * 建立方法参数名和参数值的映射关系
                     * args : value
                     *
                     * Object : String, int, array ...
                     */
                    Map<String, Object> paramValueMap = new HashMap<>();
                    Parameter[] parameters = method.getParameters();
                    for (int i = 0; i < parameters.length; i++) {
                        // arg0 -> Jack
                        Parameter parameter = parameters[i];
                        // name -> Jack
                        String paramName = parameter.getAnnotation(Param.class).value();
                        // mybatis 也支持使用 arg0(?) 表示第一个参数
                        paramValueMap.put(paramName, args[i]);
                    }
                    /*
                     * 替换占位符，这里需要的不是key-value的键值对映射关系，而是一个顺序kye的关系(List<String>)
                     * e.g. [name, age, name]
                     * 在解析原sql（将#替换成?时）顺道把该事件完成
                     * 该处理在mybatis的 org.apache.ibatis.parsing.GenericTokenParser 中，抄一下该类
                     * [position] ---> args
                     */
                    ParameterMappingTokenHandler tokenHandler = new ParameterMappingTokenHandler();
                    GenericTokenParser parser = new GenericTokenParser("#{", "}", tokenHandler);
                    // select * from user where name = ?
                    String parseSql = parser.parse(sql);
                    log.info("parseSql : {}", parseSql);
                    // name, age, ..
                    List<ParameterMapping> parameterMappings = tokenHandler.getParameterMappings();
                    log.info("parameterMappings: {}", parameterMappings.stream().map(ParameterMapping::getProperty).collect(Collectors.joining(", ")));
                    Connection connection = getConnection();
                    PreparedStatement statement = connection.prepareStatement(parseSql);
                    /*
                     * 构造 statement
                     * sql: [position] ---> value
                     */
                    for (int i = 0; i < parameterMappings.size(); i++) {
                        ParameterMapping parameterMapping = parameterMappings.get(i);
                        /*
                         * 这里也行涉及 JdbcType的概念
                         * paramValueMap 中存储的是Object，本身含java类型的
                         */
                        Object value = paramValueMap.get(parameterMapping.getProperty());
                        /*
                         * 使用策略模式
                         * 类似与反射，执行业务的对象（statement）在参数中
                         * mybatis 中不是通过 TypeHandler<Object> 实现的，是通过强转 copy.org.apache.ibatis.jdbc.Null 实现的
                         */
                        ((TypeHandler<Object>) typeHandlerRegistry.getTypeHandler(value.getClass()))
                                // JDBC里的角标起始值为1
                                .setParameter(statement, i + 1, value, null);
                    }
                    statement.execute();
                    // 解析结果
                    Class<?> resultType = null;
                    Type genericReturnType = method.getGenericReturnType();
                    if (genericReturnType instanceof Class<?>) {
                        // 不是泛型
                        resultType = (Class<?>) genericReturnType;
                    } else if (genericReturnType instanceof ParameterizedType) {
                        // 是泛型
                        Type[] actualTypeArguments = ((ParameterizedType) genericReturnType).getActualTypeArguments();
                        resultType = (Class<?>) actualTypeArguments[0];
                    }
                    assert Objects.nonNull(resultType) : "没有找到返回结果类型";
                    // 映射结果
                    ResultSet resultSet = statement.getResultSet();
                    // 获取到返回的数据库字段
                    ResultSetMetaData metaData = resultSet.getMetaData();
                    List<String> columnList = new ArrayList<>();
                    for (int i = 0; i < metaData.getColumnCount(); i++) {
                        // JDBC里的角标起始值为1
                        columnList.add(metaData.getColumnName(i + 1));
                    }
                    // 建立返回结果类型的方法名（对应字段名）和方法对象的映射
                    Map<String, Method> setterMethodMapping = new HashMap<>();
                    for (Method declaredMethod : resultType.getDeclaredMethods()) {
                        String name = declaredMethod.getName();
                        if (name.startsWith("set")) {
                            String propertyName = name.substring(3);
                            // 首字母小写
                            propertyName = propertyName.substring(0, 1).toLowerCase(Locale.ROOT) + propertyName.substring(1);
                            setterMethodMapping.put(propertyName, declaredMethod);
                        }
                    }
                    // 数据库查询结果转成java对象
                    List<Object> list = new ArrayList<>();
                    while (resultSet.next()) {
                        Object obj = resultType.getDeclaredConstructor().newInstance();
                        for (String column : columnList) {
                            Method doResultSetMethod = setterMethodMapping.get(column);
                            /*
                             * 根据set方法的参数类型，获取TypeHandler
                             */
                            Class<?> setterType = doResultSetMethod.getParameters()[0].getType();
                            Object result = ((TypeHandler<Object>)typeHandlerRegistry.getTypeHandler(setterType))
                                    .getResult(resultSet, column);
                            doResultSetMethod.invoke(obj, result);
                            list.add(obj);
                        }
                    }
                    connection.close();
                    /*
                     * isAssignableFrom是用来判断子类和父类的关系的，或者接口的实现类和接口的关系的，
                     * 默认所有的类的终极父类都是Object。
                     * 如果A.isAssignableFrom(B)结果是true，证明B可以转换成为A,也就是A可以由B转换而来。
                     */
                    if (Collection.class.isAssignableFrom(method.getReturnType())) {
                        return list;
                    } else {
                        if (list.size() > 1) {
                            throw new RuntimeException("查询得到多个结果");
                        }
                        return list.isEmpty() ? null : list.get(0);
                    }
                }));
        return (T) proxyInstance;
    }

    /**
     * @return 数据库连接
     */
    @SneakyThrows
    private static Connection getConnection() {
        return DriverManager.getConnection(
                "jdbc:mysql://192.168.200.130:3306/test_sql?useUnicode=true&allowMultiQuerie=true&characterEncoding=utf-8&zeroDateTimeBehavior=convertToNull&useSSL=false",
                "root", "Gm02_prd8!");
    }

}
