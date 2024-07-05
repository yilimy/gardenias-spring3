package com.example.boot3.yootk.cache.caffeine;

import com.example.boot3.util.ScheduleUtil;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * {@link com.github.benmanes.caffeine.cache.CacheLoader}
 * @author caimeng
 * @date 2024/7/5 10:42
 */
@Slf4j
public class CaffeineLoadTest {
    private LoadingCache<String, String> cache;

    @BeforeEach
    public void init() {
        // 初始化
        cache = Caffeine.newBuilder()
                // 设置缓存中的保存的最大数据量
                .maximumSize(100)
                // 如无访问，则3秒后失效
                .expireAfterAccess(3L, TimeUnit.SECONDS)
                // 构建Cache实例
                .build(key -> {
                    log.info("【CacheLoader】缓存数据的加载处理, key=" + key);
                    // 进行数据的加载，可以来自于 Redis or DB or 其他数据源
                    // 模拟数据的加载延迟
                    TimeUnit.SECONDS.sleep(2);
                    return "【LoadingCache】" + key;
                });
        // 第一次设定值
        cache.put("yootk", "www.yootk.com");
    }

    @SneakyThrows
    @Test
    public void loadTest() {
        /*
         * 第一次获取
         * 【未超时获取缓存数据】yootk=www.yootk.com
         */
        log.info("【未超时获取缓存数据】yootk={}", cache.getIfPresent("yootk"));
        ScheduleUtil.printSeconds();
        // 设置超时时间
        TimeUnit.SECONDS.sleep(5);
        /*
         * 第二次获取（已超时）
         * 【超时获取缓存数据】yootk=null
         *
         * 此时已经实现了 CacheLoader 接口实例的创建，但是在执行最终执行完成后并没有返回所需要的数据
         *      数据加载中休眠了2秒，而接口返回是即时的，因此返回结果是null
         * so,如何触发数据加载 or 同步加载 or 串行加载？
         * 去使用Cache中的getAll方法
         */
        log.info("【超时获取缓存数据】yootk={}", cache.getIfPresent("yootk"));
    }

    /**
     * 测试getAll
     * <p>
     *     日志结果：
     *     【未超时获取缓存数据】yootk=www.yootk.com
     *     0
     *     1
     *     2
     *     3
     *     4
     *     【CacheLoader】缓存数据的加载处理, key=yootk
     *     5
     *     6
     *     【CacheLoader】缓存数据的加载处理, key=yootk2
     *     7
     *     8
     *     【CacheLoader】缓存数据的加载处理, key=yootk3
     *     9
     *     10
     *     【数据加载】 key=yootk4, value=www.yootk.com++++++
     *     【数据加载】 key=yootk, value=【LoadingCache】yootk
     *     【数据加载】 key=yootk2, value=【LoadingCache】yootk2
     *     【数据加载】 key=yootk3, value=【LoadingCache】yootk3
     * <p>
     *     说明：取值为串行取值
     */
    @SneakyThrows
    @Test
    public void getAllTest() {
        cache.put("yootk2", "www.yootk.com++");
        log.info("【未超时获取缓存数据】yootk={}", cache.getIfPresent("yootk"));
        ScheduleUtil.printSeconds();
        TimeUnit.SECONDS.sleep(5);
        // 缓存失效后，继续追加新的数据项
        cache.put("yootk3", "www.yootk.com++");
        cache.put("yootk4", "www.yootk.com++++++");
        /*
         * 串行取值
         * yootk4 是新入数据，直接读取到
         * 虽然 yootk3 也是新入数据，由于 CacheLoader 读取其前面每个失效key的2秒延迟，到真正读取 yootk3 时，该key也失效了，故也走 CacheLoader 取值
         * 已取到的值会在一个集合中等待返回
         */
        Map<String, String> allResult = cache.getAll(List.of("yootk4", "yootk", "yootk2", "yootk3"));
        allResult.forEach((k, v) -> System.out.printf("【数据加载】 key=%s, value=%s\n", k , v));
    }
}
