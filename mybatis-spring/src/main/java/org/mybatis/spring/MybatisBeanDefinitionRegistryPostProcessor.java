package org.mybatis.spring;

import lombok.Setter;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.lang.NonNull;

/**
 * 参考 {@link org.mybatis.spring.mapper.MapperScannerConfigurer}
 * @author caimeng
 * @date 2025/3/20 16:09
 */
public class MybatisBeanDefinitionRegistryPostProcessor implements BeanDefinitionRegistryPostProcessor {
    @Setter
    private String basePackage;
    @Override
    public void postProcessBeanDefinitionRegistry(@NonNull BeanDefinitionRegistry registry) throws BeansException {
        MybatisClassPathBeanDefinitionScanner scanner = new MybatisClassPathBeanDefinitionScanner(registry);
        scanner.addIncludeFilter((metadataReader, metadataReaderFactory) -> true);
        scanner.scan(basePackage);
    }

    @Override
    public void postProcessBeanFactory(@NonNull ConfigurableListableBeanFactory beanFactory) throws BeansException {

    }
}
