package com.example.boot3.aop.factory;

import com.example.boot3.aop.proxy.ServiceProxy;
import lombok.SneakyThrows;

/**
 * 代理的工厂类
 * 1. 构造方法不需要，私有化
 * 2. 所有的方法static
 * @author caimeng
 * @date 2024/5/6 18:08
 */
public class ServiceFactory {
    private ServiceFactory() {}

    /**
     * 一个工厂要满足所有调用者的需要，这个时候用一个clazz的泛型操作来描述
     * @param clazz 待代理类型
     * @return 代理对象
     * @param <T> 代理类型的泛型
     */
    @SuppressWarnings("unchecked")
    @SneakyThrows
    public static <T> T getInstance(Class<T> clazz) {
        Object target = clazz.getConstructors()[0].newInstance();
        // 绑定代理对象
        return (T) new ServiceProxy().bind(target);
    }
}
