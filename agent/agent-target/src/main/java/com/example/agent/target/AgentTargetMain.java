package com.example.agent.target;

import com.example.agent.target.service.BusinessService;

import java.util.Timer;
import java.util.TimerTask;

/**
 * @author caimeng
 * @date 2024/11/19 16:42
 */
public class AgentTargetMain {
    public static void main(String[] args) {
        printPid();
//        BusinessService service = new BusinessService();
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                new BusinessService().doService();
            }
        };
        timer.schedule(task, 0, 2000);
    }

    private static void printPid() {
        // 获取 JVM 的pid
        long pid = ProcessHandle.current().pid();
        System.out.println("The PID of the JVM is: " + pid);
    }
}
