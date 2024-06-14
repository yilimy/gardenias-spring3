package org.example.mock.service.ability;

import org.example.mock.service.beans.ProcessorService;
import org.example.mock.spring.IBeanPostProcessor;
import org.example.mock.spring.IComponent;

/**
 * 自定义的bean后置处理器
 * 批量处理bean的一些逻辑，可以实现 IBeanPostProcessor，比如有个共同的接口标记
 * @author caimeng
 * @date 2024/6/13 17:41
 */
// 必须定义为spring组件才能让spring识别，并执行对应的权能
@IComponent
public class GenericBeanPostProcessor implements IBeanPostProcessor {
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) {
        System.out.println("GenericBeanPostProcessor::before");
        if ("processorService".equals(beanName)) {
            ProcessorService processorService = (ProcessorService) bean;
            processorService.setProcessor("postProcessBeforeInitialization");
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) {
        System.out.println("GenericBeanPostProcessor::after");
        if ("processorService".equals(beanName)) {
            ProcessorService processorService = (ProcessorService) bean;
            processorService.setProcessor("postProcessAfterInitialization");
        }
        return bean;
    }
}
