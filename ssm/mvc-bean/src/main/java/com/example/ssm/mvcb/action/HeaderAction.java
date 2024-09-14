package com.example.ssm.mvcb.action;

import cn.hutool.core.map.MapUtil;
import com.example.ssm.mvcb.action.abs.AbstractAction;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author caimeng
 * @date 2024/9/14 17:39
 */
@Slf4j
@Controller
@RequestMapping("/pages/header/*")
public class HeaderAction extends AbstractAction {
    /**
     * 通过注解获取请求头信息
     * <a href="http://localhost/pages/header/get" />
     * @param yootk yootk
     * @param acceptEncoding acceptEncoding
     * @param userAgent userAgent
     * @param cookie cookie
     * @return json
     */
    @RequestMapping("/get")
    @ResponseBody
    public Object get(
            @RequestHeader(name = "yootk", defaultValue = "www.yootk.com") String yootk,
            @RequestHeader(name = "Accept-Encoding") String acceptEncoding,
            @RequestHeader(name = "user-agent") String userAgent,
            @RequestHeader(name = "cookie") String cookie
            ) {
        /*
         * 按以往的做法，请求头中的信息要通过内置对象获取，现在直接通过注解注入
         * 优势：
         *      接收参数这种事情，尽量不要在核心业务中处理
         */
        return MapUtil.builder()
                .put("【header】yootk", yootk)
                .put("【header】acceptEncoding", acceptEncoding)
                .put("【header】userAgent", userAgent)
                .put("【header】cookie", cookie)
                .build();
    }

    /**
     * <a href="http://localhost/pages/header/set" />
     * @param response response
     * @return string
     */
    @RequestMapping("/set")
    @ResponseBody
    public Object set(HttpServletResponse response) {
        /*
         * java.lang.IllegalArgumentException
         *      代码点[27,792]处的Unicode字符[沐]无法编码，因为它超出了允许的0到255范围。
         */
        response.setHeader("yootk", "沐言优拓：www.yootk.com");
        response.setHeader("Accept-Encoding", "UTF-8");
        return "Set Header Success";
    }
}
