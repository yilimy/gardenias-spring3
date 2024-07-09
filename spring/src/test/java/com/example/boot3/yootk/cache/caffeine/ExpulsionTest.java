package com.example.boot3.yootk.cache.caffeine;

import com.github.benmanes.caffeine.cache.AsyncCache;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.Expiry;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.index.qual.NonNegative;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/**
 * 测试：驱逐策略
 * @author caimeng
 * @date 2024/7/5 14:42
 */
@Slf4j
public class ExpulsionTest {

    /**
     * 测试：依据数量的驱逐策略
     */
    @SneakyThrows
    @Test
    public void maximumSizeTest() {
        Cache<String, String> cache = Caffeine.newBuilder()
                .maximumSize(1)
                .expireAfterAccess(3L, TimeUnit.SECONDS)
                .build();
        cache.put("yootk", "www.yootk.com");
        cache.put("edu", "www.yootk.com");
        /*
         * 缓存清除需要时间，主线程阻塞一下
         * 如果不加阻塞，缓存数据虽然超过了最大限制，但仍然可以获得到
         */
        TimeUnit.MILLISECONDS.sleep(500);
        // 【未超时获取缓存数据】yootk=null
        log.info("【未超时获取缓存数据】yootk={}", cache.getIfPresent("yootk"));
        // 【未超时获取缓存数据】edu=www.yootk.com
        log.info("【未超时获取缓存数据】edu={}", cache.getIfPresent("edu"));
    }

    /**
     * 测试：依据权重的驱逐策略
     */
    @SneakyThrows
    @Test
    public void weightTest() {
        Cache<String, String> cache = Caffeine.newBuilder()
//                .maximumSize(50)
                /*
                 * 设置缓存中的最大权重
                 * 该设置与 maximumSize 不能同时设置。因为个数的算法与权重的算法是两个不同的方式，二选一
                 */
                .maximumWeight(100)
                // 自定义的权重计算
                .weigher((k, v) -> {
                    log.info("【Weigher权重计算器】key={}, value={}", k, v);
                    // 测试：设置权重非常高，比如50，那么存两个数据就满了
                    return 50;
                })
                .expireAfterAccess(3L, TimeUnit.SECONDS)
                .build();
        cache.put("yootk", "www.yootk.com");
        cache.put("edu", "www.yootk.com");
        cache.put("book", "Caffeine开发实战");
        cache.put("author", "Jea");
        TimeUnit.MILLISECONDS.sleep(500);
        /*
         * 【未超时获取缓存数据】yootk=null
         * 【未超时获取缓存数据】edu=null
         * 【未超时获取缓存数据】book=Caffeine开发实战
         * 【未超时获取缓存数据】author=Jea
         */
        log.info("【未超时获取缓存数据】yootk={}", cache.getIfPresent("yootk"));
        log.info("【未超时获取缓存数据】edu={}", cache.getIfPresent("edu"));
        log.info("【未超时获取缓存数据】book={}", cache.getIfPresent("book"));
        log.info("【未超时获取缓存数据】author={}", cache.getIfPresent("author"));
    }

    /**
     * 测试：
     * 之前一直使用的是读的时间，这里考虑使用写的时间来测试。
     * 在进行驱逐的时候，对于时间的管理有两种：
     *      - 通过最后一次读的方式进行配置（这是种常见的模式）
     *      - 通过写的时间进行计数
     */
    @SneakyThrows
    @Test
    public void timeoutTest() {
        Cache<String, String> cache = Caffeine.newBuilder()
                .maximumSize(50)
                // 写入后的两秒失效
                .expireAfterWrite(2L, TimeUnit.SECONDS)
                .build();
        // 写入2秒后开始计时
        cache.put("yootk", "www.yootk.com");
        /*
         * 18:01:18.467【数据访问:0】value=www.yootk.com
         * 18:01:19.982【数据访问:1】value=null
         * 18:01:21.494【数据访问:2】value=null
         */
        for (int i = 0; i < 3; i++) {
            // 休眠1.5秒
            TimeUnit.MILLISECONDS.sleep(1500);
            log.info("【数据访问:{}】value={}", i, cache.getIfPresent("yootk"));
        }
    }

    Cache<String, String> cacheExpiry;
    @BeforeEach
    @Tag("Expiry")
    public void definedExpiry() {
        cacheExpiry = Caffeine.newBuilder()
                .maximumSize(50)
                // 写入后的两秒失效
                .expireAfter(new Expiry<String, String>() {
                    @Override
                    public long expireAfterCreate(String key, String value, long currentTime) {
                        log.info("【创建后失效计算】, key={}, value={}", key, value);
                        /*
                         * 必须是纳秒（nanoseconds），看接口注释
                         * convert 从单位秒中转换: 将2s转换成纳秒的值
                         */
                        return TimeUnit.NANOSECONDS.convert(2, TimeUnit.SECONDS);
                    }

                    @Override
                    public long expireAfterUpdate(String key, String value, long currentTime, @NonNegative long currentDuration) {
                        log.info("【更新后失效计算】, key={}, value={}", key, value);
                        return TimeUnit.NANOSECONDS.convert(6, TimeUnit.SECONDS);
                    }

                    @Override
                    public long expireAfterRead(String key, String value, long currentTime, @NonNegative long currentDuration) {
                        log.info("【读取后失效计算】, key={}, value={}", key, value);
                        return TimeUnit.NANOSECONDS.convert(3, TimeUnit.SECONDS);
                    }
                })
                .build();
    }

    /**
     * 测试：定制化的缓存清除策略
     * {@link Caffeine#expireAfter(Expiry)}
     */
    @SneakyThrows
    @Test
    @Tag("Expiry")
    public void definedTest() {

        String key = "yootk";
        cacheExpiry.put(key, "www.yootk.com");
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                log.info("【数据访问】, key={}, value={}", key, cacheExpiry.getIfPresent(key));
            }
        }, 0L, 1500L);
        /*
         * 【创建后失效计算】, key=yootk, value=www.yootk.com
         * 【读取后失效计算】, key=yootk, value=www.yootk.com
         * 【数据访问】, key=yootk, value=www.yootk.com
         * 【读取后失效计算】, key=yootk, value=www.yootk.com
         * 【数据访问】, key=yootk, value=www.yootk.com
         * 【读取后失效计算】, key=yootk, value=www.yootk.com
         * 【数据访问】, key=yootk, value=www.yootk.com
         *
         * 说明：只要3秒内读取数据，缓存就不会失效
         */
        TimeUnit.SECONDS.sleep(Integer.MAX_VALUE);
    }

    /**
     * 测试：定制化的缓存清除策略，
     * 超时的情况
     */
    @SneakyThrows
    @Test
    @Tag("Expiry")
    public void definedTimeOutTest() {
        String key = "yootk";
        cacheExpiry.put(key, "www.yootk.com");
        /*
         * 休眠，超时
         * 创建后超时是2秒，读取超时是3秒
         */
        TimeUnit.SECONDS.sleep(3);
        /*
         * 【创建后失效计算】, key=yootk, value=www.yootk.com
         * 【数据访问】, key=yootk, value=null
         */
        log.info("【数据访问】, key={}, value={}", key, cacheExpiry.getIfPresent(key));
    }

    /**
     * 测试：使用JVM中提供的数据驱逐操作
     */
    @SneakyThrows
    @Test
    public void JVMProviderTest() {
        Cache<Object, Object> cache = Caffeine.newBuilder()
                .maximumSize(100)
                // 弱引用的Key
                .weakKeys()
                // 弱引用的Value
                .weakValues()
                .build();
        // 强引用
//        String key = "yootk";
        // 模拟弱引用
        String key = "yootk";
        String value = new String("www.yootk.com");
        cache.put(key, value);
        log.info("【GC调用前】, key={}, value={}", key, cache.getIfPresent(key));
        /*
         * 清空引用
         * 如果强制清空引用，第二次还是能获取到值
         */
        value = null;
        /*
         * 强制执行Full-GC
         * 注意：正常代码中绝对不要调用该方法
         */
        Runtime.getRuntime().gc();
        // 因为不是立刻执行回收，所以休眠一小会儿
        TimeUnit.MILLISECONDS.sleep(100);
        /*
         * 不能用key，否则就是强引用了
         * 【GC调用前】, key=yootk, value=www.yootk.com
         * 【GC调用后】, key=yootk, value=null
         */
        log.info("【GC调用后】, key={}, value={}", key, cache.getIfPresent(key));
    }

    /**
     * 测试：异步缓存 + 弱引用
     * 基于弱引用或者软引用的数据驱逐处理形式，是不支持异步缓存之中使用的
     */
    @SneakyThrows
    @Test
    public void asyncAndWeakTest() {
        AsyncCache<Object, Object> cache = Caffeine.newBuilder()
                .maximumSize(100)
                // 弱引用的Key
                .weakKeys()
                // 弱引用的Value
                .weakValues()
                /*
                 * 异步缓存不支持GC的操作过程。会报错java.lang.IllegalStateException:
                 *      Weak or soft values can not be combined with AsyncCache
                 */
                .buildAsync();
        String key = new String("yootk");
        String value = new String("www.yootk.com");
        cache.put(key, CompletableFuture.completedFuture(value));
        value = null;

        Runtime.getRuntime().gc();
        TimeUnit.MILLISECONDS.sleep(100);
        log.info("【GC调用后】, key={}, value={}", key, cache.getIfPresent(key));
    }
}
