package com.example.boot3.processor.roadjava;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 使用配置初始化bean
 * @author caimeng
 * @date 2024/5/15 10:01
 */
@Configuration
public class InitializationTestConfig {
    @Bean(initMethod = "init", destroyMethod = "destroyResource")
    public InitializationTestService initializationTestServiceFromConfig() {
        return new InitializationTestService();
    }
}
