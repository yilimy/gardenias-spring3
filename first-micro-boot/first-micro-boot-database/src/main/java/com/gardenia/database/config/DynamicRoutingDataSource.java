package com.gardenia.database.config;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.sql.DataSource;
import java.util.Map;

/**
 * 动态数据源决策树
 * @author caimeng
 * @date 2025/5/27 14:20
 */
public class DynamicRoutingDataSource extends AbstractRoutingDataSource {   // 数据源的动态切换
    // 每一次不同的线程请求，可能用到自己的数据源
    public static final ThreadLocal<String> DATASOURCE_CONTEXT_HOLDER = new ThreadLocal<>();
    @Override
    protected Object determineCurrentLookupKey() {  // 获取当前的查询结果，根据该结果去获取 DataSource 实例
        return getDataSource();
    }

    interface DataSourceType {
        String TEST_SQL = "test_sql";
        String TEST_QQQ = "test_qqq";
    }

    /**
     * 构建动态数据源
     * @param defaultDataSource 默认数据源
     * @param targetDataSources 全部数据源对象
     */
    public DynamicRoutingDataSource(DataSource defaultDataSource,
                                    Map<Object, Object> targetDataSources) {
        super.setDefaultTargetDataSource(defaultDataSource);
        super.setTargetDataSources(targetDataSources);
        // 属性设置，进行数据源解析等操作
        super.afterPropertiesSet();
    }

    /**
     * 扩充的数据源处理方法
     * @param dataSourceName 数据源名称
     */
    public static void setDataSource(String dataSourceName) {
        DATASOURCE_CONTEXT_HOLDER.set(dataSourceName);
    }

    /**
     * 获取当前数据源名称
     * @return 数据源名称
     */
    public static String getDataSource() {
        return DATASOURCE_CONTEXT_HOLDER.get();
    }

    /**
     * 清除当前线程中保存的数据源名称
     */
    public static void clearDataSource() {
        DATASOURCE_CONTEXT_HOLDER.remove();
    }
}
