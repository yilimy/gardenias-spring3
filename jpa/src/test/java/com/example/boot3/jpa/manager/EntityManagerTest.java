package com.example.boot3.jpa.manager;

import com.example.boot3.jpa.po.CourseEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Objects;

/**
 * @author caimeng
 * @date 2024/6/11 9:47
 */
@Slf4j
public class EntityManagerTest {
    private EntityManager entityManager;

    @BeforeEach
    public void init() {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("exampleJPA");
        entityManager = factory.createEntityManager();
        entityManager.getTransaction().begin();
    }

    /**
     * JPA数据库更新操作
     * <p>
     *     在每一次使用JPA进行数据更新的时候，都需要将完整的属性设置上，否则最终更新的时候，对应的数据字段就设置为null
     *     这种更新就属于数据行的全部更新操作。
     */
    @Test
    public void editTest() {
        CourseEntity course = CourseEntity.builder()
                // 需要修改的课程编号
                .cid(3L)
                .cname("SpringBoot编程实战")
                .credit(2)
                /*
                 * 如果此处没有设置，则内容为空
                 * 更新结果为：该三项数据库中的值为 null
                 */
//                .num(10)
//                .start(DateUtil.date().offset(DateField.MONTH, -2))
//                .end(DateUtil.date())
                .build();
        // 数据合并（修改）
        entityManager.merge(course);
    }

    @Test
    public void editWithValueExistsTest() {
        // 根据ID查询
        CourseEntity course = entityManager.find(CourseEntity.class, 2);
        log.info("数据查询，course = {}", course);
        // 数据修改
        course.setCname("Netty就业课程实战");
        course.setCredit(5);
        // 执行更新
        entityManager.merge(course);
    }

    /**
     * 测试：getReference
     * find 和 getReference 两种查询操作在数据存在的时候，没有任何使用上的区别；
     * 而在数据部存在的时候，find返回的是null，getReference会抛异常
     */
    @Test
    public void referenceTest() {
        // 查询一个不存在的数据ID
        CourseEntity course = entityManager.find(CourseEntity.class, 6);
        // 数据查询 find，     course = null
        log.info("数据查询 find，     course = {}", course);
        course = entityManager.getReference(CourseEntity.class, 6);
        /*
         * 数据查询 reference，course = [FAILED toString()]
         * jakarta.persistence.EntityNotFoundException:
         *          Unable to find com.example.boot3.jpa.po.CourseEntity with id 6
         */
        log.info("数据查询 reference，course = {}", course);
    }

    /**
     * 测试：删除实体对象
     * 删除对象之前需要先查找到对象，建议使用 getReference
     */
    @Test
    public void removeTest() {
        CourseEntity course = entityManager.getReference(CourseEntity.class, 3);
        // 实际执行的SQL Hibernate: delete from course where cid=?
        entityManager.remove(course);
    }

    /**
     * 测试：删除实体对象
     * 不做查询，直接根据ID删除数据
     */
    @Test
    public void removeByIdTest() {
        CourseEntity course = CourseEntity.builder().cid(4).build();
        /*
         * 删除报错 java.lang.IllegalArgumentException:
         *      Removing a detached instance com.example.boot3.jpa.po.CourseEntity#4
         * 如果想要删除数据，那么必须首先获取到数据实例（与JPA数据对象的状态有关）
         * 而如果是一个自定义的对象将无法进行删除。
         * 但是，这样的删除操作在需要前置查询，要比传统的删除多了一次查询，自然性能上就存在有严重的限制。
         */
        entityManager.remove(course);
    }

    @AfterEach
    public void after(){
        if (Objects.nonNull(entityManager)) {
            entityManager.getTransaction().commit();
            entityManager.close();
        }
    }
}
