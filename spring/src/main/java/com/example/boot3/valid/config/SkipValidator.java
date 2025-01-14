package com.example.boot3.valid.config;

import lombok.SneakyThrows;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.lang.NonNull;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.servlet.mvc.method.annotation.AbstractMessageConverterMethodArgumentResolver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * 忽略指定属性的校验
 * @author caimeng
 * @date 2024/12/19 9:35
 */
public class SkipValidator implements Validator {
    private final LocalValidatorFactoryBean validator;

    public SkipValidator(LocalValidatorFactoryBean validator) {
        this.validator = validator;
    }

    @Override
    public boolean supports(@NonNull Class<?> clazz) {
            SkipValidate annotation = AnnotationUtils.findAnnotation(clazz, SkipValidate.class);
            return annotation != null;
    }

    /**
     * 验证实现
     * <p>
     *     由数据绑定对象中获取验证器
     *     {@link org.springframework.validation.DataBinder#getValidators()}
     *     验证器的实例
     *     {@link LocalValidatorFactoryBean}
     *     执行验证
     *     {@link AbstractMessageConverterMethodArgumentResolver#validateIfApplicable(WebDataBinder, MethodParameter)}
     * @param target the object that is to be validated
     * @param errors contextual state about the validation process
     */
    @SneakyThrows
    @Override
    public void validate(@NonNull Object target, @NonNull Errors errors) {
        SkipValidate annotation = AnnotationUtils.findAnnotation(target.getClass(), SkipValidate.class);
        List<String> skipFields = Optional.ofNullable(annotation).map(SkipValidate::value).map(Arrays::asList).orElse(new ArrayList<>());
        // 创建一个BeanPropertyBindingResult来收集错误
        BeanPropertyBindingResult bindingResult = new BeanPropertyBindingResult(target, target.getClass().getSimpleName());
        // 调用Spring的验证框架来执行对象上注解的验证
        validator.validate(target, bindingResult);
        // 排除某些属性的验证结果
        List<ObjectError> errorList = new ArrayList<>(bindingResult.getAllErrors());
        errorList.removeIf(error -> {
            // 检查错误是否是FieldError并且字段名在排除列表中
            if (error instanceof FieldError) {
                String field = ((FieldError) error).getField();
                return skipFields.contains(field);
            }
            return false;
        });
        //noinspection DataFlowIssue
        errorList.forEach(item -> errors.reject(item.getCode(), item.getDefaultMessage()));
        System.out.println(errorList);
    }
}