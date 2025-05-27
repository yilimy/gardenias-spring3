package com.gardenia.database.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
import java.util.Map;

/**
 * 动态数据源配置
 * @author caimeng
 * @date 2025/5/27 14:42
 */
@Configuration
public class DynamicDataSourceConfig {

    /**
     * 获取动态数据源
     * {@link DruidMultiDataSourceConfig}
     * @return 决策后的数据源
     */
    @Primary
    @Bean("dynamicDataSource")
    @DependsOn({"testSqlDataSource", "testQqqDataSource"})  // 依赖的数据源
    public DynamicRoutingDataSource getDataSource(
            @Autowired @Qualifier("testSqlDataSource") DataSource testSqlDataSource,
            @Autowired @Qualifier("testQqqDataSource") DataSource testQqqDataSource
    ) {
        Map<Object, Object> targetDataSources = Map.of(
                DynamicRoutingDataSource.DataSourceType.TEST_SQL, testSqlDataSource,
                DynamicRoutingDataSource.DataSourceType.TEST_QQQ, testQqqDataSource
        );
        return new DynamicRoutingDataSource(testSqlDataSource, targetDataSources);
    }
}
