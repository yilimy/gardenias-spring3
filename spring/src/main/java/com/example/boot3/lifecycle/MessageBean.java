package com.example.boot3.lifecycle;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.stereotype.Component;

/**
 * @author caimeng
 * @date 2024/4/15 14:36
 */
@Slf4j
@Component
//@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE) // 如果使用的是原型模式，不会调用SmartInitializingSingleton的初始化操作
public class MessageBean implements InitializingBean, SmartInitializingSingleton {

    public MessageBean() {
        log.info("构造方法 - MessageBean(), 实例化MessageBean对象");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        log.info("InitializingBean - afterPropertiesSet(), 进行MessageBean的初始化配置操作");
    }

    @Override
    public void afterSingletonsInstantiated() {
        log.info("SmartInitializingSingleton - afterSingletonsInstantiated(), MessageBean初始化完成，而且采用单例模型");
    }
}
