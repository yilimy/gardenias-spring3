package com.example.boot3.processor;

import com.example.boot3.bean.MessageChannel;
import com.example.boot3.test.BeanDefinitionTest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.stereotype.Component;

/**
 * 测试：{@link BeanDefinitionTest#beanFactoryProcessorTest()} 等
 * @author caimeng
 * @date 2024/4/11 11:30
 */
@Slf4j
@Component
public class MessageBeanFactoryPostProcessor implements BeanFactoryPostProcessor {
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        /*
         * ConfigurableListableBeanFactory 是 BeanFactory 的子接口
         * 也就是说，在该接口中可以使用 BeanFactory 的实例去获取Bean
         * 包括 Spring 的三级缓存结构
         * 此时，Spring的BeanFactory已经构建成功
         */
        MessageChannel channel = beanFactory.getBean(MessageChannel.class);
        log.info("channel = {}", channel);
        // 修改bean的属性定义
        channel.setHost("192.168.200.164");
        channel.setToken("illegal Token");
    }
}
