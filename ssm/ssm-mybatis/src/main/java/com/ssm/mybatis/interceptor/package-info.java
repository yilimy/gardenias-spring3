/**
 * MyBatis拦截器简介
 * <a href="https://www.bilibili.com/video/BV1knyqYFEk5/" />
 * <p>
 *     代理模式（JDK代理、CGLIB代理），spring的基础概念AOP也是基于代理模式实现的。
 *     在Mybatis中，这种代理就是拦截器
 *     在执行某些核心调用操作之前，做一些拦截处理。
 *     {@link org.apache.ibatis.plugin.Interceptor}
 *
 * 对StatementHandler的拦截
 * {@link com.ssm.mybatis.interceptor.StatementInterceptor}
 * <a href="https://www.bilibili.com/video/BV1rpyqYZENe/" />
 * {@link org.apache.ibatis.executor.statement.StatementHandler}
 * <p>
 *     在 StatementHandler 接口内部提供有{@link org.apache.ibatis.executor.statement.StatementHandler#prepare(Connection, Integer)}方法，
 *     这个方法的主要功能是进行数据操作前的准备工作，方法中含有 {@link java.sql.Connection} 作为入参，意味着：
 *          - Connection 可以创建若干个 Statement、PreparedStatement、CallableStatement接口实例
 *              每一个实例代表着不同数据库的操作结构(select | insert | update | delete)
 *          - Connection 还可可以实现手工事务控制。
 * @author caimeng
 * @date 2024/12/12 15:12
 */
package com.ssm.mybatis.interceptor;

import java.sql.Connection;
