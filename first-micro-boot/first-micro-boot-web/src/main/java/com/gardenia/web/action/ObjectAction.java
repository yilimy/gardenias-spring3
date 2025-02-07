package com.gardenia.web.action;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/**
 * @author caimeng
 * @date 2025/2/7 14:02
 */
@RestController
@RequestMapping("/object/*")
public class ObjectAction {

    /**
     * 测试：获取Servlet内置对象(五个)
     * @see jakarta.servlet.ServletRequest
     * @see jakarta.servlet.ServletResponse
     * @see jakarta.servlet.ServletContext
     * @see jakarta.servlet.ServletConfig
     * @see jakarta.websocket.Session
     * @return 相应
     */
    @RequestMapping("first")
    public Object firstShow(HttpServletRequest request) {
        return Map.of(
                "【request】contextPath", request.getContextPath(),
                "【request】messageParam", Optional.of(request).map(r -> r.getParameter("message")).orElse(""),
                "【request】method", request.getMethod(),
                "【session】sessionId", request.getSession().getId(),
                // 获取虚拟主机名
                "【application】virtualServerName", request.getServletContext().getVirtualServerName(),
                "【config】initParameter", request.getServletContext().getInitParameter("teacher")
        );
    }

    @RequestMapping("second")
    public Object secondShow() {
        ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (Objects.isNull(sra)) {
            throw new RuntimeException("sra is NULL");
        }
        HttpServletRequest request = sra.getRequest();
        return Map.of(
                "【request】contextPath", request.getContextPath(),
                "【request】messageParam", Optional.of(request).map(r -> r.getParameter("message")).orElse(""),
                "【request】method", request.getMethod(),
                "【session】sessionId", request.getSession().getId(),
                // 获取虚拟主机名
                "【application】virtualServerName", request.getServletContext().getVirtualServerName(),
                "【config】initParameter", request.getServletContext().getInitParameter("teacher")
        );
    }
}
