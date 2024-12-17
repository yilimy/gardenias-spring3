package com.ssm.mybatis.sql;

import com.ssm.mybatis.mapper.IMessageAnnotationDao;
import com.ssm.mybatis.vo.Message;
import org.apache.ibatis.jdbc.SQL;

import java.util.Map;

/**
 * SQL语句构建工具类
 * <p>
 *     动态生成sql命令，建立java类与sql命令之间的桥梁
 * @see Message
 * @see IMessageAnnotationDao
 * @author caimeng
 * @date 2024/12/17 17:50
 */
public class MessageSQL {

    /**
     * 生成 SQL 预处理语句
     * <p>
     *     INSERT INTO message
     *      (title, sender, content)
     *      VALUES (#{title}, #{sender}, #{content})
     * @return sql预处理语句
     */
    public String insertMessageSQL() {
        return new SQL()
                // 设置表名
                .INSERT_INTO("message")
                // 设置列名
                .INTO_COLUMNS("title", "sender", "content")
                // 设置占位符
                .INTO_VALUES("#{title}", "#{sender}", "#{content}")
                .toString();
    }

    /**
     * 分页查询
     * {@link IMessageAnnotationDao#findAll(Map)}
     * @return 分页查询的SQL
     */
    public String selectMessageSplitSQL() {
        return new SQL()
                .SELECT("mid", "title", "sender", "content")
                .FROM("message")
                .LIMIT("#{start}, #{line}")
                .toString();
    }
}
