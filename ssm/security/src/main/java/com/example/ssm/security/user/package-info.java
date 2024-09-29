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
 * @author caimeng
 * @date 2024/9/29 18:41
 */
package com.example.ssm.security.user;