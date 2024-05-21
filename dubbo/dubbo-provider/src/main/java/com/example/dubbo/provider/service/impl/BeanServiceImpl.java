package com.example.dubbo.provider.service.impl;

import com.alibaba.dubbo.config.spring.AnnotationBean;
import com.example.dubbo.inter.service.IOrderService;
import com.example.dubbo.provider.service.BeanService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author caimeng
 * @date 2024/5/21 18:26
 */
@Slf4j
@Service
public class BeanServiceImpl implements BeanService {
    /*
     * 使用 @Autowired 会告警，实际上也能注入成功
     */
    @Resource
    private IOrderService iOrderService;
    @Autowired
    private ApplicationContext applicationContext;

    /**
     * 被@Service注解的类会被 处理 {@link AnnotationBean}，该类实现了 BeanFactoryPostProcessor, BeanPostProcessor
     * @see AnnotationBean#postProcessBeanFactory(ConfigurableListableBeanFactory)
     * 结论：dubbo对象是spring-bean
     * @return 是否spring-bean对象
     */
    @Override
    public Boolean ifSpringBean() {
        log.info("iOrderService = {}", iOrderService);
        // 得找到 IOrderService 的FactoryBean才能用 &FactoryBean 的方式获取
//        Object bean = applicationContext.getBean("&IOrderServiceImpl");
//        log.info("bean = {}", bean);
        Map<String, IOrderService> beansOfType = applicationContext.getBeansOfType(IOrderService.class);
        log.info("beansOfType = {}", beansOfType);
        return !ObjectUtils.isEmpty(iOrderService);
    }
}
