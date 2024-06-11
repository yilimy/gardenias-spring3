package com.example.boot3.jpa.jpql;

import com.example.boot3.jpa.po.CourseEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Objects;

/**
 * JPQL查询操作
 * @author caimeng
 * @date 2024/6/11 10:56
 */
@Slf4j
public class JpqlSelectTest {
    private EntityManager entityManager;

    @BeforeEach
    public void init() {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("exampleJPA");
        entityManager = factory.createEntityManager();
    }

    /**
     * 测试：JPQL查询全部
     * <p>
     *     执行日志：
     *     Hibernate: select c1_0.cid,c1_0.cname,c1_0.credit,c1_0.end,c1_0.num,c1_0.start from course c1_0
     *
     */
    @Test
    public void selectAll() {
        /*
         * JPQL查询
         * 此时查询中的 CourseEntity 是PO类的名称，不是表名称
         */
        String jpql = "select c from CourseEntity AS c";
        // 类全名也是可行的
//        String jpql = "select c from com.example.boot3.jpa.po.CourseEntity AS c";
        Query query = entityManager.createQuery(jpql);
        @SuppressWarnings("unchecked")
        List<CourseEntity> courseEntities = query.getResultList();
        courseEntities.forEach(item -> System.out.println("查询结果：" + item));
    }

    /**
     * 测试：根据ID查询数据
     */
    @Test
    public void selectById() {
        /*
         * 根据ID查询，需要使用 "占位符 + 索引" 的方式进行，比如："?1"
         */
        String jpql = "select c from CourseEntity AS c WHERE c.cid=?1";
        Query query = entityManager.createQuery(jpql);
        // 根据索引设置占位符的值
        query.setParameter(1, 1L);
        /*
         * 这种操作里面，需要进行类型转换处理，本身就存在有安全隐患。
         * Query 不支持泛型类型的标记，为了避免强制对象转型所带来的问题，可以考虑 TypedQuery 接口
         * SQL日志打印：
         *      Hibernate: select c1_0.cid,c1_0.cname,c1_0.credit,c1_0.end,c1_0.num,c1_0.start from course c1_0 where c1_0.cid=?
         */
        CourseEntity course = (CourseEntity) query.getSingleResult();
        System.out.println(course);
    }

    @Test
    public void selectByIdWithType() {
        String jpql = "select c from CourseEntity AS c WHERE c.cid=?1";
        TypedQuery<CourseEntity> query = entityManager.createQuery(jpql, CourseEntity.class);
        query.setParameter(1, 1L);
        CourseEntity course = query.getSingleResult();
        System.out.println(course);
    }

    /**
     * 测试：分页查询
     */
    @Test
    public void selectSplit() {
        int currentPage = 1, pageSize = 2;
        String keyword = "%就业%";
        /*
         * 使用PO类中的属性（不是数据库字段）进行模糊匹配
         * 实际上，JPA是针对java的编程规范，就很少接触到数据库字段的吧
         */
        String jpql = "select c from CourseEntity AS c WHERE c.cname LIKE ?1";
        TypedQuery<CourseEntity> query = entityManager.createQuery(jpql, CourseEntity.class);
        /*
         * 设置开始查询行
         * 起始：0
         */
        query.setFirstResult((currentPage - 1) * pageSize);
        // 长度
        query.setMaxResults(pageSize);
        query.setParameter(1, keyword);
        /*
         * Hibernate: select c1_0.cid,c1_0.cname,c1_0.credit,c1_0.end,c1_0.num,c1_0.start from course c1_0 where c1_0.cname like ? limit ?,?
         * 该处使用的是 limit，因为设置的方言是Mysql，如果是Oracle则会自动换成子查询。
         */
        List<CourseEntity> resultList = query.getResultList();
        /*
         * 查询结果：CourseEntity(cid=2, cname=Netty就业课程实战, start=2024-04-11, end=2024-06-11, credit=5, num=10)
         * 查询结果：CourseEntity(cid=5, cname=Spring就业实战, start=2024-04-11, end=2024-06-11, credit=2, num=10)
         */
        resultList.forEach(item -> System.out.println("查询结果：" + item));
    }

    /**
     * 测试：count
     */
    @Test
    public void countTest() {
        String keyword = "%就业%";
        String jpql = "select COUNT(c) from CourseEntity AS c WHERE c.cname LIKE ?1";
        TypedQuery<CourseEntity> query = entityManager.createQuery(jpql, CourseEntity.class);
        query.setParameter(1, keyword);
        // count结果：2
        System.out.println("count结果：" + query.getSingleResult());
    }

    @AfterEach
    public void after(){
        if (Objects.nonNull(entityManager)) {
            entityManager.close();
        }
    }
}
