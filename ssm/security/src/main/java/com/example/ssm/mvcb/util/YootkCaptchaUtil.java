package com.example.ssm.mvcb.util;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Objects;

/**
 * @author caimeng
 * @date 2024/10/21 11:41
 */
@Slf4j
public class YootkCaptchaUtil {
    // 验证码名称
    public static final String CAPTCHA_NAME = "yootk-captcha";
    public static final int WIDTH = 80;
    public static final int HEIGHT = 25;
    // 随机数数量
    public static final int LENGTH = 4;

    @SneakyThrows
    public static void outputCaptcha() {
        ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (Objects.nonNull(sra)) {
            HttpServletRequest request = sra.getRequest();
            HttpServletResponse response = sra.getResponse();
            HttpSession session = request.getSession();
            if (Objects.nonNull(response)) {
                response.setHeader(HttpHeaders.PRAGMA, "No-cache");
                response.setHeader(HttpHeaders.CACHE_CONTROL, "no-cache");
                response.setDateHeader(HttpHeaders.EXPIRES, 0);
                response.setContentType(MediaType.IMAGE_PNG_VALUE);
                LineCaptcha lineCaptcha = CaptchaUtil.createLineCaptcha(WIDTH, HEIGHT, LENGTH, 80);
                String code = lineCaptcha.getCode();
                log.info("验证码, code={}", code);
                session.setAttribute(CAPTCHA_NAME, code);
                lineCaptcha.write(response.getOutputStream());
            }
        }
    }
}
