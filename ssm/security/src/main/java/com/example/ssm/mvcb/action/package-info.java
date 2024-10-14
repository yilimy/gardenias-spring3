/**
 * SpringSecurity认证拦截的请求资源，大多需要用户登录
 * 未认证的用户会跳转到 <a href="http://localhost/login" /> 登录页
 * 基于session的用户登录验证
 * {@link com.example.ssm.mvcb.service.YootkUserDetailsService#loadUserByUsername(String)}
 *
 * @author caimeng
 * @date 2024/10/14 10:27
 */
package com.example.ssm.mvcb.action;