/**
 * Druid数据库连接池
 * <p>
 *     com.alibaba:druid-spring-boot-starter
 *     可以通过配置文件进行自动装配
 * <p>
 *     com.alibaba:druid
 *     可以自定义数据库连接池的配置
 *     {@link com.gardenia.database.config.DruidDataSourceConfig}
 * <p>
 *     Druid监控 {@link com.gardenia.database.config.DruidMonitorConfig}
 *     URI监控 - 分布区间：[1,4,0,1,0,0,0,0]
 *          1. 0-1 毫秒内访问量
 *          2. 1-10 毫秒内访问量
 *          3. 10-100 毫秒内访问量
 *          4. 100-1000 毫秒内访问量
 *          5. 1-10 秒内访问量
 *          6. 10-100 秒内访问量
 *          7. 100-1000 秒内访问量
 *          8. > 1000 秒内访问量
 * <p>
 *     添加对SQL的监控
 *       1. 注入拦截器
 *              {@link com.gardenia.database.config.DruidMonitorConfig#sqlStatFilter(boolean, boolean, long)}
 *       2. 将拦截器配置到 DataSource 中
 *              {@link com.gardenia.database.config.DruidDataSourceConfig#getDruidDataSource(QqqDatasourceProperties, QqqDruidDataSourceWrapper, StatFilter, WallFilter)}
 * <p>
 *     SQL 防火墙
 *       1. 创建防火墙配置
 *              {@link com.gardenia.database.config.DruidMonitorConfig#sqlWallConfig()}
 *       2. 创建防火墙拦截器
 *              {@link com.gardenia.database.config.DruidMonitorConfig#sqlWallFilter()}
 *       3. 与数据源进行整合
 *              {@link com.gardenia.database.config.DruidDataSourceConfig#getDruidDataSource(QqqDatasourceProperties, QqqDruidDataSourceWrapper, StatFilter, WallFilter)}
 * @author caimeng
 * @date 2025/5/20 13:58
 */
package com.gardenia.database;

import com.alibaba.druid.filter.stat.StatFilter;
import com.alibaba.druid.wall.WallFilter;
import com.gardenia.database.config.QqqDatasourceProperties;
import com.gardenia.database.config.QqqDruidDataSourceWrapper;