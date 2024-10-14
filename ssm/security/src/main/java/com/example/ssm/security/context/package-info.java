/**
 * SecurityContextHolder
 * <a href="https://www.bilibili.com/video/BV1fMtJejEwt/" />
 * <p>
 *     SpringSecurity 实现了用户认证与登录之后，所有的信息其实已经都保存在Session属性之中，
 *     但是在一些内部业务处理的时候，需要获取到一些认证信息，这些信息的获取就需要通过SecurityContextHolder类来实现。
 *     {@link org.springframework.security.core.context.SecurityContextHolder}
 * <p>
 *     关于 {@link org.springframework.security.core.Authentication}
 *     SecurityContextHolder 类中，setContext方法的对象，可以返回认证信息 Authentication
 *     - getAuthorities     获取授权信息
 *     - getCredentials     获取认证信息
 *     - getDetails         获取用户详情
 *     - getPrincipal       获取认证的用户数据，如果是用户名密码登录的，此时返回的是用户名
 *     - isAuthenticated    判断当前用户是否已经登录过了，每个用户线程保存有各自的接口实例
 *     - setAuthenticated   设置是否已经登录过的标记
 * <p>
 *     代码示例 {@link com.example.ssm.mvcb.action.MemberAction}
 * <p>
 *     如果要获取到 SpringSecurity 里保存的认证与授权信息，那么就使用以下两种方式
 *          - {@link com.example.ssm.mvcb.action.MemberAction#info()}
 *          - {@link com.example.ssm.mvcb.action.MemberAction#principal(Principal)}
 *     SpringMVC的特点是希望帮助用户隐藏全部的内置对象，
 *     SpringSecurity在操作时，本质上也是隐藏了内置对象的。
 * @author caimeng
 * @date 2024/10/14 14:48
 */
package com.example.ssm.security.context;

import java.security.Principal;