package com.gardenia.web.task;

import net.javacrumbs.shedlock.core.LockProvider;
import net.javacrumbs.shedlock.provider.redis.spring.RedisLockProvider;
import net.javacrumbs.shedlock.spring.annotation.EnableSchedulerLock;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;

/**
 * @author caimeng
 * @date 2025/3/24 17:05
 */
// 2分钟释放分布式锁
@EnableSchedulerLock(defaultLockAtMostFor = "PT2M")
@ConditionalOnProperty(name = "task.type", havingValue = "shedlock")
@Configuration
public class ShedlockConfig {
    @Value("${spring.profiles.active:dev}")
    private String env;

    /**
     * 不设置 RedisLockProvider 将不会通过redis进行任务锁定
     * <p>
     *     redis锁定的key是 "job-lock:dev:shedlock-task",
     *     其value是 ADDED:2025-03-24T09:26:55.099Z@win-cai-20230601, 即当前时间戳+@+机器名
     * @param factory redis连接工厂
     * @return RedisLockProvider
     */
    @Bean
    public LockProvider lockProvider(RedisConnectionFactory factory) {
        return new RedisLockProvider(factory, env);
    }

    @Bean
    public ShedlockTask shedlockTask() {
        return new ShedlockTask();
    }
}
