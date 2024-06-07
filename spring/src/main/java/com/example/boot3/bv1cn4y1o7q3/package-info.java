/**
 * spring 源码讲解
 * <a href="https://www.bilibili.com/video/BV1cn4y1o7Q3">
 *     2024年B站最新【spring源码教程】整整50集，Spring IOC、Spring事务、Spring AOP、Spring MVC从基础到高级全学遍！
 * </a>
 * <p>
 *     bean的生命周期
 *     1. 检查bean是否存在
 *     2. 实例化早期bean
 *     3. 属性注入
 *          3.1 解析 @Autowired
 *     4. 初始化
 *          主要是调用xml中指定的 init-method @PostConstruct 等初始化方法
 *          4.1 初始化前
 *          4.2 初始化
 *          4.3 初始化后
 *     5.
 *     6.
 *
 * @author caimeng
 * @date 2024/6/7 17:11
 */
package com.example.boot3.bv1cn4y1o7q3;