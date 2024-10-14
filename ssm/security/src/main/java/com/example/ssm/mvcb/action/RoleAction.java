package com.example.ssm.mvcb.action;

import cn.hutool.core.map.MapUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author caimeng
 * @date 2024/10/14 11:45
 */
@Slf4j
@Controller
@RequestMapping("/pages/role")
public class RoleAction {

    /**
     * 访问地址 <a href="http://localhost/pages/role/info" />
     * <p>
     *     该地址需要登录认证，
     *     要求 yootk / hello 有角色 ADMIN， 配置的值为 ROLE_ADMIN，"ROLE_"为前缀
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
     * 访问地址 <a href="http://localhost/pages/role/any" />
     * 访问权限中含有任意角色中的一个
     * <p>
     *     lee / hello      能访问 /any, 但是不能访问 /info
     * @return 返回结果
     */
    @GetMapping("/any")
    @ResponseBody
    public Object any(){
        return MapUtil.builder()
                .put("yootk", "沐言科技 any")
                .put("lee", "可爱的小李老师 any")
                .build();
    }
}
