package com.gardenia.web.action;

import com.gardenia.common.action.abs.AbstractBaseAction;
import com.gardenia.web.config.WebInterceptorConfigure;
import com.gardenia.web.interceptor.MessageValidatorInterceptor;
import com.gardenia.web.vo.MessageForJSR303;
import com.gardenia.web.vo.MessageForValidate;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;

/**
 * 数据验证控制层
 * <p>
 *     1. 创建数据验证拦截器
 *     2    {@link MessageValidatorInterceptor}
 *     2. 注册数据验证拦截器
 *          {@link WebInterceptorConfigure#addInterceptors(InterceptorRegistry)}
 * @author caimeng
 * @date 2025/2/20 18:30
 */
@Validated  // 启用类上的JSR303注解
@RestController
@RequestMapping("/validate/*")
public class ValidateAction extends AbstractBaseAction {    // 入参中含有日期类型，需要使用自定义转换器
    @RequestMapping("echo")
    public Object echo(MessageForValidate message) {
        return message;
    }

    /**
     * 开启了JSR303数据验证的访问入口
     * @param message 验证对象
     * @return 访问结果
     */
    @RequestMapping("jsr303")
    public Object jsr303(@Valid MessageForJSR303 message) {
        return message;
    }
    @RequestMapping("get")
    public Object get(@NotNull @Size(min = 1, max = 10) String id) {
        System.out.println("【ValidateAction.get】id = " + id);
        return id;
    }
}
