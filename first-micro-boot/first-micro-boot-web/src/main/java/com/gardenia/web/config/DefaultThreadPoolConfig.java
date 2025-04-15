package com.gardenia.web.config;

import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.aop.interceptor.SimpleAsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

/**
 * 异步任务的线程池配置
 * @author caimeng
 * @date 2025/4/15 17:23
 */
@EnableAsync    // 开启异步任务支持
@Configuration
public class DefaultThreadPoolConfig implements AsyncConfigurer {
    @Override
    public Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(10);   // 内核线程的个数（物理线程个数 * 2）
        executor.setMaxPoolSize(20);   // 工作线程的大小
        executor.setQueueCapacity(100); // 延迟队列大小
        executor.setKeepAliveSeconds(200);  // 线程的存活时间，时间如果短，对于耗时的处理是不利的。
        executor.setThreadNamePrefix("async-");  // 异步线程名称
        executor.initialize();  // 线程池初始化
        return executor;
    }

    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {   // 执行异常处理
        return new SimpleAsyncUncaughtExceptionHandler();
    }
}
