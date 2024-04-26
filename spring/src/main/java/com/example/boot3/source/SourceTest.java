package com.example.boot3.source;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.AbstractRefreshableApplicationContext;
import org.springframework.context.support.AbstractXmlApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.lang.reflect.Field;

/**
 * @author caimeng
 * @date 2024/4/23 17:41
 */
@Slf4j
public class SourceTest {

    /**
     * 从xml读取配置文件，一般涉及两个处理类: classPath类和文件系统类
     * @see org.springframework.context.support.ClassPathXmlApplicationContext
     * @see org.springframework.context.support.FileSystemXmlApplicationContext
     */
    @SneakyThrows
    @Test
    public void xmlTest() {
        AbstractXmlApplicationContext context =
                new ClassPathXmlApplicationContext("classpath:spring/spring-*.xml");
        // 由于该类中没有提供获取全部属性的解析方法，所以可以考虑使用反射的方式进行处理
        Field field = getFieldOfClass(context.getClass(), "configLocations");
        // 解除包装
        field.setAccessible(true);
        String[] configLocations = (String[]) field.get(context);
        log.info("配置文件存储：{}", configLocations);

    }

    @SneakyThrows
    private Field getFieldOfClass(Class<?> mClass, String fileName) {
        Field field = null;
        while (field == null) { // 没有获取到成员变量
            try {
                // 获取成员变量
                field = mClass.getDeclaredField(fileName);
            } catch (NoSuchFieldException e) {
                // 本类没有此属性，获取父类的实例
                mClass = mClass.getSuperclass();
                if (mClass == null) {
                    // 没有父类，中断
                    break;
                }
            }
        }
        return field;
    }

    /**
     * spring容器中启动的核心之一就是refresh()处理方法，这个方法是由 AbstractApplicationContext 抽象类提供的
     * <p>
     *     prepareRefresh();
     *     刷新预处理，
     *     激活操作。
     *     切换active的处理状态，同时为了更加明确的表示出当前的Spring上下文状态，也提供有一个close标记
     *     原子类型，防止并发处理
     *     initPropertySources()，在spring容器里面可以通过"<context>"命名空间配置所需要加载的资源信息
     *     getEnvironment().validateRequiredProperties(); 根据当前的配置环境，进行一些必要的属性验证处理
     *     earlyApplicationListeners 事件早期存储。如果早期事件监听为空，则初始化；如果不为空，将事件监听集合重置，再将早期事件监听存放到事件监听。
     *     earlyApplicationEvents 准备事件发布处理
     * <p>
     *     obtainFreshBeanFactory(); 通知子类刷新bean factory
     *     1. 先刷新factory: refreshBeanFactory();
     *          a. 销毁全部的单例bean
     *          b. 取消ID配置
     *          c. 清空对象引用
     *          --- 刷新beanFactory的最核心代码
     *          a. createBeanFactory()   DefaultListableBeanFactory
     *          b. setSerializationId   定义一个对象的id号
     *          c. customizeBeanFactory()   允许配置覆盖，允许循环依赖
     *          d. loadBeanDefinitions()
     *     2. 再返回factory: getBeanFactory();
     * <p>
     *     prepareBeanFactory  会重新进行所有配置bean的加载操作
     *     大量代码，大量操作
     *     <code>
     *         private static final boolean shouldIgnoreSpel = SpringProperties.getFlag("spring.spel.ignore");
     *     </code>
     *     1. 配置SpEL表达式解析器，表示可以在spring容器中使用SpEL进行字符串解析操作
     *     2. 添加资源属性编辑器注册器的定义，以实现资源的注入处理；
     *     3. 添加一系列的Aware后置处理（CallBack处理机制），实现各类核心资源的注入；
     *     4. 配置spring容器进行依赖管理时所需要的相关类：BeanFactory, ResourceLoader, ApplicationContext, Event
     *     5. 处理项目之中可能使用到的LTW技术（外部jar包织入处理）
     *     6. 处理一系列的环境配置项；
     * <p>
     *     initMessageSource();
     *     该接口可以实现所有资源配置文件的加载，而后所有的资源就都可以通过MessageSource接口提供的方法，实现key-value内容的设置和返回
     *     核心步骤如下：
     *     1. 判断在当前BeanFactory中是否存在有指定的MessageSource实例，如果存在，会根据判断来决定是否要进行父资源的结构配置（层次的资源管理）
     *     2. 如果现在没有发现MessageSource对象实例的存在，那么就会进行新的MessageSource接口子类实例化，同时将其保存在BeanFactory之中，保存类型为单例
     *
     * @see AbstractApplicationContext#refresh()
     * @see AbstractRefreshableApplicationContext#refreshBeanFactory()
     */
    @Test
    public void refreshTest() {

    }
}
