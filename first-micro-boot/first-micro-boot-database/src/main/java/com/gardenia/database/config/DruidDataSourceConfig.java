package com.gardenia.database.config;

import com.alibaba.druid.filter.Filter;
import com.alibaba.druid.filter.stat.StatFilter;
import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.wall.WallFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Arrays;
import java.util.List;

/**
 * 自定义 Druid 数据库连接池配置
 * {@link com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure}
 * @author caimeng
 * @date 2025/5/20 14:03
 */
@ConditionalOnProperty(value = "spring.test-qqq.datasource.enabled", havingValue = "true")
@EnableConfigurationProperties({QqqDatasourceProperties.class, QqqDruidDataSourceWrapper.class})
@Configuration
public class DruidDataSourceConfig {
    public static final String DATASOURCE_NAME = "qqqDruidDataSource";
    public static final String JDBC_TEMPLATE_NAME = "qqqJdbcTemplate";

    /**
     * 自定义 Druid 数据源
     * @param datasourceProperties 数据源配置
     * @param druidDataSourceWrapper 自定义 Druid 数据源配置
     * @return 数据源
     */
    @Primary    // 自动装配也注入一个 DruidDataSource，需要设置一个主从数据源
    @Bean(initMethod = "init", name = DATASOURCE_NAME)
    public DruidDataSource getDruidDataSource(
            // 通用数据源配置
            QqqDatasourceProperties datasourceProperties,
            // 自定义 Druid 数据源配置
            QqqDruidDataSourceWrapper druidDataSourceWrapper,
            // SQL 监控拦截器
            @Autowired @Qualifier(DruidMonitorConfig.SQL_STAT_FILTER_NAME) StatFilter statFilter,
            // SQL 防火墙拦截器
            WallFilter wallFilter
    ) {
        DruidDataSource dataSource = new DruidDataSource();
        // 数据库驱动
        dataSource.setDriverClassName(datasourceProperties.getDriverClassName());
        // 数据库地址
        dataSource.setUrl(datasourceProperties.getUrl());
        // 数据库用户名
        dataSource.setUsername(datasourceProperties.getUsername());
        // 数据库密码
        dataSource.setPassword(datasourceProperties.getPassword());
        // 初始化连接大小
        dataSource.setInitialSize(druidDataSourceWrapper.getInitialSize());
        // 最大连接数量
        dataSource.setMaxActive(druidDataSourceWrapper.getMaxActive());
        // 最小维持的连接数量
        dataSource.setMinIdle(druidDataSourceWrapper.getMinIdle());
        // 最大等待时间
        dataSource.setMaxWait(druidDataSourceWrapper.getMaxWait());
        // 检查的间隔时间
        dataSource.setTimeBetweenEvictionRunsMillis(druidDataSourceWrapper.getTimeBetweenEvictionRunsMillis());
        // 检测连接空闲的时间，回收的周期时间，存活时间
        dataSource.setMinEvictableIdleTimeMillis(druidDataSourceWrapper.getMinEvictableIdleTimeMillis());
        // 测试连接是否可用
        dataSource.setTestWhileIdle(druidDataSourceWrapper.isTestWhileIdle());
        // 在获得连接之前，是否要进行测试
        dataSource.setTestOnBorrow(druidDataSourceWrapper.isTestOnBorrow());
        // 归还连接前，是否要经过测试
        dataSource.setTestOnReturn(druidDataSourceWrapper.isTestOnReturn());
        // 验证 SQL
        dataSource.setValidationQuery(druidDataSourceWrapper.getValidationQuery());
        // 不缓存 PSTMT，Oracle可以这么干，其他数据库别这么做
        dataSource.setPoolPreparedStatements(druidDataSourceWrapper.isPoolPreparedStatements());
        // 每次连接的换缓存个数，配置 PSTMT 缓存个数
        dataSource.setMaxPoolPreparedStatementPerConnectionSize(druidDataSourceWrapper.getMaxPoolPreparedStatementPerConnectionSize());
        // 定义所有的Filter项
//        List<Filter> filters = Collections.singletonList(statFilter);
        List<Filter> filters = Arrays.asList(statFilter, wallFilter);
        // SQL监控与DataSource整合
        dataSource.setProxyFilters(filters);
        return dataSource;
    }

    @Primary
    @Bean(JDBC_TEMPLATE_NAME)
    public JdbcTemplate qqqJdbcTemplate(@Autowired @Qualifier(DATASOURCE_NAME) DruidDataSource druidDataSource) {
        return new JdbcTemplate(druidDataSource);
    }
}
