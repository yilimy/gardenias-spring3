package com.example.boot3.source;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.context.support.AbstractApplicationContext;
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
     * @see AbstractApplicationContext#refresh()
     */
    @Test
    public void refreshTest() {

    }
}
