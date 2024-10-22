package com.example.ssm.mvcb.filter;

import com.example.ssm.mvcb.config.WebMVCSecurityConfiguration;
import com.example.ssm.mvcb.exception.CaptchaException;
import com.example.ssm.mvcb.util.YootkCaptchaUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Setter;
import org.springframework.lang.NonNull;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.util.ObjectUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * @author caimeng
 * @date 2024/10/21 14:46
 */
public class CaptchaAuthenticationFilter extends OncePerRequestFilter { // 执行一次处理
    // 失败处理
    @Setter
    private AuthenticationFailureHandler authenticationFailureHandler;
    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {
        if (WebMVCSecurityConfiguration.LOGIN_ACTION.equals(request.getRequestURI())
                && "POST".equals(request.getMethod())) {    // 登录表单操作
            String captchaSession = (String) request.getSession().getAttribute(YootkCaptchaUtil.CAPTCHA_NAME);
            // 验证码的参数名称
            String codeParameter = "code";
            String captchaInput = request.getParameter(codeParameter);
            if (ObjectUtils.isEmpty(captchaSession)
                    || ObjectUtils.isEmpty(captchaInput)
                    || !captchaSession.equalsIgnoreCase(captchaInput)) {
                authenticationFailureHandler.onAuthenticationFailure(request, response, new CaptchaException("错误的验证码"));
            } else {
                // 执行下一个过滤
                filterChain.doFilter(request, response);
            }
        } else {
            filterChain.doFilter(request, response);
        }
    }
}
