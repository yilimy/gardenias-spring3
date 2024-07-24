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
 * 测试：缓存更新
 * @author caimeng
 * @date 2024/7/24 14:14
 */
@Slf4j
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = StartSpringCache.class)
public class IEmp2ServiceCacheUpdateTest {
    @Autowired
    private IEmp2Service iEmp2Service;

    @Test
    public void editCacheTest() {
        Emp2 empA = iEmp2Service.get("muyan");
        System.out.println("【第一次】雇员数据查询：" + empA);
        Emp2 emp = Emp2.builder()
                // 修改数据的ID
                .eid("muyan")
                // 新数据的内容
                .ename("包可爱的小李老师PLUS")
                .job("作者兼讲师兼开发者")
                .salary(3601.0)
                .build();
        // 执行更新
        System.out.println("【更新】雇员数据：" + iEmp2Service.edit(emp));
        // 因为key不一样，所以没有走缓存，使用的是默认的key，跟name有关
        Emp2 empB = iEmp2Service.getByEname("包可爱的小李老师PLUS");
        System.out.println("【第二次】雇员数据查询：" + empB);
        // key一致，走缓存，key为eid，与edit方法中的key(eid)值一致
        Emp2 empC = iEmp2Service.get("muyan");
        System.out.println("【第三次】雇员数据查询：" + empC);
    }
}
