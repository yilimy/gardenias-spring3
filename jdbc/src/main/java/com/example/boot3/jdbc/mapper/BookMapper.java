package com.example.boot3.jdbc.mapper;

import com.example.boot3.jdbc.pojo.Book;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * jdbc实体映射类
 * @author caimeng
 * @date 2024/5/28 16:34
 */
public class BookMapper implements RowMapper<Book> {
    @Override
    public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
        Book book = new Book();
        book.setBid(rs.getLong(1));
        book.setTitle(rs.getString(2));
        book.setAuthor(rs.getString(3));
        book.setPrice(rs.getDouble(4));
        return book;
    }
}
