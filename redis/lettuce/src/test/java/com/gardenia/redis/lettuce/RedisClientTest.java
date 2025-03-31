package com.gardenia.redis.lettuce;

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
    public static final String REDIS_HOST = "192.168.200.130";
    public static final int REDIS_PORT = 6389;
    public static final String REDIS_URL_USERNAME = "default";
    public static final String REDIS_URL_PASSWORD = "GmRes_prd10!";
    public static final int REDIS_URL_DATABASE = 0;
    /**
     * 使用字符串连接
     * <p>
     *     单实例规则: redis://[username]:[password]@[host]:[port]/[database][?[timeout=timeout[d|h|m|s|ms|us|ns]]]
     *     SSL 规则: rediss://[username]:[password]@[host]:[port]/[database][?[timeout=timeout[d|h|m|s|ms|us|ns]]]
     *     Socket 规则: redis-socket://[username]:[password]@[host]:[port]/[database][?[timeout=timeout[d|h|m|s|ms|us|ns]]]
     *     哨兵规则: redis://[username]:[password]@[host]:[port],[host]:[port]/[database][?[timeout=timeout[d|h|m|s|ms|us|ns]]]
     */
    public static final String REDIS_URL = "redis://default:GmRes_prd10!@192.168.200.130:6389/0";

    /**
     * 测试 redis 连接，其一
     * <p>
     *     传统的连接方式，不推荐
     */
    @Test
    public void connectionTest() {
        RedisURI redisURI = RedisURI.builder()
                .withHost(REDIS_HOST)
                .withPort(REDIS_PORT)
                .withDatabase(REDIS_URL_DATABASE)
                .build();
        RedisCredentialsProvider redisCredentialsProvider =
                new StaticCredentialsProvider(REDIS_URL_USERNAME, REDIS_URL_PASSWORD.toCharArray());
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
        try (RedisClient client = RedisClient.create(REDIS_URL)) {
            log.info("【Redis客户端实例】client={}", client);
        }
    }
}
