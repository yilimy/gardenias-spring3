package com.example.mybatis.core;

import com.example.mybatis.core.pojo.Blog;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;

import java.io.InputStream;

/**
 * 测试：缓存
 * @author caimeng
 * @date 2024/6/6 10:52
 */
@Slf4j
public class CacheTest {
    private SqlSessionFactory sqlSessionFactory;
    @SneakyThrows
    @Before
    public void before() {
        InputStream inputStream = Resources.getResourceAsStream("mybatis-config-130.xml");
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        sqlSessionFactory.getConfiguration().setMapUnderscoreToCamelCase(true);
    }

    /**
     * 测试：验证一级缓存
     * 同样的查询条件，只会查询一次数据库；
     * 不同的查询条件，会再查一次数据库
     */
    @Test
    public void validateCache() {
        SqlSession session = sqlSessionFactory.openSession();
        Blog blog1 = session.selectOne(
                "com.example.mybatis.core.mapper.BlogMapper.selectBlog",
                101);
        Blog blog2 = session.selectOne(
                "com.example.mybatis.core.mapper.BlogMapper.selectBlog",
                101);
        /*
         * 只打印了一条 Preparing 和 Parameters
         * ==> Preparing: select * from Blog where id = ?
         * ==> Parameters: 101(Integer)
         * <== Total: 1
         * 说明只做了一次查询，第二次是缓存
         */
        log.info("blog1 = {}", blog1);
        log.info("blog2 = {}", blog2);
    }
}
