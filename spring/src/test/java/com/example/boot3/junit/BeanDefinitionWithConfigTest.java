package com.example.boot3.junit;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.boot.context.properties.ConfigurationPropertiesBindingPostProcessor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * 测试链路数据传递
 * <p>
 *     添加了 @EnableConfigurationProperties 才能让 @ConfigurationProperties 生效
 *     该配置注册了 ConfigurationPropertiesBindingPostProcessor
 *     {@link org.springframework.boot.context.properties.EnableConfigurationPropertiesRegistrar#registerInfrastructureBeans(BeanDefinitionRegistry)}
 *     这个类 ConfigurationPropertiesBindingPostProcessor 会给配置 Configuration 添加了ResourcePropertySource和SystemEnvironmentPropertySource等属性解析器
 *     {@link ConfigurationPropertiesBindingPostProcessor#postProcessBeforeInitialization(Object, String)}
 *     但是debug好像有问题，跳过了很多代码块，看不到真实的bean属性了
 * @author caimeng
 * @date 2024/5/30 16:21
 */
@Slf4j
@EnableConfigurationProperties
@ExtendWith(SpringExtension.class)
@Import({SCConfig.class, BeanDefinitionWithConfigTest.AcrossNginxImportBeanDefinitionRegistrar.class})
public class BeanDefinitionWithConfigTest implements BeanFactoryAware {
    private BeanFactory beanFactory;

    /*
     * 自定义环境变量
     * 全局生效
     * BeforeEach init 方法调用了，但是环境变量的改动没有对单元测试生效
     * BeforeAll 全局调用使环境变量。生效了
     */
    @BeforeAll
    static void init() {
        System.out.println("++++++++++++++++++++++++++");
        System.setProperty("sc.host", "http://192.168.200.36:5080/hub/api");
        System.setProperty("sc.appKey", "100348");
        System.setProperty("sc.appSecret", "8d1654d6b54ba6b2");
    }

    /**
     * 通过 EnableConfigurationProperties + Import(SCConfig) 方式启动
     */
    @Test
    public void startWithConfigurationTest() {
        SCConfig bean = beanFactory.getBean(SCConfig.class);
        log.info("加载bean成功, bean={}", bean);
    }

    @Override
    public void setBeanFactory(@Nullable BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

    static class AcrossNginxImportBeanDefinitionRegistrar implements ImportBeanDefinitionRegistrar{
        @Override
        public void registerBeanDefinitions(@Nonnull AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
            AbstractBeanDefinition apiHubServiceImplBeanDefinition = BeanDefinitionBuilder.genericBeanDefinition(NormalBean.class).getBeanDefinition();
            registry.registerBeanDefinition("NormalBean", apiHubServiceImplBeanDefinition);
        }
    }
}
