package com.example.boot3.jpa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import java.util.Objects;

/**
 * @author caimeng
 * @date 2024/6/24 14:51
 */
public class EntityManagerBaseTest {
    protected EntityManager entityManager;

    @BeforeEach
    public void init() {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("exampleJPA");
        entityManager = factory.createEntityManager();
    }

    @AfterEach
    public void after(){
        if (Objects.nonNull(entityManager) && entityManager.isOpen()) {
            entityManager.close();
        }
    }
}
