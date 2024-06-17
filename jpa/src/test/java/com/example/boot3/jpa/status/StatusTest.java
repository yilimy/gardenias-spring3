package com.example.boot3.jpa.status;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.RandomUtil;
import com.example.boot3.jpa.po.CourseEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Objects;

/**
 * @author caimeng
 * @date 2024/6/17 10:35
 */
public class StatusTest {
    private EntityManager entityManager;

    @BeforeEach
    public void init() {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("exampleJPA");
        entityManager = factory.createEntityManager();
    }

    /**
     * 测试步骤：
     * 1. 执行了查询
     * 2. 开启了事务
     * 3. 修改了属性
     * 4. 没有调用merge
     * 5. 执行了提交
     * 执行结果：执行了更新
     * <p>
     *     由于修改了持久态对象属性的内容，当进行事务提交之后，该修改会直接同步到数据库中。
     *     对于当前的操作形式，实际上来自于早期的EJB开发技术。
     */
    @Test
    public void managed() {
        // Hibernate: select c1_0.cid,c1_0.cname,c1_0.credit,c1_0.end,c1_0.num,c1_0.start from course c1_0 where c1_0.cid=?
        CourseEntity course = entityManager.find(CourseEntity.class, 11L);
        entityManager.getTransaction().begin();
        course.setCname("Redis开发实战");
        course.setNum(100);
        // Hibernate: update course set cname=?, credit=?, end=?, num=?, start=? where cid=?
        entityManager.getTransaction().commit();
    }

    /**
     * 测试步骤：
     * 1. 执行一条新增
     * 2. 事务提交
     * 3. 执行对ID数据的查询
     * 测试结果：在查询阶段，没有进行数据库的查询，直接走的缓存
     * <p>
     *     由于JPA中存在有一级缓存配置，用户将一个瞬时态的持久化对象通过持久化方法转为持久态后，就会自动保存在一级缓存中，
     *     再次进行指定ID数据的查询时，会通过一级缓存查询数据，此时不会向数据库发出查询指令。
     *     如果仅仅保存一条数据，没有问题；
     *     如果进行数据的批量存储，就会有问题。有可能随着缓存数量的增加，导致缓存溢出。最佳的做法是及时清空缓存。
     */
    @Test
    public void insert() {
        entityManager.getTransaction().begin();
        // 瞬时态
        CourseEntity course = CourseEntity.builder()
                .cname("Spring编程实战")
                .credit(8)
                .num(92)
                .start(DateUtil.date().offset(DateField.MONTH, -2))
                .end(DateUtil.date())
                .build();
        /*
         * 持久态
         * Hibernate: insert into course (cname, credit, end, num, start) values (?, ?, ?, ?, ?)
         */
        entityManager.persist(course);
        entityManager.getTransaction().commit();
        /*
         * 在没有关闭EntityManager的时候，执行对上一步ID的数据查询
         * close() 是在@AfterEach中调用的
         * Natively generated identity: 15
         */
        CourseEntity courseA = entityManager.find(CourseEntity.class, course.getCid());
        // 数据查询CourseEntity(cid=15, cname=Spring编程实战, start=2024-04-17 10:51:12, end=2024-06-17 10:51:12, credit=8, num=92)
        System.out.println("数据查询" + courseA);
    }

    /**
     * 批量新增
     * <p>
     *     由于缓存的影响，在进行批量数据处理的时候，必须时刻思考缓存清空的问题。
     *     如果没有及时清空缓存，最终内存一定是不够用的，缓存数据多也会出现其他问题。
     */
    @Test
    public void insertBatch() {
        entityManager.getTransaction().begin();
        for (int i = 0; i < 10; i++) {
            CourseEntity course = CourseEntity.builder()
                    .cname("批量开发编程实战_" + RandomUtil.randomInt(33))
                    .credit(RandomUtil.randomInt(20))
                    .num(RandomUtil.randomInt(100))
                    .start(DateUtil.date().offset(DateField.MONTH, -2))
                    .end(DateUtil.date())
                    .build();
            // 存储表示，加入到一级缓存
            entityManager.persist(course);
            // 每隔3条数据执行一次缓存刷新。
            if (i % 3 == 0) {
                // 强制刷新
                entityManager.flush();
                // 清空缓存
                entityManager.clear();
            }
        }
        /*
         * Hibernate: insert into course (cname, credit, end, num, start) values (?, ?, ?, ?, ?)
         * Processing flush-time cascades
         * Flushed: 0 insertions, 0 updates, 0 deletions to 1 objects
         * Flushed: 0 (re)creations, 0 updates, 0 removals to 0 collections
         * Hibernate: insert into course (cname, credit, end, num, start) values (?, ?, ?, ?, ?)
         * Hibernate: insert into course (cname, credit, end, num, start) values (?, ?, ?, ?, ?)
         * Hibernate: insert into course (cname, credit, end, num, start) values (?, ?, ?, ?, ?)
         * Processing flush-time cascades
         * Flushed: 0 insertions, 0 updates, 0 deletions to 3 objects
         * Flushed: 0 (re)creations, 0 updates, 0 removals to 0 collections
         * Hibernate: insert into course (cname, credit, end, num, start) values (?, ?, ?, ?, ?)
         * Hibernate: insert into course (cname, credit, end, num, start) values (?, ?, ?, ?, ?)
         * Hibernate: insert into course (cname, credit, end, num, start) values (?, ?, ?, ?, ?)
         * Processing flush-time cascades
         * Flushed: 0 insertions, 0 updates, 0 deletions to 3 objects
         * Flushed: 0 (re)creations, 0 updates, 0 removals to 0 collections
         * Hibernate: insert into course (cname, credit, end, num, start) values (?, ?, ?, ?, ?)
         * Hibernate: insert into course (cname, credit, end, num, start) values (?, ?, ?, ?, ?)
         * Hibernate: insert into course (cname, credit, end, num, start) values (?, ?, ?, ?, ?)
         * Processing flush-time cascades
         * Flushed: 0 insertions, 0 updates, 0 deletions to 3 objects
         * Flushed: 0 (re)creations, 0 updates, 0 removals to 0 collections
         * committing
         */
        entityManager.getTransaction().commit();
    }

    @AfterEach
    public void after(){
        if (Objects.nonNull(entityManager)) {
            entityManager.close();
        }
    }
}
