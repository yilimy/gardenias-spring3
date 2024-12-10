package com.ssm.mybatis.vo;

import com.ssm.mybatis.util.MybatisSessionFactory;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.InputStream;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @author caimeng
 * @date 2024/10/28 11:31
 */
@Slf4j
public class BookTest {
    private static final String NAME_SPACE_BOOK = "com.ssm.mybatis.mapper.BookMapper";

    @SneakyThrows
    @Test
    public void doCreateTest() {
        // 1. 需要加载mybatis.cfg.xml资源项，这个资源项是一个标准的输入流读取
        try (InputStream inputStream = Resources.getResourceAsStream("mybatis/mybatis.cfg.xml")) {
            // 2. 根据mybatis.cfg.xml配置的定义，获取到SQL连接工厂对象的实例
            SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);    // 构建会话工厂
            // 3. 通过SQL连接工厂，获取到SqlSession接口的实例，以实现最终的数据操作
            SqlSession session = sqlSessionFactory.openSession();   // 获取连接
            // 4. 将所需要保存的实例保存在数据库中
            Book book = Book.builder()
                    .title("Spring开发实战")
                    .author("小李老师")
                    .price(69.8)
                    .build();
            // 5. 在执行具体的SQL操作时，需要提供有准确的命令定义，而命令都在映射文件中
            log.info("【数据增加】更新数据行数: {}", session.insert(
                    /*
                     * 命名空间 + 方法ID
                     * 使用 SqlSession 执行 sql 操作是不需要有Mapper接口及其方法的。
                     * 反而是Mapper接口的方法需要通过命名空间找mapper.xml中的id去完成具体的 statement 拼接。
                     * 通过使用Mapper接口，可以隐藏掉 SqlSession 的操作。
                     */
                    "com.ssm.mybatis.mapper.BookMapper.doCreate", book
            ));
            // 6. 所有的操作都受到事务的保护，那么需要手工进行事务的提交
            session.commit();   // 提交事务
            session.close();    // 关闭事务
        }
    }

    /**
     * 测试工厂连接类改造后的方法
     */
    @Test
    public void doCreateWithFactoryTest() {
        // 4. 将所需要保存的实例保存在数据库中
        Book book = Book.builder()
                .title("Spring开发实战2")
                .author("小李老师")
                .price(79.8)
                .build();
        SqlSession sqlSession = MybatisSessionFactory.getSqlSession();
        log.info("【数据增加】更新数据行数: {}", sqlSession.insert(
                "com.ssm.mybatis.mapper.BookMapper.doCreate", book
        ));
        log.info("【获取主键】当前新增图书的主键内容为: {}", book.getBid());
        sqlSession.commit();
        MybatisSessionFactory.close();
    }

    /**
     * 测试：通过selectKey获取新插入数据之后的ID
     * <p>
     *     执行了两次数据库操作
     *     1. insert into book(title, author, price) values (?, ?, ?);
     *     2. SELECT LAST_INSERT_ID();
     */
    @Test
    public void doCreateWithLastInsertIdTest() {
        // 4. 将所需要保存的实例保存在数据库中
        Book book = Book.builder()
                .title("Spring开发实战2")
                .author("小李老师")
                .price(79.8)
                .build();
        SqlSession sqlSession = MybatisSessionFactory.getSqlSession();
        log.info("【数据增加】更新数据行数: {}", sqlSession.insert(
                "com.ssm.mybatis.mapper.BookMapper.doCreateAdapterOracle", book
        ));
        log.info("【获取主键】当前新增图书的主键内容为: {}", book.getBid());
        sqlSession.commit();
        MybatisSessionFactory.close();
    }

    @Test
    public void doEditTest() {
        Book book = Book.builder()
                .bid(1L)
                .title("JAVA从入门到入土2")
                .author("小王老师")
                .price(996.96)
                .build();
        SqlSession sqlSession = MybatisSessionFactory.getSqlSession();
        int update = sqlSession.update("com.ssm.mybatis.mapper.BookMapper.doEdit", book);
        log.info("【数据更新】更新数据行数: {}",update);
        sqlSession.commit();
        sqlSession.close();
    }

    @Test
    public void doRemoveTest() {
        Book book = Book.builder().bid(60L).build();
        SqlSession sqlSession = MybatisSessionFactory.getSqlSession();
        int delete = sqlSession.delete("com.ssm.mybatis.mapper.BookMapper.doRemove", book);
        log.info("【数据删除】删除数据行数: {}",delete);
        sqlSession.commit();
        sqlSession.close();
    }

    @Test
    public void findByIdTest() {
        // 此时的查询直接返回一个VO对象，所以符合ORM的设计要求
        Book book = MybatisSessionFactory.getSqlSession().selectOne(NAME_SPACE_BOOK + ".findById", 1L);
        System.out.println(book);
        MybatisSessionFactory.close();
    }

    @Test
    public void findAllTest() {
        List<Book> bookList = MybatisSessionFactory.getSqlSession().selectList(NAME_SPACE_BOOK + ".findAll");
        bookList.forEach(System.out::println);
        MybatisSessionFactory.close();
    }

    @Test
    public void findSplitTest() {
        // MyBatis调用的时候，只允许传递一个参数项，所以使用Map接收
        Map<String, ? extends Serializable> queryMap = Map.of(
                "column", "title",
                "keyword", "%Spring%",
                "start", 0,
                "lineSize", 10);
        List<Book> bookList = MybatisSessionFactory.getSqlSession().selectList(NAME_SPACE_BOOK + ".findSplit", queryMap);
        bookList.forEach(System.out::println);
        MybatisSessionFactory.close();
    }

    @Test
    public void getAllCountTest() {
        Map<String, ? extends Serializable> queryMap = Map.of(
                "column", "title",
                "keyword", "%Spring%");
        Long count = MybatisSessionFactory.getSqlSession().selectOne(NAME_SPACE_BOOK + ".getAllCount", queryMap);
        System.out.println("count = " + count);
        MybatisSessionFactory.close();
    }
}
