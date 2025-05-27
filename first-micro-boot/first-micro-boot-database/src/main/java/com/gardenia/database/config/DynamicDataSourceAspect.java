package com.gardenia.database.config;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * 动态数据源的切面处理类
 * @author caimeng
 * @date 2025/5/27 14:50
 */
@Component
@Slf4j
@Aspect
@Order(-100)    // 让这个切面处理的优先级高一些
public class DynamicDataSourceAspect {

    @Before("execution(* com.gardenia.database.*.qqq..*.*(..))")
    public void switchQqqDataSource() {
        DynamicRoutingDataSource.clearDataSource();
        DynamicRoutingDataSource.setDataSource(DynamicRoutingDataSource.DataSourceType.TEST_QQQ);
        log.info("切换数据源：{}", DynamicRoutingDataSource.getDataSource());
    }

    @Before("execution(* com.gardenia.database.*.sql..*.*(..))")
    public void switchSqlDataSource() {
        DynamicRoutingDataSource.clearDataSource();
        DynamicRoutingDataSource.setDataSource(DynamicRoutingDataSource.DataSourceType.TEST_SQL);
        log.info("切换数据源：{}", DynamicRoutingDataSource.getDataSource());
    }
}
