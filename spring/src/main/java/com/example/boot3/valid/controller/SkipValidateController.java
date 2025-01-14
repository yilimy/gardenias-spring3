package com.example.boot3.valid.controller;

import com.example.boot3.valid.config.SkipValidator;
import com.example.boot3.valid.pojo.UserValid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 跳过验证
 * @author caimeng
 * @date 2025/1/14 11:29
 */
@Slf4j
@RestController
public class SkipValidateController {
    // spring 的本地验证工厂对象
    @Autowired
    private LocalValidatorFactoryBean localValidatorFactoryBean;

    /**
     * 数据绑定
     * @param binder 数据绑定对象
     */
    @InitBinder("user")     // 必须与所在controller中的方法参数名相同
    public void initBinder(WebDataBinder binder) {
        // 找到指定方法后，设定自定义验证器
        binder.setValidator(new SkipValidator(localValidatorFactoryBean));
    }

    /**
     * 验证跳过属性验证
     * @param user 请求参数
     * @return 请求结果
     */
    @RequestMapping(value = "/skip/checkUser", method = RequestMethod.POST)
    public String skipReqParamTest(@Validated @RequestBody UserValid user) {
        log.info("跳过了age的校验 : {}", user);
        return "通过了校验";
    }
}
