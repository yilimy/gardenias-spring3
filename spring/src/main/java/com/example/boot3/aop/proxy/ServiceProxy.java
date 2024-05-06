package com.example.boot3.aop.proxy;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 动态代理
 * 如果要进行业务层的代理操作，那么肯定要进行包装处理的，那么最好的方法就是创建工厂类。
 *
 * @author caimeng
 * @date 2024/5/6 17:37
 */
@Slf4j
public class ServiceProxy implements InvocationHandler {
    // 保存的真实对象
    private Object target;

    /**
     * 绑定真实对象和代理对象
     * @param target 真实对象
     * @return 代理对象
     */
    public Object bind(Object target) {
        this.target = target;
        return Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(), this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // 需要判断当前的操作方法之中是否需要进行动态代理控制
        if (isOpenTransaction(method)) {
            log.info("【JDBC事务控制】当前的业务方法需要进行事务的开启...");
        }
        // 方法调用
        Object result = method.invoke(this.target, args);
        if (isOpenTransaction(method)) {
            log.info("【JDBC事务控制】当前的业务方法需要进行事务的提交或者回滚处理...");
        }
        return result;
    }

    /**
     * 是否要开启事务
     * @param method 代理方法
     * @return 是否开启事务
     */
    private boolean isOpenTransaction(Method method) {
        // 如果是这个逻辑需要修改，或者是要扩充更多的方法呢？这样的逻辑需要写多少个？
        if (method.getName().startsWith("add")) {
            // 需要开启事务
            return true;
        } else {
            // 不需要开启事务
            return false;
        }
    }
}
