package com.example.boot3.util;

import java.util.Timer;
import java.util.TimerTask;

/**
 * 定时任务的工具包
 * @author caimeng
 * @date 2024/7/5 11:24
 */
public class ScheduleUtil {
    private ScheduleUtil(){}
    private static int second;

    /**
     * 读秒
     */
    public static void printSeconds() {
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println(second++);
            }
        }, 0L, 1000L);
    }
}
