package com.ssm.mybatis.plus.service;

import com.ssm.mybatis.plus.StartMyBatisPlusApplication;
import com.ssm.mybatis.plus.vo.Project;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.stream.IntStream;

/**
 * @author caimeng
 * @date 2024/12/26 10:30
 */
@Slf4j
@ContextConfiguration(classes = {StartMyBatisPlusApplication.class})
@ExtendWith(SpringExtension.class)
public class IProjectServiceTest {
    @Autowired
    private IProjectService iProjectService;

    /**
     * 测试：service批量新增
     */
    @Test
    public void addTest() {
        List<Project> projectList = IntStream.range(0, 10).mapToObj(i -> Project.builder()
                .name("李兴华Java就业编程")
                .charge("李兴华")
                .note("系统讲解Java程序员与架构师的完整技术丛书[" + i + "]")
                .build())
                .toList();
        /*
         * Preparing: INSERT INTO project (pid, name, charge, note, status, tenant_id) VALUES (?, ?, ?, ?, ?, 'muyan')
         * Parameters: 1056883767316054052(Long), 李兴华Java就业编程(String), 李兴华(String), 系统讲解Java程序员与架构师的完整技术丛书[0](String), 0(Integer)
         * ...
         * Parameters: 1056883769073467454(Long), 李兴华Java就业编程(String), 李兴华(String), 系统讲解Java程序员与架构师的完整技术丛书[9](String), 0(Integer)
         */
        boolean result = iProjectService.saveOrUpdateBatch(projectList);
        // 更新结果: true
        System.out.println("更新结果: " + result);
    }
}
