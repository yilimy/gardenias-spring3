package com.example.mybatis.core;

import com.example.mybatis.core.pojo.Blog;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.InputStream;

/**
 * 测试数据库连接
 * @author caimeng
 * @date 2024/5/29 14:40
 */
@Slf4j
public class ConnectTest {

    /**
     * 测试连接
     * <a href="https://mybatis.org/mybatis-3/getting-started.html">官方文档</a>
     */
    @SneakyThrows
    @Test
    public void connectByXml() {
        String resource = "mybatis-config-130.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory =
                new SqlSessionFactoryBuilder().build(inputStream);
        sqlSessionFactory.getConfiguration().setMapUnderscoreToCamelCase(true);
        try (SqlSession session = sqlSessionFactory.openSession()) {
            Blog blog = session.selectOne(
                    // statement : 对应xml中 namespace + id
                    "com.example.mybatis.core.mapper.BlogMapper.selectBlog",
                    101);
            log.info("blog = {}", blog);
        }
    }
}
