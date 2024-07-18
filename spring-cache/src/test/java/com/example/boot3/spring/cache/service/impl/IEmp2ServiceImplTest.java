package com.example.boot3.spring.cache.service.impl;

import com.example.boot3.spring.cache.StartSpringCache;
import com.example.boot3.spring.cache.po.Emp2;
import com.example.boot3.spring.cache.service.IEmp2Service;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * @author caimeng
 * @date 2024/7/18 17:55
 */
@Slf4j
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = StartSpringCache.class)
public class IEmp2ServiceImplTest {
    @Autowired
    private IEmp2Service iEmp2Service;

    @Test
    public void editTest() {
        Emp2 emp2 = Emp2.builder()
                .eid("muyan")
                .ename("包可爱的小李老师")
                .job("作者兼讲师")
                .salary(3600.0)
                .build();
        System.out.printf("雇员数据修改：%s\n", iEmp2Service.edit(emp2));
    }

    @Test
    public void getTest() {
        Emp2 emp2 = iEmp2Service.get("muyan");
        // 雇员数据查询: emp2 = Emp2(eid=muyan, ename=包可爱的小李老师, job=作者兼讲师, salary=3600.0)
        System.out.printf("雇员数据查询: emp2 = %s\n", emp2);
    }

    @Test
    public void getByEnameTest() {
        Emp2 emp2 = iEmp2Service.getByEname("包可爱的小李老师");
        // 雇员数据查询: emp2 = Emp2(eid=muyan, ename=包可爱的小李老师, job=作者兼讲师, salary=3600.0)
        System.out.printf("雇员数据查询: emp2 = %s\n", emp2);
    }

    @Test
    public void deleteTest() {
        boolean del = iEmp2Service.delete("muyan");
        // 雇员数据删除：true
        System.out.println("雇员数据删除：" + del);
    }
}
