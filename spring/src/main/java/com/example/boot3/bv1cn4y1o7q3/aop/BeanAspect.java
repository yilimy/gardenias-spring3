package com.example.boot3.bv1cn4y1o7q3.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

/**
 * 切面测试
 * @author caimeng
 * @date 2024/6/11 17:14
 */
@Component
@Aspect
public class BeanAspect {
    @Before("execution(* com.example.boot3.bv1cn4y1o7q3.service.*.test(..))")
    public void testBefore() {
        System.out.println("BeanAspect: testBefore ---->");
    }
}
