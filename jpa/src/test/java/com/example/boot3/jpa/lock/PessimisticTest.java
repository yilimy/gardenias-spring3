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
 * 测试悲观锁
 * @author caimeng
 * @date 2024/6/19 17:38
 */
public class PessimisticTest {
    // 待更新的数据编号
    public static final Long CID = 12L;
    private EntityManagerFactory factory;

    @BeforeEach
    public void init() {
        factory = Persistence.createEntityManagerFactory("exampleJPA");
    }

    /**
     * 测试悲观锁
     * <p>
     *     只要在项目中启用了悲观锁的处理机制，那么所有的查询语句之后，都要追加“for update”以进行行锁的配置处理。
     *     随后所带来的问题就是当前的Session如果没有提交或更新事务，其他更新线程就要持续等待。
     */
    @SneakyThrows
    @Test
    public void lockTest() {
        /*
         * Hibernate: select c1_0.cid,c1_0.cname,c1_0.credit,c1_0.end,c1_0.num,c1_0.start from course c1_0 where c1_0.cid=? for update
         * [锁定线程], course = CourseEntity(cid=12, cname=Redis开发实战, start=2024-04-11, end=2024-07-11, credit=77, num=13)
         * Hibernate: select c1_0.cid,c1_0.cname,c1_0.credit,c1_0.end,c1_0.num,c1_0.start from course c1_0 where c1_0.cid=? for update
         * [锁定线程], 休眠完成，进行事务的回滚操作
         * 18:39:17.156 [锁定线程] DEBUG org.hibernate.engine.transaction.internal.TransactionImpl - rolling back
         * [更新的线程], course = CourseEntity(cid=12, cname=Redis开发实战, start=2024-04-11, end=2024-07-11, credit=77, num=13)
         * [更新的线程], 执行事务的更新，course = CourseEntity(cid=12, cname=悲观写锁就业实战, start=2024-04-11, end=2024-07-11, credit=77, num=13)
         * Hibernate: update course set cname=?, credit=?, end=?, num=?, start=? where cid=?
         *
         * for update 是数据库行锁的配置
         */
        startLockThread();
        TimeUnit.MILLISECONDS.sleep(500);
        startWriteThread();
        TimeUnit.SECONDS.sleep(Long.MAX_VALUE);
    }

    /**
     * 测试: 锁对读取的影响
     */
    @SneakyThrows
    @Test
    public void lockReadWithWriteTest() {
        /*
         * Hibernate: select c1_0.cid,c1_0.cname,c1_0.credit,c1_0.end,c1_0.num,c1_0.start from course c1_0 where c1_0.cid=? for update
         * [锁定线程], course = CourseEntity(cid=12, cname=Redis开发实战, start=2024-04-11, end=2024-07-11, credit=77, num=13)
         * [锁定线程], 休眠完成，进行事务的回滚操作
         * 18:36:47.819 [锁定线程] DEBUG org.hibernate.engine.transaction.internal.TransactionImpl - rolling back
         * [读取的线程], course = CourseEntity(cid=12, cname=Redis开发实战, start=2024-04-11, end=2024-07-11, credit=77, num=13)
         *
         * 第二次读取使用了缓存，因此没有打印sql
         */
        startLockThread();
        TimeUnit.MILLISECONDS.sleep(500);
        startReadOpenWriteThread();
        TimeUnit.SECONDS.sleep(Long.MAX_VALUE);
    }

    @SneakyThrows
    @Test
    public void lockReadTest() {
        /*
         * Hibernate: select c1_0.cid,c1_0.cname,c1_0.credit,c1_0.end,c1_0.num,c1_0.start from course c1_0 where c1_0.cid=? for update
         * [锁定线程], course = CourseEntity(cid=12, cname=Redis开发实战, start=2024-04-11, end=2024-07-11, credit=77, num=13)
         * Hibernate: select c1_0.cid,c1_0.cname,c1_0.credit,c1_0.end,c1_0.num,c1_0.start from course c1_0 where c1_0.cid=? lock in share mode
         * [锁定线程], 休眠完成，进行事务的回滚操作
         * [读取的线程], course = CourseEntity(cid=12, cname=Redis开发实战, start=2024-04-11, end=2024-07-11, credit=77, num=13)
         *
         * 此时的执行结果可以发现，在第一个线程没有提交或者回滚的时候，第二个线程还在等待着。
         * 因为此时，一个是写一个是读
         */
        startLockThread();
        TimeUnit.MILLISECONDS.sleep(500);
        startReadThread();
        TimeUnit.SECONDS.sleep(Long.MAX_VALUE);
    }

    @SneakyThrows
    @Test
    public void lockReadReadTest() {
        /*
         * Hibernate: select c1_0.cid,c1_0.cname,c1_0.credit,c1_0.end,c1_0.num,c1_0.start from course c1_0 where c1_0.cid=? lock in share mode
         * [锁定线程], course = CourseEntity(cid=12, cname=Redis开发实战, start=2024-04-11, end=2024-07-11, credit=77, num=13)
         * Hibernate: select c1_0.cid,c1_0.cname,c1_0.credit,c1_0.end,c1_0.num,c1_0.start from course c1_0 where c1_0.cid=? lock in share mode
         * [读取的线程], course = CourseEntity(cid=12, cname=Redis开发实战, start=2024-04-11, end=2024-07-11, credit=77, num=13)
         * [锁定线程], 休眠完成，进行事务的回滚操作
         *
         * 如果多个线程都是悲观读锁，那么就变为了数据共享，所以可以进行多个线程进行数据的查询操作。
         * 所有的一切操作，悲观锁都是基于数据库机制来实现的。
         */
        startLockReadThread();
        TimeUnit.MILLISECONDS.sleep(500);
        startReadThread();
        TimeUnit.SECONDS.sleep(Long.MAX_VALUE);
    }

    /**
     * 开启锁定线程
     */
    public void startLockThread() {
        // 有一个线程已经提前获取到了锁，并且已经通过悲观锁，锁定了资源
        new Thread(() -> {
            EntityManager entityManager = factory.createEntityManager();
            // 如果现在要关注悲观锁的操作，那么一定要有一个完整的事务
            entityManager.getTransaction().begin();
            CourseEntity course = entityManager.find(CourseEntity.class, CID,
                    /*
                     * 查询数据，并添加一个悲观数据写锁。
                     * 后续将对该数据进行更新
                     */
                    LockModeType.PESSIMISTIC_WRITE);
            System.out.println("[" + Thread.currentThread().getName() + "], course = " + course);
            course.setCname("悲观写锁回滚实战");
            course.setCredit(1);
            try {
                // 强制休眠3秒
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("[" + Thread.currentThread().getName() + "], 休眠完成，进行事务的回滚操作");
            // 对于悲观锁而言，只有进行提交和回滚，才能解锁数据
            entityManager.getTransaction().rollback();
            entityManager.close();
        }, "锁定线程").start();
    }

    public void startLockReadThread() {
        new Thread(() -> {
            EntityManager entityManager = factory.createEntityManager();
            entityManager.getTransaction().begin();
            CourseEntity course = entityManager.find(CourseEntity.class, CID,
                    // 设定为悲观读
                    LockModeType.PESSIMISTIC_READ);
            System.out.println("[" + Thread.currentThread().getName() + "], course = " + course);
            course.setCname("悲观写锁回滚实战");
            course.setCredit(1);
            try {
                // 强制休眠3秒
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("[" + Thread.currentThread().getName() + "], 休眠完成，进行事务的回滚操作");
            entityManager.getTransaction().rollback();
            entityManager.close();
        }, "锁定线程").start();
    }

    /**
     * 开启更新的处理线程
     */
    public void startWriteThread() {
        // 有其他的线程来晚了，得等锁定线程释放锁之后才能继续执行
        new Thread(() -> {
            EntityManager entityManager = factory.createEntityManager();
            entityManager.getTransaction().begin();
            /*
             * 此时的方式并不是使用JPQL的方式完成的，而是直接利用持久化状态的方式进行了PO的更新
             */
            CourseEntity course = entityManager.find(CourseEntity.class, CID,
                    /*
                     * 同样是查询数据，并设置悲观写锁
                     */
                    LockModeType.PESSIMISTIC_WRITE);
            System.out.println("[" + Thread.currentThread().getName() + "], course = " + course);
            course.setCname("悲观写锁就业实战");
            course.setCredit(77);
            System.out.println("[" + Thread.currentThread().getName() + "], 执行事务的更新，course = " + course);
            entityManager.getTransaction().commit();
            entityManager.close();
        }, "更新的线程").start();
    }

    /**
     * 读，但是使用写锁
     */
    public void startReadOpenWriteThread() {
        new Thread(() -> {
            EntityManager entityManager = factory.createEntityManager();
            entityManager.getTransaction().begin();
            CourseEntity course = entityManager.find(CourseEntity.class, CID,
                    LockModeType.PESSIMISTIC_WRITE);
            System.out.println("[" + Thread.currentThread().getName() + "], course = " + course);
            entityManager.getTransaction().rollback();
            entityManager.close();
        }, "读取的线程").start();
    }

    /**
     * 普通读
     */
    public void startReadThread() {
        new Thread(() -> {
            EntityManager entityManager = factory.createEntityManager();
            entityManager.getTransaction().begin();
            CourseEntity course = entityManager.find(CourseEntity.class, CID,
                    LockModeType.PESSIMISTIC_READ);
            System.out.println("[" + Thread.currentThread().getName() + "], course = " + course);
            entityManager.getTransaction().rollback();
            entityManager.close();
        }, "读取的线程").start();
    }
}
