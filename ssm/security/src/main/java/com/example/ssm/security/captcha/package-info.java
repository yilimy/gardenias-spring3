/**
 * 集成验证码功能
 * <a href="https://www.bilibili.com/video/BV1jGtJeyENm/" />
 * <p>
 *     工具类 {@link com.example.ssm.mvcb.util.YootkCaptchaUtil}
 *     控制层 {@link com.example.ssm.mvcb.action.CaptchaAction}
 *     异常处理类 {@link com.example.ssm.mvcb.exception.CaptchaException}
 *     验证码过滤器 {@link com.example.ssm.mvcb.filter.CaptchaAuthenticationFilter}
 *     追加错误处理的配置 {@link com.example.ssm.mvcb.config.WebMVCSecurityConfiguration#authenticationFailureHandler()}
 *     追加自定义过滤器 {@link com.example.ssm.mvcb.config.WebMVCSecurityConfiguration#filterChain(HttpSecurity, UserDetailsService, JdbcTokenRepositoryImpl, SimpleUrlAuthenticationFailureHandler)}
 * @author caimeng
 * @date 2024/10/21 14:03
 */
package com.example.ssm.security.captcha;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;