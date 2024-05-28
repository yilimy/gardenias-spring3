package com.example.boot3.aop.dynamic.service.impl;

import cn.hutool.core.text.CharSequenceUtil;
import com.example.boot3.aop.dynamic.common.DynamicAdvisor;
import com.example.boot3.aop.dynamic.common.OperatorEventEnum;
import com.example.boot3.aop.dynamic.service.DynamicAopService;
import com.example.boot3.aop.dynamic.service.DynamicProxy;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Map;

/**
 * @author caimeng
 * @date 2024/5/28 14:29
 */
@Slf4j
@Service
public class DynamicAopServiceImpl implements DynamicAopService {
    @Autowired
    private DynamicProxy proxy;

    private static final Map<String, DynamicAdvisor> advisorMap = new HashMap<>();

    @SuppressWarnings("unchecked")
    @SneakyThrows
    @Override
    public void add(String interceptorClass, String expression, String annotationClass) {
        if (advisorMap.containsKey(interceptorClass + expression)
                || advisorMap.containsKey(interceptorClass + annotationClass)) {
            log.warn("containsKey");
            return;
        }
        MethodInterceptor methodInterceptor = (MethodInterceptor) Class.forName(interceptorClass).getDeclaredConstructor().newInstance();
        DynamicAdvisor advisor;
        if (CharSequenceUtil.isNotBlank(annotationClass)) {
            Class<? extends Annotation> ann = (Class<? extends Annotation>) Class.forName(annotationClass);
            advisor = new DynamicAdvisor(ann, methodInterceptor);
            advisorMap.put(interceptorClass + annotationClass, advisor);
        } else {
            advisor = new DynamicAdvisor(expression, methodInterceptor);
            advisorMap.put(interceptorClass + expression, advisor);
        }
        proxy.operateAdvisor(advisor, OperatorEventEnum.ADD);
    }

    @Override
    public void del(String interceptorClass, String expression, String annotationClass) {
        if (!advisorMap.containsKey(interceptorClass + expression)
                && !advisorMap.containsKey(interceptorClass + annotationClass)) {
            log.warn("advisor not exist .");
            return;
        }
        StringBuilder advisorKey = new StringBuilder(interceptorClass);
        if (CharSequenceUtil.isNotBlank(annotationClass)) {
            advisorKey.append(annotationClass);
        } else {
            advisorKey.append(expression);
        }
        String key = advisorKey.toString();
        DynamicAdvisor advisor = advisorMap.get(key);
        proxy.operateAdvisor(advisor, OperatorEventEnum.DEL);
        advisorMap.remove(key);
    }
}
