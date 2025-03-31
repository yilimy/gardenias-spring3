package com.gardenia.redis.lettuce.util;

import com.gardenia.redis.lettuce.common.RedisConstant;
import io.lettuce.core.RedisClient;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.support.ConnectionPoolSupport;
import lombok.SneakyThrows;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

/**
 * redis连接池工具类
 * @author caimeng
 * @date 2025/3/31 16:35
 */
public final class RedisConnectionPoolUtil {
    /**
     * 最大空闲连接数
     */
    private static final int MAX_IDLE = 10;
    /**
     * 最小空闲连接数
     */
    public static final int MIN_IDLE = 2;
    /**
     * 最大连接数
     */
    public static final int MAX_TOTAL = 100;
    // 定义本次要使用的对象连接池
    public static GenericObjectPool<StatefulRedisConnection<String, String>> pool = null;

    private RedisConnectionPoolUtil() {}

    static {
        buildObjectPool();
    }

    // 构建连接池
    public static void buildObjectPool() {
        //noinspection resource
        RedisClient client = RedisClient.create(RedisConstant.REDIS_URL);
        // 配置连接池
        GenericObjectPoolConfig<StatefulRedisConnection<String, String>> config = new GenericObjectPoolConfig<>();
        config.setMaxIdle(MAX_IDLE);
        config.setMinIdle(MIN_IDLE);
        config.setMaxTotal(MAX_TOTAL);
        pool = ConnectionPoolSupport.createGenericObjectPool(client::connect, config);
    }

    /**
     * 获取连接
     * @return 连接对象
     */
    @SneakyThrows
    public static StatefulRedisConnection<String, String> getConnection() {
        return pool.borrowObject();
    }
}
