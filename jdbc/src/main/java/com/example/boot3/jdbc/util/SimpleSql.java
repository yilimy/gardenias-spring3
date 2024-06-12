package com.example.boot3.jdbc.util;

import com.example.boot3.jdbc.pojo.Book;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * @author caimeng
 * @date 2024/6/12 17:06
 */
public final class SimpleSql {
    private SimpleSql(){}

    public static void simpleSelect(JdbcTemplate jdbcTemplate) {
        System.out.println("simpleSelect");
        String sql = "SELECT bid, title, author, price  FROM book WHERE bid = ?";
        Book book = jdbcTemplate.queryForObject(sql,
                // 此时传入一个ResultSet 接口实例，这个接口可以获取查询结果
                (rs, rowNum) -> {
                    Book vo = new Book();
                    vo.setBid(rs.getLong(1));
                    vo.setTitle(rs.getString(2));
                    vo.setAuthor(rs.getString(3));
                    vo.setPrice(rs.getDouble(4));
                    return vo;
                },
                // 查询指定编号的数据
                3);
        System.out.println(book);
    }

    public static void simpleInsert(JdbcTemplate jdbcTemplate) {
        String insertSql = "INSERT INTO book(title, author, price) VALUES (?, ?, ?)";
        jdbcTemplate.update(insertSql, "Redis开发实战", "Andy", 77.32);
    }
}
