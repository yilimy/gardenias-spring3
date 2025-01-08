package com.ssm.batch.schedule;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

/**
 * @author caimeng
 * @date 2025/1/8 15:47
 */
public class TimerTaskTest {

    /**
     * 测试：java自带的定时任务（间隔任务）
     */
    @SneakyThrows
    @Test
    public void scheduleTest() {
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() { // 定时任务的本质是：专属的操作线程
                System.out.println("+++++");
            }
        };
        timer.schedule(task, 0, 2000);
        TimeUnit.SECONDS.sleep(Integer.MAX_VALUE);
    }
}
