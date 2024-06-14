package org.example.mock.spring;

/**
 * 初始化Bean时执行的方法
 * @author caimeng
 * @date 2024/6/13 16:21
 */
public interface IInitializingBean {

    void afterPropertiesSet();
}
