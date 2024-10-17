package com.example.ssm.mvcb.action;

import cn.hutool.core.map.MapUtil;
import com.example.ssm.mvcb.config.WebMVCSecurityConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * @author caimeng
 * @date 2024/9/29 15:28
 */
@Slf4j
@Controller
@RequestMapping("/pages/message")
public class MessageAction {

    /**
     * 访问地址 <a href="http://localhost/pages/message/info" />
     * <p>
     *     结果跳转到了 <a href="http://localhost/login" /> 登录页
     *     {@link com.example.ssm.mvcb.service.YootkUserDetailsService#loadUserByUsername(String)}
     * @return 返回结果
     */
    @GetMapping("/info")
    @ResponseBody
    public Object info(){
        return MapUtil.builder()
                .put("yootk", "沐言科技")
                .put("lee", "可爱的小李老师")
                .build();
    }

    /**
     * 测试CSRF 跨域访问
     * 访问地址 <a href="http://localhost/pages/message/input" />
     * <p>
     *     该表单提交时，会报错 403，用户权限不足，实际上是 CSRF 保护机制起作用了。
     *     解决方案：
     *      在input中添加隐藏域，_csrf.parameterName=_csrf.token
     * @return 页面地址
     */
    @GetMapping("/input")
    public String input(){
        // 输入的表单路径
        return "/message/input";
    }

    /**
     * 内网访问，不启动 CSRF 功能
     * 访问地址 <a href="http://localhost/pages/message/inputInner" />
     * 在配置 {@link WebMVCSecurityConfiguration#filterChain(HttpSecurity)} 中关闭 CSRF
     * @return 页面地址
     */
    @GetMapping("/inputInner")
    public String inputInner(){
        // 输入的表单路径
        return "/message/input_inner";
    }


    /**
     * 页面 {@link MessageAction#input()} 响应接口
     * @param msg 提交数据
     * @return 响应体
     */
    @PostMapping("/echo")
    @ResponseBody
    public Object echo(String msg){
        return Map.of("result", "【echo】" + msg);
    }
}
