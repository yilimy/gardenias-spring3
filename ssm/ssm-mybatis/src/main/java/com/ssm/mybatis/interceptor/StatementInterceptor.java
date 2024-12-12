package com.ssm.mybatis.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.executor.statement.PreparedStatementHandler;
import org.apache.ibatis.executor.statement.RoutingStatementHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.util.Arrays;

/**
 * @author caimeng
 * @date 2024/12/12 18:20
 */
@Intercepts(value = @Signature(
        type = StatementHandler.class,
        method = "prepare",
        args = {Connection.class, Integer.class}
))
@Slf4j
public class StatementInterceptor implements Interceptor {
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        // 【StatementHandler 对象实例】org.apache.ibatis.executor.statement.RoutingStatementHandler
        log.debug("【StatementHandler 对象实例】{}", invocation.getTarget().getClass().getName());
        // 【StatementHandler 执行方法】prepare
        log.debug("【StatementHandler 执行方法】{}", invocation.getMethod().getName());
        // 【StatementHandler 参数接收】[org.apache.ibatis.logging.jdbc.ConnectionLogger@ca93621, null]
        log.debug("【StatementHandler 参数接收】{}", Arrays.asList(invocation.getArgs()));
        log.debug("【JDBC连接对象】{}", invocation.getArgs()[0]);
        if (invocation.getTarget() instanceof RoutingStatementHandler handler) {
            // 从 RoutingStatementHandler 构造器中看到，根据不同的MappedStatement类型，可以获取到不同的StatementHandler实例
            // 通过反射获取 StatementHandler
            Field delegateField = handler.getClass().getDeclaredField("delegate");
            delegateField.setAccessible(true);
            // 因为拦截器拦截的是prepare方法，所以这里的 StatementHandler 一定是 PreparedStatementHandler 类型
            PreparedStatementHandler delegate = (PreparedStatementHandler) delegateField.get(handler);
            log.debug("获取到了" + delegate);
            // 从 RoutingStatementHandler 的方法 getBoundSql 中，可以得到执行的sql和执行参数
            BoundSql boundSql = handler.getBoundSql();
            // 【SQL命令】select bid, title, author, price from book
            log.debug("【SQL命令】{}", boundSql.getSql());
            // 【参数映射】[ParameterMapping{property='bid', mode=IN, javaType=class java.lang.Long, jdbcType=null, numericScale=null, resultMapId='null', jdbcTypeName='null', expression='null'}]
            log.debug("【参数映射】{}", boundSql.getParameterMappings());
            // 【参数内容】1
            log.debug("【参数内容】{}", boundSql.getParameterObject());
        }
        Object result = invocation.proceed();
        // 【StatementHandler 执行结果】org.apache.ibatis.logging.jdbc.PreparedStatementLogger@107f4980
        log.debug("【StatementHandler 执行结果】{}", result);
        return result;
    }
}
