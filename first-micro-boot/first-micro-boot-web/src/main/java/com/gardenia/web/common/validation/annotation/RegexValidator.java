package com.gardenia.web.common.validation.annotation;

import com.gardenia.web.common.validation.handler.RegexConstraintValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 自定义的正则验证器
 * <a href="https://www.bilibili.com/video/BV1FWNhe4EAr/" />
 * <p>
 *     所有的验证器注解里都需要提供有三个核心属性：message | groups | payload
 * @author caimeng
 * @date 2025/2/21 18:13
 */
@Constraint(validatedBy = { RegexConstraintValidator.class })
@Documented
@Target({ FIELD, PARAMETER })   // 该注解允许在成员和参数上使用
@Retention(RUNTIME)
public @interface RegexValidator {
    String message() default "数据的正则验证错误";   // 错误信息
    Class<?>[] groups() default { };    // 分组
    Class<? extends Payload>[] payload() default { };  // 附加的源数据信息
    String regex(); // 自定义注解属性，接收要使用的正则表达式
}
