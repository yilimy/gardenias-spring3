package com.gardenia.web.task;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;

/**
 * 动态配置定时任务
 * <p>
 *     将原本静态的cron配置，改成动态配置
 * @author caimeng
 * @date 2025/3/31 10:33
 */
@Slf4j
@Configuration
@ConditionalOnProperty(name = TaskConstant.TASK_TYPE, havingValue = "dynamic")
public class DynamicShedConfig implements SchedulingConfigurer {
    @Autowired
    private DynamicCronExpression dynamicCronExpression;
    @Autowired
    private DynamicShedlockTask dynamicShedlockTask;
    @Override
    public void configureTasks(@NonNull ScheduledTaskRegistrar taskRegistrar) {
        // 设置触发的任务
        taskRegistrar.addTriggerTask(
                // 运行任务
                () -> dynamicShedlockTask.run(),
                // 触发条件
                triggerContext -> {
                    String cron = dynamicCronExpression.getCronExpression();
                    /*
                     * 2025-03-31 11:02:25.003 [pool-3-thread-5] - INFO [] c.g.web.task.DynamicShedlockTask - 【DynamicShedlock任务】执行了, 2025-03-31 11:02:25.003
                     * 2025-03-31 11:02:25.004 [pool-3-thread-5] - INFO [] c.g.web.task.DynamicShedConfig - 设置当前的CRON表达式: ...
                     * -----
                     * 主动调用后是否立即执行？假设默认每10秒执行一次
                     *   最后执行时间: 2025-03-31 11:12:50.014 [pool-2-thread-1] - INFO [] c.g.web.task.DynamicShedlockTask - 【DynamicShedlock任务】执行了, 2025-03-31 11:12:50.014
                     *   调用时间：Mon, 31 Mar 2025 03:12:51 GMT
                     *   修改配置时间: 2025-03-31 11:12:50.996 [http-nio-8080-exec-1] - INFO [db9a4496-227a-4bb1-95fe-5bf5eb03732a] c.g.web.action.DynamicCronAction
                     *   执行时间: 2025-03-31 11:13:00.003 [pool-2-thread-3] - INFO [] c.g.web.task.DynamicShedlockTask - 【DynamicShedlock任务】执行了, 2025-03-31 11:13:00.003
                     * 说明修改任务后不是立即生效，而是等上一个任务周期后，开始执行任务，并设置新的任务周期。
                     */
                    log.info("设置当前的CRON表达式: {}", cron);
                    return new CronTrigger(cron).nextExecution(triggerContext);
                }
        );
    }
}
