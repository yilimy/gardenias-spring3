package com.example.boot3.junit;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.BeansException;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * 相比于 {@link BeanDefinitionWithConfigTest} 使用手动注册bean，而不是读取配置文件
 * @author caimeng
 * @date 2024/5/31 18:42
 */
@Slf4j
@ExtendWith(SpringExtension.class)
@Import({BeanDefinitionWithRegistrarTest.AcrossNginxImportBeanDefinitionRegistrar.class})
public class BeanDefinitionWithRegistrarTest implements BeanFactoryAware {

    private BeanFactory beanFactory;

    /**
     * 自定义环境变量
     * 单项测试生效
     * 相比于 {@link BeanDefinitionWithConfigTest#init()} 手动注册而不是读取配置文件的方式，BeforeEach生效了
     */
    @BeforeEach
    void init() {
        System.out.println("++++++++++++++++++++++++++");
        System.setProperty("sc.host", "http://192.168.200.36:5080/hub/api");
        System.setProperty("sc.appKey", "100348");
        System.setProperty("sc.appSecret", "8d1654d6b54ba6b2");
    }

    @Test
    public void startCustomDefinitionTest() {
        SCConfig bean = beanFactory.getBean(SCConfig.class);
        log.info("加载bean成功, bean={}", bean);
    }

    @Override
    public void setBeanFactory(@Nullable BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

    static class AcrossNginxImportBeanDefinitionRegistrar implements ImportBeanDefinitionRegistrar {

        @Override
        public void registerBeanDefinitions(@Nonnull AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
            AbstractBeanDefinition scConfigBeanDefinition = BeanDefinitionBuilder.genericBeanDefinition(SCConfig.class).getBeanDefinition();
            scConfigBeanDefinition.setPropertyValues(initConfig());
            registry.registerBeanDefinition("SCConfig", scConfigBeanDefinition);

            AbstractBeanDefinition apiHubServiceImplBeanDefinition = BeanDefinitionBuilder.genericBeanDefinition(NormalBean.class).getBeanDefinition();
            registry.registerBeanDefinition("NormalBean", apiHubServiceImplBeanDefinition);
        }

        /**
         * 不知道为啥读取不到配置问及爱你
         * 手动添加注册信息
         * @return 配置信息
         */
        private MutablePropertyValues initConfig() {
            MutablePropertyValues mpv = new MutablePropertyValues();
            /*
             * 注册中心：http://192.168.200.36:8890/
             * 环境变量：/data/dzyz_install_sn/qianzhi-yst-business-system/env.txt
             * 网关：192.168.200.36:7780
             * nginx配置地址：/data/nginx-sn/conf/nginx.conf
             * 过nginx的地址：http://192.168.200.36:5080/hub/api
             * 过网关的地址：http://192.168.200.36:7780/api
             * nginx 需要添加配置，能获取到真实地址
             *      proxy_set_header X-Real-IP $remote_addr;
             *      proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
             */
//        mpv.add("host", "http://192.168.200.36:5080/hub/api");
            mpv.add("host", "http://192.168.200.36:5080/SnAuthentication/api");
//        mpv.add("host", "http://192.168.200.36:7780/api");
            mpv.add("appKey", "100348");
            mpv.add("appSecret", "8d1654d6b54ba6b2");
            return mpv;
        }
    }
}
