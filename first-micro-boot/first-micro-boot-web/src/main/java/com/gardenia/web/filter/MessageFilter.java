package com.gardenia.web.filter;

import com.gardenia.web.action.MessageAction;
import com.gardenia.web.vo.Message;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.util.StringUtils;

import java.io.IOException;

/**
 * 过滤器
 * <p>
 *     因为该过滤器对异步响应测试产生了影响，关闭该过滤器 @date 2025-04-15
 * @author caimeng
 * @date 2025/2/7 17:54
 */
//@WebFilter("/*")    // 过滤路径
//@Component
public class MessageFilter extends HttpFilter {

    /**
     * 对指定接口进行拦截 {@link MessageAction#transStrToDate(Message)}
     * <p>
     *     需要在启动类上添加Servlet组件扫描的注解 {@link org.springframework.boot.web.servlet.ServletComponentScan}
     * <p>
     *     过滤器属于JavaWeb的原生开发组件，根据实际情况来决定是否引入。
     *     如果一个路径下，存在多个过滤器，是通过过滤器名称来进行排序的
     * @param req a {@link HttpServletRequest} object that contains the request the client has made of the filter
     *
     * @param res a {@link HttpServletResponse} object that contains the response the filter sends to the client
     *
     * @param chain the <code>FilterChain</code> for invoking the next filter or the resource
     *
     * @throws IOException  IOException
     * @throws ServletException ServletException
     */
    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        if ("/message/transStrToDate".equals(req.getRequestURI())) {
            // 接收title参数
            String title = req.getParameter("title");
            if (StringUtils.hasLength(title)) {
                // 【MessageFilter】title参数内容为: 消息标题
                System.out.println("【MessageFilter】title参数内容为: " + title);
            }
        }
        chain.doFilter(req, res);
    }
}
