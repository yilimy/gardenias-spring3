package com.example.boot3.jdbc.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.AbstractDriverBasedDataSource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.sql.DriverManager;
import java.util.Properties;

/**
 * 数据库连接配置类
 * @author caimeng
 * @date 2024/5/20 15:58
 */
@Configuration
public class DataSourceConfig {
    /**
     * 这种数据源的管理是最为基础的操作结构，但是性能不高，只是可以与最早的JDBC基础概念有所衔接
     * 能成功创建连接，说明当前环境没有太大问题
     * <p>
     *     为什么性能不好？
     *     {@link AbstractDriverBasedDataSource#getConnectionFromDriver(String, String)}
     *     {@link DriverManager#getConnection(String, Properties, Class)}
     *     每一次获取连接的时候，才进行数据库连接的操作(new DataSource),
     *     数据库在连接处理之中，一定会创建若干个socket连接，会有耗时，在数据库连接关闭时也会存在有同样耗时的处理，
     *     在高并发的处理下很难得到有效的控制，
     *     所以在实际的项目中，最佳的数据库连接管理一定是通过连接池的方式实现的。
     * <p>
     *     早期的数据库连接池组件：C3P0
     *     不再维护更新，直到2019，可能的情况
     *     1. 官方标准，标准一直没更新，所以包不更新
     *     2. 优质代码，整个程序设计非常到位，不需要做出任何修正
     *     3. 作者放弃，出现了更优秀的代码, e.g. HikariCP (spring默认数据库连接池组件)
     * <p>
     *     Druid 与 HikariCP
     *     Druid在性能上可能低于 HikariCP, 但是有完善的管理界面
     * <p>
     *     HikariCP
     *     1. 字节码更精简，可以在缓存中添加更多的程序代码
     *     2. 实现了一个无锁的集合，减少了并发访问造成的资源竞争问题
     *     3. 使用了自定义的FastList替代ArrayList，提高了get和remove的操作性能
     *     4. 针对CPU的时间片算法进行了优化，尽可能在一个时间片内完成所有操作
     * @see HikariDataSourceConfig
     * @param driverClassName 加载驱动程序
     * @param url 连接路径
     * @param username 用户名
     * @param password 密码
     * @return 数据库连接对象
     */
    @Bean("dataSource")
    public DataSource dataSource(
            @Value("${spring.datasource.driverClassName:com.mysql.cj.jdbc.Driver}")
            String driverClassName,
            @Value("${spring.datasource.url:jdbc:mysql://192.168.200.130:3306/test_sql?useUnicode=true&allowMultiQuerie=true&characterEncoding=utf-8&zeroDateTimeBehavior=convertToNull&useSSL=false}")
            String url,
            @Value("${spring.datasource.username:root}")
            String username,
            @Value("${spring.datasource.password:Gm02_prd8!}")
            String password) {
        // 驱动数据源
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        // 加载驱动程序
        dataSource.setDriverClassName(driverClassName);
        // 连接路径
        dataSource.setUrl(url);
        // 用户名
        dataSource.setUsername(username);
        // 密码
        dataSource.setPassword(password);
        return dataSource;
    }
}
