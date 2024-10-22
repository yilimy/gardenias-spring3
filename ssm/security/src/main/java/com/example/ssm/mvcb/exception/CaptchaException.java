package com.example.ssm.mvcb.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * 验证码异常
 * @author caimeng
 * @date 2024/10/21 14:00
 */
public class CaptchaException extends AuthenticationException {

    public CaptchaException(String msg) {
        super(msg);
    }
}
