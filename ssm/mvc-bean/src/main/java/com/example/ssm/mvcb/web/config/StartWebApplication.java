package com.example.ssm.mvcb.web.config;

import jakarta.servlet.DispatcherType;
import jakarta.servlet.FilterRegistration;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletRegistration;
import org.springframework.lang.NonNull;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.DispatcherServlet;

import java.util.EnumSet;

/**
 * 启动类
 * <p>
 *     spring的容器启动类
 *     {@link org.springframework.web.SpringServletContainerInitializer}
 *     感兴趣 {@link jakarta.servlet.annotation.HandlesTypes}的是 {@link WebApplicationInitializer}
 *     SpringServletContainerInitializer 会将所有的 WebApplicationInitializer 排序，并依次调用 onStartup 方法
 * @author caimeng
 * @date 2024/8/27 17:29
 */
public class StartWebApplication implements WebApplicationInitializer {
    /**
     * 启动
     * <p>
     *     1. 实例化上下文
     *     2. 扫描 beans
     *     3. 刷新上下文
     *     4. 注册分发器
     *     5. 注册过滤器
     *     6. 测试： 访问 <a href="http://localhost/pages/message/echo?message=123456" />
     *          这个链接最终会通过 action 跳转到 webapp 目录下的 jsp 页面
     * @param servletContext the {@code ServletContext} to initialize
     */
    @Override
    public void onStartup(@NonNull ServletContext servletContext) {
        // 在该类中可以直接获取到当前的 servletContext 接口实例（web上下文对象）
        // 注解上下文启动
        AnnotationConfigWebApplicationContext webContext = new AnnotationConfigWebApplicationContext();
        // 配置扫描包
        webContext.scan("com.example.ssm.mvcb.action","com.example.ssm.mvcb.service");
        // 刷新上下文
        webContext.refresh();
        // 在SpringMVC中要设置一个 DispatcherServlet 分发处理类
        ServletRegistration.Dynamic servletRegistration =
                servletContext.addServlet("DispatcherServlet", new DispatcherServlet(webContext));
        // 容器启动的时候加载
        servletRegistration.setLoadOnStartup(1);
        // 分发路径
        servletRegistration.addMapping("/");
        // 在整个web项目里，除了包含有分发servlet之外，还需要过滤器
        // 编码过滤器
        FilterRegistration.Dynamic encodingFilter =
                servletContext.addFilter("EncodingFilter", new CharacterEncodingFilter());
        encodingFilter.setInitParameter("encoding", "UTF-8");
        // 过滤的请求方式，请求的前后位置，以及请求的路径
        encodingFilter.addMappingForServletNames(
                EnumSet.of(DispatcherType.FORWARD, DispatcherType.REQUEST), false, "/*");
    }
}
