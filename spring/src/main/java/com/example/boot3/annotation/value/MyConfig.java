package com.example.boot3.annotation.value;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * @author caimeng
 * @date 2024/7/19 10:23
 */
@Configuration
@ComponentScan
@PropertySource("classpath:application.properties")
public class MyConfig {
    @Bean
    public String version() {
        return "2.0.0";
    }

    @Bean
    public User user() {
        User user = new User();
        user.setName("123");
        return user;
    }
}
