package org.example.mock.service;

import org.example.mock.service.beans.AopSimpleService;
import org.example.mock.service.beans.InjectService;
import org.example.mock.service.beans.ProcessorService;
import org.example.mock.service.beans.UserService;
import org.example.mock.service.config.AppConfig;
import org.example.mock.spring.IApplicationContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * 1. 创建自己的容器
 * 2. getBean
 * 3. 调用xxx
 * 3. ...
 * @author caimeng
 * @date 2024/6/12 18:46
 */
public class ApplicationTest {
    @BeforeEach
    public void before() {
//        System.setProperty("print.org.example.mock.spring.IApplicationContext", "false");
    }

    /**
     * bean的不同作用域
     */
    @Test
    public void beanScope() {
        IApplicationContext applicationContext = new IApplicationContext(AppConfig.class);
        // userService = org.example.mock.service.beans.UserService@233fe9b6
        System.out.println("userService = " + applicationContext.getBean("userService"));
        // userService = org.example.mock.service.beans.UserService@233fe9b6
        System.out.println("userService = " + applicationContext.getBean("userService"));
        // protoService = org.example.mock.service.beans.ProtoService@ec756bd
        System.out.println("protoService = " + applicationContext.getBean("protoService"));
        // protoService = org.example.mock.service.beans.ProtoService@3c72f59f
        System.out.println("protoService = " + applicationContext.getBean("protoService"));
    }

    /**
     * 测试依赖注入
     */
    @Test
    public void inject() {
        IApplicationContext applicationContext = new IApplicationContext(AppConfig.class);
        InjectService injectService = (InjectService) applicationContext.getBean("injectService");
        // userService injected : org.example.mock.service.beans.UserService@3c72f59f
        injectService.test();
    }

    /**
     * 测试回调
     */
    @Test
    public void aware() {
        IApplicationContext applicationContext = new IApplicationContext(AppConfig.class);
        UserService userService = (UserService) applicationContext.getBean("userService");
        userService.testAware();
    }

    /**
     * 测试 BeanPostProcessor
     */
    @Test
    public void beanPostProcessor() {
        IApplicationContext applicationContext = new IApplicationContext(AppConfig.class);
        ProcessorService processorService = (ProcessorService) applicationContext.getBean("processorService");
        /*
         * GenericBeanPostProcessor::before
         * ProcessorService IInitializingBean ...
         * GenericBeanPostProcessor::after
         * bean : complete : processorService
         *
         * GenericBeanPostProcessor::before
         * UserService 执行了 afterPropertiesSet() 方法
         * GenericBeanPostProcessor::after
         * bean : complete : userService
         *
         * GenericBeanPostProcessor::before
         * GenericBeanPostProcessor::after
         * bean : complete : injectService
         *
         * GenericBeanPostProcessor::before
         * GenericBeanPostProcessor::after
         * bean : complete : genericBeanPostProcessor
         *
         * processor = postProcessAfterInitialization
         */
        System.out.println("processor = " + processorService.getProcessor());
    }

    @Test
    public void aopBeanTest() {
        IApplicationContext applicationContext = new IApplicationContext(AppConfig.class);
        AopSimpleService aopService = (AopSimpleService) applicationContext.getBean("aopSimpleService");
        /*
         * 不能直接打印 aopService 对象
         * org.springframework.cglib.core.ReflectUtils$1: ClassLoader mismatch for [java.lang.Object]
         *      JVM should be started with --add-opens=java.base/java.lang=ALL-UNNAMED for ClassLoader.defineClass to be accessible on jdk.internal.loader.ClassLoaders$AppClassLoader
         * 添加完 JVM 参数后：
         * Cglib before ...
         * Cglib before ...
         * Cglib after ...
         * Cglib after ...
         * org.example.mock.service.beans.AopSimpleService$$EnhancerByCGLIB$$55b1f054@43bc63a3
         */
        System.out.println(aopService);
        /*
         * Cglib before ...
         * AopSimpleService test ...
         * Cglib after ...
         */
        aopService.test();
    }

}
