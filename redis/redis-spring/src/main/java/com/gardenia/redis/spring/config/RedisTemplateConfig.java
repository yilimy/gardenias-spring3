package com.gardenia.redis.spring.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * @author caimeng
 * @date 2025/4/2 16:23
 */
@Configuration
public class RedisTemplateConfig {

    @Bean   // 注入Redis操作模板
    public RedisTemplate<String, String> redisTemplate(
            @Autowired LettuceConnectionFactory lettuceConnectionFactory) {
        // 定义模板类
        RedisTemplate<String, String> redisTemplate = new StringRedisTemplate();
        // 设置连接工厂
        redisTemplate.setConnectionFactory(lettuceConnectionFactory);
        return redisTemplate;
    }
}
