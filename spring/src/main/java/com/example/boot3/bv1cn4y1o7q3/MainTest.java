package com.example.boot3.bv1cn4y1o7q3;

import com.example.boot3.bv1cn4y1o7q3.config.ConfigClass;
import com.example.boot3.bv1cn4y1o7q3.service.AopService;
import com.example.boot3.bv1cn4y1o7q3.service.MixService;
import com.example.boot3.bv1cn4y1o7q3.service.MixStaticInstanceService;
import com.example.boot3.bv1cn4y1o7q3.service.OrderService;
import com.example.boot3.bv1cn4y1o7q3.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author caimeng
 * @date 2024/6/7 17:19
 */
@Slf4j
public class MainTest {
    private AnnotationConfigApplicationContext ioc;

    @Before
    public void init() {
        ioc = new AnnotationConfigApplicationContext(ConfigClass.class);
    }

    /**
     * 测试：依赖注入
     * {@link UserService} 的有参构造函数依赖于 {@link OrderService}
     */
    @Test
    public void dependencyInjectTest() {
        UserService userService = (UserService) ioc.getBean("userService");
        userService.test();
        System.out.println("++++++++++++++++++++");
        // 多个有参构造器的情况
        MixService mixService = (MixService) ioc.getBean("mixService");
        mixService.test();
        // 通过静态方法创建对象
        System.out.println("++++++++++++++++++++");
        MixStaticInstanceService mixStaticInstanceService = (MixStaticInstanceService) ioc.getBean("mixStaticInstanceService");
        mixStaticInstanceService.test();
    }

    @Test
    public void aopBeanTest() {
        AopService aopService = (AopService) ioc.getBean("aopService");
        /*
         * BeanAspect: testBefore ---->
         * AopService: This is aop service .
         */
        aopService.test();
    }
}
