package com.gardenia.web.task;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @author caimeng
 * @date 2025/4/15 17:28
 */
@Slf4j
@Component
public class YootkThreadTask {

    /**
     * 执行异步任务
     * <p>
     *     异步任务的线程池配置: {@link com.gardenia.web.config.DefaultThreadPoolConfig#getAsyncExecutor()}
     */
    @SneakyThrows
    @Async  // 异步任务标记
    public void startTaskHandle() {
        // 2025-04-15 17:37:12.052 [async-1] - INFO [] c.gardenia.web.task.YootkThreadTask - 【异步线程】开启，执行线程:async-1
        log.info("【异步线程】开启，执行线程:{}", Thread.currentThread().getName());
        // 模拟耗时任务的处理延迟
        TimeUnit.SECONDS.sleep(5);
        // 2025-04-15 17:37:17.062 [async-1] - INFO [] c.gardenia.web.task.YootkThreadTask - 【异步线程】结束，执行线程:async-1
        log.info("【异步线程】结束，执行线程:{}", Thread.currentThread().getName());
    }
}
