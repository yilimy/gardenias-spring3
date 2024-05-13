package com.example.boot3.aop.config;

import com.example.boot3.aop.advice.expose.ServiceAnnotationExposeProxyAdvice;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * 对外暴露代理类实例的配置
 * @author caimeng
 * @date 2024/5/13 14:40
 */
// 启用aop配置环境
@EnableAspectJAutoProxy(exposeProxy = true)
// 设置扫描路径
@ComponentScan(
        basePackages = "com.example.boot3.aop.service",
        // 实际会扫描指定类，及其同包下的类
        basePackageClasses = ServiceAnnotationExposeProxyAdvice.class
)
public class AopConfig4ExposeProxy {
}
