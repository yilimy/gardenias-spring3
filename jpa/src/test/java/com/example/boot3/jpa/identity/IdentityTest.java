package com.example.boot3.jpa.identity;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateUtil;
import com.example.boot3.jpa.po.CourseUUID;
import com.example.boot3.jpa.po.DeptPO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.Objects;

/**
 * 测试：主键生成
 * @author caimeng
 * @date 2024/6/7 14:30
 */
public class IdentityTest {
    private EntityManager entityManager;

    @AfterEach
    public void after(){
        if (Objects.nonNull(entityManager)) {
            entityManager.getTransaction().commit();
            entityManager.close();
        }
    }

    /**
     * 测试：UUID主键
     */
    @Test
    public void uuidTest() {
        /*
         * 以删除的方式执行测试，因为经常要改动属性
         */
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("exampleJpaIdentity");
        entityManager = factory.createEntityManager();
        entityManager.getTransaction().begin();
        CourseUUID courseUUID = CourseUUID.builder()
                .cname("Spring编程实战UUID")
                .credit(4)
                .num(12)
                .start(DateUtil.date().offset(DateField.MONTH, -4))
                .end(DateUtil.date())
                .build();
        entityManager.persist(courseUUID);
    }

    /**
     * 测试：table主键
     */
    @Test
    public void tableTest() {
        /*
         * 注意，该处不能使用 hibernate.hbm2ddl.auto=create，否则，主键表 table_id_generate 也会被删除
         */
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("exampleJPA");
        entityManager = factory.createEntityManager();
        entityManager.getTransaction().begin();
        DeptPO dept = new DeptPO("教学研发部");
        entityManager.persist(dept);
        // com.example.boot3.jpa.po.DeptPO{dname=教学研发部, deptno=6667}
        System.out.println("更新完成: deptno = " + dept.getDeptno());
    }
}
