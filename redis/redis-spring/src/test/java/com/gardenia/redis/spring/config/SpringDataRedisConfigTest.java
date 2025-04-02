package com.gardenia.redis.spring.config;

import com.gardenia.redis.spring.StartRedisApplication;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * @author caimeng
 * @date 2025/4/2 15:30
 */
@Slf4j
@ExtendWith(SpringExtension.class)  // 使用Spring扩展
@ContextConfiguration(classes = StartRedisApplication.class)    // 上下文启动配置类
public class SpringDataRedisConfigTest {
    @Autowired
    private LettuceConnectionFactory connectionFactory;

    /**
     * 测试: Redis连接
     */
    @Test
    public void connectionTest() {
        // 【Redis连接】org.springframework.data.redis.connection.lettuce.LettuceConnection@4e375bba
        log.info("【Redis连接】{}", connectionFactory.getConnection());
    }
}
