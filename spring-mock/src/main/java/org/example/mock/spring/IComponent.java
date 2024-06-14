package org.example.mock.spring;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 表面提供给用户的组件
 * @author caimeng
 * @date 2024/6/13 10:25
 */
// 生效时间：运行时
@Retention(RetentionPolicy.RUNTIME)
// 只能在类上生效
@Target(ElementType.TYPE)
public @interface IComponent {
    // bean的名字
    String value() default "";
}
