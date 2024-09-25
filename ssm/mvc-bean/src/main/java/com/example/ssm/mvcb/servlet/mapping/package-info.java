/**
 * 源码解析
 * <a href="https://www.bilibili.com/video/BV1ToHSeeE8R/" />
 * 关注 DispatcherServlet 类在刷新(onRefresh)时调用的 initHandlerMappings 方法
 * {@link org.springframework.web.servlet.DispatcherServlet#onRefresh(ApplicationContext)}
 * {@link org.springframework.web.servlet.DispatcherServlet#initHandlerMappings(ApplicationContext)}
 * <p>
 *     所有的SpringMVC里面的控制器都是通过路径匹配的，
 *     也就是说，所有的请求都会首先发送到 DispatcherServlet 分发处理器中，再根据路径的匹配找到指定的Action处理方法。
 * <p>
 *     在 HandlerMapping 对象中顶一个执行链，
 *     {@link org.springframework.web.servlet.HandlerMapping#getHandler(HttpServletRequest)}
 *     所有的请求都要交给 DispatcherServlet, 但是请求路径上可能有不同的操作，
 *     按照之前拦截器的定义，不同的拦截器会有不同的拦截路径，有的Action上需要拦截处理，有的Action不需要拦截，
 *     为了便于执行，提供了 {@link org.springframework.web.servlet.HandlerExecutionChain} 执行链的配置类
 * <p>
 *     关于 HandlerExecutionChain 的属性定义
 *      - Object handler    要处理的Action对象
 *      - List<HandlerInterceptor> interceptorList  拦截器列表
 * <p>
 *     观察 HandlerMapping 实现的抽象类 AbstractHandlerMapping
 *     {@link org.springframework.web.servlet.handler.AbstractHandlerMapping#getHandler(HttpServletRequest)}
 * @author caimeng
 * @date 2024/9/23 11:41
 */
package com.example.ssm.mvcb.servlet.mapping;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.ApplicationContext;