package com.example.boot3.annotation.value;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.TypeConverter;
import org.springframework.beans.factory.config.DependencyDescriptor;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Set;

/**
 * value 注解的解析地址
 *      {@link DefaultListableBeanFactory#doResolveDependency(DependencyDescriptor, String, Set, TypeConverter)}
 *      Object value = getAutowireCandidateResolver().getSuggestedValue(descriptor);
 * @author caimeng
 * @date 2024/7/19 10:07
 */
public class UserServiceTest {

    @BeforeAll
    public static void init() {
        System.setProperty("version", "1.2.003");
    }

    /**
     * 测试：value直接赋值
     */
    @Test
    public void directValueTest() {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext("com.example.boot3.annotation.value");
        UserService userService = context.getBean(UserService.class);
        // 123456
        userService.test();
    }

    /**
     * 测试：value通过占位符设置
     */
    @Test
    public void placeholderTest() {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext("com.example.boot3.annotation.value");
        UserService userService = context.getBean(UserService.class);
        // 1.2.003
        userService.test2();
    }

    @Test
    public void expressionTest() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(MyConfig.class);
        context.refresh();
        UserService userService = context.getBean(UserService.class);
        // 2.0.0
        userService.test3();
    }

    @Test
    public void expressionUserTest() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(MyConfig.class);
        context.refresh();
        UserService userService = context.getBean(UserService.class);
        userService.test4();
    }

    @Test
    public void sameTest() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(MyConfig.class);
        context.refresh();
        UserService userService = context.getBean(UserService.class);
        // port1=123456, port2=123456
        userService.test5();
    }
}
