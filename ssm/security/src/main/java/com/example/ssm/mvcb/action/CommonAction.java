package com.example.ssm.mvcb.action;

import com.example.ssm.mvcb.config.WebMVCSecurityConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 公用的action
 * <p>
 *     应对的是自定义登录的一些控制层
 *     {@link WebMVCSecurityConfiguration#filterChain(HttpSecurity)}
 *     http.formLogin()
 *     http.logout()
 *     http.exceptionHandling()
 * @author caimeng
 * @date 2024/10/17 17:39
 */
@Slf4j
@Controller
public class CommonAction {

    /**
     * 访问地址 <a href="http://localhost" />
     * @return 跳转地址
     */
    @RequestMapping("/")
    public String index() {
        return "/index";
    }

    @RequestMapping(WebMVCSecurityConfiguration.LOGIN_PAGE)
    public String login() {
        // 登录表单页
        return "/login";
    }

    @RequestMapping(WebMVCSecurityConfiguration.LOGOUT_PAGE)
    public String logout(Model model) {
        model.addAttribute("msg", "用户注销成功，欢迎下次访问");
        // 默认页
        return "/index";
    }

    @RequestMapping(WebMVCSecurityConfiguration.ERROR_403)
    public String errorPage403() {
        // 错误的显示页
        return "/common/error_page_403";
    }

}
