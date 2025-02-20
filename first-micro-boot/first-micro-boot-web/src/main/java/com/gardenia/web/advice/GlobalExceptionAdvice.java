package com.gardenia.web.advice;

import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.HashMap;
import java.util.Map;

/**
 * 全局异常处理
 * @author caimeng
 * @date 2025/2/20 14:17
 */
@Slf4j
@ControllerAdvice   // SpringMVC中的注解，用于全局异常处理
public class GlobalExceptionAdvice {

    /**
     * 捕获所有异常
     * <p>
     *     如果此时有一个完整的业务异常，可以在此处捕获自定义的业务异常
     * @param e 异常
     * @return rest结果
     */
    @ExceptionHandler({Exception.class})   // 可以捕获的异常类型
    @ResponseBody   // 本次基于Rest架构，返回JSON格式数据
    public Object handleException(Exception e) {  // 捕获所有异常
        log.error("【全局异常】捕获到异常，异常信息: {}", e.getMessage());
        Map<String, Object> retMap = new HashMap<>();
        retMap.put("message", e.getMessage());  // 直接获取一异常信息
        retMap.put("status", HttpServletResponse.SC_INTERNAL_SERVER_ERROR); // 设置一个HTTP状态码
        ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (sra != null) {
            String requestURI = sra.getRequest().getRequestURI();
            retMap.put("path", requestURI);     // 异常发生的路径
        }
        return retMap;
    }
}
