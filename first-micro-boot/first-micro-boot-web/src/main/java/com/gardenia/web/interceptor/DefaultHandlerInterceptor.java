package com.gardenia.web.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.lang.NonNull;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * 默认拦截器
 * @author caimeng
 * @date 2025/2/8 16:12
 */
public class DefaultHandlerInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object handler) {
        if (handler instanceof HandlerMethod handlerMethod) {     // 判断是否为指定类型的实例
            // 控制器的实例
            // 【Action对象】com.gardenia.web.action.OrderAction@668fe72d
            System.out.println("【Action对象】" + handlerMethod.getBean());
            // 【Action类型】class com.gardenia.web.action.OrderAction
            System.out.println("【Action类型】" + handlerMethod.getBeanType());
            // 【Action方法】public java.lang.Object com.gardenia.web.action.OrderAction.echo()
            System.out.println("【Action方法】" + handlerMethod.getMethod());
        }
        // 放行
        return true;
    }
}
