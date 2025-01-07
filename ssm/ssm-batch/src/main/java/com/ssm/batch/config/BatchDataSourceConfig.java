package com.ssm.batch.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;

import javax.sql.DataSource;

/**
 * @author caimeng
 * @date 2024/12/17 10:04
 */
@Configuration
@PropertySource(value = "classpath:config/database.properties")
public class BatchDataSourceConfig {
    // JDBC驱动
    @Value("${batch.database.driverClassName:}")
    private String driverClassName;
    // JDBC连接地址
    @Value("${batch.database.url:}")
    private String url;
    // 用户名
    @Value("${batch.database.username:}")
    private String username;
    // 密码
    @Value("${batch.database.password:}")
    private String password;
    // 数据库是否只读
    @Value("${batch.database.readOnly:}")
    private Boolean readOnly;
    // 数据库连接超时时间，单位：毫秒
    @Value("${batch.database.connection.timeOut:}")
    private Long connectionTimeOut;
    // 连接最小的维持时间，单位：毫秒
    @Value("${batch.database.pool.idle.timeOut:}")
    private Long idleTimeOut;
    // 连接存活的最长时间，单位：毫秒
    @Value("${batch.database.pool.max.leftTime:}")
    private Long maxLeftTime;
    // 最大连接池数量
    @Value("${batch.database.pool.max.size:}")
    private Integer maxSize;
    // 数据库在没有任何用户访问的时候，最少维持的连接
    @Value("${batch.database.pool.min.idle:}")
    private Integer minIdle;

    @Bean("batchDataSource")
    @Primary
    public DataSource batchDataSource() {
        return getDataSource(
                driverClassName,
                url,
                username,
                password,
                readOnly,
                connectionTimeOut,
                idleTimeOut,
                maxLeftTime,
                maxSize,
                minIdle
        );
    }

    public static DataSource getDataSource(String driverClassName, String url, String username, String password, Boolean readOnly, Long connectionTimeOut, Long idleTimeOut, Long maxLeftTime, Integer maxSize, Integer minIdle) {
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
