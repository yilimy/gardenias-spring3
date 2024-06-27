package com.example.boot3.datajpa;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * DataJPA应用启动类
 * <p>
 *     注解 @EnableJpaRepositories 直接决定了数据层代码的简化
 * @author caimeng
 * @date 2024/6/27 16:43
 */
@EnableJpaRepositories("com.example.boot3.datajpa.dao")
@ComponentScan("com.example.boot3.datajpa")
public class StartSpringDataJPA {
}
