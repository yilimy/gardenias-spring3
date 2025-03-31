package com.gardenia.web.task;

import lombok.extern.slf4j.Slf4j;
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author caimeng
 * @date 2025/3/31 10:09
 */
@Slf4j
@Component
public class DynamicShedlockTask {

    @SchedulerLock(name = "shedlock-task", lockAtLeastFor = "PT5S", lockAtMostFor = "PT30S")
    public void run() {
        log.info("【DynamicShedlock任务】执行了, {}", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date()));
    }
}
