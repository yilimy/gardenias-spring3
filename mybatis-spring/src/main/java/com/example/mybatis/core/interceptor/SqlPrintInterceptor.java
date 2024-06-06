package com.example.mybatis.core.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.parsing.XNode;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import java.util.Properties;

/**
 * 打印sql信息得拦截器
 * <p>
 *     Mybatis解析xml中插件的代码
 *     {@link org.apache.ibatis.builder.xml.XMLConfigBuilder#pluginElement(XNode)}
 * @author caimeng
 * @date 2024/6/6 14:41
 */
@Slf4j
@Intercepts({
        // 配置抄的 Intercepts 的注释
        @Signature(
                type= Executor.class,
                method = "query",
                // args 的配置参考 Executor 中对应方法的参数类型
                args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class}),
        @Signature(
                type= Executor.class,
                method = "update",
                args = {MappedStatement.class, Object.class})
})
public class SqlPrintInterceptor implements Interceptor {
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        // 第一个参数是 statement
        MappedStatement statement = (MappedStatement) invocation.getArgs()[0];
        // 第二个是查询参数
        Object paramObject = null;
        if (invocation.getArgs().length > 1) {
            paramObject = invocation.getArgs()[1];
            log.info("paramObject = {}", paramObject);
        }
        long start = System.currentTimeMillis();
        Object result = invocation.proceed();
        String sid = statement.getId();
        BoundSql boundSql = statement.getBoundSql(paramObject);
        Configuration configuration = statement.getConfiguration();
        log.info("sid           = {}", sid);
        log.info("boundSql      = {}", boundSql.getSql());
        // configuration.newMetaObject(paramObject)
        log.info("configuration = {}", configuration);
        log.info("SQL执行耗时: {} ms", System.currentTimeMillis() - start);
        return result;
    }

    @Override
    public Object plugin(Object target) {
        if (target instanceof Executor) {
            return Plugin.wrap(target, this);
        }
        return target;
    }

    @Override
    public void setProperties(Properties properties) {
        Interceptor.super.setProperties(properties);
    }
}
