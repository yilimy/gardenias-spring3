package com.ssm.mybatis.intercept;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * 测试：Mybatis拦截器
 * @author caimeng
 * @date 2024/12/12 15:57
 */
@Slf4j
public class InterceptorTest {

    /**
     * 测试： 拦截器
     */
    @Test
    public void defaultTest() {
        // 本次操作其实可以通过任意的对象进行包装处理，本次用一个Map集合模拟。
        // 此时，由于需要采用JDK动态代理设计模式进行操作，所以要将真实对象实现进行包装
        // 接收的对象也只能是接口
        //noinspection unchecked
        Map<String, String> proxyMap = (Map<String, String>) new DefaultInterceptor()
                .plugin(new HashMap<String, String>());
        // 【代理对象】类型: class jdk.proxy1.$Proxy9
        log.info("【代理对象】类型: {}", proxyMap.getClass());
        // 此时的Map集合没有任何数据，但是被代理后，会返回代理设置的值
        // 【获取数据】yootk = 沐言科技~爆可爱的小李老师
        System.out.println("【获取数据】yootk = " + proxyMap.get("yootk"));
    }

    /**
     * 自定义的拦截器
     * <p>
     *     在 Mybatis 进行拦截的时候，要显示定义拦截的具体方法
     *     {@link Plugin#getSignatureMap(Interceptor)}
     */
    @Intercepts(@Signature(     // 定位待代理的方法
            type = Map.class,   // 注意，类型只能是接口类，因为使用的是JDK动态代理
            method = "get",
            args = Object.class
    ))
    private static class DefaultInterceptor implements Interceptor {

        @Override
        public Object intercept(Invocation invocation) throws Throwable {
            log.info("【拦截操作前】对象: {}, 方法: {}, 参数: {}",
                    invocation.getTarget(), invocation.getMethod(), invocation.getArgs());
            // 调用最终的真实业务方法
            Object result = invocation.proceed();
            if (result == null) {
                result = "沐言科技~爆可爱的小李老师";
            }
            return result;
        }
    }
}
