package com.example.boot3.yootk.cache.caffeine;

import com.example.boot3.util.ScheduleUtil;
import com.github.benmanes.caffeine.cache.AsyncLoadingCache;
import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/**
 * 测试：异步缓存加载实现
 * {@link com.github.benmanes.caffeine.cache.AsyncCacheLoader}
 * @author caimeng
 * @date 2024/7/5 13:42
 */
@Slf4j
public class AsyncCacheTest {
    private AsyncLoadingCache<String, String> cache;
    @BeforeEach
    public void init() {
        cache = Caffeine.newBuilder()
                .maximumSize(100)
                .expireAfterAccess(3L, TimeUnit.SECONDS)
                .buildAsync((key, executor) -> CompletableFuture.supplyAsync(() -> {
                    log.info("【CacheLoader】缓存数据的加载处理, key=" + key);
                    try {
                        // 延长单个线程的加载时间，2 ---> 10
                        TimeUnit.SECONDS.sleep(10);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    return "【LoadingCache】" + key;
                }, executor));
        cache.put("yootk", CompletableFuture.completedFuture("www.yootk.com"));
    }

    /**
     * 测试：异步获取缓存数据
     * 异步是指getAll里面的每一项是异步的，但是getAll方法本身是同步的（阻塞），
     * 有点像是栅栏 {@link java.util.concurrent.CyclicBarrier}
     * 使用的线程池是 ForkJoinPool.commonPool-worker，关于 CompletableFuture 的功能，可以看Monkey老师的课程资料
     * {@link com.example.boot3.concurrent.future.CompleteFutureByMonkeyTest#supplyAsyncTest()}
     * <p>
     *     控制台打印：
     *     【未超时获取缓存数据】yootk=www.yootk.com
     *          --> 此前新增了数据 yootk2，主线程开始休眠5秒（阻塞）
     *          --> 期间缓存3秒时间到期， yootk 和 yootk2 相继失效
     *     0
     *     1
     *     2
     *     3
     *     4
     *          --> 主线程休眠结束，新增数据 yootk3 和 yootk4
     *          --> 开始执行 getAll，主线程方法（同步），内部子项获取（异步）
     *          --> 此时，yootk3 和 yootk4 是立刻获取到的，已经存放到 getAll 的结果集中
     *     【CacheLoader】缓存数据的加载处理, key=yootk
     *          --> 异步获取 yootk，异步线程阻塞10秒
     *     【CacheLoader】缓存数据的加载处理, key=yootk2
     *          --> 异步获取 yootk2，异步线程阻塞10秒
     *          --> yootk 和 yootk2 几乎是同时完成，所以主线程的阻塞时间是他俩最大耗时时间，约10秒
     *     5
     *     6
     *     7
     *     8
     *     9
     *     10
     *     11
     *     12
     *     13
     *     14
     *          --> 子线程阻塞结束，获取到 yootk 和 yootk2 的结果，追加到 getAll 结果集中
     *          --> 打印 getAll 结果集
     *     【数据加载】 key=yootk4, value=www.yootk.com++++++
     *     【数据加载】 key=yootk, value=【LoadingCache】yootk
     *     【数据加载】 key=yootk2, value=【LoadingCache】yootk2
     *     【数据加载】 key=yootk3, value=www.yootk.com++
     */
    @SneakyThrows
    @Test
    public void asyncTest() {
        // 追加缓存项
        cache.put("yootk2", CompletableFuture.completedFuture("www.yootk.com++"));
        CompletableFuture<String> yootkFuture = cache.getIfPresent("yootk");
        assert yootkFuture != null;
        log.info("【未超时获取缓存数据】yootk={}", yootkFuture.get());
        ScheduleUtil.printSeconds();
        TimeUnit.SECONDS.sleep(5);
        // 缓存失效后，继续追加新的数据项
        cache.put("yootk3", CompletableFuture.completedFuture("www.yootk.com++"));
        cache.put("yootk4", CompletableFuture.completedFuture("www.yootk.com++++++"));
        Map<String, String> allResult = cache.getAll(List.of("yootk4", "yootk", "yootk2", "yootk3")).get();
        allResult.forEach((k, v) -> System.out.printf("【数据加载】 key=%s, value=%s\n", k , v));
    }

}
