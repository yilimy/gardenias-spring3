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
 *          4.1 初始化前
 *              - BeanPostProcessor.before...
 *              - 调用 xxxAware
 *          4.2 初始化
 *              主要是调用xml中指定的 init-method @PostConstruct 等初始化方法
 *                  - 1. @PostConstruct注解的方法
 *                  - 2. 实现 InitializingBean 接口的 afterPropertiesSet 方法
 *                  - 3. 配置文件(xml或者注解)中指定的初始化方法
 *          4.3 初始化后
 *              - BeanPostProcessor.after...
 *     5. AOP
 *          基于动态代理，实际是在 BeanPostProcessor 后置处理器中处理的，
 *          {@link org.springframework.aop.framework.autoproxy.AbstractAutoProxyCreator#postProcessAfterInitialization(Object, String)}
 *              在方法 wrapIfNecessary 中，如果有必要则创建动态代理
 *          {@link org.springframework.aop.framework.autoproxy.AbstractAutoProxyCreator#wrapIfNecessary(Object, String, Object)}
 *              在方法 getAdvicesAndAdvisorsForBean 中，
 *              判断当前的bean是否存在匹配的Advice，如果存在，则生成一个代理对象，
 *              根据类以及类中的方法去匹配到Interceptor(也就是Advice)，然后生成代理对象
 *              如果命中advisedBean的切点表达式，则走if线创建动态代理
 *          {@link org.springframework.aop.framework.autoproxy.AbstractAutoProxyCreator#getAdvicesAndAdvisorsForBean(Class, String, TargetSource)}
 *          两种代理方式：
 *              {@link org.springframework.aop.framework.ProxyFactory#getProxy(ClassLoader)}
 *              {@link org.springframework.aop.framework.DefaultAopProxyFactory#createAopProxy(AdvisedSupport)}
 *              接口、Proxy、lambda三种情况默认使用JDK动态代理
 *              - Cglib
 *                  如果没有实现接口，或者指定(active)，则使用Cglib动态代理
 *              - JDK
 *                  如果实现了接口，或者指定(active)，则使用JDK动态代理
 *          两种代理的异同：
 *              同：先调用增强，再调用目标方法，最后调用后置增强（流程一致）
 *              异：
 *                  JDK     实现目标接口，调用目标方法是通过反射调用，method.invoke(obj, params)
 *                  Cglib   继承目标对象，通过子类调用父类的方式（java对象的方法调用）实现
 *              JDK8或之后，JVM针对反射做了优化，比如索引等，性能与Cglib相差不大了
 *     6. bean 的销毁
 *          - 调用容器关闭时， ioc.close()
 *          - 通过beanFactory调用销毁方法时，beanFactory.destroySingleton(beanName);
 *     7.
 *
 * @author caimeng
 * @date 2024/6/7 17:11
 */
package com.example.boot3.bv1cn4y1o7q3;

import org.springframework.aop.TargetSource;
import org.springframework.aop.framework.AdvisedSupport;