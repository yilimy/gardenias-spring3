/**
 * MyBatis拦截器简介
 * <a href="https://www.bilibili.com/video/BV1knyqYFEk5/" />
 * <p>
 *     代理模式（JDK代理、CGLIB代理），spring的基础概念AOP也是基于代理模式实现的。
 *     在Mybatis中，这种代理就是拦截器
 *     在执行某些核心调用操作之前，做一些拦截处理。
 *     {@link org.apache.ibatis.plugin.Interceptor}
 * @author caimeng
 * @date 2024/12/12 15:12
 */
package com.ssm.mybatis.interceptor;