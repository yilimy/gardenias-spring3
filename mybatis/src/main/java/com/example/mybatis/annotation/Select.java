package com.example.mybatis.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author caimeng
 * @date 2024/5/10 17:21
 */
// 运行时注解
@Retention(RetentionPolicy.RUNTIME)
// 方法上标记
@Target(ElementType.METHOD)
public @interface Select {
    String value();
}
