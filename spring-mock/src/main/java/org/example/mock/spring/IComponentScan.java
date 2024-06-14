package org.example.mock.spring;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 提供给用户的扫描注解
 * @author caimeng
 * @date 2024/6/13 10:25
 */
// 生效时间：运行时
@Retention(RetentionPolicy.RUNTIME)
// 只能在类上生效
@Target(ElementType.TYPE)
public @interface IComponentScan {
    // 扫面的包名
    String[] value() default "";
}
