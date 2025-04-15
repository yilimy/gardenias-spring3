package com.gardenia.redis.spring.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;

/**
 * @author caimeng
 * @date 2025/4/2 16:23
 */
@Configuration
public class RedisTemplateConfig {

    @Bean   // 注入Redis操作模板
    // 注意，ConditionalOnMissingBean 与 ConditionalOnProperty是平级的
    @ConditionalOnMissingBean(RedisTemplate.class)
    // 为了防止加载多个bean，需要添加该默认配置
    @ConditionalOnProperty(name = "redis.serializer", havingValue = "default", matchIfMissing = true)
    public RedisTemplate<String, String> redisTemplate(
            @Autowired LettuceConnectionFactory lettuceConnectionFactory) {
        // 定义模板类
        RedisTemplate<String, String> redisTemplate = new StringRedisTemplate();
        // 设置连接工厂
        redisTemplate.setConnectionFactory(lettuceConnectionFactory);
        return redisTemplate;
    }

    /**
     * 包含有序列化的 Redis 模拟类
     * <p>
     *     因为 value 序列化成 java 对象，对应的泛型也要改
     * @param lettuceConnectionFactory 连接工厂
     * @return Redis 模拟类
     */
    @Bean
    @ConditionalOnProperty(name = "redis.serializer", havingValue = "string-java")
    public RedisTemplate<String, Object> redisTemplateWithSerializer(
            @Autowired LettuceConnectionFactory lettuceConnectionFactory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(lettuceConnectionFactory);
        /*
         * 采用字符串的方式进行key的序列化.
         * 等效于 new StringRedisSerializer()
         */
        redisTemplate.setKeySerializer(RedisSerializer.string());
        /*
         * 采用 Java 的序列化方式进行 value 的序列化.
         * 等效于 new JdkSerializationRedisSerializer()
         */
        redisTemplate.setValueSerializer(RedisSerializer.java());
        // 序列化同上
        redisTemplate.setHashKeySerializer(RedisSerializer.string());
        redisTemplate.setHashValueSerializer(RedisSerializer.java());
        return redisTemplate;
    }
}
