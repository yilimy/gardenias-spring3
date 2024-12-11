package com.ssm.mybatis.vo;

import com.ssm.mybatis.util.MybatisSessionFactory;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.IntStream;

/**
 * @author caimeng
 * @date 2024/12/11 11:30
 */
public class ForeachTest {
    // 按照正常的数据传递而言，此操作应该用set接收要删除的数据编号集合，没有重复
    private final Set<Long> bids = Set.of(300L, 301L, 302L);

    /**
     * 新增测试数据：300-302
     */
    @Test
    public void add() {
        exec(session -> {
            bids.forEach(i -> {
                Book book = Book.builder()
                        .bid(i)
                        .title("测试批量删除名")
                        .author("删除君")
                        .price(888.88)
                        .build();
                session.insert(BookTest.NAME_SPACE_BOOK + ".doCreateWithBid", book);
            });

        });
    }

    /**
     * 测试：删除数据， 300-302
     */
    @Test
    public void doRemoveBatchTest() {
        /*
         * Preparing: delete from book WHERE bid IN ( ? , ? , ? )
         * Parameters: 302(Long), 301(Long), 300(Long)
         */
        exec(session -> session.insert(BookTest.NAME_SPACE_BOOK + ".doRemoveBatch", bids.toArray()));
    }

    /**
     * 测试：删除数据，空
     */
    @Test
    public void isNullTest() {
        /*
         * 报错：
         *  org.apache.ibatis.builder.BuilderException: The expression 'array' evaluated to a null value.
         */
        exec(session -> session.insert(BookTest.NAME_SPACE_BOOK + ".doRemoveBatch", null));
    }

    /**
     * 测试：删除数据，空数组
     */
    @Test
    public void emptyTest() {
        /*
         * SQL: delete from book WHERE bid IN
         * 报错：
         *  java.sql.SQLSyntaxErrorException: You have an error in your SQL syntax;
         *  check the manual that corresponds to your MySQL server version for the right syntax to use near '' at line 2
         */
        exec(session -> session.insert(BookTest.NAME_SPACE_BOOK + ".doRemoveBatch", new Long[]{}));
    }

    @Test
    public void batchSelectTest() {
        exec(session -> {
            List<Book> bookList = session.selectList(BookTest.NAME_SPACE_BOOK + ".findByIds", bids.toArray());
            bookList.forEach(System.out::println);
        });
    }

    /**
     * 测试：批量新增
     */
    @Test
    public void doCreateBatchTest() {
        // [1, 5)
        List<Book> books = IntStream.range(1, 5)
                .mapToObj(i -> Book.builder().title("测试批量名" + i).author("批量君" + i).price(123.45D).build())
                .toList();
        // insert into book(title, author, price) values (?, ?, ?) , (?, ?, ?) , (?, ?, ?) , (?, ?, ?)
        exec(session -> session.insert(BookTest.NAME_SPACE_BOOK + ".doCreateBatch", books));
    }

    private static void exec(Consumer<SqlSession> consumer) {
        SqlSession sqlSession = MybatisSessionFactory.getSqlSession();
        consumer.accept(sqlSession);
        sqlSession.commit();
        MybatisSessionFactory.close();
    }
}
