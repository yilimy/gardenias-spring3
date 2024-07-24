package com.example.boot3.spring.cache.caffeine;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * 混合的缓存管理器
 * 同职责 {@link com.example.boot3.spring.cache.config.CacheConfig}
 * @author caimeng
 * @date 2024/7/22 16:49
 */
@ConditionalOnProperty(value = "cache.type", havingValue="mix")
@Configuration
@EnableCaching
public class MixCacheConfig {
    @Bean
    public CacheManager cacheManager() {
        SimpleCacheManager simpleCacheManager = new SimpleCacheManager();
        Set<Cache> caches = new HashSet<>();
        com.github.benmanes.caffeine.cache.Cache<Object, Object> caffeineCache = Caffeine.newBuilder()
                .maximumSize(100).expireAfterAccess(3L, TimeUnit.SECONDS).build();
        caches.add(new CaffeineCache("emp", caffeineCache));
        caches.add(new ConcurrentMapCache("empMap"));
        simpleCacheManager.setCaches(caches);
        return simpleCacheManager;
    }
}
