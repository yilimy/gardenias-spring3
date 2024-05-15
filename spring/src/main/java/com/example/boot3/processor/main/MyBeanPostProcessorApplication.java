package com.example.boot3.processor.main;

import com.example.boot3.processor.roadjava.InitializationTestService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author caimeng
 * @date 2024/5/15 10:20
 */
public class MyBeanPostProcessorApplication {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext("com.example.boot3.processor.roadjava");
        InitializationTestService bean = context.getBean(InitializationTestService.class);
        bean.echo();
    }
}
