package com.example.boot3.spring.cache.caffeine;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;
import java.util.concurrent.TimeUnit;

/**
 * 同职责 {@link com.example.boot3.spring.cache.config.CacheConfig}
 * @author caimeng
 * @date 2024/7/22 16:49
 */
@ConditionalOnProperty(value = "cache.type", havingValue="caffeine")
@Configuration
@EnableCaching
public class CaffeineCacheConfig {
    @Bean
    public CacheManager cacheManager() {
        // Requires Caffeine 2.1 or higher.
        CaffeineCacheManager caffeineCacheManager = new CaffeineCacheManager();
        Caffeine<Object, Object> caffeine = Caffeine.newBuilder().maximumSize(100).expireAfterAccess(3L, TimeUnit.SECONDS);
        // 设置缓存实现
        caffeineCacheManager.setCaffeine(caffeine);
        // 设置缓存名称
        caffeineCacheManager.setCacheNames(Collections.singleton("emp"));
        return caffeineCacheManager;
    }
}
