package com.example.ssm.mvcb.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.util.Objects;

/**
 * 自定义拦截器
 * <p>
 *     模拟请求的目标对象
 *          {@link com.example.ssm.mvcb.action.MessageAction#echoWebInfoConfig(String)}
 *     请求日志
 *          GET "/pages/message/web_info_config/echo?message=123456", parameters={masked}
 *          【请求处理前】Action对象:com.example.ssm.mvcb.action.MessageAction@278ed05a
 *          【请求处理前】Action类:class com.example.ssm.mvcb.action.MessageAction
 *          【请求处理前】Action方法:public org.springframework.web.servlet.ModelAndView com.example.ssm.mvcb.action.MessageAction.echoWebInfoConfig(java.lang.String)
 *          【请求处理后】跳转的视图:/message/show
 *          【请求处理后】传递属性:{message=123456, level=null, pupdate=null, echoMessage=【ECHO】123456}
 *          【控制层请求处理完毕】
 *          Completed 200 OK
 * <p>
 *     如果方法执行过程中有异常的话，不会执行 postHandle 和 afterCompletion,
 *     也就是说拦截器最有用的部分是 preHandle，因为它必定会执行。
 *     在实际开发中，拦截器大多用在数据的请求验证的模型上。
 * @author caimeng
 * @date 2024/9/20 16:58
 */
@Slf4j
public class YootkHandlerInterceptor implements HandlerInterceptor {

    /**
     * @param request current HTTP request
     * @param response current HTTP response
     * @param handler chosen handler to execute, for type and/or instance evaluation
     * @return  true    表示执行目标路径
     *          false   不执行目标路径
     */
    @Override
    public boolean preHandle(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,
                             @NonNull Object handler) {
        if (handler instanceof HandlerMethod hm) {  // 基本上都是 HandlerMethod 的实例
            log.info("【请求处理前】Action对象:{}", hm.getBean());
            log.info("【请求处理前】Action类:{}", hm.getBeanType());
            log.info("【请求处理前】Action方法:{}", hm.getMethod());
        }
        return true;    // true 表示进行后续的控制层执行
    }

    @Override   // 方法执行发生异常，不会调用该方法
    public void postHandle(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,
                           @NonNull Object handler, ModelAndView modelAndView) {
        if (Objects.nonNull(modelAndView)) {
            log.info("【请求处理后】跳转的视图:{}", modelAndView.getViewName());
            log.info("【请求处理后】传递属性:{}", modelAndView.getModel());
        }
    }

    @Override   // 方法执行发生异常，不会调用该方法
    public void afterCompletion(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,
                                @NonNull Object handler, Exception ex) {
        log.info("【控制层请求处理完毕】");
    }
}
