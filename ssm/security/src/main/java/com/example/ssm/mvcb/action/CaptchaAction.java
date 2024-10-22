package com.example.ssm.mvcb.action;

import com.example.ssm.mvcb.util.YootkCaptchaUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * 验证码控制器
 * @author caimeng
 * @date 2024/10/21 13:50
 */
@Controller
public class CaptchaAction {

    /**
     * 访问地址 <a href="http://localhost/captcha" />
     * @return 验证码响应结果
     */
    @RequestMapping("/captcha")
    public ModelAndView captcha() {
        // 生成验证码
        YootkCaptchaUtil.outputCaptcha();
        return null;
    }
}
