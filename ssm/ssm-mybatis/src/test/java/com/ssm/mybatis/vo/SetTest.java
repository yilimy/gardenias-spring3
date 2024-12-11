package com.ssm.mybatis.vo;

import com.ssm.mybatis.util.MybatisSessionFactory;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

/**
 * set标签会自动去掉标签块末尾的逗号
 * @author caimeng
 * @date 2024/12/11 10:35
 */
public class SetTest {

    @Test
    public void doEditOptimizeTest() {
        SqlSession sqlSession = MybatisSessionFactory.getSqlSession();

        Book book = Book.builder().author("小王老师2").build();
        int update = sqlSession.update(BookTest.NAME_SPACE_BOOK + ".doEditOptimize", book);
        System.out.println("update : " + update);
        System.out.println("-------- 无更新 --------");

        book = Book.builder().title("JAVA从入门到入土3").bid(1L).build();
        update = sqlSession.update(BookTest.NAME_SPACE_BOOK + ".doEditOptimize", book);
        System.out.println("update : " + update);
        System.out.println("-------- 更新title --------");

        book = Book.builder().author("小王老师2").bid(1L).build();
        update = sqlSession.update(BookTest.NAME_SPACE_BOOK + ".doEditOptimize", book);
        System.out.println("update : " + update);
        System.out.println("-------- 更新author --------");

        // SQL: update book WHERE bid=?
//        book = Book.builder().price(0D).bid(1L).build();
//        update = sqlSession.update(BookTest.NAME_SPACE_BOOK + ".doEditOptimize", book);
//        System.out.println("update : " + update);
//        System.out.println("-------- 不更新price --------");

        book = Book.builder().price(996.97D).bid(1L).build();
        update = sqlSession.update(BookTest.NAME_SPACE_BOOK + ".doEditOptimize", book);
        System.out.println("update : " + update);
        System.out.println("-------- 更新price --------");

        sqlSession.commit();
        MybatisSessionFactory.close();
    }
}
