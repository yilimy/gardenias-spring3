package com.example.boot3.source;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionCustomizer;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.BeanDefinitionReaderUtils;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.AnnotatedBeanDefinitionReader;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.GenericApplicationContext;

import java.util.function.Supplier;

/**
 * AnnotatedBeanDefinitionReader配置类处理
 * <p>
 *     两个属性，两个不同的逻辑，触发的方式也不同
 *     AnnotatedBeanDefinitionReader 配置类实现的注册
 *     ClassPathBeanDefinitionScanner 扫描包实现的注册
 * </p>
 * 通过配置类的构造进行触发调用的，构造器中有register和refresh {@link AnnotationConfigApplicationContext#AnnotationConfigApplicationContext(Class[])}
 *
 * @see AnnotationConfigApplicationContext
 * @author caimeng
 * @date 2024/5/6 10:27
 */
public class AnnotationConfigApplicationContextTest {

    /**
     * 注册bean
     * <p>
     *     abd 创建一个BeanDefinition子类实例（Spring里如果找到了BeanDefinition实例就表示注册成功）
     *     shouldSkip 得到元数据，并判断是否需要跳过
     *     setInstanceSupplier 设置实例供给
     *     resolveScopeMetadata 解析当前bean中的scope元数据信息
     *     setScope 保存在BeanDefinition接口实例中
     *     generateBeanName 生成bean名称
     *     processCommonDefinitionAnnotations 解析通用的bean注解
     *     注解 Primary 和 Lazy 的处理，含 qualifiers
     *     BeanDefinitionCustomizer 其他bean自定义信息
     *     BeanDefinitionHolder 将当前Bean名称，BeanDefinition实例对象保存在beanDefinitionHolder对象中（承载器）
     *     registerBeanDefinition 最终的注册处理
     * </p>
     * 配置类的注册方式，实际上要不比扫描包的方式更简单，因为所有的配置类都在CLASSPATH之中，直接对这些类进行有效解析，而后就得到最终可用的BeanDefinition
     * 获取到了BeanDefinition接口的实例，就表示获取到了bean的信息，这是spring设计的核心所在。
     * @see AnnotatedBeanDefinitionReader#doRegisterBean(Class, String, Class[], Supplier, BeanDefinitionCustomizer[])
     */
    public void doRegisterBeanTest() {}

    /**
     * 注册bean
     * registerBeanDefinition 由BeanDefinitionRegistry的子类GenericApplicationContext进行调用
     * {@link GenericApplicationContext#registerBeanDefinition(String, BeanDefinition)}
     * 有beanFactory意味着可以进行注册了
     * {@link DefaultListableBeanFactory#registerBeanDefinition(String, BeanDefinition)}
     * <p>
     *     instanceof - 判断类型是否满足要求
     *     validate - bean验证
     *     existingDefinition - 从 BeanDefinitionMap 中获取已存在的bean
     *     isAllowBeanDefinitionOverriding - 如果bean已存在，是否允许覆盖
     *     getRole - 如果不可覆盖，判断角色，该方法目前为空
     *     equals - bean相同判断
     *     beanDefinitionMap.put - 如果以上都没问题，保存BeanDefinition的定义到bean定义的map集合中
     *     hasBeanCreationStarted - bean创建的时候正在被启动，map集合修改要加锁
     * </p>
     * beanDefinitionMap.put(beanName, beanDefinition) 将程序通过扫描获取到的BeanDefinitionHolder对象实例保存在当前的BeanFactory中，
     * 这样BeanFactory就可以直接进行Bean的实例化处理了
     * TO be continued ...
     * @see BeanDefinitionReaderUtils#registerBeanDefinition(BeanDefinitionHolder, BeanDefinitionRegistry)
     */
    public void registerBeanDefinitionTest() {}


}
