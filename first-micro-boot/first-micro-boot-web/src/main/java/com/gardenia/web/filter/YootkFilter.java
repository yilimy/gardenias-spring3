package com.gardenia.web.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * 测试：一个路径匹配到多个过滤器
 * @author caimeng
 * @date 2025/2/7 18:24
 */
//@WebFilter("/orders/*")
public class YootkFilter extends HttpFilter {

    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        System.out.println("【YootkFilter】" + "*".repeat(30));
        chain.doFilter(req, res);
    }
}
