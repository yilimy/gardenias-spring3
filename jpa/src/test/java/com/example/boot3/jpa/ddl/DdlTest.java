package com.example.boot3.jpa.ddl;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateUtil;
import com.example.boot3.jpa.po.CourseEntity;
import com.example.boot3.jpa.po.CourseWrapper;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * 测试：DDL
 * @author caimeng
 * @date 2024/6/7 11:34
 */
public class DdlTest {
    private CourseEntity course;
    private CourseWrapper courseWrapper;
    private  EntityManager entityManager;

    @BeforeEach
    public void init(){
        course = CourseEntity.builder()
                .cname("Spring编程实战")
                .credit(2)
                .num(10)
                .start(DateUtil.date().offset(DateField.MONTH, -2))
                .end(DateUtil.date())
                .build();
        courseWrapper = CourseWrapper.builder()
                .cname("Spring编程实战Wrapper")
                .credit(3)
                .num(11)
                .start(DateUtil.date().offset(DateField.MONTH, -3))
                .end(DateUtil.date())
                .teacher("老司机")
                .school("不增学校")
                .build();
    }

    @AfterEach
    public void after(){
        entityManager.getTransaction().commit();
        entityManager.close();
    }
    /**
     * 测试： DDL-create
     * <p>
     *      日志中出现了字样:
     *          Hibernate: drop table if exists course
     *          ... create table course ...
     * <p>
     *     执行完毕后，数据库中只剩下一条数据
     */
    @Test
    public void createTest() {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("exampleJpaDdlCreate");
        // 在这里指定ddl不生效
//        factory.getProperties().put("hibernate.hbm2ddl.auto", "create");
        entityManager = factory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(course);
    }

    /**
     * 测试： ddl - update
     */
    @Test
    public void updateTest() {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("exampleJpaDdlUpdate");
        entityManager = factory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(course);
    }

    /**
     * 测试： ddl - update - 有字段新增
     * 如果PO中删除了某个属性，数据库表字段，不会做改动
     */
    @Test
    public void updateChangeTest() {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("exampleJpaDdlUpdate");
        entityManager = factory.createEntityManager();
        entityManager.getTransaction().begin();
        /*
         * 新增了字段 teacher
         * 本地打印如下，与视频不符 <a href="https://www.bilibili.com/video/BV1zy411b7Eg/" />
         *      No alter strings for table : course
         * 但是结果一致
         * 执行sql
         *      Hibernate: insert into course (cname, credit, end, num, start, teacher) values (?, ?, ?, ?, ?, ?)
         * 与属性school无关
         */
        entityManager.persist(courseWrapper);
    }
}
