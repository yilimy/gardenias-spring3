package com.gardenia.database.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 参考 {@link com.alibaba.druid.spring.boot.autoconfigure.properties.DruidStatProperties}
 * <p>
 *     这里选择继承的是 DruidDataSource，(X)
 *     是否继承  DruidDataSourceWrapper 意义不大，因为使用的是 @ConditionalOnMissingBean
 *     {@link com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure#dataSource()}
 * <p>
 *     不能继承 DruidDataSource 否则健康检查时会报错
 * @author caimeng
 * @date 2025/5/20 14:25
 */
@Data
@ConfigurationProperties("spring.test-qqq.datasource.druid")
public class QqqDruidDataSourceWrapper {
    // 初始化连接大小
    private int initialSize;
    // 最大连接数量
    private int maxActive;
    // 最小维持的连接数量
    private int minIdle;
    // 最大等待时间
    private long maxWait;
    // 检查的间隔时间
    private long timeBetweenEvictionRunsMillis;
    // 测试连接是否可用
    private boolean testWhileIdle;
    // 检测连接空闲的时间，回收的周期时间，存活时间
    private long minEvictableIdleTimeMillis;
    // 在获得连接之前，是否要进行测试
    private boolean testOnBorrow;
    // 归还连接前，是否要经过测试
    private boolean testOnReturn;
    // 验证 SQL
    private String validationQuery;
    // 不缓存 PSTMT，Oracle可以这么干，其他数据库别这么做
    private boolean poolPreparedStatements;
    // 每次连接的换缓存个数，配置 PSTMT 缓存个数
    private int maxPoolPreparedStatementPerConnectionSize;
}
