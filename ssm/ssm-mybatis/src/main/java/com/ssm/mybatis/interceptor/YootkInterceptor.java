package com.ssm.mybatis.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import java.util.Arrays;
import java.util.Properties;

/**
 * 定义一个拦截器，拦截Mybatis执行器的方法
 * {@link org.apache.ibatis.executor.Executor}
 * 需要在 mybatis.cfg.xml 中进行配置
 * 测试时，注意关闭缓存
 * @author caimeng
 * @date 2024/12/12 16:37
 */
@Intercepts(value = {
        // 查询方法拦截
        @Signature(
                args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class},
                method = "query",
                type = org.apache.ibatis.executor.Executor.class
        ),
        // 更新方法拦截
        @Signature(
                args = {MappedStatement.class, Object.class},
                method = "update",
                type = org.apache.ibatis.executor.Executor.class
        ),
})
@Slf4j
public class YootkInterceptor implements Interceptor {
    public String prefix;
    public String suffix;
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        log.debug("【对象实例】{}", invocation.getTarget().getClass().getName());
        log.debug("【执行方法】{}", invocation.getMethod().getName());
        log.debug("【参数接收】{}", Arrays.asList(invocation.getArgs()));
        Object result = invocation.proceed();
        log.debug("【执行结果】{}", result);
        return result;
    }

    @Override
    public void setProperties(Properties properties) {  // 通过配置文件获取，用于进行内容的初始化
        // 获取配置属性
        this.prefix = properties.getProperty("prefix");
        // 获取配置属性
        this.suffix = properties.getProperty("suffix");
        log.info("【配置参数】prefix={}, suffix={}", this.prefix, this.suffix);
    }
}
