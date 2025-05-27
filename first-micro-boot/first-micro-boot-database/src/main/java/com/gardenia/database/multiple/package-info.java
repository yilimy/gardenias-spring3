/**
 * 多数据源操作
 * <p>
 *     1. 准备数据表
 *          dept_multiple
 *          emp_multiple
 *     2. 使用数据源starter
 *          com.alibaba:druid-spring-boot-starter
 *     3. 禁用掉自动Bean注入
 *          {@link com.gardenia.database.config.DruidDataSourceConfig}
 *          {@link com.gardenia.database.config.DruidLogConfig}
 *          {@link com.gardenia.database.config.DruidMonitorConfig}
 *          {@link com.gardenia.database.config.DruidSpringConfig}
 *          实际上只要配置 spring.test-qqq.datasource.enabled=false 就行了
 *     4. 修改application.yml追加数据源配置项
 *          spring.datasource.test-sql
 *          spring.datasource.test-qqq
 *     5. 根据前缀配置多数据源
 *          {@link com.gardenia.database.config.DruidMultiDataSourceConfig#testSqlDataSource()}
 *          {@link com.gardenia.database.config.DruidMultiDataSourceConfig#testQqqDataSource()}
 *     6. 测试
 *          com.gardenia.database.config.DruidMultiDataSourceConfigTest
 *     7. 借助 AbstractRoutingDataSource 进行动态切换数据源，创建数据源决策树的子类
 *          {@link org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource}
 *          {@link com.gardenia.database.config.DynamicRoutingDataSource}
 *     8. 创建动态数据源配置
 *          {@link com.gardenia.database.config.DynamicDataSourceConfig}
 *     9. 动态切换条件配置，使用AOP类进行处理
 *          {@link com.gardenia.database.config.DynamicDataSourceAspect}
 *
 * 切换数据源的核心是将自定义的决策树作为获取数据源的工厂，对外提供数据源的对象
 * {@link com.gardenia.database.config.DynamicDataSourceConfig#getDataSource(DataSource, DataSource)}
 *
 * @author caimeng
 * @date 2025/5/27 10:32
 */
package com.gardenia.database.multiple;

import javax.sql.DataSource;