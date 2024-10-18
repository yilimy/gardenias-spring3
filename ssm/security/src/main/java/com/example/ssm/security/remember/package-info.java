/**
 * Spring-Security与RememberMe
 * <a href="https://www.bilibili.com/video/BV1dGtJeCEqo/" />
 * <p>
 *     免登录的控制核心是在客户端浏览器上保留一个登录Cookie信息，这个信息是需要加密的。
 * <p>
 *     1. 修改login.jsp页面，追加免登录的复选框
 *     2. 修改SpringSecurity配置 http.rememberMe()
 *          {@link com.example.ssm.mvcb.config.WebMVCSecurityConfiguration}
 *          WebMVCSecurityConfiguration#filterChain(HttpSecurity)
 *              -> WebMVCSecurityConfiguration#filterChain(HttpSecurity, UserDetailsService)
 *     3. 验证
 *          登录 <a href="http://localhost/login_page" />
 *          查询 <a href="http://localhost/pages/message/info" />
 *     4. 免登录的操作主要是基于Cookie的，这些数据保存在WEB应用的内存中，如果WEB应用突然垮掉，仍然可以访问。
 *     5. 现在添加了用户免登录，但是如果是安全要求比较高的资源访问，需要使用强制登录处理，可以添加其他的授权限制。
 *          在 WebMVCSecurityConfiguration 中 authenticated() 改成 fullyAuthenticated()
 *          再访问 <a href="http://localhost/pages/message/info" />
 *     6. 内存太多怎么办，持久化
 * <p>
 *     token持久化
 *     官方文档 <a href="https://docs.spring.io/spring-security/reference/6.0/servlet/authentication/rememberme.html" />
 *          1. 配置数据库
 *          2. 创建token存储的数据库类
 *          3. 配置文件中添加
 *                  http.rememberMe()..tokenRepository(jdbcTokenRepository)
 *                  {@link com.example.ssm.mvcb.config.WebMVCSecurityConfiguration}
 * @author caimeng
 * @date 2024/10/18 9:58
 */
package com.example.ssm.security.remember;