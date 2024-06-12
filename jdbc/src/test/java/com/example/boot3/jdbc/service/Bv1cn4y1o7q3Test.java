package com.example.boot3.jdbc.service;

import com.example.boot3.jdbc.service.impl.BookServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * 该测试类来源于 <a href="https://www.bilibili.com/video/BV1cn4y1o7Q3/?p=17">AOP底层原理之事务控制</a>
 * @author caimeng
 * @date 2024/6/11 18:17
 */
@ContextConfiguration(locations = {
        // 扫描注入spring bean
        "classpath:spring/spring-base.xml",
        // 开启事务注解
        "classpath:spring/spring-transaction.xml"
})
@ExtendWith(SpringExtension.class)
public class Bv1cn4y1o7q3Test {
    @Autowired
    private BookServiceImpl bookService;

    /**
     * 执行出错，bookService是一个JDK动态代理，没有注入 BookServiceImpl 对象
     * org.springframework.beans.factory.UnsatisfiedDependencyException:
     *      Error creating bean with name 'com.example.boot3.jdbc.service.Bv1cn4y1o7q3Test':
     *          Unsatisfied dependency expressed through field 'bookService':
     *              Bean named 'bookServiceImpl' is expected to be of type 'com.example.boot3.jdbc.service.impl.BookServiceImpl' but was actually of type 'jdk.proxy2.$Proxy28'
     *
     */
    @Test
    public void jdkProxyTransactional() {
        bookService.proxyTransactional();
    }
}
