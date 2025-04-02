package com.gardenia.redis.spring.config;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration;

import java.time.Duration;

/**
 * Redis连接配置
 * <p>
 *     分为以下几步:
 *        1. 配置redis连接信息
 *        2. 配置连接池信息
 *        3. 配置Lettuce客户端
 *        4. 配置Lettuce连接工厂
 * @author caimeng
 * @date 2025/4/2 14:35
 */
@Configuration
@PropertySource("classpath:config/redis.properties")    // 定义资源文件的路径
public class SpringDataRedisConfig {

    @Bean   // redis连接配置（标准独立配置信息）
    public RedisStandaloneConfiguration redisStandaloneConfiguration(
            @Value("${redis.host}") String host,
            @Value("${redis.port}") int port,
            @Value("${redis.username}") String username,
            @Value("${redis.database}") int database,
            @Value("${redis.password}") String password
    ) {
        RedisStandaloneConfiguration redisConfig = new RedisStandaloneConfiguration();
        redisConfig.setHostName(host);
        redisConfig.setPort(port);
        redisConfig.setUsername(username);
        redisConfig.setDatabase(database);
        redisConfig.setPassword(password);
        return redisConfig;
    }

    @Bean   // 连接池配置
    public GenericObjectPoolConfig<?> redisPoolConfig(
            @Value("${redis.pool.max-total}") int maxTotal,
            @Value("${redis.pool.max-idle}") int maxIdle,
            @Value("${redis.pool.min-idle}") int minIdle,
            @Value("${redis.pool.max-wait-millis}") long maxWaitMillis,
            @Value("${redis.pool.test-on-borrow}") boolean testOnBorrow
    ) {
        GenericObjectPoolConfig<?> poolConfig = new GenericObjectPoolConfig<>();
        poolConfig.setMaxTotal(maxTotal);
        poolConfig.setMaxIdle(maxIdle);
        poolConfig.setMinIdle(minIdle);
        poolConfig.setMaxWait(Duration.ofMillis(maxWaitMillis));
        poolConfig.setTestOnBorrow(testOnBorrow);
        return poolConfig;
    }

    @Bean   // redis客户端使用的是Lettuce组件
    public LettuceClientConfiguration lettuceClientConfiguration(
            @Autowired GenericObjectPoolConfig<?> redisPoolConfig) {
        // 通过连接池创建
        return LettucePoolingClientConfiguration.builder()
                .poolConfig(redisPoolConfig)
                .build();
    }

    @Bean   // 定义Lettuce连接工厂（Redis配置的核心）
    public LettuceConnectionFactory lettuceConnectionFactory(
            // redis连接的配置信息
            @Autowired RedisStandaloneConfiguration redisStandaloneConfiguration,
            // redis客户端配置
            @Autowired LettuceClientConfiguration lettuceClientConfiguration) {
        return new LettuceConnectionFactory(redisStandaloneConfiguration, lettuceClientConfiguration);
    }
}
