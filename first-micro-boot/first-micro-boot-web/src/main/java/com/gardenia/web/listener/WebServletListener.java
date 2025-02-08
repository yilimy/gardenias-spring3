package com.gardenia.web.listener;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

/**
 * Servlet监听器
 * <p>
 *     1. 需要在启动类上配置扫描路径（防止Bean的重复注入，也可以不用指定路径，但是ServletComponentScan得要）
 *     2. 可以通过配置类进行注入，假如这样做，扫描配置的时候需要做回避，防止重复注入。
 * @author caimeng
 * @date 2025/2/8 15:53
 */
@WebListener
public class WebServletListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {       // 上下文初始化
        System.out.println("【WebServletListener】Servlet初始化：" + sce.getServletContext().getServerInfo());
        System.out.println("【WebServletListener】Servlet初始化：" + sce.getServletContext().getRealPath("/"));
        System.out.println("【WebServletListener】Servlet初始化：" + sce.getServletContext().getVirtualServerName());
    }
}
