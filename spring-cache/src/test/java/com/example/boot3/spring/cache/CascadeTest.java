package com.example.boot3.spring.cache;

import cn.hutool.core.util.RandomUtil;
import com.example.boot3.spring.cache.po.Emp2;
import com.example.boot3.spring.cache.service.IEmp2Service;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * 测试：多级缓存
 * @author caimeng
 * @date 2024/7/25 19:53
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = StartSpringCache.class)
public class CascadeTest {

    @Autowired
    private IEmp2Service iEmp2Service;

    /**
     * 测试多级缓存
     * <p>
     *     Hibernate: select e1_0.eid,e1_0.ename,e1_0.job,e1_0.salary from Emp2 e1_0 where e1_0.ename=?
     *     【第一次】查询雇员信息：Emp2(eid=mylee, ename=李兴华, job=讲师, salary=3000.0)
     *     Hibernate: select e1_0.eid,e1_0.ename,e1_0.job,e1_0.salary from Emp2 e1_0 where e1_0.eid=?
     *     Hibernate: update Emp2 set ename=?, job=?, salary=? where eid=?
     *     【更新】雇员数据：Emp2(eid=mylee, ename=包可爱的小李老师PLUS-4R, job=作者兼讲师兼开发者, salary=3605.0)
     *     【第二次】查询雇员信息：Emp2(eid=mylee, ename=李兴华, job=讲师, salary=3000.0)
     * <p>
     *     说明通过名称查询的缓存并没有受到通过edi编辑缓存的影响，这种情况是单极缓存
     */
    @Test
    public void editTest() {
        Emp2 empA = iEmp2Service.getByEname("李兴华");
        System.out.println("【第一次】查询雇员信息：" + empA);
        Emp2 empB = Emp2.builder()
                // 李兴华对应的eid
                .eid("mylee")
                // 新数据的内容
                .ename("包可爱的小李老师PLUS-" + RandomUtil.randomString(2))
                .job("作者兼讲师兼开发者")
                .salary(3601.0 + RandomUtil.randomInt(10))
                .build();
        // 执行更新
        System.out.println("【更新】雇员数据：" + iEmp2Service.edit(empB));
        Emp2 empC = iEmp2Service.getByEname("李兴华");
        System.out.println("【第二次】查询雇员信息：" + empC);
    }

    /**
     * 测试：多级缓存
     * <p>
     *     Hibernate: select e1_0.eid,e1_0.ename,e1_0.job,e1_0.salary from Emp2 e1_0 where e1_0.ename=?
     *     【第一次】查询雇员信息：Emp2(eid=mylee, ename=李兴华, job=作者兼讲师兼开发者, salary=3605.0)
     *     Hibernate: select e1_0.eid,e1_0.ename,e1_0.job,e1_0.salary from Emp2 e1_0 where e1_0.eid=?
     *     【第二次】查询雇员信息：Emp2(eid=mylee, ename=李兴华, job=作者兼讲师兼开发者, salary=3605.0)
     *     Hibernate: select e1_0.eid,e1_0.ename,e1_0.job,e1_0.salary from Emp2 e1_0 where e1_0.eid=?
     *     Hibernate: update Emp2 set ename=?, job=?, salary=? where eid=?
     *     【更新】雇员数据：Emp2(eid=mylee, ename=包可爱的小李老师PLUS-F3, job=作者兼讲师兼开发者, salary=3607.0)
     *     【第三次】查询雇员信息：Emp2(eid=mylee, ename=李兴华, job=作者兼讲师兼开发者, salary=3605.0)
     *     【第四次】查询雇员信息：Emp2(eid=mylee, ename=包可爱的小李老师PLUS-F3, job=作者兼讲师兼开发者, salary=3607.0)
     */
    @Test
    public void editCascadeTest() {
        String eid = "mylee";
        String oldName = "李兴华";
        // 查询时，是根据ename查缓存
        Emp2 empA = iEmp2Service.getByEname(oldName);
        System.out.println("【第一次】查询雇员信息：" + empA);
        Emp2 empB = iEmp2Service.get(eid);
        System.out.println("【第二次】查询雇员信息：" + empB);
        Emp2 empC = Emp2.builder()
                // 李兴华对应的eid
                .eid(eid)
                // 新数据的内容
                .ename("包可爱的小李老师PLUS-" + RandomUtil.randomString(2))
                .job("作者兼讲师兼开发者")
                .salary(3601.0 + RandomUtil.randomInt(10))
                .build();
        /*
         * 执行更新
         * 更新时，同时根据ename和eid更新缓存（多级缓存）
         */
        System.out.println("【更新】雇员数据：" + iEmp2Service.editCascade(empC));
        /*
         * 再次通过ename查询缓存
         * 因为缓存不会删除，所以会出现一个 "包可爱的小李老师PLUS-*" 新缓存对象，使用名字的缓存对象仍存在
         */
        Emp2 empD = iEmp2Service.getByEname(oldName);
        System.out.println("【第三次】查询雇员信息：" + empD);
        Emp2 empE = iEmp2Service.get(eid);
        System.out.println("【第四次】查询雇员信息：" + empE);
    }

    /**
     * {@link this#editCascadeTest()} 的对照组，不改变ename,测试缓存的双改
     * <p>
     *     Hibernate: select e1_0.eid,e1_0.ename,e1_0.job,e1_0.salary from Emp2 e1_0 where e1_0.ename=?
     *     【第一次】查询雇员信息：Emp2(eid=mylee, ename=李兴华, job=作者兼讲师兼开发者, salary=3607.0)
     *     Hibernate: select e1_0.eid,e1_0.ename,e1_0.job,e1_0.salary from Emp2 e1_0 where e1_0.eid=?
     *     【第二次】查询雇员信息：Emp2(eid=mylee, ename=李兴华, job=作者兼讲师兼开发者, salary=3607.0)
     *     Hibernate: select e1_0.eid,e1_0.ename,e1_0.job,e1_0.salary from Emp2 e1_0 where e1_0.eid=?
     *     Hibernate: update Emp2 set ename=?, job=?, salary=? where eid=?
     *     【更新】雇员数据：Emp2(eid=mylee, ename=李兴华, job=作者兼讲师兼开发者, salary=3610.0)
     *     【第三次】查询雇员信息：Emp2(eid=mylee, ename=李兴华, job=作者兼讲师兼开发者, salary=3610.0)
     *     【第四次】查询雇员信息：Emp2(eid=mylee, ename=李兴华, job=作者兼讲师兼开发者, salary=3610.0)
     */
    @Test
    public void editCascade2Test() {
        String eid = "mylee";
        String oldName = "李兴华";
        Emp2 empA = iEmp2Service.getByEname(oldName);
        System.out.println("【第一次】查询雇员信息：" + empA);
        Emp2 empB = iEmp2Service.get(eid);
        System.out.println("【第二次】查询雇员信息：" + empB);
        Emp2 empC = Emp2.builder()
                // 李兴华对应的eid
                .eid(eid)
                // 新数据的内容
                .ename(oldName)
                .job("作者兼讲师兼开发者")
                .salary(3601.0 + RandomUtil.randomInt(10))
                .build();
        /*
         * 执行更新
         * 更新时，同时根据ename和eid更新缓存（多级缓存）
         */
        System.out.println("【更新】雇员数据：" + iEmp2Service.editCascade(empC));
        Emp2 empD = iEmp2Service.getByEname(oldName);
        System.out.println("【第三次】查询雇员信息：" + empD);
        Emp2 empE = iEmp2Service.get(eid);
        System.out.println("【第四次】查询雇员信息：" + empE);
    }

}
