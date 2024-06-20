package com.example.boot3.jpa.lock;

import com.example.boot3.jpa.po.CourseEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.LockModeType;
import jakarta.persistence.Persistence;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

/**
 * 乐观锁测试
 * @author caimeng
 * @date 2024/6/20 14:50
 */
public class OptimisticTest {
    public static final Long CID = 13L;
    private EntityManagerFactory factory;

    @BeforeEach
    public void init() {
        factory = Persistence.createEntityManagerFactory("exampleJPA");
    }

    /**
     * 基本乐观锁测试
     */
    @Test
    public void baseUpdateTest() {
        EntityManager entityManager = factory.createEntityManager();
        entityManager.getTransaction().begin();
        CourseEntity course = entityManager.find(CourseEntity.class, CID,
                // 查询时使用乐观锁
                LockModeType.OPTIMISTIC_FORCE_INCREMENT);
        System.out.println("[" + Thread.currentThread().getName() + "], course = " + course);
        course.setCname("乐观锁开发实战");
        course.setCredit(1);
        /*
         * Hibernate: select c1_0.cid,c1_0.cname,c1_0.credit,c1_0.end,c1_0.num,c1_0.start,c1_0.vseq from course c1_0 where c1_0.cid=?
         * Hibernate: update course set cname=?, credit=?, end=?, num=?, start=?, vseq=? where cid=? and vseq=?
         * Hibernate: update course set vseq=? where cid=? and vseq=?
         *
         * 在执行数据查询时，没有任何锁定的机制，允许直接进行并发的查询处理操作，但是在数据更新的时候，就已经出现了where子句的问题了。
         */
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    /**
     * 乐观锁测试
     */
    @SneakyThrows
    @Test
    public void optimisticLockTest() {
        startWriteThreadA();
        TimeUnit.MILLISECONDS.sleep(500);
        startWriteThreadB();
        /*
         * Hibernate: select c1_0.cid,c1_0.cname,c1_0.credit,c1_0.end,c1_0.num,c1_0.start,c1_0.vseq from course c1_0 where c1_0.cid=?
         * [乐观写线程A], course = CourseEntity(cid=13, cname=Spring编程实战, start=2024-04-11, end=2024-06-11, credit=1, num=1, vseq=3)
         * Hibernate: select c1_0.cid,c1_0.cname,c1_0.credit,c1_0.end,c1_0.num,c1_0.start,c1_0.vseq from course c1_0 where c1_0.cid=?
         * [乐观写线程B], course = CourseEntity(cid=13, cname=Spring编程实战, start=2024-04-11, end=2024-06-11, credit=1, num=1, vseq=3)
         * Hibernate: update course set cname=?, credit=?, end=?, num=?, start=?, vseq=? where cid=? and vseq=?
         * Hibernate: update course set vseq=? where cid=? and vseq=?
         * Hibernate: update course set cname=?, credit=?, end=?, num=?, start=?, vseq=? where cid=? and vseq=?
         * org.hibernate.resource.transaction.backend.jdbc.internal.JdbcResourceLocalTransactionCoordinatorImpl - JDBC transaction marked for rollback-only (exception provided for stack trace)
         * Hibernate: select c1_0.cid,c1_0.cname,c1_0.credit,c1_0.end,c1_0.num,c1_0.start,c1_0.vseq from course c1_0 where c1_0.cid=?
         * Error while committing the transaction
         * jakarta.persistence.OptimisticLockException: Row was updated or deleted by another transaction (or unsaved-value mapping was incorrect) : [com.example.boot3.jpa.po.CourseEntity#13]
         *
         * 执行结果 : 乐观写锁实战B
         * 结论：在版本号相匹配的时候，可以正常更新（线程B），而在版本号不同的时候，就会自动由JPA抛出一个乐观锁异常，
         * 因为数据库表中的版本号已经发生了变化，所以不允许更新。
         */
        TimeUnit.SECONDS.sleep(Long.MAX_VALUE);
    }

    public void startWriteThreadA() {
        new Thread(() -> {
            EntityManager entityManager = factory.createEntityManager();
            entityManager.getTransaction().begin();
            CourseEntity course = entityManager.find(CourseEntity.class, CID,
                    LockModeType.OPTIMISTIC_FORCE_INCREMENT);
            System.out.println("[" + Thread.currentThread().getName() + "], course = " + course);
            course.setCname("乐观写锁实战A");
            course.setCredit(1);
            try {
                // 追加延迟
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            entityManager.getTransaction().commit();
            entityManager.close();
        }, "乐观写线程A").start();
    }

    public void startWriteThreadB() {
        new Thread(() -> {
            EntityManager entityManager = factory.createEntityManager();
            entityManager.getTransaction().begin();
            CourseEntity course = entityManager.find(CourseEntity.class, CID,
                    LockModeType.OPTIMISTIC_FORCE_INCREMENT);
            System.out.println("[" + Thread.currentThread().getName() + "], course = " + course);
            course.setCname("乐观写锁实战B");
            course.setCredit(1);
            entityManager.getTransaction().commit();
            entityManager.close();
        }, "乐观写线程B").start();
    }
}
