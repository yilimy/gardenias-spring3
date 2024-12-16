package com.ssm.mybatis.type;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

/**
 * 数据类型转换
 * @author caimeng
 * @date 2024/12/16 10:26
 */
public class BooleanAndIntegerTypeHandler implements TypeHandler<Boolean> {
    @Override
    public void setParameter(PreparedStatement ps, int i, Boolean parameter, JdbcType jdbcType) throws SQLException {
        if (parameter == null) {    // 对应的属性没有内容
            ps.setNull(i, Types.NULL);
        } else {
            if (parameter) {
                ps.setInt(i, 1);
            } else {
                ps.setInt(i, 0);
            }
        }
    }

    @Override
    public Boolean getResult(ResultSet rs, String columnName) throws SQLException {
        int anInt = rs.getInt(columnName);
        return anInt != 0;
    }

    @Override
    public Boolean getResult(ResultSet rs, int columnIndex) throws SQLException {
        int anInt = rs.getInt(columnIndex);
        return anInt != 0;
    }

    @Override
    public Boolean getResult(CallableStatement cs, int columnIndex) throws SQLException {
        int anInt = cs.getInt(columnIndex);
        return anInt != 0;
    }
}
