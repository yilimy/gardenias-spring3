package copy.org.apache.ibatis.jdbc;

import copy.org.apache.ibatis.type.IntegerTypeHandler;
import copy.org.apache.ibatis.type.JdbcType;
import copy.org.apache.ibatis.type.StringTypeHandler;
import copy.org.apache.ibatis.type.TypeHandler;

/**
 * @author caimeng
 * @date 2024/5/11 16:13
 */
public enum Null {
    INTEGER(new IntegerTypeHandler(), JdbcType.INTEGER),
    STRING(new StringTypeHandler(), JdbcType.VARCHAR),
    ;
    private TypeHandler<?> typeHandler;
    private JdbcType jdbcType;
    Null(TypeHandler<?> typeHandler, JdbcType jdbcType) {
        this.typeHandler = typeHandler;
        this.jdbcType = jdbcType;
    }

    public TypeHandler<?> getTypeHandler() {
        return typeHandler;
    }

    public JdbcType getJdbcType() {
        return jdbcType;
    }
}
