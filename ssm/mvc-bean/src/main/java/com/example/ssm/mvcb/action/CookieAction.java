package com.example.ssm.mvcb.action;

import cn.hutool.core.map.MapUtil;
import com.example.ssm.mvcb.action.abs.AbstractAction;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author caimeng
 * @date 2024/9/14 18:21
 */
@Slf4j
@Controller
@RequestMapping("/pages/cookie/*")
public class CookieAction extends AbstractAction {

    /**
     * <a href="http://localhost/pages/cookie/get" />
     * @param yootk yootk
     * @param jixianit jixianit
     * @param id id
     * @return map
     */
    @RequestMapping("/get")
    @ResponseBody
    public Object get(
            @CookieValue(name = "yootk", defaultValue = "yootk.com") String yootk,
            @CookieValue(name = "jixianit", defaultValue = "jixianit.com")String jixianit,
            @CookieValue(name = "JESESSIONID", defaultValue = "YOOTK-DEFAULT")String id){
        return MapUtil.builder()
                .put("【Cookie】yootk", yootk)
                .put("【Cookie】jixianit", jixianit)
                .put("【Cookie】JESESSIONID", id)
                .build();
    }

    /**
     * <a href="http://localhost/pages/cookie/set" />
     * @param response response
     * @return string
     */
    @RequestMapping("/set")
    @ResponseBody
    public Object set(HttpServletResponse response){
        Cookie c1 = new Cookie("yootk", "www.yootk.com");
        Cookie c2 = new Cookie("jixianit", "www.jixianit.com");
        c1.setPath("/");
        c2.setPath("/");
        // 单位：秒
        c1.setMaxAge(3600);
        c2.setMaxAge(3600);
        response.addCookie(c1);
        response.addCookie(c2);
        return "Cookie Set Success";
    }
}
