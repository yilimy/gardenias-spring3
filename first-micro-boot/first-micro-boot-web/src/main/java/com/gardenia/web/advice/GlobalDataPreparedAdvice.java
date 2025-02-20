package com.gardenia.web.advice;

import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.InitBinder;

/**
 * 全局数据预处理
 * @author caimeng
 * @date 2025/2/20 18:11
 */
@ControllerAdvice // 全局数据准备
public class GlobalDataPreparedAdvice {
    @InitBinder("company")
    public void company(WebDataBinder binder) {
        System.out.println("GlobalDataPreparedAdvice.company()");
        binder.setFieldDefaultPrefix("company."); // 指定字段前缀
    }

    @InitBinder("dept")
    public void dept(WebDataBinder binder) {
        System.out.println("GlobalDataPreparedAdvice.dept()");
        binder.setFieldDefaultPrefix("dept."); // 指定字段前缀
    }
}
