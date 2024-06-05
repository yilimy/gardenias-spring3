package com.example.boot3.jpa;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateUtil;
import com.example.boot3.jpa.po.CourseEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.Test;

/**
 * 测试：JPA
 * <p>
 *     开发者不需要编写任何的SQL语句，就可以直接实现数据的增加处理，这就是ORM的核心设计思想。
 *     相当于程序通过操作方法来实现CURD操作
 * persistence.xml 参考 : <a href="https://docs.jboss.org/hibernate/orm/5.6/quickstart/html_single/" /> 4.1
 * @author caimeng
 * @date 2024/6/4 18:24
 */
public class CourseTest {
    /**
     * 测试:添加数据
     */
    @Test
    public void add() {
        /*
         * 通过实体标记获取实体工厂
         * EntityManagerFactory 应该跟进程的生命周期一致，否则会报错 RejectedExecutionException
         *      Task com.mysql.cj.jdbc.ConnectionImpl$NetworkTimeoutSetter@704dd5e rejected from java.util.concurrent.ThreadPoolExecutor@1b8f1e2a[Terminated, pool size = 0, active threads = 0, queued tasks = 0, completed tasks = 27]
         * 即此处不要使用 try-resource 关闭连接池
         */
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("exampleJPA");
        // 创建JPA的操作对象
        EntityManager entityManager = factory.createEntityManager();
        /*
         * 开启JPA事务,
         * 该事务与spring事务无关
         */
        entityManager.getTransaction().begin();
        CourseEntity course = CourseEntity.builder()
                .cname("Spring编程实战")
                .credit(2)
                .num(10)
                .start(DateUtil.date().offset(DateField.MONTH, -2))
                .end(DateUtil.date())
                .build();
        // 数据存储
        entityManager.persist(course);
        // 事务提交
        entityManager.getTransaction().commit();
        entityManager.close();
    }
}
