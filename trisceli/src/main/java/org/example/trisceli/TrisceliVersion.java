package org.example.trisceli;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author caimeng
 * @date 2024/7/26 10:23
 */
@Documented
// 只在编译期有效，最终不会打包进class文件
@Retention(RetentionPolicy.SOURCE)
// 只允许作用在类属性上
@Target(ElementType.FIELD)
public @interface TrisceliVersion {
}
