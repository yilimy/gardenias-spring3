package com.example.boot3.jpa.criteria;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateUtil;
import com.example.boot3.jpa.po.CourseEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaDelete;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Objects;

/**
 * @author caimeng
 * @date 2024/6/12 15:09
 */
@Slf4j
public class CriteriaTest {
    private EntityManager entityManager;

    @BeforeEach
    public void init() {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("exampleJPA");
        entityManager = factory.createEntityManager();
    }

    /**
     * 测试：使用 Criteria 进行查询
     */
    @Test
    public void selectAll() {
        /*
         * 创建Criteria的查询构建器，利用此构建器能执行查询和更新操作
         */
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        // 在创建查询的时候，需要明确的的先设置需要匹配的PO类型对象（FROM子句就是通过此处来的）
        CriteriaQuery<CourseEntity> criteriaQuery = criteriaBuilder.createQuery(CourseEntity.class);
        // 面向对象的开发过程中，最为头疼的问题就是：每一步的操作都需要通过明确的方法进行调用
        // 创建查询
        criteriaQuery.from(CourseEntity.class);
        // 创建查询对象
        TypedQuery<CourseEntity> query = entityManager.createQuery(criteriaQuery);
        List<CourseEntity> resultList = query.getResultList();
        resultList.forEach(System.out::println);
    }

    /**
     * 在进行处理的时候，编写了from子句，
     * 这个方法返回了一个ROOT接口实例，它表示的就是一个根查询的配置
     * 尝试通过这个接口实例获取部分信息
     */
    @Test
    public void rootTest() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<CourseEntity> criteriaQuery = criteriaBuilder.createQuery(CourseEntity.class);
        Root<CourseEntity> root = criteriaQuery.from(CourseEntity.class);
        /*
         * CID路径：SqmBasicValuedSimplePath(com.example.boot3.jpa.po.CourseEntity(21836090599200).cid)
         * 所谓的处理路径，实际上是最终生成的查询语句时，需要根据成员属性的信息，获取到对应的“@Column”注解的列名称配置项，
         * 这个路径保存的就是具体对应的内容。
         */
        log.info("CID路径：{}", root.get("cid"));
        // PO的类型：class com.example.boot3.jpa.po.CourseEntity
        log.info("PO的类型：{}", root.getJavaType());
    }

    /**
     * 测试：单条件查询
     */
    @Test
    public void singletonConditionTest() {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<CourseEntity> criteriaQuery = builder.createQuery(CourseEntity.class);
        Root<CourseEntity> root = criteriaQuery.from(CourseEntity.class);
        // 创建查询条件
        Predicate pdCredit = builder.equal(root.get("credit"), 2);
        // 追加where查询子句
        criteriaQuery.where(pdCredit);
        TypedQuery<CourseEntity> query = entityManager.createQuery(criteriaQuery);
        List<CourseEntity> resultList = query.getResultList();
        resultList.forEach(System.out::println);
    }

    /**
     * 多条件查询
     * 需要使用逻辑逻辑运算符进行连接。
     * AND | OR
     */
    @Test
    public void manyConditionTest() {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<CourseEntity> criteriaQuery = builder.createQuery(CourseEntity.class);
        Root<CourseEntity> root = criteriaQuery.from(CourseEntity.class);
        // 定义多查询条件
        Predicate or = builder.or(
                builder.gt(root.get("credit"), 4),
                builder.between(root.get("end"),
                        // 2024-06-12 15:56:38
                        DateUtil.date(),
                        // 2024-08-12 15:56:17
                        DateUtil.date().offset(DateField.MONTH, 2))
        );
        Predicate like = builder.like(root.get("cname"), "%编程%");
        Predicate gt = builder.gt(root.get("num"), 9);
        // 追加where查询子句
        criteriaQuery.where(or, like, gt);
        TypedQuery<CourseEntity> query = entityManager.createQuery(criteriaQuery);
        /*
         * Hibernate:
         *      select c1_0.cid,c1_0.cname,c1_0.credit,c1_0.end,c1_0.num,c1_0.start
         *      from course c1_0
         *      where
         *          (c1_0.credit>? or c1_0.end between ? and ?)
         *          and c1_0.cname like ?
         *          and c1_0.num>?
         */
        List<CourseEntity> resultList = query.getResultList();
        /*
         * CourseEntity(cid=1, cname=Spring编程实战, start=2024-04-11, end=2024-07-11, credit=5, num=10)
         * CourseEntity(cid=4, cname=Spring编程实战, start=2024-04-11, end=2024-06-11, credit=5, num=10)
         * CourseEntity(cid=7, cname=Dubbo编程实战, start=2024-04-11, end=2024-07-11, credit=5, num=10)
         * CourseEntity(cid=9, cname=Redis编程实战, start=2024-04-11, end=2024-05-11, credit=5, num=10)
         * CourseEntity(cid=12, cname=Spring编程实战, start=2024-04-11, end=2024-07-11, credit=2, num=13)
         */
        resultList.forEach(System.out::println);
    }

    @Test
    public void inTest() {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<CourseEntity> criteriaQuery = builder.createQuery(CourseEntity.class);
        Root<CourseEntity> root = criteriaQuery.from(CourseEntity.class);
        // 查询 cid = 6-9 的数据条
        Predicate pdIn = root.get("cid").in(6, 7, 8, 9);
        /*
         * 如果此时使用的时JPQL的方式处理，则此时的查询需要通过循环的方式，动态修改字符串
         */
        criteriaQuery.where(pdIn);
        TypedQuery<CourseEntity> query = entityManager.createQuery(criteriaQuery);
        /*
         * Hibernate: select c1_0.cid,c1_0.cname,c1_0.credit,c1_0.end,c1_0.num,c1_0.start from course c1_0 where c1_0.cid in(?,?,?,?)
         */
        List<CourseEntity> resultList = query.getResultList();
        /*
         * CourseEntity(cid=6, cname=Spring实习实战, start=2024-04-11, end=2024-05-11, credit=4, num=15)
         * CourseEntity(cid=7, cname=Dubbo编程实战, start=2024-04-11, end=2024-07-11, credit=5, num=10)
         * CourseEntity(cid=8, cname=Dubbo自习实战, start=2024-04-11, end=2024-06-11, credit=4, num=13)
         * CourseEntity(cid=9, cname=Redis编程实战, start=2024-04-11, end=2024-05-11, credit=5, num=10)
         */
        resultList.forEach(System.out::println);
    }

    /**
     * 测试：删除
     */
    @Test
    public void deleteTest() {
        entityManager.getTransaction().begin();
        // ------------
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaDelete<CourseEntity> criteriaDelete = builder.createCriteriaDelete(CourseEntity.class);
        Root<CourseEntity> root = criteriaDelete.from(CourseEntity.class);
        Predicate pdIn = root.get("cid").in(14);
        criteriaDelete.where(pdIn);
        Query query = entityManager.createQuery(criteriaDelete);
        // Hibernate: delete from course where cid in(?)
        int singleResult = query.executeUpdate();
        // ------------
        entityManager.getTransaction().commit();
        // 删除结果：1
        log.info("删除结果：{}", singleResult);
    }

    @AfterEach
    public void after(){
        if (Objects.nonNull(entityManager)) {
            entityManager.close();
        }
    }
}
