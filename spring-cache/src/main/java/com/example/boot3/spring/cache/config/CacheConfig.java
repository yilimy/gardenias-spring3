package com.example.boot3.spring.cache.config;

import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashSet;
import java.util.Set;

/**
 * 缓存配置类
 * @author caimeng
 * @date 2024/7/18 18:32
 */
// 配置类
@Configuration
// 当前应用要开启缓存
@EnableCaching
public class CacheConfig {
    @Bean
    public CacheManager cacheManager() {
        // 获取缓存管理接口实例
        SimpleCacheManager simpleCacheManager = new SimpleCacheManager();
        /*
         * 此时在应用的环境里面，Cache属于一个常见的单词，
         * 所以只要引入了各种不同的依赖库，那么都可能存在有同名不同包的Cache
         */
        Set<Cache> caches = new HashSet<>();
        // 创建一个雇员缓存
        caches.add(new ConcurrentMapCache("emp"));
        // 创建一个部门缓存
        caches.add(new ConcurrentMapCache("dept"));
        // 创建一个薪资缓存
        caches.add(new ConcurrentMapCache("sal"));
        // 将缓存放置到缓存管理器中
        simpleCacheManager.setCaches(caches);
        return simpleCacheManager;
    }
}
