package com.example.mybatis.orm;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.example.baomidou.mybatisplus.toolkit.StringUtils;
import jakarta.validation.constraints.Max;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.util.ObjectUtils;

import java.lang.reflect.Field;
import java.sql.JDBCType;
import java.sql.Statement;
import java.sql.Types;
import java.util.Date;
import java.util.Objects;

/**
 * @author caimeng
 * @date 2024/6/26 11:20
 */
@Slf4j
public final class DbUtils {
    private DbUtils(){}

    public static void createTableByPO(Class<?> clazz, SqlSessionFactory sqlSessionFactory) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        createTableByPO(clazz, sqlSession);
        sqlSession.close();
    }

    /**
     * 根据entity创建表名
     * <p>
     *     因子太多，不如用开源库，
     *     com.gitee.sunchenbin.mybatis.actable:mybatis-enhance-actable:1.1.1.RELEASE
     * <p>
     *     不如直接在 mapper中写创建方法
     * @param clazz entity类型
     */
    public static void createTableByPO(Class<?> clazz, SqlSession sqlSession) {
        String creatorSql = genCreatorSql(clazz);
        log.info("\n{}", creatorSql);
        createTable(sqlSession, creatorSql);
    }

    public static String genCreatorSql(Class<?> clazz) {
        TableName annotationTableName = clazz.getAnnotation(TableName.class);
        if (Objects.isNull(annotationTableName)) {
            throw new RuntimeException("没有找到@TableName注解");
        }
        String tableName = annotationTableName.value();
        if (ObjectUtils.isEmpty(tableName)) {
            tableName = StringUtils.camelToUnderline(clazz.getSimpleName());
        }
        StringBuilder sqlBuilder = new StringBuilder("CREATE TABLE IF NOT EXISTS ");
        sqlBuilder.append(tableName);
        sqlBuilder.append(" (");
        Field[] declaredFields = clazz.getDeclaredFields();
        if (declaredFields.length == 0) {
            throw new RuntimeException("创建表语句失败，属性列表为空");
        }
        for (Field field : declaredFields) {
            appendColumn(sqlBuilder, field);
        }
        // 删除最后一个","
        sqlBuilder.deleteCharAt(sqlBuilder.length() - 1);
        // 这里需要根据驱动名来设置，暂定为 InnoDB
        sqlBuilder.append(" ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;");
        return sqlBuilder.toString();
    }

    private static void appendColumn(StringBuilder sqlBuilder, Field field) {
        int typeCode;
        typeCode = getTypeCodeByField(field);
        JDBCType jdbcType = JDBCType.valueOf(typeCode);
        String columnName = StringUtils.camelToUnderline(field.getName());
        sqlBuilder.append(" ");
        sqlBuilder.append(columnName);
        sqlBuilder.append(" ");
        sqlBuilder.append(jdbcType);
        Max annotationMax = field.getAnnotation(Max.class);
        if (Objects.nonNull(annotationMax)) {
            sqlBuilder.append("(");
            long max = annotationMax.value();
            sqlBuilder.append(max == 0 ? 255 : max);
            sqlBuilder.append(")");
        }
        TableId annotationId = field.getAnnotation(TableId.class);
        if (Objects.nonNull(annotationId)) {
            // 暂时只支持自增主键
            sqlBuilder.append(" NOT NULL AUTO_INCREMENT,");
            sqlBuilder.append(" PRIMARY KEY (");
            sqlBuilder.append(columnName);
            sqlBuilder.append("),");
        }
        TableField annotationField = field.getAnnotation(TableField.class);
        if (Objects.nonNull(annotationField)) {
            sqlBuilder.append(" DEFAULT NULL,");
        }
    }

    private static int getTypeCodeByField(Field field) {
        int typeCode;
        Class<?> fieldType = field.getType();
        if (fieldType.equals(Long.class) || fieldType.equals(long.class)) {
            typeCode = Types.BIGINT;
        } else if (fieldType.equals(String.class) || fieldType.equals(CharSequence.class)) {
            typeCode = Types.VARCHAR;
        } else if (fieldType.equals(Date.class)) {
            typeCode = Types.TIMESTAMP;
        } else {
            throw new UnsupportedOperationException("不支持的属性映射类型：" + fieldType);
        }
        return typeCode;
    }

    @SneakyThrows
    private static void createTable(SqlSession sqlSession, String creatorSql) {
        try (Statement statement = sqlSession.getConnection().createStatement()) {
            statement.executeUpdate(creatorSql);
        }
    }
}
