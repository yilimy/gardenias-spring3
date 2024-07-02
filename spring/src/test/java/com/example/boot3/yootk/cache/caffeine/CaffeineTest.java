package com.example.boot3.yootk.cache.caffeine;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

/**
 * @author caimeng
 * @date 2024/7/2 11:51
 */
@Slf4j
public class CaffeineTest {

    @SneakyThrows
    @Test
    public void cacheTest() {
        Cache<String, String> cache = Caffeine.newBuilder()
                // 设置缓存中的保存的最大数据量
                .maximumSize(100)
                // 如无访问，则3秒后失效
                .expireAfterAccess(3L, TimeUnit.SECONDS)
                // 构建Cache实例
                .build();
        cache.put("yootk", "www.yootk.com");
        cache.put("edu", "www.yootk.com");
        cache.put("book", "Spring开发实战");
        /*
         * 获取数据
         * 【未超时获取缓存数据】yootk=www.yootk.com
         */
        log.info("【未超时获取缓存数据】yootk={}", cache.getIfPresent("yootk"));
        // 设置时间去超时
        TimeUnit.SECONDS.sleep(5);
        // 【已超时获取缓存数据】yootk=null
        log.info("【已超时获取缓存数据】yootk={}", cache.getIfPresent("yootk"));
        System.out.println("-------------- 测试数据控制 --------------");
        /*
         * 缓存数据失效之后，可以通过get的函数式接口，加载所需要的数据内容，而这个加载的操作属于同步的操作范畴。
         * 加载不停，数据是不会返回的
         */
        log.info("【已超时获取缓存数据】yootk={}", cache.get("yootk", key -> {
            log.info("【已超时获取缓存数据】没有发现 key={} 的数据，要进行数据失效控制。", key);
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            // 失效数据返回
            return "【EXPIRE】" + key;
        }));
    }
}
