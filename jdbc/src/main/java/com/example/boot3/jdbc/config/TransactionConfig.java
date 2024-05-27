package com.example.boot3.jdbc.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.ReactiveTransactionManager;
import org.springframework.transaction.TransactionDefinition;

import javax.sql.DataSource;

/**
 * 事务控制
 * <p>
 *     不管现在使用的是哪一种ORM开发框架，只要核心是JDBC，那么所有的事务处理都是围绕JDBC展开的，而JDBC中的事务控制是由Connection接口提供的支持。
 *     1. 关闭自动事务提交 connection.setAutoCommit(false)
 *     2. 事务手动提交    connection.commit()
 *     3. 事务回滚       connection.rollback()
 * <p>
 *     在程序的开发中，事务的使用是有前提的：
 *     如果某一个业务现在需要同时执行若干条数据更新处理操作，这个时候才会使用到事务控制，除此之外是不需要强制性处理的。
 * <p>
 *     ACID事务原则
 *     1. 原子性(Atomicity):
 *          整个事务中的所有操作，要么全部完成，要么全部不完成，不可能停滞在中间某个环节。
 *          事务在执行过程中出错，会被回滚(RollBack)到事务前的状态，就像事务没有发生过一样；
 *     2. 一致性(Consistency):
 *          一个事务可以封装状态改变（除非它是一个只读的）。
 *          事务必须始终保持系统处于一致性状态，不管在任何给定的时间内并发多少事务；
 *     3. 隔离性(Isolation):
 *          隔离状态执行事务，使它们好像是在系统给的时间内执行的唯一操作。
 *          如果有两个事务，在运行相同时间内，执行相同的功能，事务的隔离性将确保每一个事务在系统中认为只有该事务在使用系统；
 *     4. 持久性(Durability):
 *          在事务完成后，该事务对数据库的所有更改，持久的保存在数据库中，不会被回滚。
 * <a href="https://docs.spring.io/spring-framework/docs/6.0.0-M4/reference/html/data-access.html#spring-data-tier"/>
 * @author caimeng
 * @date 2024/5/27 11:32
 */
@Configuration
public class TransactionConfig {

    /**
     * 事务控制的注册
     * 事务控制的过程跟 DataSource 相关
     * 事务传播的定义位于 {@link PlatformTransactionManager#getTransaction(TransactionDefinition)} 方法的入参 {@link TransactionDefinition} 中
     * <p>
     *     TransactionManager 是 spring 5.2 时提供的接口，是 PlatformTransactionManager 的父接口
     *     TransactionManager 是为 5.2 响应式编程提供的接口，其子接口 {@link ReactiveTransactionManager} 定义了响应式接口的范式
     * @param dataSource 数据库连接对象
     * @return 事务控制对象
     */
    @Bean
    public PlatformTransactionManager transactionManager(DataSource dataSource) {
        // PlatformTransactionManager 是一个事务控制标准，而后不同的数据库组件需要实现该标准
        return new DataSourceTransactionManager(dataSource);
    }
}
