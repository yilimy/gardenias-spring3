package com.example.boot3.jdbc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author caimeng
 * @date 2024/6/3 11:50
 */
@Service
public class BookService {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Transactional
    public void remove(){
        String delSql = "DElETE FROM book WHERE bid = ?";
        // 数据删除
        jdbcTemplate.update(delSql, 7);
        String insertSql = "insert into book (title, author, price) values (null, null, null)";
        // 执行错误的数据
        jdbcTemplate.update(insertSql);
    }

    @Transactional
    public void removeSuccess(){
        String delSql = "DElETE FROM book WHERE bid = ?";
        // 数据删除
        jdbcTemplate.update(delSql, 7);
        String insertSql = "insert into book (title, author, price) values ('Netty开发实战', '小黄', 98.33)";
        // 执行错误的数据
        jdbcTemplate.update(insertSql);
    }
}
