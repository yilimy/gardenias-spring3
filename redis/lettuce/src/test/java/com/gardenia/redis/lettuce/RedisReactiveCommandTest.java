package com.gardenia.redis.lettuce;

import com.gardenia.redis.lettuce.util.RedisConnectionPoolUtil;
import io.lettuce.core.ScanArgs;
import io.lettuce.core.ScanStream;
import io.lettuce.core.api.reactive.RedisReactiveCommands;
import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

/**
 * 测试: redis响应式开发
 * @author caimeng
 * @date 2025/4/1 14:53
 */
public class RedisReactiveCommandTest {

    /**
     * 测试：响应式编程的单值设置
     * <p>
     *     响应式编程WebFlux中的
     *     {@link reactor.core.publisher.Mono} 指单数据操作
     *     {@link reactor.core.publisher.Flux} 指多数据操作
     */
    @Test
    public void connectTest() {
        RedisConnectionPoolUtil.connectAndDo(connection -> {
            // 打开一个响应式的连接
            RedisReactiveCommands<String, String> commands = connection.reactive();
            commands.set("yootk", "yootk.com")
                    .doOnSuccess(aVoid -> System.out.println("设置成功"))
                    .doOnError(throwable -> System.out.println("设置失败"))
                    // 【响应式处理】Redis数据存储结果：OK
                    .subscribe(result -> System.out.println("【响应式处理】Redis数据存储结果：" + result));
            sleep(2);
        });
    }

    /**
     * 响应式编程，执行循环扫描
     * <p>
     *     当应用模型中存在大量的数据，需要频繁扫描数据，那么可以使用响应式编程，同时设置了返回数量的限制，避免了大范围key所带来的性能问题。
     */
    @Test
    public void fluxTest() {
        RedisConnectionPoolUtil.connectAndDo(connection -> {
            RedisReactiveCommands<String, String> commands = connection.reactive();
            // 循环扫描
            //noinspection InfiniteLoopStatement
            while (true) {
                ScanStream
                        .scan(commands, ScanArgs.Builder
                                // 每次返回一个定长的数据
                                .limit(200)
                                .match("*yootk*"))
                        .filter(key -> key.contains("yootk"))
                        .doOnNext(key -> System.out.println("【数据Key扫描】key=" + key))
                        .subscribe();
                sleep(5);
            }
        });
    }

    private static void sleep(int seconds) {
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
