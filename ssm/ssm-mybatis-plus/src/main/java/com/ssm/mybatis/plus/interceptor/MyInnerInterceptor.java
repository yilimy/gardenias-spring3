package com.ssm.mybatis.plus.interceptor;

import com.baomidou.mybatisplus.extension.plugins.inner.InnerInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import java.sql.SQLException;

/**
 * 自定义MyBatis-Plus的拦截器
 * @author caimeng
 * @date 2024/12/23 18:13
 */
@Slf4j
public class MyInnerInterceptor implements InnerInterceptor {
    @Override
    public boolean willDoQuery(Executor executor, MappedStatement ms, Object parameter, RowBounds rowBounds, ResultHandler resultHandler, BoundSql boundSql) {
        log.info("【willDoQuery()方法执行】绑定的SQL: {}", boundSql.getSql());
        /*
         * true表示执行sql
         * false表示不执行sql，返回一个空集合。
         */
        return true;
    }

    @Override
    public void beforeQuery(Executor executor, MappedStatement ms, Object parameter, RowBounds rowBounds, ResultHandler resultHandler, BoundSql boundSql) {
        log.info("【beforeQuery()方法执行】绑定的SQL: {}", boundSql.getSql());
    }
}
