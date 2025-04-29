package com.example.boot3.thread;

import java.util.concurrent.FutureTask;

/**
 * @author caimeng
 * @date 2025/4/18 15:41
 */
@SuppressWarnings("unused")
public class ThreadUtil {

    public void createThread() {
        // 线程的创建
        // 其一: 通过runnable创建
        Thread thread = new Thread(() -> System.out.println("Hello, World!"));
        // 其二: 通过Callable创建线程
        FutureTask<String> futureTask = new FutureTask<>(() -> {
            System.out.println("Hello, World!");
            return null;
        });
        // 线程安全: 加锁
    }
}
