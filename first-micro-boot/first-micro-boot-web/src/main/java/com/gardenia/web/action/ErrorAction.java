package com.gardenia.web.action;

import cn.hutool.core.map.MapUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * 错误页的处理
 * @author caimeng
 * @date 2025/2/14 17:34
 */
@RestController
@RequestMapping("/errors/*")
public class ErrorAction {

    @RequestMapping("error_404")
    public Object errorCode404(){
        ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (sra != null) {
            HttpServletRequest request = sra.getRequest();
            HttpServletResponse response = sra.getResponse();
            //noinspection DataFlowIssue
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return MapUtil.builder()
                    .put("status", HttpServletResponse.SC_NOT_FOUND)
                    .put("content", "无法找到用户访问路径")
                    // 获取之前的来源
                    .put("referer", request.getHeader("Referer"))
                    // 访问路径
                    .put("path", request.getRequestURI())
                    .build();
        }
        return null;
    }

    @RequestMapping("error_500")
    public Object errorCode500(){
        ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (sra != null) {
            HttpServletRequest request = sra.getRequest();
            HttpServletResponse response = sra.getResponse();
            //noinspection DataFlowIssue
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return MapUtil.builder()
                    .put("status", HttpServletResponse.SC_INTERNAL_SERVER_ERROR)
                    .put("content", "服务器端程序出错")
                    // 获取之前的来源
                    .put("referer", request.getHeader("Referer"))
                    // 访问路径
                    .put("path", request.getRequestURI())
                    .build();
        }
        return null;
    }
}
