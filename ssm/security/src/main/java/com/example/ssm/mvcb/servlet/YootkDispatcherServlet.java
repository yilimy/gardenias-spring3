package com.example.ssm.mvcb.servlet;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.lang.NonNull;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

/**
 * 自定义的分发器
 * <p>
 *     通过转发实现，
 *     需要路径解析器的支持
 *          security.enable=true
 *          {@link com.example.ssm.mvcb.config.ResourceViewConfig}
 * @author caimeng
 * @date 2024/9/20 15:59
 */
public class YootkDispatcherServlet extends DispatcherServlet {
    public YootkDispatcherServlet(WebApplicationContext webApplicationContext) {
        super(webApplicationContext);   // 调用父类构造
    }

    @Override   // 根据需要进行父类方法的覆写
    protected void noHandlerFound(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response) throws Exception {
        // 转发到控制层: /notfound
        response.sendRedirect("/notfound");
    }
}
