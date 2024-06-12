package com.example.boot3.jdbc.service;

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
    private BookServiceCglibBean bookServiceCglibBean;

    /**
     * 事务均没生效
     */
    @Test
    public void proxyTransactional() {
        /*
         * BookServiceCglibBean::proxyTransactional
         * BookServiceImpl::proxyTransactional
         * 有接口的动态代理执行事务
         * BookServiceImpl::testNever
         * 调用事务控制：NEVER
         * simpleSelect
         * Book(bid=3, title=Python从入门到放弃, author=小明, price=7.0)
         * BookServiceCglibBean::testNever
         * 调用事务控制：NEVER
         * BookServiceCglibBean::simpleSelect
         * simpleSelect
         * Book(bid=3, title=Python从入门到放弃, author=小明, price=7.0)
         */
        bookServiceCglibBean.proxyTransactional();
    }

    /**
     * 事务没有传播
     */
    @Test
    public void testTransactionalInvoke() {
        /*
         * BookServiceCglibBean::testTransactionalInvoke
         * 开启事务控制
         * BookServiceCglibBean::testNever
         * 调用事务控制：NEVER
         * BookServiceCglibBean::simpleSelect
         * simpleSelect
         * Book(bid=3, title=Python从入门到放弃, author=小明, price=7.0)
         */
        bookServiceCglibBean.testTransactionalInvoke();
    }

    /**
     * 事务传播生效
     */
    @Test
    public void testTransactionalSelfInvoke() {
        /*
         * BookServiceCglibBean::testTransactionalInvoke
         * 开启事务控制
         * org.springframework.transaction.IllegalTransactionStateException:
         *      Existing transaction found for transaction marked with propagation 'never'
         */
        bookServiceCglibBean.testTransactionalSelfInvoke();
    }
}
