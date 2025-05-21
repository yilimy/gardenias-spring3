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
 * @author caimeng
 * @date 2025/5/20 13:58
 */
package com.gardenia.database;