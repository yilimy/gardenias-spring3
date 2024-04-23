package com.example.dubbo.provider.controller;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.dubbo.common.pojo.User;

/**
 * @author caimeng
 * @date 2024/4/18 10:54
 */
@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    @GetMapping("/getUser")
    public String getUser() {
        User liLei = User.builder().name("LiLei").age(18).build();
        String userStr = JSON.toJSONString(liLei);
        log.info("userStr = {}", userStr);
        return userStr;
    }
}
