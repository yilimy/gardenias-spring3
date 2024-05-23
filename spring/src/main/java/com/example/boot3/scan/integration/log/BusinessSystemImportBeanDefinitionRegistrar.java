package com.example.boot3.scan.integration.log;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.ObjectUtils;

import java.util.Map;

/**
 * @author caimeng
 * @date 2024/5/23 14:00
 */
@Slf4j
public class BusinessSystemImportBeanDefinitionRegistrar implements ImportBeanDefinitionRegistrar {

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        log.debug("BusinessSystemImportBeanDefinitionRegistrar");
        Map<String, Object> annotationAttributes = importingClassMetadata.getAnnotationAttributes(BusinessLogScan.class.getName());
        String basePackages = (String) annotationAttributes.get("value");
        if (ObjectUtils.isEmpty(basePackages)) {
            String className = importingClassMetadata.getClassName();
            basePackages = className.substring(0, className.lastIndexOf("."));
        }
        Class<?> codeFunEnumClass = (Class<?>) annotationAttributes.get("targetEnum");
        if (!ObjectUtils.isEmpty(codeFunEnumClass)) {
            if (codeFunEnumClass.isEnum() && CodeFunctionInterface.class.isAssignableFrom(codeFunEnumClass)) {
                CodeFunctionInterface[] cfs = (CodeFunctionInterface[]) codeFunEnumClass.getEnumConstants();
                String expression = BusinessLogPointCut.collectExpressionByScan(basePackages, cfs);
                if (!ObjectUtils.isEmpty(expression)) {
                    registerBean(registry, expression);
                }
            }
        }
    }

    private void registerBean(BeanDefinitionRegistry registry, String expression) {
        AbstractBeanDefinition pointcutBeanDefinition = BeanDefinitionBuilder.genericBeanDefinition().getBeanDefinition();
        pointcutBeanDefinition.setBeanClass(BusinessLogPointCut.class);
        MutablePropertyValues mpv = new MutablePropertyValues();
        mpv.add("expression", expression);
        pointcutBeanDefinition.setPropertyValues(mpv);
        registry.registerBeanDefinition(BusinessLogPointCut.class.getSimpleName(), pointcutBeanDefinition);

        AbstractBeanDefinition forwardLogAdviceBeanDefinition = BeanDefinitionBuilder.genericBeanDefinition().getBeanDefinition();
        forwardLogAdviceBeanDefinition.setBeanClass(BusinessLogAdvice.class);
        registry.registerBeanDefinition(BusinessLogAdvice.class.getSimpleName(), forwardLogAdviceBeanDefinition);

        AbstractBeanDefinition pointcutAdvisorBeanDefinition = BeanDefinitionBuilder.genericBeanDefinition().getBeanDefinition();
        pointcutAdvisorBeanDefinition.setBeanClass(BusinessLogPointcutAdvisor.class);
        registry.registerBeanDefinition(BusinessLogPointcutAdvisor.class.getSimpleName(), pointcutAdvisorBeanDefinition);
    }

}
