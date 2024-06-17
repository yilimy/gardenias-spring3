package com.example.boot3.jpa.cache;

import com.example.boot3.jpa.po.CourseCache;
import com.example.boot3.jpa.po.CourseEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.Test;

/**
 * 测试：二级缓存
 * @author caimeng
 * @date 2024/6/17 13:43
 */
public class SecondLevelCacheTest {

    /**
     * 两个不同的session发起了两个不同的查询，并且这两个查询之间没有任何的联系，
     * 但是这两个缓存是相同的，毕竟当前的数据ID内容是相同的，
     * 如果现在只想要查询一次，那么就要使用到二级缓存，
     * 二级缓存是跨越多个EntityManager实现的缓存处理操作，需要引入专属的缓存处理组件。
     * <p>
     *     二级缓存组件
     *     - redis      分布式存储
     *     - memcached  分布式存储
     *     - EHCache    磁盘存储
     *     - Caffeine   内存存储
     */
    @Test
    public void threadQuery() {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("exampleJPA");
        EntityManager entityManagerA = factory.createEntityManager();
        /*
         * Hibernate: select c1_0.cid,c1_0.cname,c1_0.credit,c1_0.end,c1_0.num,c1_0.start from course c1_0 where c1_0.cid=?
         * 第一次查询: CourseEntity(cid=11, cname=Redis开发实战, start=2024-04-11, end=2024-06-11, credit=4, num=100)
         */
        System.out.println("第一次查询: " + entityManagerA.find(CourseEntity.class, 11L));
        entityManagerA.close();
        EntityManager entityManagerB = factory.createEntityManager();
        /*
         * Hibernate: select c1_0.cid,c1_0.cname,c1_0.credit,c1_0.end,c1_0.num,c1_0.start from course c1_0 where c1_0.cid=?
         * 第二次查询: CourseEntity(cid=11, cname=Redis开发实战, start=2024-04-11, end=2024-06-11, credit=4, num=100)
         */
        System.out.println("第二次查询: " + entityManagerB.find(CourseEntity.class, 11L));
        entityManagerA.close();
    }

    /**
     * 开启了二级缓存的测试
     */
    @Test
    public void ehcacheTest() {
        // 使用了缓存单元
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("exampleJPACache");
        EntityManager entityManagerA = factory.createEntityManager();
        /*
         * Hibernate: select c1_0.cid,c1_0.cname,c1_0.credit,c1_0.end,c1_0.num,c1_0.start from course c1_0 where c1_0.cid=?
         * 第一次查询: CourseCache(cid=11, cname=Redis开发实战, start=2024-04-11, end=2024-06-11, credit=4, num=100)
         */
        System.out.println("第一次查询: " + entityManagerA.find(CourseCache.class, 11L));
        entityManagerA.close();
        EntityManager entityManagerB = factory.createEntityManager();
        /*
         * Cache hit : region = `com.example.boot3.jpa.po.CourseCache`, key = `com.example.boot3.jpa.po.CourseCache#11`
         * 第二次查询: CourseCache(cid=11, cname=Redis开发实战, start=2024-04-11, end=2024-06-11, credit=4, num=100)
         */
        System.out.println("第二次查询: " + entityManagerB.find(CourseCache.class, 11L));
        entityManagerA.close();
    }
}
