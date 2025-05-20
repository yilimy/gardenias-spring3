package com.gardenia.database.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 参考 {@link com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceWrapper}
 * <p>
 *     这里选择继承的是 DruidDataSource，
 *     是否继承  DruidDataSourceWrapper 意义不大，因为使用的是 @ConditionalOnMissingBean
 *     {@link com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure#dataSource()}
 * @author caimeng
 * @date 2025/5/20 14:25
 */
@ConfigurationProperties("spring.test-qqq.datasource.druid")
public class QqqDruidDataSourceWrapper extends DruidDataSource {
}
