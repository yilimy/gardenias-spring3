package org.example.mock.service.ability;

import org.example.mock.spring.IBeanPostProcessor;

/**
 * 一个类被多个切面代理的情况
 * @see AopComplexOnePostProcessor
 * @deprecated 未完成的实现
 * @author caimeng
 * @date 2024/6/14 16:29
 */
@Deprecated
public class AopComplexTwoPostProcessor extends AopComplexPostProcessorAbs implements IBeanPostProcessor {
    private static final String CLASS_NAME = AopComplexTwoPostProcessor.class.getSimpleName();

    @Override
    void before() {
        System.out.println(CLASS_NAME + " proxy before ...");
    }

    @Override
    void after() {
        System.out.println(CLASS_NAME + " proxy after ...");
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) {
        return super.postProcessAfterInitialization(bean, beanName);
    }
}
