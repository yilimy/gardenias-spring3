package com.example.boot3.jpa.cache;

import com.example.boot3.jpa.po.CourseEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Objects;

/**
 * 测试：一级缓存
 * @author caimeng
 * @date 2024/6/13 9:52
 */
public class FirstLevelCacheTest {
    private EntityManager entityManager;

    @BeforeEach
    public void init() {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("exampleJPA");
        entityManager = factory.createEntityManager();
    }

    /**
     * 测试: 一级缓存
     * 业务查询中只执行了一次数据库连接，只执行了一次sql查询。
     * 第一次查询的结果，除了返回外，还会保存在一级缓存中；
     * 第二次查询的时候，由于现在已经有了缓存项，也就不会发出第二次查询了。
     * <p>
     *     缓存针对的是EntityManager，EntityManager是针对于每一个线程
     */
    @Test
    public void find() {
        // 数据库查询，会执行数据库调用
        CourseEntity courseA = entityManager.find(CourseEntity.class, 1L);
        /*
         * Hibernate: select c1_0.cid,c1_0.cname,c1_0.credit,c1_0.end,c1_0.num,c1_0.start from course c1_0 where c1_0.cid=?
         * 第一次find查询: CourseEntity(cid=1, cname=Spring编程实战, start=2024-04-11, end=2024-07-11, credit=5, num=10)
         */
        System.out.println("第一次find查询: " + courseA);
        CourseEntity courseB = entityManager.find(CourseEntity.class, 1L);
        /*
         * 第二次find查询: CourseEntity(cid=1, cname=Spring编程实战, start=2024-04-11, end=2024-07-11, credit=5, num=10)
         */
        System.out.println("第二次find查询: " + courseB);
    }

    /**
     * 第一次查询后，又修改了数据，此时数据库中的数据未同步。
     * 当进行缓存数据进行修改的时候，实际上这些内容不会立即同步到数据库中，
     * 而缓存中的数据此时与实际的表中的数据，是存在有不同步的问题，所以后续再次获取的时候，是修改后的数据项了。
     */
    @Test
    public void findAndModify() {
        CourseEntity courseA = entityManager.find(CourseEntity.class, 1L);
        courseA.setCredit(-99);
        /*
         * Hibernate: select c1_0.cid,c1_0.cname,c1_0.credit,c1_0.end,c1_0.num,c1_0.start from course c1_0 where c1_0.cid=?
         * 第一次find查询: CourseEntity(cid=1, cname=Spring编程实战, start=2024-04-11, end=2024-07-11, credit=-99, num=10)
         */
        System.out.println("第一次find查询: " + courseA);
        System.out.println("-------------世界的分割线-------------");
        CourseEntity courseB = entityManager.find(CourseEntity.class, 1L);
        /*
         * 第二次find查询: CourseEntity(cid=1, cname=Spring编程实战, start=2024-04-11, end=2024-07-11, credit=-99, num=10)
         * 此时数据已发生修改！
         */
        System.out.println("第二次find查询: " + courseB);
    }

    /**
     * 在执行第二次查询前，执行一次强制刷新
     * <p>
     *     执行结果：
     *      Hibernate: select c1_0.cid,c1_0.cname,c1_0.credit,c1_0.end,c1_0.num,c1_0.start from course c1_0 where c1_0.cid=?
     *      第一次find查询: CourseEntity(cid=1, cname=Spring编程实战, start=2024-04-11, end=2024-07-11, credit=-99, num=10)
     *      Hibernate: select c1_0.cid,c1_0.cname,c1_0.credit,c1_0.end,c1_0.num,c1_0.start from course c1_0 where c1_0.cid=?
     *      -------------世界的分割线-------------
     *      第二次find查询: CourseEntity(cid=1, cname=Spring编程实战, start=2024-04-11, end=2024-07-11, credit=5, num=10)
     */
    @Test
    public void forceRefresh() {
        CourseEntity courseA = entityManager.find(CourseEntity.class, 1L);
        courseA.setCredit(-99);
        /*
         * Hibernate: select c1_0.cid,c1_0.cname,c1_0.credit,c1_0.end,c1_0.num,c1_0.start from course c1_0 where c1_0.cid=?
         * 第一次find查询: CourseEntity(cid=1, cname=Spring编程实战, start=2024-04-11, end=2024-07-11, credit=-99, num=10)
         */
        System.out.println("第一次find查询: " + courseA);
        /*
         * 强制性刷新操作
         * Skipping reading Query result cache data: cache-enabled = false, cache-mode = IGNORE
         * Hibernate: select c1_0.cid,c1_0.cname,c1_0.credit,c1_0.end,c1_0.num,c1_0.start from course c1_0 where c1_0.cid=?
         */
        entityManager.refresh(courseA);
        System.out.println("-------------世界的分割线-------------");
        CourseEntity courseB = entityManager.find(CourseEntity.class, 1L);
        /*
         * 第二次find查询: CourseEntity(cid=1, cname=Spring编程实战, start=2024-04-11, end=2024-07-11, credit=5, num=10)
         */
        System.out.println("第二次find查询: " + courseB);
    }

    @AfterEach
    public void after(){
        if (Objects.nonNull(entityManager)) {
            entityManager.close();
        }
    }
}
