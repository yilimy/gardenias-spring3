/**
 * 自定义404页面未发现处理
 * <a href="https://www.bilibili.com/video/BV11fHSeAERB/" />
 * <p>
 *     访问一个不存在的页面，比如
 *     <a href="http://localhost/pages/message/hello" />
 *     默认该页面是由tomcat提供。
 *     想要进行修改，可以通过修改 web.xml 来实现。
 *     如果不想使用 web.xml,
 *          SpringMVC 中并没有提供对于404错误的处理，需要覆写部分方法
 *          在分发器 doDispatch 中操作中调用了 noHandlerFound 方法
 *              {@link org.springframework.web.servlet.DispatcherServlet#doDispatch(HttpServletRequest, HttpServletResponse)}
 *              {@link org.springframework.web.servlet.DispatcherServlet#noHandlerFound(HttpServletRequest, HttpServletResponse)}
 *     自定义分发器
 *          {@link com.example.ssm.mvcb.servlet.YootkDispatcherServlet}
 *     修改启动类，使自定义分发器生效
 *          {@link com.example.ssm.mvcb.web.config.StartWebAnnotationApplication#createDispatcherServlet(WebApplicationContext)}
 *     转发到的控制层
 *          {@link com.example.ssm.mvcb.action.NotFoundAction#notFound()}
 *     404页面的地址: /WEB-INF/pages/notfound.jsp
 * @author caimeng
 * @date 2024/9/20 15:48
 */
package com.example.ssm.mvcb.unfound;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.context.WebApplicationContext;