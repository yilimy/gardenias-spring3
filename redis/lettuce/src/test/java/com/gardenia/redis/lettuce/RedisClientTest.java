package com.gardenia.redis.lettuce;

import com.gardenia.redis.lettuce.common.RedisConstant;
import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisCredentialsProvider;
import io.lettuce.core.RedisURI;
import io.lettuce.core.StaticCredentialsProvider;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

/**
 * @author caimeng
 * @date 2025/3/31 14:13
 */
@Slf4j
public class RedisClientTest {


    /**
     * 测试 redis 连接，其一
     * <p>
     *     传统的连接方式，不推荐
     */
    @Test
    public void connectionTest() {
        RedisURI redisURI = RedisURI.builder()
                .withHost(RedisConstant.REDIS_HOST)
                .withPort(RedisConstant.REDIS_PORT)
                .withDatabase(RedisConstant.REDIS_URL_DATABASE)
                .build();
        RedisCredentialsProvider redisCredentialsProvider = new StaticCredentialsProvider(
                RedisConstant.REDIS_URL_USERNAME, RedisConstant.REDIS_URL_PASSWORD.toCharArray());
        redisURI.setCredentialsProvider(redisCredentialsProvider);
        try (RedisClient client = RedisClient.create(redisURI)) {
            log.info("【Redis客户端实例】client={}", client);
        }
    }

    /**
     * 测试 redis 连接，其二
     * <p>
     *     推荐
     */
    @Test
    public void connectionWithURLTest() {
        try (RedisClient client = RedisClient.create(RedisConstant.REDIS_URL)) {
            log.info("【Redis客户端实例】client={}", client);
        }
    }
}
