/**
 * 源码解析
 * <a href="https://www.bilibili.com/video/BV14oHSeeENX/" />
 * <p>
 *     关于 DispatcherServlet 的刷新
 *     {@link org.springframework.web.servlet.DispatcherServlet#onRefresh(ApplicationContext)}
 *          - initMultipartResolver 初始化上传解析
 *          - initLocaleResolver    初始化Locale配置
 *          - initThemeResolver     初始化模板配置（JSP模板、FreeMarker、Velocity）
 *          - initHandlerMappings   映射处理
 *          - initHandlerAdapters   映射的适配器
 *          - initHandlerExceptionResolvers 异常的解析处理
 *          - initRequestToViewNameTranslator   视图渲染处理
 *          - initViewResolvers 视图解析处理
 *          - initFlashMapManager   临时信息管理
 * <p>
 *     在 DispatcherServlet 父类（抽象类） FrameworkServlet 中的初始化方法
 *     {@link org.springframework.web.servlet.FrameworkServlet#initWebApplicationContext()}
 *     关注其中的 configureAndRefreshWebApplicationContext 方法
 *     {@link org.springframework.web.servlet.FrameworkServlet#configureAndRefreshWebApplicationContext(ConfigurableWebApplicationContext)}
 *     该方法需要关注最后三个调用
 *      - postProcessWebApplicationContext  进行一些PostBean类似的处理
 *      - applyInitializers     初始化类依次执行 initialize 方法
 *      - refresh       刷新上下文
 *          该方法调用的是 ConfigurableApplicationContext 中的刷新方法，与 Spring 中调用的完全相同，
 *          {@link org.springframework.context.ConfigurableApplicationContext#refresh()}
 *          也就是说，有些Bean，SpringMVC能加载，Spring也能加载。
 *          因此，在SpringMVC与Spring整合的过程中，如果重复加载，可能会出现意料之外的问题。
 * @author caimeng
 * @date 2024/9/23 10:57
 */
package com.example.ssm.mvcb.servlet;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.ConfigurableWebApplicationContext;