package com.example.boot3.test;

import com.example.boot3.bean.DeptBean;
import com.example.boot3.bean.MessageChannel;
import com.example.boot3.bean.UserBean;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

/**
 * @author caimeng
 * @date 2024/4/10 16:36
 */
@Slf4j
public class BeanDefinitionTest {

    /**
     * 测试：beanFactory
     */
    private void beanFactoryTest() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        // 扫描包
        context.scan("com.example.boot3.bean");
        // 刷新上下文
        context.refresh();
        // 子类实例转化为父类接口对象
        // AnnotationConfigApplicationContext 是一个 BeanFactory ，这一点很重要
        @SuppressWarnings("")
        BeanFactory beanFactory = context;
        String beanName = "deptBean";
        log.info("[BEAN 信息] 单例状态: {}", beanFactory.isSingleton(beanName));
        log.info("[BEAN 信息] 原型状态: {}", beanFactory.isPrototype(beanName));
        log.info("[BEAN 信息] 实例信息: {}", beanFactory.getType(beanName));
        /*
         * 从debug中可以看到，创建bean的过程中都使用了 DefaultListableBeanFactory
         * 下一步，看下 DefaultListableBeanFactory 做了什么
         * ListableBeanFactory 对bean的数量做了几个维度的统计、归档等
         */
        // 该方法是 ListableBeanFactory 接口中的方法 (7)
        log.info("Spring容器中，bean的数量: {}", context.getBeanDefinitionCount());
        // 含有Component注解的bean名称
        String[] beanNamesForAnnotation = context.getBeanNamesForAnnotation(Component.class);
        // deptBean userBean
        Arrays.stream(beanNamesForAnnotation).forEach(System.out::println);
    }

    /**
     * 测试：单例和原型
     */
    @Test
    public void singletonTest() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.scan("com.example.boot3.bean");
        context.refresh();
        // 单例bean
        log.info("singleton: {}", context.getBean(DeptBean.class));
        log.info("singleton: {}", context.getBean(DeptBean.class));
        log.info("singleton: {}", context.getBean(DeptBean.class));
        // 原型bean
        log.info("prototype: {}", context.getBean(UserBean.class));
        log.info("prototype: {}", context.getBean(UserBean.class));
        log.info("prototype: {}", context.getBean(UserBean.class));
        /*
         * 单例和原型，是在 ConfigurableBeanFactory 中处理的
         * 返回的实际是 ConfigurableListableBeanFactory ，但它是 ConfigurableBeanFactory 的子接口
         */
        ConfigurableBeanFactory beanFactory = context.getBeanFactory();
        log.info("ConfigurableBean: {}", beanFactory.getBean(UserBean.class));
        // UserBean 为原型bean，通过单例方法获取将返回null
        log.info("ConfigurableBean: {}", beanFactory.getSingleton("userBean"));
    }

    /**
     * 测试 beanProvider
     */
    @Test
    public void beanProviderTest() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.scan("com.example.boot3.bean");
        context.registerBean("user1", UserBean.class);
        context.registerBean("user2", UserBean.class);
        context.refresh();
        ObjectProvider<DeptBean> beanProvider = context.getBeanProvider(DeptBean.class);
        // org.springframework.beans.factory.support.DefaultListableBeanFactory$1@4de4b452
        log.info("实现类: {}", beanProvider);
        /*
         * beanProvider 实现了迭代器
         * [1029472813] DeptBean 对象实例
         * beanProvider 获取到了之后，相当于获取到了bean对象，但不是bean对象本身
         * 迭代器调用了 stream()，在实现类中返回循环获取了 bean
         */
        beanProvider.forEach(System.out::println);
        // 针对多个bean往一个类型上注册
        ObjectProvider<UserBean> userProvider = context.getBeanProvider(UserBean.class);
        userProvider.forEach(System.out::println);
    }

    /**
     * 测试：factoryBean
     * 1. factoryBean 与 beanFactory 都是提供bean的方式，
     * 2. beanFactory 包含 factoryBean
     * 3. beanFactory 包含了所有bean的集合
     * 4. factoryBean 与 ObjectProvider 比较相似，是指都有 getObject 方法，但factoryBean相当于生产者，ObjectProvider相当于消费者
     * 5. factoryBean 是要开发者自己来定义的，自己创建自己的对象工厂实例，而后根据该类获取到最终的对象实例
     */
    @SneakyThrows
    @Test
    public void factoryBeanTest() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.scan("com.example.boot3.bean");
        context.scan("com.example.boot3.factory");
        context.refresh();
        DeptBean deptBean = (DeptBean) context.getBean("deptBean");
        System.out.println("deptBean  = " + deptBean);
        /*
         * 每次使用factoryBean获取对象实例的时候，都可以返回一个新的对象实例
         * 所有的factoryBean也接收BeanFactory的管理
         */
        FactoryBean<DeptBean> factoryBean = context.getBean("&deptFactoryBean", FactoryBean.class);
        System.out.println("deptBean2 = " + factoryBean.getObject());
    }

    /**
     * 测试 BeanFactoryProcessor
     * 某些bean是需要程序来初始化的，需要在spring容器实例化完成之后再来动态配置，这就是bean实例化的后期处理类
     */
    @Test
    public void beanFactoryProcessorTest() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.scan("com.example.boot3.config", "com.example.boot3.processor");
        context.refresh();
        MessageChannel channel = context.getBean(MessageChannel.class);
        // 测试hash
        int identify = System.identityHashCode(channel);
        System.out.println("identify = " + identify);
        System.out.println("channel = " + channel);
    }

    /**
     * 测试 BeanPostProcessor
     */
    @SneakyThrows
    @Test
    public void beanPostProcessor() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.scan("com.example.boot3.bean", "com.example.boot3.config", "com.example.boot3.processor");
        context.refresh();
        // 慢一点结束
        TimeUnit.SECONDS.sleep(2);
        /*
         * 注意，此时 MessageBeanPostProcessor 不会打印关于 MessageBeanFactoryPostProcessor 中 MessageChannel 的信息
         * 因为：如果在BeanFactoryPostProcessor接口的子类中已经获取到了bean，那么就表示该bean已经初始化完成，后续不需要再通过BeanPostProcessor接口的子类进行响应的初始化前和初始化后的处理
         *
         * 另外，原型模式(UserBean, prototype)也不会在此处打印
         */
    }

    /**
     * 测试bean的生命周期
     */
    @Test
    public void lifeCycleTest() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.scan("com.example.boot3.lifecycle");
        context.refresh();
    }
}
