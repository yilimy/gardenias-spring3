package com.gardenia.database.config;

import com.alibaba.druid.filter.logging.Slf4jLogFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Druid 的日志配置类
 * @author caimeng
 * @date 2025/5/23 16:22
 */
@Configuration
public class DruidLogConfig {

    @Bean
    public Slf4jLogFilter slf4jLogFilter() {
        Slf4jLogFilter slf4jLogFilter = new Slf4jLogFilter();
        // 启用数据库配置
        slf4jLogFilter.setDataSourceLogEnabled(true);
        // 记录执行日志
        slf4jLogFilter.setStatementExecutableSqlLogEnable(true);
        return slf4jLogFilter;
     }
}
