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
}
