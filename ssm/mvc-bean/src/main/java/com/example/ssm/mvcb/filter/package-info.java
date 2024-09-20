/**
 * WebApplicationContextUtils
 * <a href="https://www.bilibili.com/video/BV14oHSeeEzQ/" />
 * @see org.springframework.web.context.WebApplicationContext
 * <p>
 *     SpringMVC整体的处理核心是在 DispatcherServlet 类里面提供的，这个类运行在 SpringWEB 容器中（WebApplicationContext）。
 *     但是为了与 Spring 框架整合， WEB容器与Spring容器形成了父子关系。
 *     Tomcat容器 -> SpringWEB容器 -> Spring容器
 * <p>
 *     在 JakartaEE 标准中，WEB核心组件有三个：Servlet、Filter、Listener
 * <p>
 *     如果需要在过滤器中使用 WebApplicationContext 中的内容，就需要使用 WebApplicationContextUtils 工具类来获取。
 *     {@link org.springframework.web.context.support.WebApplicationContextUtils}
 * <p>
 *     自定义的拦截器
 *          {@link com.example.ssm.mvcb.filter.YootkMessageFilter}
 *     添加过滤器
 *          {@link com.example.ssm.mvcb.web.config.StartWebAnnotationApplication#getServletFilters()}
 *     访问 <a href="http://localhost/pages/message/web_info_config/echo?message=123456" /> 查看日志
 * @author caimeng
 * @date 2024/9/20 18:02
 */
package com.example.ssm.mvcb.filter;