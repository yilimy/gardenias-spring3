package com.example.boot3.processor.roadjava;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;


/**
 * 为{@link MyBeanPostProcessor}提供的初始化三种方法的例子
 * @author caimeng
 * @date 2024/5/15 9:58
 */
public class InitializationTestService implements InitializingBean, DisposableBean {
    public void echo() {
        System.out.println("Hello !");
    }
    /**
     * jsr250规范定义，实现bean的初始化
     */
    @PostConstruct
    public void doInit() {
        System.out.println("InitializationTestService-1-PostConstruct");
    }

    /**
     * 使用配置文件指定初始化方法
     * @see InitializationTestConfig#initializationTestServiceFromConfig()
     */
    public void init() {
        System.out.println("InitializationTestService-3-init");
    }

    @Override
    public void destroy() throws Exception {

    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("InitializationTestService-2-afterPropertiesSet");
    }

    /**
     * 使用配置文件指定销毁方法
     * @see InitializationTestConfig#initializationTestServiceFromConfig()
     */
    public void destroyResource(){

    }
}
