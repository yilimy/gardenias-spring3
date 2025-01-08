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
public class YootkTask {

    @Scheduled(fixedRate = 2000)    // 间隔两秒进行业务调度
    public void runTask() {
        log.info("【YOOTK - 定时任务】当前时间: {}", System.currentTimeMillis());
    }
}
