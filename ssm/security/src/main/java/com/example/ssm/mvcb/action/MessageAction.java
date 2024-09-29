package com.example.ssm.mvcb.action;

import cn.hutool.core.map.MapUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
