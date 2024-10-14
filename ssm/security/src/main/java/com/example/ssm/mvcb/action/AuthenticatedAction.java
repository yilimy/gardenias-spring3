package com.example.ssm.mvcb.action;

import cn.hutool.core.map.MapUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 该action配置了Security的规则
 * {@link com.example.ssm.mvcb.config.WebMVCSecurityConfiguration#filterChain(HttpSecurity)}
 * <p>
 *     用户名密码  yootk / hello
 *
 * @author caimeng
 * @date 2024/10/14 10:24
 */
@Slf4j
@Controller
@RequestMapping("/pages/authenticated")
public class AuthenticatedAction {

    /**
     * 访问地址 <a href="http://localhost/pages/authenticated/info" />
     * <p>
     *     该地址需要登录认证
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
}
