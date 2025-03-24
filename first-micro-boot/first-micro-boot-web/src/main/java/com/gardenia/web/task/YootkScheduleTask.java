package com.gardenia.web.task;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @author caimeng
 * @date 2025/3/24 10:11
 */
@Slf4j
public class YootkScheduleTask {    // 定时任务处理类

    /**
     * 执行间隔任务
     */
    @SneakyThrows
    @Scheduled(fixedDelay = 2000)   // 每两秒触发一次间隔任务
    public void runRate() {     // 间隔任务
        log.info("【间隔任务】执行了, {}", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date()));
        // 模拟5秒延迟
        TimeUnit.SECONDS.sleep(5);
    }

    /**
     * 执行定时调度任务
     */
    @Scheduled(cron = "* * * * * ?")     // 每秒触发一次定时任务
    public void runCron() {     // 间隔任务
        log.info("【定时任务】执行了, {}", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date()));
    }
}
