package com.example.boot3.jpa.relevancy;

import com.example.boot3.jpa.EntityManagerBaseTest;
import com.example.boot3.jpa.po.Details;
import com.example.boot3.jpa.po.Login;
import com.example.boot3.jpa.po.lazy.LoginLazy;
import org.junit.jupiter.api.Test;

/**
 * @author caimeng
 * @date 2024/6/24 10:45
 */
public class OneToOneTest extends EntityManagerBaseTest {

    /**
     * 测试：关联新增
     * Hibernate: insert into login (password, uid) values (?, ?)
     * Hibernate: insert into details (age, name, uid) values (?, ?, ?)
     * <p>
     *     虽然只实现了 Login 对象的持久化
     *          entityManager.persist(login);
     *     但是由于一对一关联结构的配置，那么也会自动进行details数据的持久化。
     *     如果没有配置级联关系，这个时候是不会进行级联配置的。
     */
    @Test
    public void addTest() {
        entityManager.getTransaction().begin();
        Login login = new Login();
        login.setUid("lee");
        login.setPassword("hello");
        Details details = new Details();
        // 与 Login.uid 成员属性一致
        details.setUid("lee");
        details.setName("lee Name");
        details.setAge(23);
        // 本次操作同时设置了login与details表数据的设置，实际开发中可能只会设置login数据
        // 设置用户登录与用户详情之间的关联
        login.setDetails(details);
        // 设置用户详情与用户登录之间的关联
        details.setLogin(login);
        // 只存储login的实例
        entityManager.persist(login);
        entityManager.getTransaction().commit();
    }

    @Test
    public void findTest() {
        // 数据查询
        Login login = entityManager.find(Login.class, "lee");
        /*
         * Hibernate: select l1_0.uid,d1_0.uid,d1_0.age,d1_0.name,l1_0.password from login l1_0 left join details d1_0 on l1_0.uid=d1_0.uid where l1_0.uid=?
         * Login(uid=lee, password=hello)
         *
         * 此时的程序会自动将Login以及其关联的Details数据信息一起进行完整的查询，查询一次就可以获取两个实体的数据。
         * 如果此时不需要使用到Details，那么这样的查询就没有意义。
         * O.o : 相互依赖打印toString,内存溢出了,排掉
         */
        System.out.println(login);
    }

    /**
     * 测试: fetch懒加载
     * <p>
     *     在一对一关联结构里,懒加载的操作是指分为两个不同的SQL查询语句.
     *     因为JPA认为一对一是两个子结构的完整结构.
     *     而对于一对多的时候,就会出现不同的场景了,会产生两次的数据查询逻辑.
     */
    @Test
    public void findLazyTest() {
        LoginLazy login = entityManager.find(LoginLazy.class, "lee");
        /*
         * Hibernate: select d1_0.uid,d1_0.age,d1_0.name from details d1_0 where d1_0.uid=?
         * Hibernate: select d1_0.uid,d1_0.age,d1_0.name from details d1_0 where d1_0.uid=?
         * 此条SQL语句可以发现:
         *      代码里已经不需要进行多表查询了,而是只查询了login数据,
         *      当要加载details数据的时候再次发出第二条查询语句.
         */
        // 第一次查询的结果:LoginLazy(uid=lee, password=hello)
        System.out.println("第一次查询的结果:" + login);
        // 第二次查询的结果:DetailsLazy(uid=lee, name=lee Name, age=23)
        System.out.println("第二次查询的结果:" + login.getDetails());
    }
}
