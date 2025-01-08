package com.ssm.batch.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.retry.RetryCallback;
import org.springframework.retry.RetryContext;
import org.springframework.retry.listener.RetryListenerSupport;

/**
 * @author caimeng
 * @date 2025/1/8 14:17
 */
@Slf4j
public class BillStepRetryListener extends RetryListenerSupport {

    @Override
    public <T, E extends Throwable> boolean open(RetryContext context, RetryCallback<T, E> callback) {
        log.info("【Retry - open】进入Retry操作，{}", context);
        return true;
    }

    @Override
    public <T, E extends Throwable> void close(RetryContext context, RetryCallback<T, E> callback, Throwable throwable) {
        log.info("【Retry - close】进入Retry操作，{}", context);
    }

    @Override
    public <T, E extends Throwable> void onError(RetryContext context, RetryCallback<T, E> callback, Throwable throwable) {
        log.error("【Retry - onError】进入Retry操作，{}", throwable.getMessage());
    }
}
