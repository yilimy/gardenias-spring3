package com.example.boot3.spring.cache.service;

import com.example.boot3.spring.cache.StartSpringCache;
import com.example.boot3.spring.cache.po.Emp2;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * 测试： 查询缓存
 * @author caimeng
 * @date 2024/7/22 13:57
 */
@Slf4j
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = StartSpringCache.class)
public class IEmp2ServiceCacheTest {
    @Autowired
    private IEmp2Service iEmp2Service;

    /**
     * 测试缓存
     * <p>
     *     连续两次接口调用，只有一次数据库连接
     *     Hibernate: select e1_0.eid,e1_0.ename,e1_0.job,e1_0.salary from Emp2 e1_0 where e1_0.eid=?
     *     【第一次】雇员数据查询: emp2 = Emp2(eid=muyan, ename=包可爱的小李老师, job=作者兼讲师, salary=3600.0)
     *     【第二次】雇员数据查询: emp2 = Emp2(eid=muyan, ename=包可爱的小李老师, job=作者兼讲师, salary=3600.0)
     * <p>
     *     但是查询返回null，也只执行一次数据库连接
     */
    @Test
    public void getTestCache() {
        Emp2 empA = iEmp2Service.get("muyan");
        System.out.printf("【第一次】雇员数据查询: emp2 = %s\n", empA);
        Emp2 empB = iEmp2Service.get("muyan");
        System.out.printf("【第二次】雇员数据查询: emp2 = %s\n", empB);
    }

    /**
     * Hibernate: select e1_0.eid,e1_0.ename,e1_0.job,e1_0.salary from Emp2 e1_0 where e1_0.ename=?
     * 【第一次】雇员数据查询: emp = Emp2(eid=muyan, ename=包可爱的小李老师, job=作者兼讲师, salary=3600.0)
     * 【第二次】雇员数据查询: emp = Emp2(eid=muyan, ename=包可爱的小李老师, job=作者兼讲师, salary=3600.0)
     */
    @Test
    public void getByEnameTest() {
        Emp2 empA = iEmp2Service.getByEname("包可爱的小李老师");
        System.out.printf("【第一次】雇员数据查询: emp = %s\n", empA);
        Emp2 empB = iEmp2Service.getByEname("包可爱的小李老师");
        System.out.printf("【第二次】雇员数据查询: emp = %s\n", empB);
    }
}
