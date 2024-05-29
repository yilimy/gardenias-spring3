package org.mybatis.spring;

import com.example.mybatis.spring.mapper.OrderMapper;
import com.example.mybatis.spring.mapper.UserMapper;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanNameGenerator;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 导入器
 * @author caimeng
 * @date 2024/3/26 18:11
 */
public class MybatisImportBeanDefinitionRegistrar implements ImportBeanDefinitionRegistrar {
    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry, BeanNameGenerator importBeanNameGenerator) {
        Map<String, Object> annotationAttributes = importingClassMetadata.getAnnotationAttributes(MapperScan.class.getName());
        String basePackages = (String) annotationAttributes.get("value");
        MybatisClassPathBeanDefinitionScanner scanner = new MybatisClassPathBeanDefinitionScanner(registry);
        // 设置扫描路径
        scanner.scan(basePackages);
//        registerBean(registry);
    }

    /**
     * 通过指定类的方式，注册 Mapper 的实例
     * 注册的类：OrderMapper、UserMapper
     * 该功能被扫描器替代 {@link MybatisClassPathBeanDefinitionScanner#doScan(String...)}
     * 扫描器扫描包路径后，将待实例化的对类存入 Set&lt;BeanDefinitionHolder&gt; 中，在此之前可以修改Bean定义(BeanDefinition)
     * @param registry 注册器
     */
    private void registerBean(BeanDefinitionRegistry registry) {
        List<Class<?>> list = Arrays.asList(OrderMapper.class, UserMapper.class);
        for (Class<?> mClass : list) {
            AbstractBeanDefinition beanDefinition = BeanDefinitionBuilder.genericBeanDefinition().getBeanDefinition();
            beanDefinition.setBeanClass(MybatisFactoryBean.class);
            beanDefinition.getConstructorArgumentValues().addGenericArgumentValue(mClass);
            registry.registerBeanDefinition(mClass.getSimpleName(), beanDefinition);
        }
    }
}
