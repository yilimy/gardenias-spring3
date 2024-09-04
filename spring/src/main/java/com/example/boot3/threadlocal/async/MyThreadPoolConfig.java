package com.example.boot3.threadlocal.async;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.aop.interceptor.AsyncExecutionAspectSupport;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 线程池配置
 * <p>
 *     简单手动实现threadLocal数据传递到子线程的方案
 * @author caimeng
 * @date 2022/7/28 11:40
 */
@EnableAsync
@Configuration
@ConditionalOnProperty(value = "tl.async", havingValue = "simple")
public class MyThreadPoolConfig {
    @Value("${thread.pool.max.size:200}")
    private int maximumPoolSize;
    /** 文件和印章的保存对象 **/
    public static final ThreadLocal<TlMark> LOG_LOCAL = new ThreadLocal<>();

    /**
     * 默认线程池
     * DEFAULT_TASK_EXECUTOR_BEAN_NAME 是注解 @Async 默认查找的线程池名
     *
     * @return 线程池配置
     */
    @Bean(name = AsyncExecutionAspectSupport.DEFAULT_TASK_EXECUTOR_BEAN_NAME)
    public Executor defaultExecutor() {
        return createExecutor("taskExecutor-", maximumPoolSize, 500, 60, true, null);
    }

    /**
     * 一件事项目定制化线程池
     * @return 线程池
     */
    @Bean(name = "yjsExecutor")
    public Executor yjsExecutor() {
        return createExecutor("yjsTask-", maximumPoolSize, 500, 60, true, new YjsThreadPoolTaskExecutor());
    }

    /**
     * 自定义线程池
     * @param threadNamePrefix 线程名前缀
     * @param maxPoolSize 最大线程数
     * @param queueCapacity 队列容量
     * @param keepAliveSeconds 活跃时间
     * @param mdc 是否拷贝MDC参数
     * @param executor 自定义线程池
     * @return 线程池
     */
    public static ThreadPoolTaskExecutor createExecutor(
            String threadNamePrefix, int maxPoolSize, int queueCapacity,
            int keepAliveSeconds, boolean mdc, ThreadPoolTaskExecutor executor){
        if (Objects.isNull(executor)){
            executor = new ThreadPoolTaskExecutor();
        }
        //核心线程池大小
        int cpuSize = Math.max(4, Runtime.getRuntime().availableProcessors());
        executor.setCorePoolSize(cpuSize * 2);
        //最大线程数
        executor.setMaxPoolSize(maxPoolSize);
        //队列容量
        executor.setQueueCapacity(queueCapacity);
        //活跃时间
        executor.setKeepAliveSeconds(keepAliveSeconds);
        //线程名字前缀
        executor.setThreadNamePrefix(threadNamePrefix);
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        // 设置日志链
        if (mdc){
            executor.setTaskDecorator(runnable -> {
                Map<String, String> copyOfContextMap = MDC.getCopyOfContextMap();
                return () -> {
                    try {
                        if (Objects.nonNull(copyOfContextMap)) {
                            MDC.setContextMap(copyOfContextMap);
                        }
                        runnable.run();
                    } finally {
                        MDC.clear();
                    }
                };
            });
        }
        return executor;
    }

    /**
     * ThreadLocal对象类型
     */
    public static interface TlMark {}

    /**
     * 当拒绝策略是CallerRunsPolicy时，执行任务的线程是父线程，这个时候清理数据，会直接把父线程的threadLocal清理，极有可能导致后续业务出错，
     * 所以我们需要判断一下，当前执行任务的线程是不是和父线程相等，
     * 如果是的话，就没必要执行移除ThreadLocal，让父线程的逻辑代码自行移除
     * 如果只是针对 @Async 注解使用，下面代码中，没必要重写execute方法，直接重写submit就行
     * <a href="https://blog.csdn.net/yang11qiang/article/details/125490779">解决@Async导致ThreadLocal值丢失问题</>
     */
    @SuppressWarnings("NullableProblems")
    private static class YjsThreadPoolTaskExecutor extends ThreadPoolTaskExecutor{
        @Override
        public Future<?> submit(@NonNull Runnable task) {
            TlMark po = LOG_LOCAL.get();
            return super.submit(new Runner(Thread.currentThread(), task, po));
        }

        @Override
        public <T> Future<T> submit(@NonNull Callable<T> task) {
            TlMark po = LOG_LOCAL.get();
            return super.submit(new Caller<>(Thread.currentThread(), task, po));
        }
    }

    /**
     * 自定义Runner
     * 清理ThreadLocal数据
     */
    @Slf4j
    private static class Runner implements Runnable{
        private final Thread parentThread;
        private final Runnable runnable;
        private final TlMark po;

        Runner(Thread parentThread, Runnable runnable, TlMark po) {
            this.parentThread = parentThread;
            this.runnable = runnable;
            this.po = po;
        }

        @Override
        public void run() {
            // 这里是子线程
            LOG_LOCAL.set(po);
            try {
                runnable.run();
            } catch (Exception e){
                log.error("异步任务异常：{}", e.getMessage());
                throw e;
            } finally {
                /*
                 * 如果线程池不够用，导致任务在主线程执行，此时不会删除 LOG_LOCAL，直到主线程结束.
                 * 但仍会受到tomcat等容器线程复用得影响，
                 * 因此主线程中使用LOG_LOCAL时，也要set和remove成对使用
                 */
                if (parentThread != Thread.currentThread()){
                    LOG_LOCAL.remove();
                }
            }
        }
    }

    /**
     * 自定义Caller
     * 清理ThreadLocal数据
     */
    @Slf4j
    private static class Caller<V> implements Callable<V>{
        private final Thread parentThread;
        private final Callable<V> callable;
        private final TlMark po;

        Caller(Thread parentThread, Callable<V> callable, TlMark po) {
            this.parentThread = parentThread;
            this.callable = callable;
            this.po = po;
        }

        @Override
        public V call() throws Exception {
            // 这里是子线程
            LOG_LOCAL.set(po);
            try {
                return callable.call();
            } catch (Exception e){
                log.error("异步任务异常：{}", e.getMessage());
                throw e;
            } finally {
                if (parentThread != Thread.currentThread()){
                    LOG_LOCAL.remove();
                }
            }
        }
    }
}
