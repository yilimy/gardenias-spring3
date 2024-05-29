package org.mybatis.spring;

import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author caimeng
 * @date 2024/3/29 9:42
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Import(MybatisImportBeanDefinitionRegistrar.class)
public @interface MapperScan {
    String value();
}
