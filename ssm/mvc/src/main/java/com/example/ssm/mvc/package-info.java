/**
 * 配置SpringMVC开发环境
 * <a href="https://www.bilibili.com/video/BV15tYseqET2/" />
 * <p>
 *     如果按照以往的老项目的编写方式，项目里肯定会包含有很多的spring-*.xml类型的配置文件，
 *     这样在进行项目维护的时候比较麻烦，但是相对于Bean的方式更简单。
 *     SpringMVC的核心依然是基于传统的Servlet开发模式处理，
 *     JakartaEE提供的Servlet可以接收以及回应用户的请求，
 *     这种Servlet的实现在SpringMVC里面就可以基于DispatcherServlet类进行处理。
 * <p>
 *     请求流程
 *     ServletContextListener -- Filter -- HttpServlet
 *     容器监听                    过滤器      请求处理
 * <p>
 *     创建spring配置文件： src/main/resources/spring/spring-base.xml
 * @author caimeng
 * @date 2024/8/23 17:54
 */
package com.example.ssm.mvc;