package com.example.boot3.jpa.cache;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;
import org.hibernate.jpa.AvailableHints;
import org.junit.jupiter.api.Test;

/**
 * 测试：查询缓存
 * @author caimeng
 * @date 2024/6/17 16:01
 */
public class QueryCacheTest {
    /**
     * 相比于 {@link SecondLevelCacheTest#ehcacheTest()} 没有使用 EntityManager 查询，而是直接使用的JPQL语句（Query接口）查询
     * 之前所配置的二级缓存对于Query接口查询来讲没有任何效果，因为没有进行查询缓存的启用。
     *
     */
    @Test
    public void query() {
        String jpql = "select c from CourseCache as c where c.cid=:cid";
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("exampleJPACache");
        EntityManager entityManagerA = factory.createEntityManager();
        Query queryA = entityManagerA.createQuery(jpql);
        queryA.setParameter("cid", 11L);
        /*
         * Hibernate: select c1_0.cid,c1_0.cname,c1_0.credit,c1_0.end,c1_0.num,c1_0.start from course c1_0 where c1_0.cid=?
         * 第一次查询: CourseCache(cid=11, cname=Redis开发实战, start=2024-04-11, end=2024-06-11, credit=4, num=100)
         */
        System.out.println("第一次查询: " + queryA.getSingleResult());
        entityManagerA.close();
        EntityManager entityManagerB = factory.createEntityManager();
        Query queryB = entityManagerB.createQuery(jpql);
        queryB.setParameter("cid", 11L);
        /*
         * Hibernate: select c1_0.cid,c1_0.cname,c1_0.credit,c1_0.end,c1_0.num,c1_0.start from course c1_0 where c1_0.cid=?
         * 第二次查询: CourseCache(cid=11, cname=Redis开发实战, start=2024-04-11, end=2024-06-11, credit=4, num=100)
         */
        System.out.println("第二次查询: " + queryB.getSingleResult());
        entityManagerA.close();
    }

    /**
     * 开启查询缓存
     * 1. 增加配置 hibernate.cache.use_query_cache=true
     * 2. PO类需要实现序列化接口 Serializable
     * 3. 添加缓存的命中配置 setHint(.. true), 两个对象(queryA, queryB)都需要开启缓存配置
     */
    @Test
    public void queryCacheOpen() {
        String jpql = "select c from CourseCache as c where c.cid=:cid";
        // 开启查询缓存的配置单元
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("exampleJPACacheWithQuery");
        EntityManager entityManagerA = factory.createEntityManager();
        Query queryA = entityManagerA.createQuery(jpql);
        queryA.setParameter("cid", 11L);
        // 缓存配置
        queryA.setHint(AvailableHints.HINT_CACHEABLE, true);
        /*
         * Query results were not found in cache
         * Hibernate: select c1_0.cid,c1_0.cname,c1_0.credit,c1_0.end,c1_0.num,c1_0.start from course c1_0 where c1_0.cid=?
         * 第一次查询: CourseCache(cid=11, cname=Redis开发实战, start=2024-04-11, end=2024-06-11, credit=4, num=100)
         */
        System.out.println("第一次查询: " + queryA.getSingleResult());
        entityManagerA.close();
        EntityManager entityManagerB = factory.createEntityManager();
        Query queryB = entityManagerB.createQuery(jpql);
        queryB.setParameter("cid", 11L);
        queryB.setHint(AvailableHints.HINT_CACHEABLE, true);
        /*
         * Returning cached query results
         * 第二次查询: CourseCache(cid=11, cname=Redis开发实战, start=2024-04-11, end=2024-06-11, credit=4, num=100)
         */
        System.out.println("第二次查询: " + queryB.getSingleResult());
        entityManagerA.close();
    }
}
