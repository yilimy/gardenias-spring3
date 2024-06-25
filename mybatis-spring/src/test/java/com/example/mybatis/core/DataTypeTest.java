package com.example.mybatis.core;

import com.example.mybatis.core.mapper.BlogMapper;
import com.example.mybatis.core.pojo.Blog;
import lombok.SneakyThrows;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;

import java.io.InputStream;
import java.util.Date;
import java.util.UUID;

/**
 * 数据类型相关的测试
 * @author caimeng
 * @date 2024/6/25 9:40
 */
public class DataTypeTest {
    private SqlSessionFactory sqlSessionFactory;
    @SneakyThrows
    @Before
    public void before() {
        InputStream inputStream = Resources.getResourceAsStream("mybatis-config-130.xml");
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        sqlSessionFactory.getConfiguration().setMapUnderscoreToCamelCase(true);
    }

    /**
     * jdbcType 设置为 Date 的测试
     * 该情况下，“时分秒”精度会丢失
     */
    @Test
    public void jdbcTypeDateTest() {
        SqlSession session = sqlSessionFactory.openSession();
        BlogMapper mapper = session.getMapper(BlogMapper.class);
        Blog blog = mapper.selectToDateMapById(103);
        /*
         * Blog(id=103, userName=Finker, title=第三条数据, context=Work work!, updateTime=Tue Jun 25 00:00:00 CST 2024)
         * 说明 jdbcType=Date 导致"时分秒"精度丢失
         */
        System.out.println(blog);
    }

    @Test
    public void jdbcTypeTimeStampTest() {
        SqlSession session = sqlSessionFactory.openSession();
        BlogMapper mapper = session.getMapper(BlogMapper.class);
        Blog blog = mapper.selectByTimeStampMapById(103);
        /*
         * Blog(id=103, userName=Finker, title=第三条数据, context=Work work!, updateTime=Tue Jun 25 09:50:04 CST 2024)
         */
        System.out.println(blog);
    }

    @Test
    public void addByDateTest() {
        SqlSession session = sqlSessionFactory.openSession();
        BlogMapper mapper = session.getMapper(BlogMapper.class);
        Blog blog = Blog.builder()
                .userName("Lee")
                .title("新增数据_" + System.currentTimeMillis())
                .context(UUID.randomUUID().toString())
                .updateTime(new Date())
                .build();
        mapper.addByDate(blog);
        /*
         * 105 Lee 新增数据_1719281289874 7a2384ae-701e-478d-9722-e96faff09c6b 2024-06-25 00:00:00
         * “时分秒”精度丢失
         */
        session.commit();
    }

    @Test
    public void addByTimeStampTest() {
        SqlSession session = sqlSessionFactory.openSession();
        BlogMapper mapper = session.getMapper(BlogMapper.class);
        Blog blog = Blog.builder()
                .userName("Lee")
                .title("新增数据_" + System.currentTimeMillis())
                .context(UUID.randomUUID().toString())
                .updateTime(new Date())
                .build();
        mapper.addByTimeStamp(blog);
        /*
         * 106 Lee 新增数据_1719281453351 9daf34d5-d34a-4639-8224-82ac84c28cd9 2024-06-25 10:10:53
         */
        session.commit();
    }
}
