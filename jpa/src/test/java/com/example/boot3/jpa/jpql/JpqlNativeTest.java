package com.example.boot3.jpa.jpql;

import com.example.boot3.jpa.po.CourseEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Objects;

/**
 * JPQL使用原生的SQL
 * @author caimeng
 * @date 2024/6/11 14:18
 */
@Slf4j
public class JpqlNativeTest {
    private EntityManager entityManager;

    @BeforeEach
    public void init() {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("exampleJPA");
        entityManager = factory.createEntityManager();
    }

    /**
     * 根据id查询数据，返回数据库字段
     * 返回java对象 {@link this#selectByIdBackJavaObj()}
     */
    @Test
    public void selectById() {
        String jpql = "SELECT c1_0.cid,c1_0.cname,c1_0.credit,c1_0.end,c1_0.num,c1_0.start FROM course c1_0 WHERE c1_0.cid=?1";
        Query nativeQuery = entityManager.createNativeQuery(jpql);
        nativeQuery.setParameter(1, 2);
        /*
         * 使用原生SQL就不能直接转换成对象，会报错 java.lang.ClassCastException:
         *          class [Ljava.lang.Object; cannot be cast to class com.example.boot3.jpa.po.CourseEntity ([Ljava.lang.Object; is in module java.base of loader 'bootstrap'; com.example.boot3.jpa.po.CourseEntity is in unnamed module of loader 'app')
         * 不同于JPQL的一般查询，原生查询没有建立数据库字段与java对象间的映射
         * 返回对象
         */
//        CourseEntity singleResult = (CourseEntity) nativeQuery.getSingleResult();
        Object singleResult = nativeQuery.getSingleResult();
        /*
         * 查询结果: [2, Netty就业课程实战, 5, 2024-06-11, 10, 2024-04-11]
         * 结果返回可参照 JDBC-result，通过游标获取结果
         * 此时的查询结果本质上变成一个数组信息了，而且这个数组肯定无法直接与PO类进行转换处理，意味着开发者需要自己来获取对应的内容
         */
        log.info("查询结果: {}", singleResult);
    }

    /**
     * 根据id查询数据，返回java对象
     */
    @Test
    public void selectByIdBackJavaObj() {
        String jpql = "SELECT c1_0.cid,c1_0.cname,c1_0.credit,c1_0.end,c1_0.num,c1_0.start FROM course c1_0 WHERE c1_0.cid=?1";
        // sql与结果对象绑定
        Query nativeQuery = entityManager.createNativeQuery(jpql, CourseEntity.class);
        nativeQuery.setParameter(1, 2);
        CourseEntity singleResult = (CourseEntity) nativeQuery.getSingleResult();
        // 查询结果: CourseEntity(cid=2, cname=Netty就业课程实战, start=2024-04-11, end=2024-06-11, credit=5, num=10)
        log.info("查询结果: {}", singleResult);
    }

    /**
     * 分页查询
     */
    @Test
    public void selectSplit() {
        int currentPage = 1, pageSize = 2;
        String keyword = "%编程%";
        // 冒号表示参数引用，kw是参数名
        String jpql = "SELECT c1_0.cid,c1_0.cname,c1_0.credit,c1_0.end,c1_0.num,c1_0.start FROM course c1_0 WHERE c1_0.cname like :kw";
        Query nativeQuery = entityManager.createNativeQuery(jpql, CourseEntity.class);
        // 给参数kw赋值
        nativeQuery.setParameter("kw", keyword);
        nativeQuery.setFirstResult((currentPage - 1) * pageSize);
        nativeQuery.setMaxResults(pageSize);
        @SuppressWarnings("unchecked")
        List<CourseEntity> resultList = (List<CourseEntity>) nativeQuery.getResultList();
        // 查询结果: [CourseEntity(cid=1, cname=Spring编程实战, start=2024-04-11, end=2024-06-11, credit=5, num=10), CourseEntity(cid=4, cname=Spring编程实战, start=2024-04-11, end=2024-06-11, credit=5, num=10)]
        log.info("查询结果: {}", resultList);
    }

    @Test
    public void count(){
        String keyword = "%编程%";
        String jpql = "SELECT COUNT(cid) FROM course WHERE cname like :kw";
        Query nativeQuery = entityManager.createNativeQuery(jpql);
        nativeQuery.setParameter("kw", keyword);
        // 该处 singleResult 的实际类型为 Long，可以执行强转
        Object singleResult = nativeQuery.getSingleResult();
//        Long singleResult = (Long) nativeQuery.getSingleResult();
        // 数据统计结果: 4
        log.info("数据统计结果: {}", singleResult);

    }

    @Test
    public void delete() {
        String jpql = "DELETE FROM course WHERE cid=?1";
        Query nativeQuery = entityManager.createNativeQuery(jpql);
        nativeQuery.setParameter(1, 10);
        entityManager.getTransaction().begin();
        Object singleResult = nativeQuery.executeUpdate();
        entityManager.getTransaction().commit();
        // 删除结果: 1
        log.info("删除结果: {}", singleResult);
    }

    @Test
    public void update() {
        String jpql = "UPDATE course SET cname=?1, credit=?2 WHERE cid=?3";
        Query nativeQuery = entityManager.createNativeQuery(jpql);
        entityManager.getTransaction().begin();
        Object singleResult = nativeQuery
                .setParameter(1, "SpringBoot编程实战")
                .setParameter(2, 4)
                .setParameter(3, 11)
                .executeUpdate();
        entityManager.getTransaction().commit();
        // 更新结果: 1
        log.info("更新结果: {}", singleResult);
    }

    @AfterEach
    public void after(){
        if (Objects.nonNull(entityManager)) {
            entityManager.close();
        }
    }
}
