package com.example.boot3.threadlocal.delegation;

import java.util.Map;

/**
 * @author caimeng
 * @date 2024/5/30 10:28
 */
public class DelegatingRunnable implements Runnable {
    private final Map<DelegatingWithExecutorThreadLocal<?>, Object> holder;
    private final Runnable runnable;

    public DelegatingRunnable(Runnable runnable) {
        /*
         * 线程是在主线程中创建的
         * 获取主线程的 ThreadLocal 快照，保存在属性 holder 中
         */
        this.holder = DelegatingWithExecutorThreadLocal.snapshot();
        this.runnable = runnable;
    }

    @Override
    public void run() {
        /*
         * 线程是在子线程中执行的
         * 子线程运行前，将主线程快照数据，复制到子线程的 ThreadLocal 中
         */
        DelegatingWithExecutorThreadLocal.copyFrom(holder);
        runnable.run();
    }
}
