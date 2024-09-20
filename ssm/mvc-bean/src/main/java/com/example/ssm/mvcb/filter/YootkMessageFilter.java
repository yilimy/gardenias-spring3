package com.example.ssm.mvcb.filter;

import com.example.ssm.mvcb.service.IMessageService;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.context.support.WebApplicationContextUtils;

import java.io.IOException;

/**
 * 自定义的过滤器
 * @author caimeng
 * @date 2024/9/20 18:27
 */
@Slf4j
public class YootkMessageFilter implements Filter {
//    @Autowired  // 此注解无效，因为不在 Spring 容器中
    private IMessageService messageService; // 接口定义

    /**
     * 初始化
     * @see WebApplicationContextUtils#getWebApplicationContext(ServletContext, String)
     * @see org.springframework.web.context.WebApplicationContext
     * @param filterConfig a <code>FilterConfig</code> object containing the filter's configuration and initialization
     * parameters
     */
    @SuppressWarnings("DataFlowIssue")
    @Override
    public void init(FilterConfig filterConfig) {
        /*
         * 通过 ServletContext 上下文获取 SpringWEB 上下文，再通过 SpringWEB 上下文获取 Spring 容器中的bean对象 (父子关系)
         * ServletContext -> WebApplicationContext -> ApplicationContext -> bean
         * WebApplicationContext extends ApplicationContext
         */
        this.messageService = WebApplicationContextUtils.findWebApplicationContext(filterConfig.getServletContext())
                .getBean(IMessageService.class);
    }

    /**
     * 拦截逻辑
     * <p>
     *     访问 <a href="http://localhost/pages/message/web_info_config/echo?message=123456" /> 查看日志
     *     {@link com.example.ssm.mvcb.action.MessageAction#echoWebInfoConfig(String)}
     *          【消息过滤】【ECHO】沐言科技：www.yootk.com
     *          GET "/pages/message/web_info_config/echo?message=123456", parameters={masked}
     *          ...
     *          【消息过滤】【ECHO】沐言科技：www.yootk.com
     *          GET "/yootk-css/style.css", parameters={}
     *          【消息过滤】【ECHO】沐言科技：www.yootk.com
     *          GET "/yootk-js/yootk.js", parameters={}
     * @param request the <code>ServletRequest</code> object contains the client's request
     * @param response the <code>ServletResponse</code> object contains the filter's response
     * @param chain the <code>FilterChain</code> for invoking the next filter or the resource
     * @throws IOException IOException
     * @throws ServletException ServletException
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        log.info("【消息过滤】{}", messageService.echo("沐言科技：www.yootk.com"));
        chain.doFilter(request, response);  // 请求转发
    }
}
