package com.ssm.batch.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import javax.sql.DataSource;

/**
 * 第二个数据源
 * <p>
 *     第一个数据源 {@link BatchDataSourceConfig}
 * @author caimeng
 * @date 2024/12/17 10:04
 */
@Configuration
@PropertySource(value = "classpath:config/database.properties")
public class YootkDataSourceConfig {
    // JDBC驱动
    @Value("${yootk.database.driverClassName:}")
    private String driverClassName;
    // JDBC连接地址
    @Value("${yootk.database.url:}")
    private String url;
    // 用户名
    @Value("${yootk.database.username:}")
    private String username;
    // 密码
    @Value("${yootk.database.password:}")
    private String password;
    // 数据库是否只读
    @Value("${yootk.database.readOnly:}")
    private Boolean readOnly;
    // 数据库连接超时时间，单位：毫秒
    @Value("${yootk.database.connection.timeOut:}")
    private Long connectionTimeOut;
    // 连接最小的维持时间，单位：毫秒
    @Value("${yootk.database.pool.idle.timeOut:}")
    private Long idleTimeOut;
    // 连接存活的最长时间，单位：毫秒
    @Value("${yootk.database.pool.max.leftTime:}")
    private Long maxLeftTime;
    // 最大连接池数量
    @Value("${yootk.database.pool.max.size:}")
    private Integer maxSize;
    // 数据库在没有任何用户访问的时候，最少维持的连接
    @Value("${yootk.database.pool.min.idle:}")
    private Integer minIdle;

    @Bean("yootkDataSource")
    public DataSource yootkDataSource() {
        return BatchDataSourceConfig.getDataSource(
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
}
