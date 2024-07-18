package com.example.boot3.spring.cache.config;


import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import javax.sql.DataSource;

/**
 * HikariCP数据库连接池配置
 * @author caimeng
 * @date 2024/5/20 17:12
 */
@Configuration
// 读取资源文件
@PropertySource("classpath:config/database.properties")
public class HikariDataSourceConfig {
    // JDBC驱动
    @Value("${hikari.datasource.driverClassName:}")
    private String driverClassName;
    // JDBC连接地址
    @Value("${hikari.datasource.url:}")
    private String url;
    // 用户名
    @Value("${hikari.datasource.username:}")
    private String username;
    // 密码
    @Value("${hikari.datasource.password:}")
    private String password;
    // 数据库是否只读
    @Value("${hikari.datasource.readOnly:}")
    private Boolean readOnly;
    // 数据库连接超时时间，单位：毫秒
    @Value("${hikari.datasource.connection.timeOut:}")
    private Long connectionTimeOut;
    // 连接最小的维持时间，单位：毫秒
    @Value("${hikari.datasource.pool.idle.timeOut:}")
    private Long idleTimeOut;
    // 连接存活的最长时间，单位：毫秒
    @Value("${hikari.datasource.pool.max.leftTime:}")
    private Long maxLeftTime;
    // 最大连接池数量
    @Value("${hikari.datasource.pool.max.size:}")
    private Integer maxSize;
    // 数据库在没有任何用户访问的时候，最少维持的连接
    @Value("${hikari.datasource.pool.min.idle:}")
    private Integer minIdle;

    @Bean("hikariDataSource")
    public DataSource hikariDataSource() {
        HikariDataSource hikariDataSource = new HikariDataSource();
        hikariDataSource.setDriverClassName(driverClassName);
        hikariDataSource.setJdbcUrl(url);
        hikariDataSource.setUsername(username);
        hikariDataSource.setPassword(password);
        hikariDataSource.setReadOnly(readOnly);
        hikariDataSource.setConnectionTimeout(connectionTimeOut);
        hikariDataSource.setIdleTimeout(idleTimeOut);
        hikariDataSource.setMaxLifetime(maxLeftTime);
        hikariDataSource.setMaximumPoolSize(maxSize);
        hikariDataSource.setMinimumIdle(minIdle);
        return hikariDataSource;
    }
}