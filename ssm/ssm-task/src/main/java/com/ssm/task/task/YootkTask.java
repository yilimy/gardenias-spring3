package com.ssm.task.task;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @author caimeng
 * @date 2025/1/8 16:17
 */
@Slf4j
@Component
public class YootkTask {
    @SneakyThrows
    @Scheduled(cron = "* * * * * ?")    // 每秒进行一次调度。
    public void runTask() {
        log.info("【YOOTK - 定时任务】当前时间: {}", System.currentTimeMillis());
        // 每次调度休眠5秒
        TimeUnit.SECONDS.sleep(5);
        /*
         * 17:59:15.011 [pool-1-thread-1] INFO com.ssm.task.task.YootkTask - 【YOOTK - 定时任务】当前时间: 1736330355011
         * 17:59:20.022 [pool-1-thread-1] INFO com.ssm.task.task.MuyanTask - 【Muyan - 定时任务】当前时间: 1736330360022
         * 17:59:21.000 [pool-1-thread-1] INFO com.ssm.task.task.YootkTask - 【YOOTK - 定时任务】当前时间: 1736330361000
         * 17:59:26.003 [pool-1-thread-1] INFO com.ssm.task.task.MuyanTask - 【Muyan - 定时任务】当前时间: 1736330366003
         * 17:59:27.000 [pool-1-thread-1] INFO com.ssm.task.task.MuyanTask - 【Muyan - 定时任务】当前时间: 1736330367000
         * 17:59:27.000 [pool-1-thread-1] INFO com.ssm.task.task.YootkTask - 【YOOTK - 定时任务】当前时间: 1736330367000
         * 17:59:32.003 [pool-1-thread-1] INFO com.ssm.task.task.MuyanTask - 【Muyan - 定时任务】当前时间: 1736330372003
         * 17:59:33.000 [pool-1-thread-1] INFO com.ssm.task.task.YootkTask - 【YOOTK - 定时任务】当前时间: 1736330373000
         * 17:59:38.002 [pool-1-thread-1] INFO com.ssm.task.task.MuyanTask - 【Muyan - 定时任务】当前时间: 1736330378002
         * 17:59:39.001 [pool-1-thread-1] INFO com.ssm.task.task.YootkTask - 【YOOTK - 定时任务】当前时间: 1736330379001
         * ...
         * 此时出现了一个非常严重的问题:
         *      执行时间短的任务，要等待执行时间长的任务释放线程之后，才可以继续向下执行。
         *      因为Spring容器只为任务分配了一个线程。
         * 解决该问题，需要配置任务池调度
         */
    }
}
