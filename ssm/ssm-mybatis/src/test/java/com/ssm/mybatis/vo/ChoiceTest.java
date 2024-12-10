package com.ssm.mybatis.vo;

import com.ssm.mybatis.util.MybatisSessionFactory;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import java.util.List;

/**
 * 可选查询
 * @author caimeng
 * @date 2024/12/10 16:05
 */
public class ChoiceTest {

    @Test
    public void findByColumnTest() {
        SqlSession sqlSession = MybatisSessionFactory.getSqlSession();
        Book book = Book.builder()
                .title("Spring开发实战2")
                .author("小李老师")
                // 0D == 0.0, 不作为查询条件
                .price(0D)
                .build();
        List<Book> bookList = sqlSession.selectList(BookTest.NAME_SPACE_BOOK + ".findByColumn", book);
        bookList.forEach(System.out::println);
        MybatisSessionFactory.close();
    }

    @Test
    public void doDynamicCreateTest() {
        SqlSession sqlSession = MybatisSessionFactory.getSqlSession();
        Book book0 = new Book();
        Book book1 = Book.builder()
                .title("Spring开发实战_2024-12-10")
                .author("小李老师")
//                .price(0D)
                .build();
        Book book2 = Book.builder()
                .title("")
                .author("小李老师")
                .price(123.45)
                .build();
        Book book3 = Book.builder()
                .title("Spring开发实战2024")
                .author("")
                .price(123.45)
                .build();
        int insert;
        insert = sqlSession.insert(BookTest.NAME_SPACE_BOOK + ".doDynamicCreate", book0);
        System.out.println("insert=" + insert);
        System.out.println("0. -------------------------------");
        insert = sqlSession.insert(BookTest.NAME_SPACE_BOOK + ".doDynamicCreate", book1);
        System.out.println("insert=" + insert);
        System.out.println("1. -------------------------------");
        insert = sqlSession.insert(BookTest.NAME_SPACE_BOOK + ".doDynamicCreate", book2);
        System.out.println("insert=" + insert);
        System.out.println("2. -------------------------------");
        insert = sqlSession.insert(BookTest.NAME_SPACE_BOOK + ".doDynamicCreate", book3);
        System.out.println("insert=" + insert);
        System.out.println("3. -------------------------------");
        sqlSession.commit();
        MybatisSessionFactory.close();
    }

    /**
     * 测试，多条件查询
     */
    @Test
    public void chooseTest() {
        SqlSession sqlSession = MybatisSessionFactory.getSqlSession();
        Book book = Book.builder()
                .title("Spring开发实战2")
                .author("小李老师")
                .build();
        List<Book> bookList = sqlSession.selectList(BookTest.NAME_SPACE_BOOK + ".findByCondition", book);
        bookList.forEach(System.out::println);
        System.out.println("------------------");
        book.setTitle(null);
        bookList = sqlSession.selectList(BookTest.NAME_SPACE_BOOK + ".findByCondition", book);
        bookList.forEach(System.out::println);
        System.out.println("------------------");
        book.setTitle("Spring开发实战2");
        book.setAuthor(null);
        bookList = sqlSession.selectList(BookTest.NAME_SPACE_BOOK + ".findByCondition", book);
        bookList.forEach(System.out::println);
        System.out.println("------------------");
        book = new Book();
        bookList = sqlSession.selectList(BookTest.NAME_SPACE_BOOK + ".findByCondition", book);
        bookList.forEach(System.out::println);
        System.out.println("------------------");
        // 没有了“1=1”的逐行匹配，提升了性能
        bookList = sqlSession.selectList(BookTest.NAME_SPACE_BOOK + ".findByConditionOptimize", book);
        bookList.forEach(System.out::println);
        System.out.println("------------------");
        MybatisSessionFactory.close();
    }
}
