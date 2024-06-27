package com.example.boot3.datajpa.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

/**
 * 事务控制
 * 相比于 jdbc项目中的 TransactionConfig，替换了数据源
 * @author caimeng
 * @date 2024/5/27 11:32
 */
@Configuration
public class TransactionConfig {

    /**
     * 事务控制的注册
     * 原事务管理 {@link org.springframework.jdbc.datasource.DataSourceTransactionManager }
     * 针对于 Hibernate5 的事务管理 {@link org.springframework.orm.hibernate5.HibernateTransactionManager}
     * 针对于 jpa 的事务管理 {@link org.springframework.orm.jpa.JpaTransactionManager}
     * 对接jpa的时候必须要更换事务管理器为 JpaTransactionManager，否则无法使用事务管理器
     * @param dataSource 数据库连接对象
     * @return 事务控制对象
     */
    @Bean
    public PlatformTransactionManager transactionManager(DataSource dataSource) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setDataSource(dataSource);
        return transactionManager;
    }
}
