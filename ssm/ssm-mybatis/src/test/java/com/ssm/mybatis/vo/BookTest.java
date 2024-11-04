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

/**
 * @author caimeng
 * @date 2024/10/28 11:31
 */
@Slf4j
public class BookTest {

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
}
