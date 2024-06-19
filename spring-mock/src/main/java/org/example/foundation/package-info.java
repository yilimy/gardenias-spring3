/**
 * Monkey老师讲解spring底层原理
 * P29及以后
 * <a href="https://www.bilibili.com/video/BV1cn4y1o7Q3?p=29" />
 * <p>
 *     1. Bean生命周期
 *     2. 推断构造方法
 *     3. 依赖注入
 *     4. @PastConstruct
 *     5. 初始化前
 *     6. 初始化
 *     7. 初始化后
 *     8. AOP
 *     9. Spring事务
 *     10. @Configuration
 *     11. 循环依赖
 *     12. Spring 整合 Mybatis
 * 相较于 <i>org.example.mock</i> 包，此处是直接使用spring的组件，而不是自己模拟实现其功能
 * <p>
 *     Bean创建的生命周期，其一
 *     userService --> 无参构造方法 --> 普通对象 --> 依赖注入 --> Bean对象
 *
 * @author caimeng
 * @date 2024/6/14 18:47
 */
package org.example.foundation;