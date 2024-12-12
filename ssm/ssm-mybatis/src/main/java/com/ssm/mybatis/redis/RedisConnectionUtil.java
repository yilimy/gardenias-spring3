package com.ssm.mybatis.redis;

import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.codec.ByteArrayCodec;
import io.lettuce.core.support.ConnectionPoolSupport;
import lombok.SneakyThrows;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

/**
 * redis连接管理类
 * @author caimeng
 * @date 2024/12/11 17:58
 */
public class RedisConnectionUtil {
    /**
     * 最大维持连接数量
     */
    public static final int MAX_IDLE = 10;
    /**
     * 最小维持连接数量
     */
    public static final int MIN_IDLE = 1;
    /**
     * 最大可用连接数量
     */
    public static final int MAX_TOTAL = 20;
    /**
     * 测试后结果返回，相当于给一个可靠的连接
     */
    public static final boolean TEST_ON_BORROW = true;
    /**
     * 定义一个Apache对象池，
     * 这个对象池要保存若干个连接。
     * 如果是用 lettuce, 保存的对象是 StatefulConnection，采用字节的方式进行保存
     */
    private final static GenericObjectPool<StatefulRedisConnection<byte[], byte[]>> objectPool;
    /**
     * 创建redis连接对象
     */
    public static final RedisClient REDIS_CLIENT = RedisClient.create(
            RedisURI.builder()
                    .withHost("192.168.200.130")
                    .withPort(6379)
                    .withPassword(new StringBuilder("GmRes_prd10!"))
                    .withDatabase(0)
                    .build());
    /**
     * 连接管理
     * 考虑线程的重复获取问题
     */
    public static final ThreadLocal<StatefulRedisConnection<byte[], byte[]>> REDIS_CONNECTION_THREAD_LOCAL = new ThreadLocal<>();

    static {
        // 对象池配置了类
        GenericObjectPoolConfig<StatefulRedisConnection<byte[], byte[]>> config = new GenericObjectPoolConfig<>();
        config.setMaxIdle(MAX_IDLE);
        config.setMinIdle(MIN_IDLE);
        config.setMaxTotal(MAX_TOTAL);
        config.setTestOnBorrow(TEST_ON_BORROW);
        objectPool = ConnectionPoolSupport.createGenericObjectPool(
                () -> REDIS_CLIENT.connect(new ByteArrayCodec()), config);
    }

    /**
     * @return 获取连接
     */
    public static StatefulRedisConnection<byte[], byte[]> getConnection() {
        StatefulRedisConnection<byte[], byte[]> connection = REDIS_CONNECTION_THREAD_LOCAL.get();
        if (connection == null) {
            // 没有连接，进行创建
            connection = builder();
            // 留给后续使用
            REDIS_CONNECTION_THREAD_LOCAL.set(connection);
        }
        return connection;
    }

    @SneakyThrows
    private static StatefulRedisConnection<byte[], byte[]> builder() {
        // 获取连接池的连接
        return objectPool.borrowObject();
    }

    /**
     * 关闭线程中的redis连接
     */
    public static void close() {
        StatefulRedisConnection<byte[], byte[]> connection = REDIS_CONNECTION_THREAD_LOCAL.get();
        if (connection != null) {
            // 当前线程存在有连接，则关闭
            connection.close();
            REDIS_CONNECTION_THREAD_LOCAL.remove();
        }
    }
}
