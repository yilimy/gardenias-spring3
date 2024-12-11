package com.ssm.mybatis.vo;

import com.ssm.mybatis.util.MybatisSessionFactory;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

/**
 * 二级缓存
 * <a href="https://www.bilibili.com/video/BV1yK1qYYEFQ/" />
 * 公共缓存
 * <p>
 *     一级缓存是指Session缓存，如果要想多个Session共享同一个缓存数据则需要开启二级缓存，即SqlSessionFactory级的缓存。
 *     二级缓存并不是默认开启的，需要开发者手工配置。
 * <p>
 *     1. 在 mybatis.cfg.xml 中添加配置 cacheEnable=true
 *          实测该配置默认是true
 *          {@link Configuration#cacheEnabled}
 *     2. 在 BookMapper.xml 中追加启用二级缓存的配置，cache 标签
 *          实测，该配置是关键
 *     3. 所有的Mybatis查询结果都保存在VO类上，那么在进行缓存的时候，也要考虑到对象的二进制存储问题，所以应该实现序列化接口
 * <p>
 *     此时缓存启用了 BookMapper.xml 文件中所有查询的二级缓存，这样一来就有可能造成许多无意义的查询也进行了缓存。
 *     缓存一旦过多，就会占用非常多的内存资源，性能也会有影响。
 *     常规的做法是在不需要缓存的地方上，做缓存的关闭。在指定的 statement 标签中添加 useCache=false
 * @author caimeng
 * @date 2024/12/11 15:10
 */
public class CacheSecondaryTest {
    // 查询数据编号
    public static final Long cacheBid = 3L;

    /**
     * 测试：二级缓存
     */
    @Test
    public void secondaryTest() {
        // 【session-A】通过SqlSessionFactory创建一个新的SqlSession接口实例
        SqlSession sqlSessionA = MybatisSessionFactory.getSessionFactory().openSession();
        Book bookA = sqlSessionA.selectOne(BookTest.NAME_SPACE_BOOK + ".findById", cacheBid);
        System.out.println("sqlSessionA 查询: " + bookA);
        // 关闭session
        sqlSessionA.close();
        System.out.println("-".repeat(30));

        // 【session-B】通过SqlSessionFactory创建一个新的SqlSession接口实例
        SqlSession sqlSessionB = MybatisSessionFactory.getSessionFactory().openSession();
        // 命中缓存: DEBUG com.ssm.mybatis.mapper.BookMapper - Cache Hit Ratio [com.ssm.mybatis.mapper.BookMapper]: 0.5
        Book bookB = sqlSessionB.selectOne(BookTest.NAME_SPACE_BOOK + ".findById", cacheBid);
        System.out.println("sqlSessionB 查询: " + bookB);
        // 关闭session
        sqlSessionB.close();
        System.out.println("-".repeat(30));
    }

    /**
     * 测试：二级缓存的关闭
     */
    @Test
    public void noCacheTest() {
        SqlSession sqlSessionA = MybatisSessionFactory.getSessionFactory().openSession();
        // Preparing: select bid, title, author, price from book where bid=?
        Book bookA = sqlSessionA.selectOne(BookTest.NAME_SPACE_BOOK + ".findByIdNoCache", cacheBid);
        System.out.println("sqlSessionA 查询: " + bookA);
        sqlSessionA.close();
        System.out.println("-".repeat(30));

        SqlSession sqlSessionB = MybatisSessionFactory.getSessionFactory().openSession();
        // Preparing: select bid, title, author, price from book where bid=?
        Book bookB = sqlSessionB.selectOne(BookTest.NAME_SPACE_BOOK + ".findByIdNoCache", cacheBid);
        System.out.println("sqlSessionB 查询: " + bookB);
        sqlSessionB.close();
        System.out.println("-".repeat(30));
    }
}
