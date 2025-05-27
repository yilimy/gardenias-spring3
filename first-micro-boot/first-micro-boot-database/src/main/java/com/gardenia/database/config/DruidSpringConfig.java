package com.gardenia.database.config;

import com.alibaba.druid.support.spring.stat.DruidStatInterceptor;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.JdkRegexpMethodPointcut;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

/**
 * Druid-Spring 监控配置类
 * <p>
 *     打开 Druid 监控页面 <a href="http://localhost/druid/spring.html" /> <br>
 *     在 ”Spring监控“中显示切面拦截结果
 * @author caimeng
 * @date 2025/5/22 11:20
 */
@Configuration
@ConditionalOnProperty(value = "spring.test-qqq.datasource.enabled", havingValue = "true")
public class DruidSpringConfig {

    /**
     * @return Druid拦截器
     */
    @Bean
    public DruidStatInterceptor druidStatInterceptor() {
        return new DruidStatInterceptor();
    }

    /**
     * 基于正则匹配的切面
     * <p>
     *     JdkRegexpMethodPointcut 被设置为原型（prototype）作用域的原因主要有以下几点：
     *      1. 状态不共享：
     *              JdkRegexpMethodPointcut 是一个基于正则表达式匹配方法的切点对象，
     *              它的匹配逻辑可能在不同的调用中发生变化（例如 pattern 或 classFilter 的变化）。
     *              使用原型作用域可以确保每次请求都创建一个新的实例，避免多个代理对象共享同一个切点实例时产生的状态冲突。
     *      2. 与 AOP 代理机制兼容：
     *              Spring AOP 在创建代理对象时，会根据切点表达式来决定是否对某个类或方法进行增强。
     *              如果 JdkRegexpMethodPointcut 是单例的，那么它可能会被多个代理对象共享，导致意外的匹配行为。
     *              原型作用域可以确保每个 Advisor 使用独立的切点实例，提高隔离性。
     *      3. Spring 官方推荐做法：
     *              在使用 DefaultPointcutAdvisor 和基于正则表达式的切点时，
     *              Spring 推荐将切点定义为原型作用域，以防止潜在的并发问题和状态污染。
     * @return spring切面对象
     */
    @Bean
    @Scope("prototype") // 因为每一个操作的过程不太一样，使用原型的 bean
    public JdkRegexpMethodPointcut druidStatPointcut() {
        JdkRegexpMethodPointcut druidStatPointcut = new JdkRegexpMethodPointcut();
        //可以set多个
        druidStatPointcut.setPatterns(
                "com.gardenia.database.action.*",
                "com.gardenia.database.service.*",
                "com.gardenia.database.dao.*"
        );
        return druidStatPointcut;
    }

    /**
     * 创建一个Advisor，将切面和拦截器关联起来
     * @return spring-Advisor
     */
    @Bean
    public DefaultPointcutAdvisor druidStatAdvisor() {
        DefaultPointcutAdvisor defaultPointAdvisor = new DefaultPointcutAdvisor();
        defaultPointAdvisor.setPointcut(druidStatPointcut());
        defaultPointAdvisor.setAdvice(druidStatInterceptor());
        return defaultPointAdvisor;
    }
}
