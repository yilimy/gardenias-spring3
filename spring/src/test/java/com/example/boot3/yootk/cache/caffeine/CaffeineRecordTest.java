package com.example.boot3.yootk.cache.caffeine;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.stats.CacheStats;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

/**
 * 测试：Caffeine的统计功能
 * @author caimeng
 * @date 2024/7/10 18:46
 */
public class CaffeineRecordTest {

    /**
     * 测试：caffeine统计
     */
    @SneakyThrows
    @Test
    public void recordTest() {
        Cache<String, String> cache = Caffeine.newBuilder()
                .recordStats()
                .expireAfterAccess(1, TimeUnit.SECONDS)
                .maximumSize(100)
                .build();
        cache.put("yootk", "www.yootk.com");
        cache.put("edu", "www.edu.com");
        cache.put("book", "www.book.com");
        /*
         * 此时设置的候选key有些是不存在的，用于模拟非命中
         */
        final String[] keys = {"yootk", "edu", "book", "lee", "happy"};
        CompletableFuture<?>[] completableFutures = Stream.generate(() -> new Random().nextInt(keys.length))
                .limit(1000)
                .map(position -> keys[position])
                .map(key -> CompletableFuture.supplyAsync(
                        () -> System.out.printf("【查询】key=%s, value=%s\n", key, cache.getIfPresent(key)),
                        // 延迟10毫秒，用于测试驱逐
                        CompletableFuture.delayedExecutor(500, TimeUnit.MILLISECONDS)))
                .toArray(CompletableFuture[]::new);
        CompletableFuture.allOf(completableFutures).join();
        // 执行太快，驱逐策略可能没生效，加个阻塞试试看
        TimeUnit.SECONDS.sleep(1);
        // 获取统计数据
        CacheStats stats = cache.stats();
        // 【CacheStats】缓存请求次数：1000
        System.out.printf("【CacheStats】缓存请求次数：%s\n", stats.requestCount());
        // 【CacheStats】缓存命中次数：606
        System.out.printf("【CacheStats】缓存命中次数：%s\n", stats.hitCount());
        // 【CacheStats】缓存未命中次数：394
        System.out.printf("【CacheStats】缓存未命中次数：%s\n", stats.missCount());
        // 评价一个缓存好坏的重要指标：命中率
        // 【CacheStats】缓存未命中率：0.394
        System.out.printf("【CacheStats】缓存未命中率：%s\n", stats.missRate());
        // 【CacheStats】缓存命中率：0.606
        System.out.printf("【CacheStats】缓存未命中率：%s\n", stats.hitRate());
        // 【CacheStats】缓存驱逐次数：0
        System.out.printf("【CacheStats】缓存驱逐次数：%s\n", stats.evictionCount());
    }
}
