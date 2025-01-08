package com.ssm.task.task;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author caimeng
 * @date 2025/1/8 16:17
 */
@Slf4j
@Component
public class MuyanTask {

//    @Scheduled(fixedRate = 2000)    // 间隔两秒进行业务调度
//    @Scheduled(cron = "* * * * * ?")    // 每秒进行一次调度。 ? 表示不生效的字段，仅适用于日和周
//    @Scheduled(cron = "0 * * * * ?")    // 每分钟进行一次调度。 每次秒为0，即为每分钟。
    @Scheduled(cron = "0/3 * * * * ?")    // 每3秒进行一次调度。 0开始，步进3
    public void runTask() {
        log.info("【Muyan - 定时任务】当前时间: {}", System.currentTimeMillis());
    }
}
