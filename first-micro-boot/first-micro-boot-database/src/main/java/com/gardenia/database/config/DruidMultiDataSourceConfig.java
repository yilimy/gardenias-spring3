package com.gardenia.database.config;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * Druid多数据源配置
 * @author caimeng
 * @date 2025/5/27 11:43
 */
@Configuration
public class DruidMultiDataSourceConfig {
    @Bean("testSqlDataSource")
//    @Primary    // 测试阶段使用。如果不指定主数据源，很多组件会报错，待使用代理后去除(com.gardenia.database.config.DynamicDataSourceConfig)
    // 含有配置 spring.datasource.test-sql 才生效
    @ConfigurationProperties(prefix = "spring.datasource.test-sql")
    public DataSource testSqlDataSource() {
        return DruidDataSourceBuilder.create().build();
    }

    @Bean("testQqqDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.test-qqq")
    public DataSource testQqqDataSource() {
        return DruidDataSourceBuilder.create().build();
    }
}
