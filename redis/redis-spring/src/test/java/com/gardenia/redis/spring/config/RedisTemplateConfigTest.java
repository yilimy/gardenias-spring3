package com.gardenia.redis.spring.config;

import com.gardenia.redis.spring.StartRedisApplication;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * 使用 "redisTemplate.opsFor*" 进行装饰器的方式，来调用redis
 * @author caimeng
 * @date 2025/4/2 16:29
 */
@Slf4j
@ExtendWith(SpringExtension.class)  // 使用Spring扩展
@ContextConfiguration(classes = StartRedisApplication.class)    // 上下文启动配置类
public class RedisTemplateConfigTest {
    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    /**
     * 测试: Redis获取普通数值
     */
    @Test
    public void valueDataTest() {
        String key = "yootk";
        redisTemplate.opsForValue().set(key, "muyan-yootk");
        // 【普通数据】: muyan-yootk
        log.info("【普通数据】: {}", redisTemplate.opsForValue().get(key));
    }

    /**
     * 测试: Redis获取Hash数据
     */
    @Test
    public void hashTest() {
        String key = "template:hash";
        redisTemplate.opsForHash().put(key, "name", "李兴华");
        redisTemplate.opsForHash().put(key, "crop", "沐言科技");
        // 【Hash数据】姓名:李兴华, 机构:沐言科技
        log.info("【Hash数据】姓名:{}, 机构:{}",
                redisTemplate.opsForHash().get(key, "name"),
                redisTemplate.opsForHash().get(key, "crop"));
    }

    /**
     * 测试: Redis获取List数据
     */
    @Test
    public void listTest() {
        String key = "template:list";
        redisTemplate.opsForList().leftPush(key, "muyan-yootk");
        redisTemplate.opsForList().leftPush(key, "yootk");
        redisTemplate.opsForList().leftPush(key, "yootk-muyan");
        redisTemplate.opsForList().leftPushAll(key, "hello", "good", "nice");
        // 【List数据】: [nice, good, hello, yootk-muyan, yootk, muyan-yootk]
        log.info("【List数据】: {}", redisTemplate.opsForList().range(key, 0, -1));
    }
}
