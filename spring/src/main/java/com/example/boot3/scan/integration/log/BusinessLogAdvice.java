package com.example.boot3.scan.integration.log;

import jakarta.servlet.http.HttpServletRequest;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.aop.MethodBeforeAdvice;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.Method;
import java.util.Objects;
import java.util.Optional;

/**
 * @author caimeng
 * @date 2024/5/22 10:50
 */
@Slf4j
@NoArgsConstructor
public class BusinessLogAdvice implements MethodBeforeAdvice {
    @Autowired
    private HttpServletRequest request;

    @Override
    public void before(Method method, Object[] args, Object target) throws Throwable {
        String mdcTraceId = MDC.get("X-B3-TraceId");
        String mdcSpanId = MDC.get("X-B3-SpanId");
        if (Objects.nonNull(request)) {
            String appKey = request.getHeader("appkey");
            String traceId = request.getHeader("traceId");
            String spanId = request.getHeader("spanId");
            if (log.isInfoEnabled()) {
                log.info("method={}, appKey={}, traceId={}, spanId={}, mdcTraceId={}, mdcSpanId={}",
                        Optional.ofNullable(method).map(Method::getName).orElse(null), appKey, traceId, spanId, mdcTraceId, mdcSpanId);
            }
        }
    }

}
