package com.example.boot3.jdbc.service.impl;

import com.example.boot3.jdbc.pojo.Book;
import com.example.boot3.jdbc.service.PubWithoutAnnotationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

/**
 * 使用切面表达式的方式实现事务控制
 * 配置文件：/spring/spring-transaction-aop.xml
 * <p>
 *     相较于 {@link BookServiceImpl#removeSuccess()} 的注解方式，
 *     解析切面表达式需要添加额外的解析依赖
 *     org.springframework:spring-aspects
 *
 * @author caimeng
 * @date 2024/6/3 15:04
 */
@Service
public class PubWithoutAnnotationServiceImpl implements PubWithoutAnnotationService {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private PubWithoutAnnotationService selfInstance;
    @Override
    public boolean add(Book book) {
        String insertSql = "insert into book (title, author, price) values (?, ?, ?)";
        int update = jdbcTemplate.update(insertSql, book.getTitle(), book.getAuthor(), book.getPrice());
        return update > 0;
    }

    @Override
    public boolean edit(Book book) {
        String updateSql = "UPDATE book SET title=?, author=?, price=? WHERE bid=?";
        int update = jdbcTemplate.update(updateSql, book.getTitle(), book.getAuthor(), book.getPrice(), book.getBid());
        return update > 0;
    }

    @Override
    public boolean editAll() {
        Book bookA = Book.builder().title("Java进阶开发实战").author("小孩").price(45.67).bid(8L).build();
        // 错误的数据
        Book bookB = Book.builder().title("小程序开发实战").author(null).price(87.64).build();
        return selfInstance.edit(bookA) && selfInstance.add(bookB);
    }
}
