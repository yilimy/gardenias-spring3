package com.gardenia.web.task;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;
import org.springframework.scheduling.annotation.Scheduled;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * 使用 shedlock 组件来执行任务
 * <p>
 *     引入的依赖:
 *     1. net.javacrumbs.shedlock:shedlock-spring
 *     2. org.springframework.boot:spring-boot-starter-data-redis
 *     3. net.javacrumbs.shedlock:shedlock-provider-redis-spring
 * @author caimeng
 * @date 2025/3/24 16:35
 */

@Slf4j
public class ShedlockTask {
    @SneakyThrows
    @Scheduled(cron = "0/11 * * * * ?")
    // lockAtLeastFor 成功执行定时任务时，任务节点所能拥有的独占锁的最短时间
    // lockAtMostFor 成功执行定时任务时，任务节点所能拥有的独占锁的最长时间
    // lockAtLeastFor 默认是 PT1M，lockAtMostFor 默认是 PT30S，
    // lockAtLeastFor 和 lockAtMostFor 的单位都是 PT，表示的是 ISO-8601 的时间单位，
    // PT1M 表示 1分钟，PT30S 表示 30 秒，PT1H 表
    @SchedulerLock(name = "shedlock-task", lockAtLeastFor = "PT12S", lockAtMostFor = "PT1M")
    public void run() {
        log.info("【Shedlock任务】执行了, {}", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date()));
        // 模拟阻塞，方便观察redis中的key
        TimeUnit.SECONDS.sleep(10);
    }
}
