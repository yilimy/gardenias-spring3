package com.example.boot3.aop.dynamic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 仿热插拔
 * 在 Dubbo 和 Sentinal 中有体现
 * <p>
 *     核心组件
 *     1. advice 动态代理之后要做什么，本质上是一个拦截器(Interceptor)
 *     2. pointcut 定义拦截规则，可以使用注解和表达式
 *     3. advisor 包含了 advice 和 pointcut 的类
 *     4. advised 代理后生成的对象，它维护了一个 List&lt;advisor&gt;
 *
 * @author caimeng
 * @date 2024/5/28 11:06
 */
@SpringBootApplication
public class DynamicAopApplication {

    public static void main(String[] args) {
        SpringApplication.run(DynamicAopApplication.class, args);
    }
}
