/**
 * 用户认证授权服务管理
 * <a href="https://www.bilibili.com/video/BV1ZutJe1E7P/" />
 * <p>
 *     引入 UserDetailsService 接口，解决用户名和密码的问题
 *          {@link org.springframework.security.core.userdetails.UserDetailsService}
 *     通过 loadUserByUsername 方法加载用户信息，
 *          由开发者自行定义数据源。
 * <p>
 *     Spring中对用户的定义是用接口 UserDetails 表述的
 *          {@link org.springframework.security.core.userdetails.UserDetails}
 *     包含有
 *       - 用户名
 *       - 密码
 *       - 锁定
 *     ORM组件结合时，不要将实体对象与 UserDetails 关联，使用时进行转化即可，否则会造成严重的依赖耦合问题。
 *     同理，进行ORM设计时，也不要与 Spring Security 产生任何的关联。
 * <p>
 *     使用 Spring Security 进行数据加密，以 bcrypt 为例。
 *          {@link org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder}
 *     看测试类。
 *     {@link com.example.ssm.mvcb.service.YootkUserDetailsService}
 * <p>
 *     SpringSecurity 授权表达式
 *     <a href="https://www.bilibili.com/video/BV1ZMtJejEy6/" />
 *     - denyAll    拒绝全部访问
 *     - permitAll  允许全部访问
 *     - hasAnyRole('角色1', '角色2', '角色3')    拥有列表中的任意一个角色即可访问
 *     - hasRole('角色')  拥有指定角色才允许访问
 *          资源使用上使用了"hasRole()"表达式，表示具有指定的权限，
 *          Role表示角色，privilege表示权限，但是SpringSecurity内部没有对这两个概念进行区分。
 *     - hasIpAddress('192.168.27.0/24')    只允许指定'192.168.27'网段的主机访问
 *     - isAnonymous    是否允许匿名访问
 *     - isAuthenticated    只允许认证访问
 *     - isRememberMe   是否允许RememberMe访问
 *     - isFullAuthenticated    必须采用标准认证流程（用户必须登录后访问）
 *     代码演示
 *     {@link com.example.ssm.mvcb.config.WebMVCSecurityConfiguration#filterChain(HttpSecurity)}
 * @author caimeng
 * @date 2024/9/29 18:41
 */
package com.example.ssm.security.user;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;