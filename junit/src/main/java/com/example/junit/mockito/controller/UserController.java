package com.example.junit.mockito.controller;

import com.example.junit.mockito.bean.req.UserAddReq;
import com.example.junit.mockito.bean.vo.UserVO;
import com.example.junit.mockito.service.UserService;
import jakarta.annotation.Resource;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author caimeng
 * @date 2024/6/21 16:53
 */
@Slf4j
@RestController
@Validated
public class UserController {
    @Resource
    private UserService userService;

    @GetMapping("/selectById")
    public UserVO selectById(@NotNull Long userId) {
        log.info("selectById, userId={}", userId);
        return userService.selectById(userId);
    }

    @PostMapping("/add")
    public String add(@Validated @RequestBody UserAddReq userAddReq) {
        log.info("add, userAddReq={}", userAddReq);
        userService.add(userAddReq.getUsername(), userAddReq.getPhone(), userAddReq.getFeatures());
        return "ok";
    }
}
