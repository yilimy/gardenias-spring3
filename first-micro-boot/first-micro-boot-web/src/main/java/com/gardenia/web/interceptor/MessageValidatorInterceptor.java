package com.gardenia.web.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.lang.NonNull;
import org.springframework.util.ObjectUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.HashMap;
import java.util.Map;

/**
 * @author caimeng
 * @date 2025/2/21 14:06
 */
public class MessageValidatorInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object handler) {
        Map<String, String> errors = new HashMap<>();
        if (ObjectUtils.isEmpty(request.getParameter("title"))) {
            errors.put("title", "标题[title]不能为空");
        }
        if (ObjectUtils.isEmpty(request.getParameter("pubDate"))) {
            errors.put("pubDate", "发布日期[pubDate]不能为空");
        }
        if (ObjectUtils.isEmpty(request.getParameter("content"))) {
            errors.put("content", "消息内容[content]不能为空");
        }
        if(errors.size() == 0) {
            return true;
        }
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=UTF-8");
        // 响应错误信息
        ObjectMapper mapper = new ObjectMapper();
        try {
            String errorString = mapper.writeValueAsString(errors);
            // 响应内容
            response.getWriter().write(errorString);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 响应状态码
        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        // 不跳转到控制层
        return false;
    }
}
