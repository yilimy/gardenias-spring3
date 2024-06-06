package com.example.mybatis.core;

import com.example.mybatis.core.mapper.BlogMapper;
import com.example.mybatis.core.mapper.BlogWithAnnotationMapper;
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
 * 测试数据库连接
 * @author caimeng
 * @date 2024/5/29 14:40
 */
@Slf4j
public class ConnectTest {
    String resource = "mybatis-config-130.xml";
    private SqlSessionFactory sqlSessionFactory;
    @SneakyThrows
    @Before
    public void before() {
        InputStream inputStream = Resources.getResourceAsStream(resource);
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        sqlSessionFactory.getConfiguration().setMapUnderscoreToCamelCase(true);
    }

    /**
     * 测试连接
     * <a href="https://mybatis.org/mybatis-3/getting-started.html">官方文档</a>
     */
    @SneakyThrows
    @Test
    public void connectByXml() {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            /*
             * 该编程风格是身为iBatis时的风格，代码转交给apache之后才支持的Annotation的方式
             * @see this#execByAnnotation()
             */
            Blog blog = session.selectOne(
                    // statement : 对应xml中 namespace + id
                    "com.example.mybatis.core.mapper.BlogMapper.selectBlog",
                    101);
            log.info("blog = {}", blog);
        }
    }

    @SneakyThrows
    @Test
    public void execByAnnotation() {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            /*
             *注解风格的sql查询
             * 1. 先将类型和动态代理对象绑定（指放在map中）
             * 2. 执行mapper解析
             * @see org.apache.ibatis.binding.MapperRegistry.addMapper(Class<T>)
             * 优先解析xml,再解析注解
             * @see org.apache.ibatis.builder.annotation.MapperAnnotationBuilder.parse()
             * @see org.apache.ibatis.builder.annotation.MapperAnnotationBuilder.loadXmlResource()
             */
            BlogMapper mapper = session.getMapper(BlogMapper.class);
            /*
             * 1. 先通过类 BlogMapper 找到动态代理对象 (session.getMapper)
             * 2. 通过方法上的注解，确定执行类型(SELECT)
             * 3. 调用SqlSession的方法 (sqlSession.selectOne)
             * @see this#connectByXml()
             * 解析方法注解中的sql
             * @see org.apache.ibatis.builder.annotation.MapperAnnotationBuilder.parse()
             */
            Blog blog = mapper.selectBlogByAnnotation(101);
            log.info("blog = {}", blog);
        }
    }

    /**
     * 测试：@Select 注解上有多个value
     * 实际上是用 String.join 做的字符串拼接
     * @see org.apache.ibatis.builder.annotation.MapperAnnotationBuilder#buildSqlSourceFromStrings(String[], Class, org.apache.ibatis.scripting.LanguageDriver)
     */
    @SneakyThrows
    @Test
    public void annotationOnly() {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            BlogWithAnnotationMapper mapper = session.getMapper(BlogWithAnnotationMapper.class);
            Blog blog = mapper.selectBlogByAnnotation(101);
            log.info("blog 101 = {}", blog);
            System.out.println("+++++++++++++++++++++++++++");
            blog = mapper.selectBlogByValues(102);
            log.info("blog 102 = {}", blog);
        }
    }
}
