package com.ssm.mybatis.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.InputStream;
import java.util.Objects;

/**
 * 创建 MybatisSessionFactory 工厂类
 * <p>
 *     考虑到后续的开发以及讲解问题，本次将创建一个专属的 MybatisSession 工厂类，
 *     通过 ThreadLocal 实现每一个线程的连接对象的管理。
 * @author caimeng
 * @date 2024/10/28 12:02
 */
@Slf4j
public class MybatisSessionFactory {
    // 配置文件路径
    public static final String CONFIG_FILE = "mybatis/mybatis.cfg.xml";
    // 连接工厂实例
    public static SqlSessionFactory sessionFactory;
    // 保存每一个线程的连接对象
    public static final ThreadLocal<SqlSession> SESSION_THREAD_LOCAL = new ThreadLocal<>();

    static {
        // 静态代码块
        // 类加载的时候就需要获取到连接工厂实例
        buildSqlSessionFactory();
    }

    /**
     * 构建 SqlSessionFactory 工厂类
     * @return SqlSessionFactory 接口实例
     */
    private static SqlSessionFactory buildSqlSessionFactory() {
        // 读取配置文件
        try (InputStream inputStream = Resources.getResourceAsStream(CONFIG_FILE)) {
            // 进行配置解析
            sessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        } catch (Exception e) {
            log.error("创建 SqlSessionFactory 失败", e);
        }
        return sessionFactory;
    }

    public static SqlSessionFactory getSessionFactory() {
        return sessionFactory;
    }

    /**
     * 获取 SqlSession 接口对象实例
     * @return SqlSession 接口实例
     */
    public static SqlSession getSqlSession() {
        // 获取当前线程的session对象
        SqlSession sqlSession = SESSION_THREAD_LOCAL.get();
        if (Objects.isNull(sqlSession)) {   // 没有连接对象
            // 创建新的连接
            sqlSession = sessionFactory.openSession();
            // 保存在线程中
            SESSION_THREAD_LOCAL.set(sqlSession);
        }
        return sqlSession;
    }

    /**
     * 关闭当前的 sqlSession 接口实例
     */
    public static void close() {
        SqlSession sqlSession = SESSION_THREAD_LOCAL.get();
        if (Objects.nonNull(sqlSession)) {
            // 关闭会话
            sqlSession.close();
            // 清除线程对象
            SESSION_THREAD_LOCAL.remove();
        }
    }
}
