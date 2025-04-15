package com.gardenia.web.action;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.WebAsyncTask;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

/**
 * 接收一个请求，进行异步处理后，再返回
 * @author caimeng
 * @date 2025/4/15 14:49
 */
@Slf4j
@RequestMapping("/async")
@RestController
public class MessageAsyncAction {

    /**
     * 测试请求的异步调用
     * <p>
     *     直接返回一个异步对象，就能解决异步处理
     * <p>
     *     报错:
     *      Async support must be enabled on a servlet and for all filters involved in async request processing.
     *      This is done in Java code using the Servlet API or by adding
     *      "<async-supported>true</async-supported>"
     *      to servlet and filter declarations in web.xml.
     *     解决方案：
     *      关闭过滤器 {@link com.gardenia.web.filter.MessageFilter}
     * @param msg 请求参数
     * @return 异步Callable对象
     */
    @RequestMapping("/callable")
    public Object echo(String msg) {
        // 2025-04-15 15:32:41.125 [http-nio-8080-exec-1] - INFO [aa3f0f07-93a2-43f7-bf07-93a3d127830e] c.g.web.action.MessageAsyncAction - 【外部线程】:http-nio-8080-exec-1
        log.info("【外部线程】:{}", Thread.currentThread().getName());
        // 子线程一定是由主线程进行启动的
        // 观察主从线程的名称
        // 返回一个异步线程
        return (Callable<String>) () -> {
            // 2025-04-15 15:32:41.133 [task-1] - INFO [] c.g.web.action.MessageAsyncAction - 【内部线程】:task-1
            // SpringBoot默认只提供了10个线程池，使用自定义异步线程池进行定制 com.gardenia.web.config.CostumeAsyncPoolConfig
            log.info("【内部线程】:{}", Thread.currentThread().getName());
            return "【ECHO】" + msg;  // 数据的响应
        };
    }

    /**
     * 通过WebAsyncTask进行异步处理
     * @param msg 请求参数
     * @return 异步任务对象 WebAsyncTask
     */
    @RequestMapping("/webAsyncTask")
    public Object webAsyncTask(String msg) {
        // 2025-04-15 16:35:31.370 [http-nio-8080-exec-2] - INFO [] c.g.web.action.MessageAsyncAction - 【外部线程】:http-nio-8080-exec-2
        log.info("【外部线程】:{}", Thread.currentThread().getName());
        Callable<String> callable = () -> {
            // 2025-04-15 16:35:31.372 [async-thread-6] - INFO [] c.g.web.action.MessageAsyncAction - 【内部线程】:async-thread-6
            log.info("【内部线程】:{}", Thread.currentThread().getName());
            // 模拟操作的延时
            // 当前的操作一定会超时
            // 2025-04-15 16:35:32.023 [http-nio-8080-exec-3] -ERROR [] c.g.web.advice.GlobalExceptionAdvice - 【全局异常】捕获到异常，异常信息: sleep interrupted
            TimeUnit.SECONDS.sleep(1);
            return "【ECHO】" + msg;  // 数据的响应
        };
        // 200毫秒内完成异步处理
        WebAsyncTask<String> webAsyncTask = new WebAsyncTask<>(200, callable);
        webAsyncTask.onTimeout(() -> {
            // 2025-04-15 16:35:31.372 [async-thread-6] - INFO [] c.g.web.action.MessageAsyncAction - 【内部线程】:async-thread-6
            log.info("【超时】:{}", Thread.currentThread().getName());
            return "【超时】" + msg;
        });
        return webAsyncTask;
    }
}
