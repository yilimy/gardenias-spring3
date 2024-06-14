package org.example.mock.spring;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 表明bean是单例bean还是多例bean
 * @author caimeng
 * @date 2024/6/13 10:25
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface IScope {
    String SINGLETON = "singleton";
    String PROTOTYPE = "prototype";
    String value() default SINGLETON;
}
