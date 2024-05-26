package com.example.boot3.valid.service.impl;

import com.example.boot3.valid.pojo.UserValid;
import com.example.boot3.valid.service.UserValidService;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.validation.beanvalidation.MethodValidationInterceptor;

import jakarta.validation.Valid;

/**
 * @author caimeng
 * @date 2024/5/17 17:24
 */
@Slf4j
// Validated是spring的注解，会触发校验逻辑
@Validated
@Service
public class UserValidServiceImpl implements UserValidService {
    /**
     * 该处的 Valid 不能替换成 Validated，替换后不会触发验证逻辑
     * @see MethodValidationInterceptor#invoke(MethodInvocation)
     * @param user 待校验对象
     */
    @Override
    public void checkUser(@Valid UserValid user) {
        System.out.printf("checkUser 通过了验证: %s", user);
    }

    @Override
    public void checkUserNoEffect(@Validated UserValid user) {
        System.out.printf("checkUserNoEffect 通过了验证: %s", user);
    }
}
