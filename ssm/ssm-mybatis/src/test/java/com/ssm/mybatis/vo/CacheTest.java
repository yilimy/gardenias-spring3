package com.ssm.mybatis.vo;

import com.ssm.mybatis.util.MybatisSessionFactory;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

/**
 * mybatis一级缓存
 * <a href="https://www.bilibili.com/video/BV1yK1qYYEYn" />
 * 数据层缓存，作用在SqlSession接口上的
 * {@link SqlSession#clearCache()}
 * {@link Executor#createCacheKey(MappedStatement, Object, RowBounds, BoundSql)}
 * <p>
 *     每一个ORM组件都有自己的缓存实现，Hibernate、Mybatis等，
 *     如果要考虑综合性能的应用，建议还是使用统一的 SpringCache。
 *     （二级缓存，应用层缓存）
 * <p>
 *     即便是缓存中的数据已经发生了变更，数据和数据库中不一致，但是因为查询条件没有发生变化，所以Mybatis不会自动加载。
 *     半自动化是针对JPA来说的，在JPA之中提供有完整的对象状态。
 * <p>
 *     如果要强制进行一级缓存的重复加载（与数据库同步），可以在执行查询前，执行缓存清除。
 * <p>
 *     Mybatis中的一级缓存永恒存在，但是机制很少用到，因为实际业务上很少在一个session中针对同一条查询参数进行重复查询。
 * @author caimeng
 * @date 2024/12/11 14:19
 */
public class CacheTest {
    // 查询数据编号
    public static final Long cacheBid = 3L;

    /**
     * 测试：Mybatis一级缓存
     */
    @Test
    public void findByIdTest() {
        SqlSession sqlSession = MybatisSessionFactory.getSqlSession();
        // 发起查询sql：Preparing: select bid, title, author, price from book where bid=?
        Book bookA = sqlSession.selectOne(BookTest.NAME_SPACE_BOOK + ".findById", cacheBid);
        // 第一次查询: Book(bid=3, title=Python从入门到放弃, author=小明, price=7.0)
        System.out.println("第一次查询: " + bookA);
        // 检测到缓存，没有发起查询sql
        Book bookB = sqlSession.selectOne(BookTest.NAME_SPACE_BOOK + ".findById", cacheBid);
        // 第二次查询: Book(bid=3, title=Python从入门到放弃, author=小明, price=7.0)
        System.out.println("第二次查询: " + bookB);
        // true
        System.out.println(bookA.equals(bookB));
        // 一级缓存：在同一个事务(SqlSession)中，查询条件相同，返回同样的数据
        MybatisSessionFactory.close();
    }

    /**
     * 测试：修改缓存数据
     */
    @Test
    public void cacheModifyTest() {
        SqlSession sqlSession = MybatisSessionFactory.getSqlSession();
        Book bookA = sqlSession.selectOne(BookTest.NAME_SPACE_BOOK + ".findById", cacheBid);
        // 第一次查询: Book(bid=3, title=Python从入门到放弃, author=小明, price=7.0)
        System.out.println("第一次查询: " + bookA);
        bookA.setAuthor("修改者");
        // 第一次查询（改）: Book(bid=3, title=Python从入门到放弃, author=修改者, price=7.0)
        System.out.println("第一次查询（改）: " + bookA);
        Book bookB = sqlSession.selectOne(BookTest.NAME_SPACE_BOOK + ".findById", cacheBid);
        // 第二次查询: Book(bid=3, title=Python从入门到放弃, author=修改者, price=7.0)
        System.out.println("第二次查询: " + bookB);
        // true
        System.out.println(bookA.equals(bookB));
        // 说明是是直接在对象上动刀子
        MybatisSessionFactory.close();
    }

    /**
     * 测试：一级缓存清除，数据同步
     */
    @Test
    public void cacheModifySyncTest() {
        SqlSession sqlSession = MybatisSessionFactory.getSqlSession();
        // Preparing: select bid, title, author, price from book where bid=?
        Book bookA = sqlSession.selectOne(BookTest.NAME_SPACE_BOOK + ".findById", cacheBid);
        // 第一次查询: Book(bid=3, title=Python从入门到放弃, author=小明, price=7.0)
        System.out.println("第一次查询: " + bookA);
        bookA.setAuthor("修改者");
        // 第一次查询（改）: Book(bid=3, title=Python从入门到放弃, author=修改者, price=7.0)
        System.out.println("第一次查询（改）: " + bookA);
        // 清除缓存
        sqlSession.clearCache();
        // Preparing: select bid, title, author, price from book where bid=?
        Book bookB = sqlSession.selectOne(BookTest.NAME_SPACE_BOOK + ".findById", cacheBid);
        // 第二次查询: Book(bid=3, title=Python从入门到放弃, author=小明, price=7.0)
        System.out.println("第二次查询: " + bookB);
        // false
        System.out.println(bookA.equals(bookB));
        MybatisSessionFactory.close();
    }
}
