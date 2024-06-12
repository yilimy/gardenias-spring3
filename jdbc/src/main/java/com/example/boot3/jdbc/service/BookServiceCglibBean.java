package com.example.boot3.jdbc.service;

import com.example.boot3.jdbc.util.SimpleSql;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author caimeng
 * @date 2024/6/12 16:26
 */
@Service
public class BookServiceCglibBean {
    // 这是一个由JDK代理的Bean
    @Autowired
    private BookService bookService;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Lazy
    @Autowired
    private BookServiceCglibBean selfInstance;

    public void proxyTransactional() {
        System.out.println("BookServiceCglibBean::proxyTransactional");
        // 这里的方法开启了事务
        bookService.proxyTransactional();
        testNever();
    }

    @Transactional
    public void testTransactionalInvoke() {
        System.out.println("BookServiceCglibBean::testTransactionalInvoke");
        System.out.println("开启事务控制");
        testNever();
    }

    @Transactional
    public void testTransactionalSelfInvoke() {
        System.out.println("BookServiceCglibBean::testTransactionalInvoke");
        System.out.println("开启事务控制");
        SimpleSql.simpleInsert(jdbcTemplate);
        selfInstance.testNever();
    }

    /**
     * 以非事务的方式运行，如果当前存在事务则抛异常。
     */
    @Transactional(propagation = Propagation.NEVER)
    public void testNever() {
        System.out.println("BookServiceCglibBean::testNever");
        System.out.println("调用事务控制：NEVER");
        simpleInsert();
    }

    private void simpleInsert() {
        System.out.println("BookServiceCglibBean::simpleInsert");
        SimpleSql.simpleInsert(jdbcTemplate);
    }
}

