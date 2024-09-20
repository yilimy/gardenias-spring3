package com.example.ssm.mvcb.advice;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

/**
 * 全局异常处理
 * @author caimeng
 * @date 2024/9/20 15:19
 */
@ControllerAdvice   // 控制层的切面操作
public class ErrorAdvice {

    @ExceptionHandler(Exception.class)  // 进行异常的捕获
    public ModelAndView handle(Exception e) {
        /*
         * 现在所配置的全局跳转路径全部是由用户开发的，
         * 毕竟学习SpringMVC的下一步是SpringBoot，SpringBoot会有默认自动配置。
         * 实际页面地址： /WEB-INF/pages/error.jsp
         */
        ModelAndView mav = new ModelAndView("/error");  // 默认的处理路径
        mav.addObject("message", e.getMessage());   // 保存异常信息
        return mav;
    }
}
