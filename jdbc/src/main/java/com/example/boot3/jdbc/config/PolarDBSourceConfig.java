package com.example.boot3.jdbc.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

/**
 * 数据库连接配置类
 * @author caimeng
 * @date 2024/5/20 15:58
 */
@Configuration
public class PolarDBSourceConfig {

    @Bean("polarDBSource")
    public DataSource dataSource(
            @Value("${spring.datasource.driverClassName:com.mysql.cj.jdbc.Driver}")
            String driverClassName,
            @Value("${spring.datasource.url:jdbc:mysql://pxc-bjr42yfxxgxkkz-pub.polarx.rds.aliyuncs.com:3306/mysql?useUnicode=true&allowMultiQuerie=true&characterEncoding=utf-8&zeroDateTimeBehavior=convertToNull&useSSL=false}")
            String url,
            @Value("${spring.datasource.username:test1}")
            String username,
            @Value("${spring.datasource.password:123456Aa!}")
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
