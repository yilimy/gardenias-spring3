package com.ssm.mybatis.plus.table;

import com.baomidou.mybatisplus.extension.plugins.handler.TableNameHandler;

/**
 * 表名称处理
 * @author caimeng
 * @date 2024/12/24 14:13
 */
public class MyTableNameHandler implements TableNameHandler {
    @Override
    public String dynamicTableName(String sql, String tableName) {  // 获取最终执行的表名称
        /*
         * 这里可以编写很多的逻辑，也可以读取各种配置文件
         * 模拟一个表名
         * SQL: SELECT pid,name,charge,note,status,version FROM dev_project WHERE pid=?  AND status=0
         * Cause: java.sql.SQLSyntaxErrorException: Table 'test_sql.dev_project' doesn't exist
         */
//        return "dev_" + tableName;
        return tableName;
    }
}
