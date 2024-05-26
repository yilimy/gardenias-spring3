package com.example.boot3.valid.service;

import com.example.boot3.valid.pojo.UserValid;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.Valid;

/**
 * @author caimeng
 * @date 2024/5/17 17:24
 */
public interface UserValidService {

    /**
     * 该处的 Valid 不能删除，
     * 接口与实现的参数注解需要保持一致，否则不会触发校验逻辑
     * <p>
     *     报错信息 {@link jakarta.validation.ConstraintDeclarationException}:
     *     HV000151: A method overriding another method must not redefine the parameter constraint configuration, but method UserValidServiceImpl#checkUser(UserValid) redefines the configuration of UserValidService#checkUser(UserValid).
     *
     * @param user 待校验对象
     */
    void checkUser(@Valid UserValid user);

    /**
     * 对照组，该注解(Validated)不会触发参数校验
     * @param user 待校验对象
     */
    void checkUserNoEffect(@Validated UserValid user);
}
