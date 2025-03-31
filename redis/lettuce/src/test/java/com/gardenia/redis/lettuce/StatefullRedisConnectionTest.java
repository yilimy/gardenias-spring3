package com.gardenia.redis.lettuce;

import com.gardenia.redis.lettuce.common.RedisConstant;
import io.lettuce.core.RedisChannelHandler;
import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisConnectionStateListener;
import io.lettuce.core.RedisFuture;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.async.RedisAsyncCommands;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.net.SocketAddress;

/**
 * 测试: redis命令操作对象
 * @author caimeng
 * @date 2025/3/31 14:58
 */
@Slf4j
public class StatefullRedisConnectionTest {

    /**
     * 测试: redis命令操作对象进行异步读写操作
     */
    @SneakyThrows
    @Test
    public void commandWithAsyncRWTest() {
        try (RedisClient client = RedisClient.create(RedisConstant.REDIS_URL)) {
            log.info("【Redis客户端实例】client={}", client);
            // K: key的类型; V: value的类型
            StatefulRedisConnection<String, String> connection = client.connect();
            // 异步命令
            RedisAsyncCommands<String, String> commands = connection.async();
            String key = "yootk:url";
            // 相当于redis中的set方法
            RedisFuture<String> setFuture = commands.set(key, "yootk.com");
            // 异步处理会有中断异常
            log.info("【数据保存】命令处理的结果: {}", setFuture.get());
            RedisFuture<String> getFuture = commands.get(key);
            log.info("【数据加载】yootk:url: {}", getFuture.get());
            connection.close();
        }
    }

    @SneakyThrows
    @Test
    public void commandWithListenerTest() {
        try (RedisClient client = RedisClient.create(RedisConstant.REDIS_URL)) {
            log.info("【Redis客户端实例】client={}", client);
            StatefulRedisConnection<String, String> connection = client.connect();
            // 追加redis连接的监听器
            connection.addListener(new RedisConnectionStateListener() {
                @Override
                public void onRedisConnected(RedisChannelHandler<?, ?> connection, SocketAddress socketAddress) {
                    // 这个方法没有调用
                    log.debug("【redis连接监听】连接redis服务器");
                }

                @Override
                public void onRedisDisconnected(RedisChannelHandler<?, ?> connection) {
                    log.debug("【redis关闭监听】关闭redis连接服务");
                }

                @Override
                public void onRedisExceptionCaught(RedisChannelHandler<?, ?> connection, Throwable cause) {
                    log.error("【redis异常监听】操作出现了异常:{}", cause.getMessage());
                }
            });
            RedisAsyncCommands<String, String> commands = connection.async();
            String key = "yootk:url";
            // 如果key存在，会进行覆盖处理
            RedisFuture<String> setFuture = commands.set(key, "yootk.com");
            log.info("【数据保存】命令处理的结果: {}", setFuture.get());
            RedisFuture<String> getFuture = commands.get(key);
            log.info("【数据加载】yootk:url: {}", getFuture.get());
            connection.close();
        }
    }
}
