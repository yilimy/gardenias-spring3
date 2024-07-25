package com.example.boot3.spring.cache.service;

import com.example.boot3.spring.cache.StartSpringCache;
import com.example.boot3.spring.cache.po.Emp2;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * 测试：缓存驱逐 | 删除
 * @author caimeng
 * @date 2024/7/24 18:06
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = StartSpringCache.class)
public class IEmp2ServiceCacheEvictTest {

    @Autowired
    private IEmp2Service iEmp2Service;

    /**
     * 测试缓存驱逐
     * <p>
     *     Hibernate: select e1_0.eid,e1_0.ename,e1_0.job,e1_0.salary from Emp2 e1_0 where e1_0.eid=?
     *     【第一次】查询雇员信息：Emp2(eid=yootk, ename=李木纹, job=讲师, salary=5000.0)
     *     Hibernate: select count(*) from Emp2 e1_0 where e1_0.eid=?
     *     Hibernate: select e1_0.eid,e1_0.ename,e1_0.job,e1_0.salary from Emp2 e1_0 where e1_0.eid=?
     *     Hibernate: delete from Emp2 where eid=?
     *     删除结果：true
     *     Hibernate: select e1_0.eid,e1_0.ename,e1_0.job,e1_0.salary from Emp2 e1_0 where e1_0.eid=?
     *     【第二次】查询雇员信息：null
     */
    @Test
    public void deleteTest() {
        String eid = "yootk";
        Emp2 empA = iEmp2Service.get(eid);
        System.out.println("【第一次】查询雇员信息：" + empA);
        boolean delRet = iEmp2Service.delete(eid);
        System.out.println("删除结果：" + delRet);
        Emp2 empB = iEmp2Service.get(eid);
        System.out.println("【第二次】查询雇员信息：" + empB);
    }
}
