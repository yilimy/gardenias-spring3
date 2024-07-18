package com.example.boot3.spring.cache;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * 项目启动类
 * @author caimeng
 * @date 2024/7/18 15:30
 */
@EnableJpaRepositories("com.example.boot3.spring.cache.dao")
@ComponentScan("com.example.boot3.spring.cache")
public class StartSpringCache {
}
