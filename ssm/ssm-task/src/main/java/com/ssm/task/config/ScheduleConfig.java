package com.ssm.task.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

import java.util.concurrent.Executors;

/**
 * 任务调度配置
 * <p>
 *     如果没有合理的线程池配置，不仅项目的性能会受到影响，同时也会影响到程序的稳定性。
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
}
