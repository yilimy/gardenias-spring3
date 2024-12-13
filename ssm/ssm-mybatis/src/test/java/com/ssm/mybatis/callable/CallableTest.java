package com.ssm.mybatis.callable;

import com.ssm.mybatis.util.MybatisSessionFactory;
import com.ssm.mybatis.vo.Book;
import com.ssm.mybatis.vo.BookTest;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

/**
 * 测试：存储过程
 * @author caimeng
 * @date 2024/12/13 15:55
 */
public class CallableTest {
    /**
     * 测试：存储过程调用，单个返回结果(结果可能是个list)
     */
    @Test
    public void singleResultTest() {
        SqlSession sqlSession = MybatisSessionFactory.getSqlSession();
        // Preparing: {call book_select_proc(?)}
        List<Book> bookList = sqlSession.selectList(BookTest.NAME_SPACE_BOOK + ".producerSingle", "Spring");
        bookList.forEach(System.out::println);
        MybatisSessionFactory.close();
    }

    /**
     * 测试：存储过程，返回多个查询结果
     */
    @Test
    public void multiResultTest() {
        SqlSession sqlSession = MybatisSessionFactory.getSqlSession();
        Map<String, Integer> queryMap = Map.of("start", 1, "size", 10);
        // Preparing: {call book_multi_select_proc( ?, ?) }
        List<List<?>> retList = sqlSession.selectList(BookTest.NAME_SPACE_BOOK + ".producerMulti", queryMap);
        retList.get(0).forEach(System.out::println);
        System.out.println(retList.get(1));
        MybatisSessionFactory.close();
    }
}
