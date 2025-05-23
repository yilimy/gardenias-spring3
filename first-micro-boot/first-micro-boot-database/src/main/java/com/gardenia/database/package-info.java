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
 *              {@link com.gardenia.database.config.DruidDataSourceConfig#getDruidDataSource(QqqDatasourceProperties, QqqDruidDataSourceWrapper, StatFilter, WallFilter, Slf4jLogFilter)}
 * <p>
 *     SQL 防火墙
 *       1. 创建防火墙配置
 *              {@link com.gardenia.database.config.DruidMonitorConfig#sqlWallConfig()}
 *       2. 创建防火墙拦截器
 *              {@link com.gardenia.database.config.DruidMonitorConfig#sqlWallFilter()}
 *       3. 与数据源进行整合
 *              {@link com.gardenia.database.config.DruidDataSourceConfig#getDruidDataSource(QqqDatasourceProperties, QqqDruidDataSourceWrapper, StatFilter, WallFilter, Slf4jLogFilter)}
 * <p>
 *     Spring集成 Druid 的监控，需要靠 AOP 来实现
 *       1. 添加依赖
 *          org.springframework.boot:spring-boot-starter-aop
 *       2. 新增切面配置
 *              a. 拦截器
 *                  {@link com.gardenia.database.config.DruidSpringConfig#druidStatInterceptor()}
 *              b. 正则匹配切面
 *                  {@link com.gardenia.database.config.DruidSpringConfig#druidStatPointcut()}
 *              c. Advisor
 *                  {@link com.gardenia.database.config.DruidSpringConfig#druidStatAdvisor()}
 * <p>
 *     开启Druid的日志
 *     1. 新增过滤器
 *          {@link com.gardenia.database.config.DruidLogConfig#slf4jLogFilter()}
 *     2. DataSource中配置过滤器
 *          {@link com.gardenia.database.config.DruidDataSourceConfig#getDruidDataSource(QqqDatasourceProperties, QqqDruidDataSourceWrapper, StatFilter, WallFilter, Slf4jLogFilter)}
 *     3. 创建 logback-spring.xml 文件
 * <p>
 *     整合 mybatis-spring
 *     1. 导入依赖
 *          org.mybatis:mybatis
 *          org.mybatis.spring.boot:mybatis-spring-boot-starter
 *     2. 创建 mybatis.cfg.xml 文件
 *          META-INF/mybatis/mybatis.cfg.xml
 *     3. application.yml 中配置 mybatis
 *     4. 创建 mybatis-mapper.xml 文件
 *          META-INF/mybatis/mapper/MemberMapper.xml
 *     5. 创建 DAO 接口
 *          {@link com.gardenia.database.dao.IMemberDAO}
 *     注意：
 *      如果你使用的是 Spring Boot 3.x，请使用 3.0.x，
 *          3.0.x 中，DruidDataSource 的 init 方法被删除了，请使用 initMethod="init"；
 *      如果是 Spring Boot 2.x，请使用 2.3.x。
 * @author caimeng
 * @date 2025/5/20 13:58
 */
package com.gardenia.database;

import com.alibaba.druid.filter.logging.Slf4jLogFilter;
import com.alibaba.druid.filter.stat.StatFilter;
import com.alibaba.druid.wall.WallFilter;
import com.gardenia.database.config.QqqDatasourceProperties;
import com.gardenia.database.config.QqqDruidDataSourceWrapper;