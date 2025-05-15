package com.gardenia.autoconfig.regist;

import com.gardenia.autoconfig.vo.DeptWithBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

/**
 * 自定义的 Bean 定义注册器
 * @author caimeng
 * @date 2025/5/15 17:29
 */
public class DefaultImportBeanDefinitionRegistrar implements ImportBeanDefinitionRegistrar {

    @SuppressWarnings("NullableProblems")
    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        RootBeanDefinition rbd = new RootBeanDefinition(DeptWithBeanDefinition.class);
        // 手工对 bean 进行注册
        registry.registerBeanDefinition("deptWithBeanDefinition", rbd);
    }
}
