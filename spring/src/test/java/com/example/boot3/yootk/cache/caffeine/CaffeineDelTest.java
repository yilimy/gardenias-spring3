package com.example.boot3.yootk.cache.caffeine;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

/**
 * 测试Caffeine的删除功能
 * @author caimeng
 * @date 2024/7/9 18:39
 */
public class CaffeineDelTest {

    /**
     * 测试：手工删除缓存
     */
    @Test
    public void delTest() {
        Cache<String, String> cache = Caffeine.newBuilder().maximumSize(100).build();
        String key = "yootk";
        cache.put(key, "www.yootk.com");
        // 【数据删除前】yootk = www.yootk.com
        System.out.printf("【数据删除前】yootk = %s\n", cache.getIfPresent(key));
        cache.invalidate(key);
        // 【数据删除后】yootk = null
        System.out.printf("【数据删除后】yootk = %s\n", cache.getIfPresent(key));
    }

    /**
     * 测试：手工删除缓存
     * 添加删除监听
     * <p>
     *     【数据删除前】yootk = www.yootk.com
     *     【数据删除后】yootk = null
     *     【数据删除的监听】key = yootk, value = www.yootk.com, cause = EXPLICIT
     * <p>
     *     枚举的类型：
     *     清除： {@link com.github.benmanes.caffeine.cache.RemovalCause#EXPLICIT}
     *     替换： {@link com.github.benmanes.caffeine.cache.RemovalCause#REPLACED}
     *     回收： {@link com.github.benmanes.caffeine.cache.RemovalCause#COLLECTED}
     *     过期： {@link com.github.benmanes.caffeine.cache.RemovalCause#EXPIRED}
     *     尺寸： {@link com.github.benmanes.caffeine.cache.RemovalCause#SIZE}
     */
    @SneakyThrows
    @Test
    public void removeListenerTest() {
        Cache<String, String> cache = Caffeine.newBuilder()
                .maximumSize(100)
                .expireAfterAccess(2, TimeUnit.SECONDS)
                /*
                 * 所有被清除的数据清楚后，都会触发配置的删除监听器，
                 * 并且可以通过枚举类 RemovalCause 获取当前数据被清除的原因.
                 */
                .removalListener((k, v, cause) ->
                        System.out.printf("【数据删除的监听】key = %s, value = %s, cause = %s\n", k, v, cause))
                .build();
        String key = "yootk";
        cache.put(key, "www.yootk.com");
        cache.put("edu", "www.edu.com");
        // 【数据删除前】yootk = www.yootk.com
        System.out.printf("【数据删除前】yootk = %s\n", cache.getIfPresent(key));
        cache.invalidate(key);
        // 【数据删除后】yootk = null
        System.out.printf("【数据删除后】yootk = %s\n", cache.getIfPresent(key));
        TimeUnit.SECONDS.sleep(5);
        System.out.printf("【数据删除后】edu = %s\n", cache.getIfPresent("edu"));
    }
}
