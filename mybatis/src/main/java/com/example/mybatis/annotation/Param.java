package com.example.mybatis.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author caimeng
 * @date 2024/5/10 19:37
 */
@Retention(RetentionPolicy.RUNTIME)
// 注解在方法的参数上
@Target(ElementType.PARAMETER)
public @interface Param {
    String value();
}
