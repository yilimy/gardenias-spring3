package com.ssm.mybatis.plus.mapper.impl;

import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.injector.methods.SelectList;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;

/**
 * 参考： {@link com.baomidou.mybatisplus.core.injector.methods.SelectList}
 * @author caimeng
 * @date 2024/12/23 17:06
 */
public class FindAll extends AbstractMethod {
    // 定义映射的方法名称
    public static final String METHOD_NAME = "findAll";
    public FindAll() {
        // 调用有参构造方法，将方法名向上传递给父类
        this(METHOD_NAME);
    }

    /**
     * @param methodName 方法名
     * @since 3.5.0
     */
    public FindAll(String methodName) {
        super(methodName);
    }

    /**
     * 详细的SQL自动生成，可以参考 {@link SelectList#injectMappedStatement(Class, Class, TableInfo)}
     * 这里只做简单的SQL拼凑
     * @param mapperClass mapper 接口
     * @param modelClass  mapper 泛型
     * @param tableInfo   数据库表反射信息
     * @return MappedStatement
     */
    @Override
    public MappedStatement injectMappedStatement(Class<?> mapperClass, Class<?> modelClass, TableInfo tableInfo) {
        // 拼凑SQL语句
        String sql = "SELECT * FROM " + tableInfo.getTableName();
        // 下面代码是抄的
        SqlSource sqlSource = languageDriver.createSqlSource(configuration, sql, modelClass);
        return this.addSelectMappedStatementForTable(mapperClass, methodName, sqlSource, tableInfo);
    }
}
