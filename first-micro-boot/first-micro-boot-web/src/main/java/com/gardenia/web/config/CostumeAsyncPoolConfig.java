package com.gardenia.web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.context.request.async.TimeoutCallableProcessingInterceptor;
import org.springframework.web.servlet.config.annotation.AsyncSupportConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * 自定义的异步线程池
 * <p>
 *     异步请求 | 线程池
 * @see com.gardenia.web.action.MessageAsyncAction
 * @author caimeng
 * @date 2025/4/15 15:43
 */
@Configuration
public class CostumeAsyncPoolConfig implements WebMvcConfigurer {

    @Override
    public void configureAsyncSupport(@NonNull AsyncSupportConfigurer configurer) { // 异步请求配置
        configurer.setDefaultTimeout(1000 * 60 * 2);    // 配置超时时间
        configurer.registerCallableInterceptors(getTimeoutInterceptor());   // 设置Callable超时拦截器
        configurer.setTaskExecutor(getAsyncThreadPoolTaskExecutor());   // 配置异步任务线程池
    }

    @Bean(name = "asyncThreadPoolTaskExecutor") // Spring本身有很多线程池，为了区分业务，最好自定义线程池的名称
    public ThreadPoolTaskExecutor getAsyncThreadPoolTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(10);   // 内核线程的个数（物理线程个数 * 2）
        executor.setMaxPoolSize(200);   // 工作线程的大小
        executor.setQueueCapacity(25); // 延迟队列大小
        executor.setKeepAliveSeconds(200);  // 线程的存活时间，时间如果短，对于耗时的处理是不利的。
        executor.setThreadNamePrefix("async-thread-");  // 异步线程名称
        // 配置拒绝策略
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.initialize();  // 线程池初始化
        return executor;
    }

    @Bean   // 专属的超时处理的拦截器
    public TimeoutCallableProcessingInterceptor getTimeoutInterceptor() {
        return new TimeoutCallableProcessingInterceptor();
    }
}
