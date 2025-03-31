package com.gardenia.redis.lettuce.util;

import io.lettuce.core.RedisFuture;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.async.RedisAsyncCommands;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

/**
 * redis连接池测试
 * @author caimeng
 * @date 2025/3/31 16:49
 */
@Slf4j
public class RedisConnectionPoolUtilTest {

    @SneakyThrows
    @Test
    public void poolTest() {
        String key = "yootk:url";
        // 通过redis的连接池，获取redis连接对象的实例
        try (StatefulRedisConnection<String, String> connection = RedisConnectionPoolUtil.getConnection()) {
            RedisAsyncCommands<String, String> commands = connection.async();
            RedisFuture<String> getFuture = commands.get(key);
            log.info("【数据加载】yootk:url: {}", getFuture.get());
        }
    }
}
