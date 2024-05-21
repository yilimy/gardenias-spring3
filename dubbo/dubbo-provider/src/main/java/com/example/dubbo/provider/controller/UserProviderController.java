package com.example.dubbo.provider.controller;

import com.alibaba.fastjson.JSON;
import com.example.dubbo.common.pojo.User;
import com.example.dubbo.provider.service.BeanService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author caimeng
 * @date 2024/4/29 10:39
 */
@Slf4j
@RequestMapping("/provider")
@RestController
public class UserProviderController {
    @Autowired
    private BeanService beanService;

    @GetMapping("/ifSpringBean")
    public Boolean ifSpringBean() {
        return beanService.ifSpringBean();
    }
}
