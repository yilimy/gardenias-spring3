package com.example.boot3.aop.dynamic.service;

/**
 * @author caimeng
 * @date 2024/5/28 14:28
 */
public interface DynamicAopService {
    void add(String interceptorClass, String expression, String annotationClass);

    void del(String interceptorClass, String expression, String annotationClass);
}
