package com.example.boot3.jdbc.service.impl;

import com.example.boot3.jdbc.pojo.Book;
import com.example.boot3.jdbc.service.BookService;
import com.example.boot3.jdbc.util.SimpleSql;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author caimeng
 * @date 2024/6/3 14:23
 */
@Service
public class BookServiceImpl implements BookService {
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

    @Transactional
    @Override
    public void proxyTransactional() {
        System.out.println("BookServiceImpl::proxyTransactional");
        System.out.println("有接口的动态代理执行事务");
        testNever();
    }

    @Transactional
    public void testTransactionalInvoke() {
        System.out.println("BookServiceImpl::testTransactionalInvoke");
        System.out.println("无接口的动态代理执行事务");
        testNever();
    }

    /**
     * 以非事务的方式运行，如果当前存在事务则抛异常。
     */
    @Transactional(propagation = Propagation.NEVER)
    public void testNever() {
        System.out.println("BookServiceImpl::testNever");
        System.out.println("调用事务控制：NEVER");
        SimpleSql.simpleSelect(jdbcTemplate);
    }

}
