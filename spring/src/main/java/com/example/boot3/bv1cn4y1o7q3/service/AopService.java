package com.example.boot3.bv1cn4y1o7q3.service;

import com.example.boot3.bv1cn4y1o7q3.aop.BeanAspect;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Component;

/**
 * 测试包含有切面的bean定义
 * @author caimeng
 * @date 2024/6/11 17:12
 */
@Component
// 开启AOP
@EnableAspectJAutoProxy
/*
 * 因为切面不在指定的扫描包下，因此手动导入,
 * import 接收的参数包含：regular component，
 * 实际上导入的是一个指定的spring组件(component)
 */
@Import(BeanAspect.class)
public class AopService {

    public void test() {
        System.out.println("AopService: This is aop service .");
    }
}
