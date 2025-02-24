package com.gardenia.web.common.validation.handler;

import com.gardenia.web.common.validation.annotation.RegexValidator;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 *
 * @author caimeng
 * @date 2025/2/21 18:24
 */
public class RegexConstraintValidator
        implements ConstraintValidator<RegexValidator, Object> {
        /*
         * 专属的正则处理类
         * RegexValidator   : RegexConstraintValidator 可能会使用到的注解
         * Object           : 待验证参数的类型
         */
        private String regex;
    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (value == null) {    // 数据不允许为空
            return false;
        }
        return value.toString().matches(regex);
    }

    @Override
    public void initialize(RegexValidator constraintAnnotation) {
        // 获取注解中的正则表达式
        this.regex = constraintAnnotation.regex();
    }
}
