package com.example.boot3.jpa.jpql;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Objects;

/**
 * JPQL更新操作
 * @author caimeng
 * @date 2024/6/11 13:54
 */
public class JpqlEditTest {
    private EntityManager entityManager;

    @BeforeEach
    public void init() {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("exampleJPA");
        entityManager = factory.createEntityManager();
        entityManager.getTransaction().begin();
    }

    /**
     * 测试：根据条件更新数据字段
     */
    @Test
    public void sqlUpdate() {
        String jpql = "UPDATE CourseEntity AS c SET c.credit=?1 WHERE c.credit<?2";
        Query query = entityManager.createQuery(jpql);
        // 将学分小于3的数据条，变更为学分5
        query.setParameter(1, 5).setParameter(2, 3);
        // Hibernate: update course set credit=? where credit<?
        int i = query.executeUpdate();
        // 更新处理结果：4
        System.out.println("更新处理结果：" + i);
    }

    /**
     * 测试：根据ID删除
     */
    @Test
    public void removeTest() {
        String jpql = "DELETE FROM CourseEntity AS c WHERE c.cid=?1";
        Query query = entityManager.createQuery(jpql);
        query.setParameter(1, 5);
        int i = query.executeUpdate();
        // 删除处理结果：1
        System.out.println("删除处理结果：" + i);
    }

    @AfterEach
    public void after(){
        if (Objects.nonNull(entityManager)) {
            entityManager.getTransaction().commit();
            entityManager.close();
        }
    }
}
