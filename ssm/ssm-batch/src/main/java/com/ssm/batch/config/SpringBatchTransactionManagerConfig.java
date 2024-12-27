package com.ssm.batch.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

/**
 * SpringBatch 的事务管理器配置
 * @author caimeng
 * @date 2024/12/27 15:53
 */
@Configuration
public class SpringBatchTransactionManagerConfig {

    @Bean
    public PlatformTransactionManager batchTransactionManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }
}
