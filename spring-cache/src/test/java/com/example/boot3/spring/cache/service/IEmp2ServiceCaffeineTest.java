package com.example.boot3.spring.cache.service;

import com.example.boot3.spring.cache.StartSpringCache;
import com.example.boot3.spring.cache.po.Emp2;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * 使用 Caffeine 测试缓存
 * 对照组: {@link IEmp2ServiceCacheTest}
 * @author caimeng
 * @date 2024/7/22 16:57
 */
@Slf4j
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = StartSpringCache.class)
public class IEmp2ServiceCaffeineTest {
    @Autowired
    private IEmp2Service iEmp2Service;

    @BeforeAll
    public static void init() {
        System.setProperty("cache.type", "caffeine");
    }

    /**
     * 测试：Caffeine 缓存
     * <p>
     *     Hibernate: select e1_0.eid,e1_0.ename,e1_0.job,e1_0.salary from Emp2 e1_0 where e1_0.eid=?
     *     【第一次】雇员数据查询: emp2 = Emp2(eid=muyan, ename=包可爱的小李老师, job=作者兼讲师, salary=3600.0)
     *     【第二次】雇员数据查询: emp2 = Emp2(eid=muyan, ename=包可爱的小李老师, job=作者兼讲师, salary=3600.0)
     * <p>
     *     1. 缓存是生效的
     *     2. bean对象中没有了 CacheConfig，取代的是 CaffeineCacheConfig
     */
    @Test
    public void getTestCache() {
        Emp2 empA = iEmp2Service.get("muyan");
        System.out.printf("【第一次】雇员数据查询: emp2 = %s\n", empA);
        Emp2 empB = iEmp2Service.get("muyan");
        System.out.printf("【第二次】雇员数据查询: emp2 = %s\n", empB);
    }
}
