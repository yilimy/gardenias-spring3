package com.gardenia.redis.lettuce.common;

/**
 * @author caimeng
 * @date 2025/3/31 15:01
 */
public final class RedisConstant {
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
    private RedisConstant() {}
}
