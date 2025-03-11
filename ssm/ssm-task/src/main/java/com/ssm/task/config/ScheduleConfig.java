package com.ssm.task.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 任务调度配置
 * <p>
 *     如果没有合理的线程池配置，不仅项目的性能会受到影响，同时也会影响到程序的稳定性。
 * <p>
 *     定时任务的线程池配置建议：
 *     核心线程数
 *          核心线程数是线程池中始终保持的线程数量。这些线程不会因为任务队列为空而被销毁，除非设置了allowCoreThreadTimeOut。
 *     1. 核心线程数通常与系统的CPU核心数相关。一个常见的经验公式是：
 *          核心线程数 = CPU核心数 × (1 + 阻塞系数)
 *        阻塞系数是指任务执行过程中阻塞的时间比例。
 *        如果任务是CPU密集型的（如计算密集型任务），阻塞系数接近0；
 *        如果是I/O密集型任务（如数据库操作、网络请求），阻塞系数可以更高（如0.8或更高）。
 *     2. 对于定时任务，如果任务主要是计算逻辑，可以设置为CPU核心数的1.5倍左右；如果是I/O密集型任务，可以设置为CPU核心数的2-3倍。
 *     最大线程数
 *          最大线程数是线程池在高负载情况下能够创建的最大线程数量。
 *     1. 最大线程数应该根据系统的资源（如内存、CPU）以及任务的并发需求来设置。
 *     2. 如果任务的执行时间较短且并发需求较高，可以适当增加最大线程数，但不要超过系统的承载能力。
 *     3. 通常建议最大线程数是核心线程数的2-3倍，但具体值需要根据实际测试调整。
 *     任务队列容量
 *          任务队列用于存储等待执行的任务。队列容量的大小会影响线程池的扩展性能。
 *     1. 如果任务执行时间较短且并发需求较高，可以设置较大的队列容量（如100-200）。
 *     2. 如果任务执行时间较长，建议设置较小的队列容量（如50-100），并通过增加最大线程数来提高并发能力。
 *     3. 如果队列容量过大，可能会导致任务堆积，占用过多内存；如果队列容量过小，可能会导致任务频繁创建新线程，增加线程切换的开销。
 *     线程存活时间
 *          线程存活时间是指非核心线程在空闲状态下可以存活的时间。
 *     1. 对于定时任务，线程存活时间可以设置为60秒或更短，因为任务通常是周期性执行的，不需要长时间保持线程。
 *     2. 如果任务的执行间隔较长，可以适当增加线程存活时间，但不要超过120秒。
 *     拒绝策略
 *          当任务队列满且线程池达到最大线程数时，新任务会被拒绝。可以选择以下拒绝策略：
 *              a. AbortPolicy：直接抛出异常（默认策略）
 *              b. DiscardPolicy：直接丢弃任务
 *              c. DiscardOldestPolicy：丢弃队列中最旧的任务，然后重新尝试执行新任务
 *              e. CallerRunsPolicy：将任务提交给调用线程执行
 *              f. RejectedExecutionHandler：自定义拒绝策略
 *     1. 对于定时任务，建议使用CallerRunsPolicy，这样可以避免任务直接被丢弃，但可能会导致调用线程阻塞。
 *     2. 如果任务不能丢失，也可以自定义拒绝策略，记录日志或进行其他处理。
 * @author caimeng
 * @date 2025/1/8 18:16
 */
@Configuration
public class ScheduleConfig implements SchedulingConfigurer {
    @Override
    public void configureTasks(@NonNull ScheduledTaskRegistrar taskRegistrar) {
        /*
         * 线程池配置
         * 18:24:49.005 [pool-1-thread-1] INFO com.ssm.task.task.YootkTask - 【YOOTK - 定时任务】当前时间: 1736331889005
         * 18:24:51.008 [pool-1-thread-2] INFO com.ssm.task.task.MuyanTask - 【Muyan - 定时任务】当前时间: 1736331891008
         * 18:24:54.000 [pool-1-thread-2] INFO com.ssm.task.task.MuyanTask - 【Muyan - 定时任务】当前时间: 1736331894000
         * 18:24:55.001 [pool-1-thread-1] INFO com.ssm.task.task.YootkTask - 【YOOTK - 定时任务】当前时间: 1736331895001
         * 18:24:57.000 [pool-1-thread-2] INFO com.ssm.task.task.MuyanTask - 【Muyan - 定时任务】当前时间: 1736331897000
         * 18:25:00.000 [pool-1-thread-2] INFO com.ssm.task.task.MuyanTask - 【Muyan - 定时任务】当前时间: 1736331900000
         * 18:25:01.000 [pool-1-thread-1] INFO com.ssm.task.task.YootkTask - 【YOOTK - 定时任务】当前时间: 1736331901000
         * 18:25:03.000 [pool-1-thread-2] INFO com.ssm.task.task.MuyanTask - 【Muyan - 定时任务】当前时间: 1736331903000
         * 18:25:06.001 [pool-1-thread-2] INFO com.ssm.task.task.MuyanTask - 【Muyan - 定时任务】当前时间: 1736331906001
         * 18:25:07.001 [pool-1-thread-1] INFO com.ssm.task.task.YootkTask - 【YOOTK - 定时任务】当前时间: 1736331907001
         */
        taskRegistrar.setScheduler(Executors.newScheduledThreadPool(2));
    }

    @Bean
    public ThreadPoolTaskExecutor scheduleThreadPoolTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        int cpuSize = Math.max(4, Runtime.getRuntime().availableProcessors());
        executor.setCorePoolSize(cpuSize);
        executor.setMaxPoolSize(cpuSize * 2);
        executor.setQueueCapacity(100); // 任务队列容量：100
        executor.setKeepAliveSeconds(120); // 线程存活时间：2分钟
        executor.setThreadNamePrefix("scheduled-task-"); // 线程名称前缀
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy()); // 拒绝策略
        executor.initialize();
        return executor;
    }
}
