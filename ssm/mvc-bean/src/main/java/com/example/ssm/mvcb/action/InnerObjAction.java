package com.example.ssm.mvcb.action;

import cn.hutool.core.map.MapUtil;
import com.example.ssm.mvcb.action.abs.AbstractAction;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Map;

/**
 * @author caimeng
 * @date 2024/9/14 17:10
 */
@Slf4j
@Controller
@RequestMapping("/pages/web/*")
public class InnerObjAction extends AbstractAction {


    /**
     * 获取内置对象信息
     * 访问 <a href="http://localhost/pages/web/object" />
     * @return 返回值
     */
    @SuppressWarnings("DataFlowIssue")
    @RequestMapping("/object")
    @ResponseBody
    public Object object() {
        // 获取servlet的属性信息
        ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = sra.getRequest();
        HttpServletResponse response = sra.getResponse();
        HttpSession session = request.getSession();
        // 获取application对象
        ServletContext context = request.getServletContext();
        Map<String, Object> retMap = MapUtil.<String, Object>builder()
                .put("【request】contextPath", request.getContextPath())
                .put("【response】Locale", response.getLocale())
                .put("【session】SessionId", session.getId())
                .put("【application】ResponseCharacterEncoding", context.getResponseCharacterEncoding())
                .build();
        log.info("获取内置对象的信息:{}", retMap);
        return retMap;
    }

    /**
     * 使用注入的方式，获取SpringMVC的内置对象
     * 访问 <a href="http://localhost/pages/web/action" />
     * <p>
     *     适用于控制层
     * @param request request
     * @param response response
     * @param session session
     * @return Object
     */
    @RequestMapping("/action")
    @ResponseBody
    public Object action(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
        // 获取application对象
        ServletContext context = request.getServletContext();
        Map<String, Object> retMap = MapUtil.<String, Object>builder()
                .put("【request】contextPath", request.getContextPath())
                .put("【response】Locale", response.getLocale())
                .put("【session】SessionId", session.getId())
                .put("【application】ResponseCharacterEncoding", context.getResponseCharacterEncoding())
                .build();
        log.info("获取内置对象的信息:{}", retMap);
        return retMap;
    }
}
