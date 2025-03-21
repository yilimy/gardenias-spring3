package com.gardenia.web.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.util.UUID;

/**
 * 在MDC中设置requestId的拦截器
 * @author caimeng
 * @date 2025/3/17 14:37
 */
@Slf4j
public class MDCInterceptor implements HandlerInterceptor {
    public static final String REQUEST_ID = "requestId";

    @Override
    public boolean preHandle(@NonNull HttpServletRequest request,
                             @NonNull HttpServletResponse response,
                             @NonNull Object handler) {
        String forward = request.getHeader("X-forwarded-For");
        String clientIp = request.getRemoteAddr();
        String uuid = UUID.randomUUID().toString();
        // 日志输出
        log.info("MDC操作记录开始，requestId = {}", uuid);
        log.info("requestId={}, clientIp={}, X-forwarded-For={}", uuid, clientIp, forward);
        // 保存MDC数据
        MDC.put(REQUEST_ID, uuid);
        return true;
    }

    @Override
    public void postHandle(@NonNull HttpServletRequest request,
                           @NonNull HttpServletResponse response,
                           @NonNull Object handler, ModelAndView modelAndView) {
        String uuid = MDC.get(REQUEST_ID);
        log.info("MDC操作记录结束，requestId = {}", uuid);
        MDC.remove(REQUEST_ID);
    }
}
