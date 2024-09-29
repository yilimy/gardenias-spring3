/**
 * SpringSecurity 快速构建与启动
 * <a href="https://www.bilibili.com/video/BV1BStJeEE6g/" />
 * 使用本地 tomcat 容器 : mvc-security
 * <p>
 *     修改 StartWebApplication 配置类，在这个类中添加 Spring Security 相关的过滤器
 *     {@link com.example.ssm.mvcb.web.config.StartWebAnnotationApplication}
 *     Spring Security 的过滤器
 *     {@link org.springframework.web.filter.DelegatingFilterProxy}
 * <p>
 *     定义Spring Security 的配置类
 *     {@link com.example.ssm.mvcb.config.WebMVCSecurityConfiguration}
 * @author caimeng
 * @date 2024/9/29 14:04
 */
package com.example.ssm.security;