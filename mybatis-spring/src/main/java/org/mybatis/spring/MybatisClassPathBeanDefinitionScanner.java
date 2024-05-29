package org.mybatis.spring;

import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.core.type.classreading.MetadataReader;

import java.io.IOException;
import java.util.Set;

/**
 * 自定义的Bean目录扫描器
 * @author caimeng
 * @date 2024/3/26 18:17
 */
public class MybatisClassPathBeanDefinitionScanner extends ClassPathBeanDefinitionScanner {
    public MybatisClassPathBeanDefinitionScanner(BeanDefinitionRegistry registry) {
        super(registry);
    }

    @Override
    protected boolean isCandidateComponent(MetadataReader metadataReader) throws IOException {
        // 是否含有 Component
        return true;
    }

    @Override
    protected boolean isCandidateComponent(AnnotatedBeanDefinition beanDefinition) {
        // 是否接口
        return beanDefinition.getMetadata().isInterface();
    }

    @Override
    protected Set<BeanDefinitionHolder> doScan(String... basePackages) {
        // 扫描得到待实例化的Bean定义的集合
        Set<BeanDefinitionHolder> holderSet = super.doScan(basePackages);
        // 因为是待实例化的Bean定义的集合，所以在这个方法中，可以进行bean构造的定义和bean类型的替换
        holderSet.forEach(item -> {
            GenericBeanDefinition genericBeanDefinition = (GenericBeanDefinition) item.getBeanDefinition();
            // 通过代码给构造方法传值（string --> class?）
            genericBeanDefinition.getConstructorArgumentValues().addGenericArgumentValue(genericBeanDefinition.getBeanClassName());
            // 变更bean类型为factory，通过factory桥接，获取mybatis的代理对象
            genericBeanDefinition.setBeanClass(MybatisFactoryBean.class);
        });
        return holderSet;
    }
}
