package com.example.boot3.jpa.cache;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;
import org.hibernate.CacheMode;
import org.hibernate.jpa.AvailableHints;
import org.junit.jupiter.api.Test;

/**
 * 测试：缓存模式
 * @author caimeng
 * @date 2024/6/17 17:01
 */
public class CacheModeTest {

    /**
     * 先写入，再刷新
     */
    @Test
    public void putThenRefreshTest() {
        String jpql = "select c from CourseCache as c where c.cid=:cid";
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("exampleJPACacheWithQuery");
        EntityManager entityManagerA = factory.createEntityManager();
        Query queryA = entityManagerA.createQuery(jpql);
        queryA.setParameter("cid", 11L);
        // 缓存配置
        queryA.setHint(AvailableHints.HINT_CACHEABLE, true);
        // 缓存数据写入模式
        queryA.setHint(AvailableHints.HINT_CACHE_MODE, CacheMode.PUT);
        /*
         * Hibernate: select c1_0.cid,c1_0.cname,c1_0.credit,c1_0.end,c1_0.num,c1_0.start from course c1_0 where c1_0.cid=?
         * 第一次查询: CourseCache(cid=11, cname=Redis开发实战, start=2024-04-11, end=2024-06-11, credit=4, num=100)
         */
        System.out.println("第一次查询: " + queryA.getSingleResult());
        entityManagerA.close();
        EntityManager entityManagerB = factory.createEntityManager();
        Query queryB = entityManagerB.createQuery(jpql);
        queryB.setParameter("cid", 11L);
        queryB.setHint(AvailableHints.HINT_CACHEABLE, true);
        // 缓存数据强制刷新
        queryB.setHint(AvailableHints.HINT_CACHE_MODE, CacheMode.REFRESH);
        /*
         * Skipping reading Query result cache data: cache-enabled = true, cache-mode = REFRESH
         * Hibernate: select c1_0.cid,c1_0.cname,c1_0.credit,c1_0.end,c1_0.num,c1_0.start from course c1_0 where c1_0.cid=?
         * 第二次查询: CourseCache(cid=11, cname=Redis开发实战, start=2024-04-11, end=2024-06-11, credit=4, num=100)
         *
         * 检测到需要强制刷新，因此执行sql查询，去抓取最新数据
         */
        System.out.println("第二次查询: " + queryB.getSingleResult());
        entityManagerA.close();
    }

    /**
     * 不执行缓存，再只读缓存
     */
    @Test
    public void ignoreThenGetTest() {
        String jpql = "select c from CourseCache as c where c.cid=:cid";
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("exampleJPACacheWithQuery");
        EntityManager entityManagerA = factory.createEntityManager();
        Query queryA = entityManagerA.createQuery(jpql);
        queryA.setParameter("cid", 11L);
        queryA.setHint(AvailableHints.HINT_CACHEABLE, true);
        queryA.setHint(AvailableHints.HINT_CACHE_MODE, CacheMode.IGNORE);
        /*
         * Hibernate: select c1_0.cid,c1_0.cname,c1_0.credit,c1_0.end,c1_0.num,c1_0.start from course c1_0 where c1_0.cid=?
         * 第一次查询: CourseCache(cid=11, cname=Redis开发实战, start=2024-04-11, end=2024-06-11, credit=4, num=100)
         */
        System.out.println("第一次查询: " + queryA.getSingleResult());
        entityManagerA.close();
        EntityManager entityManagerB = factory.createEntityManager();
        Query queryB = entityManagerB.createQuery(jpql);
        queryB.setParameter("cid", 11L);
        queryB.setHint(AvailableHints.HINT_CACHEABLE, true);
        queryB.setHint(AvailableHints.HINT_CACHE_MODE, CacheMode.GET);
        /*
         * Reading Query result cache data per CacheMode#isGetEnabled [GET]
         * Query results were not found in cache
         * Hibernate: select c1_0.cid,c1_0.cname,c1_0.credit,c1_0.end,c1_0.num,c1_0.start from course c1_0 where c1_0.cid=?
         * 第二次查询: CourseCache(cid=11, cname=Redis开发实战, start=2024-04-11, end=2024-06-11, credit=4, num=100)
         */
        System.out.println("第二次查询: " + queryB.getSingleResult());
        entityManagerA.close();
    }
}
