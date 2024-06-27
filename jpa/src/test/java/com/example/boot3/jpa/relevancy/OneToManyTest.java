package com.example.boot3.jpa.relevancy;

import com.example.boot3.jpa.EntityManagerBaseTest;
import com.example.boot3.jpa.po.Dept;
import com.example.boot3.jpa.po.Emp;
import com.example.boot3.jpa.po.eager.DeptEager;
import jakarta.persistence.TypedQuery;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 测试: 一对多
 * @author caimeng
 * @date 2024/6/24 14:50
 */
public class OneToManyTest extends EntityManagerBaseTest {
    private final static Long DEPT_NO = 55L;

    /**
     * 一对多的新增
     */
    @Test
    public void addTest() {
        // 关联更新,必须要开启事务
        entityManager.getTransaction().begin();
        Dept dept = new Dept(DEPT_NO, "开发部", "洛阳");
        dept.setEmps(List.of(
                new Emp(7839L, "小明", 9919D, dept),
                new Emp(7840L, "小红", 9920D, dept),
                new Emp(7841L, "小蓝", 9921D, dept),
                new Emp(7842L, "小绿", 9922D, dept)
        ));
        /*
         * Hibernate: insert into dept (dname, loc, deptno) values (?, ?, ?)
         * Hibernate: insert into emp (deptno, ename, sal, empno) values (?, ?, ?, ?)
         * Hibernate: insert into emp (deptno, ename, sal, empno) values (?, ?, ?, ?)
         * Hibernate: insert into emp (deptno, ename, sal, empno) values (?, ?, ?, ?)
         * Hibernate: insert into emp (deptno, ename, sal, empno) values (?, ?, ?, ?)
         */
        entityManager.persist(dept);
        entityManager.getTransaction().commit();
    }

    /**
     * 测试:一对多数据查询
     * <p>
     *     相比于一对一的查询 {@link OneToOneTest#findTest()},一对多的查询没有进行连表查.
     *     在当前查询部门数据的时候,其实真的只是在查询对应的部门表(这是和一对一不同的地方)
     *     因为当前的数据采用了懒加载的模式 {@link OneToManyTest#findLazyTest()}.
     */
    @Test
    public void findTest() {
        Dept dept = entityManager.find(Dept.class, DEPT_NO);
        /*
         * Hibernate: select d1_0.deptno,d1_0.dname,d1_0.loc from dept d1_0 where d1_0.deptno=?
         * Dept(deptno=55, dname=开发部, loc=洛阳)
         */
        System.out.println(dept);
    }

    /**
     * 在JPA里面进行一对多数据查询的时候,考虑到"多"方的数据量很大,所以默认采用的机制是懒加载,
     * 即: 在需要用到多方数据的时候再进行数据的查询处理,如果不用就不查询.
     */
    @Test
    public void findLazyTest() {
        Dept dept = entityManager.find(Dept.class, DEPT_NO);
        /*
         * Hibernate: select d1_0.deptno,d1_0.dname,d1_0.loc from dept d1_0 where d1_0.deptno=?
         * 第一次查询: Dept(deptno=55, dname=开发部, loc=洛阳)
         */
        System.out.println("第一次查询: " + dept);
        /*
         * Hibernate: select e1_0.deptno,e1_0.empno,e1_0.ename,e1_0.sal from emp e1_0 where e1_0.deptno=?
         * 第二次查询: Emp(empno=7839, ename=小明, sal=9919.0)
         * 第二次查询: Emp(empno=7840, ename=小红, sal=9920.0)
         * 第二次查询: Emp(empno=7841, ename=小蓝, sal=9921.0)
         * 第二次查询: Emp(empno=7842, ename=小绿, sal=9922.0)
         */
        dept.getEmps().forEach(emp -> System.out.println("第二次查询: " + emp));
    }

    /**
     * 测试:懒加载过程中,释放数据库连接
     * <p>
     *     no Session 是 hibernate 早期的概念; EntityManagerFactory 会提示 SessionFactory; 两个提示是对等的.
     *     在 spring 中为了解决这个问题,会提供有一种页面关闭 session 的支持, 但是不建议用.
     *     对于级联的操作,也不是很赞同用.
     * 变更抓取方式 {@link OneToManyTest#findLazyMidCloseWithFetchChangeTest()}
     */
    @SneakyThrows
    @Test
    public void findLazyMidCloseTest() {
        Dept dept = entityManager.find(Dept.class, DEPT_NO);
        System.out.println("第一次查询: " + dept);
        entityManager.close();
        /*
         * 加载雇员信息
         * 调用 dept.getEmps() 并不会导致报错, 要去获取到更详细的信息才会触发,比如 size
         * org.hibernate.LazyInitializationException:
         *      failed to lazily initialize a collection of role:
         *          com.example.boot3.jpa.po.Dept.emps: could not initialize proxy - no Session
         */
        //noinspection ResultOfMethodCallIgnored
        dept.getEmps().size();
        TimeUnit.SECONDS.sleep(Integer.MAX_VALUE);
    }

    /**
     * 变更数据抓取方式为 EAGER 后,在查询中途关闭数据库连接
     * 查询的SQL语句中出现了 left join
     * <p>
     *     Hibernate: select d1_0.deptno,d1_0.dname,e1_0.deptno,e1_0.empno,e1_0.ename,e1_0.sal,d1_0.loc from dept d1_0 left join emp e1_0 on d1_0.deptno=e1_0.deptno where d1_0.deptno=?
     *     Hibernate: select d1_0.deptno,d1_0.dname,d1_0.loc from dept d1_0 where d1_0.deptno=?
     *     第一次查询: DeptEager(deptno=55, dname=开发部, loc=洛阳)
     *     Emp(empno=7839, ename=小明, sal=9919.0)
     *     Emp(empno=7840, ename=小红, sal=9920.0)
     *     Emp(empno=7841, ename=小蓝, sal=9921.0)
     *     Emp(empno=7842, ename=小绿, sal=9922.0)
     * <p>
     *     此时的程序会在查询部门的数据的同时,将所有对应的雇员数据查询出来.
     *     如果此时要执行的是全部部门的查询呢
     */
    @SneakyThrows
    @Test
    public void findLazyMidCloseWithFetchChangeTest() {
        DeptEager dept = entityManager.find(DeptEager.class, DEPT_NO);
        System.out.println("第一次查询: " + dept);
        entityManager.close();
        dept.getEmps().forEach(System.out::println);
        TimeUnit.SECONDS.sleep(Integer.MAX_VALUE);
    }

    /**
     * 测试:抓取数据为 EAGER, 查询全部数据
     * 查询次数为 1+N 次,此方案查询效率极低,性能很差，而且容易出现各类不确定的问题。
     */
    @SneakyThrows
    @Test
    public void findFetchChangeAllTest() {
        String jpql = "select d from DeptEager as d ";
        TypedQuery<DeptEager> query = entityManager.createQuery(jpql, DeptEager.class);
        List<DeptEager> resultList = query.getResultList();
        /*
         * Hibernate: select d1_0.deptno,d1_0.dname,d1_0.loc from dept d1_0
         * Hibernate: select e1_0.deptno,e1_0.empno,e1_0.ename,e1_0.sal from emp e1_0 where e1_0.deptno=?
         * Hibernate: select d1_0.deptno,d1_0.dname,d1_0.loc from dept d1_0 where d1_0.deptno=?
         * Hibernate: select e1_0.deptno,e1_0.empno,e1_0.ename,e1_0.sal from emp e1_0 where e1_0.deptno=?
         * Hibernate: select d1_0.deptno,d1_0.dname,d1_0.loc from dept d1_0 where d1_0.deptno=?
         * Hibernate: select e1_0.deptno,e1_0.empno,e1_0.ename,e1_0.sal from emp e1_0 where e1_0.deptno=?
         * Hibernate: select d1_0.deptno,d1_0.dname,d1_0.loc from dept d1_0 where d1_0.deptno=?
         * DeptEager(deptno=10, dname=教学部, loc=北京)
         * size = 3
         * DeptEager(deptno=20, dname=市场部, loc=上海)
         * size = 2
         * DeptEager(deptno=55, dname=开发部, loc=洛阳)
         * size = 4
         */
        resultList.forEach(item -> {
            System.out.println(item);
            System.out.println("size = " + item.getEmps().size());
        });
    }
}
