package com.gardenia.web.task;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

import java.util.concurrent.Executors;

/**
 * @author caimeng
 * @date 2025/3/24 11:14
 */
@Configuration
@EnableScheduling
@ConditionalOnProperty(prefix = "yootk.task", name = "enable", havingValue = "true")
public class YootkScheduleConfig implements SchedulingConfigurer {
    @Override
    public void configureTasks(@NonNull ScheduledTaskRegistrar taskRegistrar) {
        int cpuNum = Runtime.getRuntime().availableProcessors();
        taskRegistrar.setScheduler(Executors.newScheduledThreadPool(cpuNum));
    }

    @Bean
    public YootkScheduleTask yootkScheduleTask() {
        return new YootkScheduleTask();
    }
}
