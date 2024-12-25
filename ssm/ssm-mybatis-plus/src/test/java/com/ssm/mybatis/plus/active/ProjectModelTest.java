package com.ssm.mybatis.plus.active;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ssm.mybatis.plus.StartMyBatisPlusApplication;
import com.ssm.mybatis.plus.vo.Project;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

/**
 * 测试：使用实体类直接操作数据
 * @author caimeng
 * @date 2024/12/25 9:42
 */
@Slf4j
@ContextConfiguration(classes = {StartMyBatisPlusApplication.class})
@ExtendWith(SpringExtension.class)
public class ProjectModelTest {

    /**
     * 测试：AR领域模型 - 新增
     */
    @Test
    public void addTest() {
        Project project = Project.builder()
                .name("AR领域模型新增")
                .charge("李兴华")
                .note("AR领域模型展开范式")
                .build();
        /*
         * Preparing: INSERT INTO project (pid, name, charge, note, status, tenant_id) VALUES (?, ?, ?, ?, ?, 'muyan')
         * Parameters: 1056508772526063707(Long), AR领域模型新增(String), 李兴华(String), AR领域模型展开范式(String), 0(Integer)
         *
         * AR模式下，租户拦截器也是生效的
         */
        boolean insertResult = project.insert();
        // 更新数据结果: true, 当前项目ID:1056508772526063707
        System.out.println("更新数据结果: " + insertResult + ", 当前项目ID:" + project.getPid());
    }

    /**
     * 测试：AR领域模型 - 删除
     */
    @Test
    public void delTest() {
        /*
         * Preparing: UPDATE project SET status = 1 WHERE status = 0 AND (pid = ?) AND tenant_id = 'muyan'
         * Parameters: 1056508772526063707(Long)
         */
        boolean deleteResult = new Project().delete(new QueryWrapper<Project>().lambda()
                .eq(Project::getPid, 1056508772526063707L));
        System.out.println("根据条件删除项目: " + deleteResult);
    }

    /**
     * 测试：AR领域模型 - 查询
     */
    @Test
    public void selectIdTest() {
        // 实例化 Project 操作
        Project project = Project.builder()
                .pid(1056508772526063707L)
                .charge("李兴华")
                .build();
        /*
         * Preparing: SELECT pid, name, charge, note, status, version, tenant_id FROM project WHERE pid = ? AND status = 0 AND tenant_id = 'muyan'
         * Parameters: 1056508772526063707(Long)
         */
        Project selectByIdResult = project.selectById();
        // Project(pid=1056508772526063707, name=AR领域模型新增, charge=李兴华, note=AR领域模型展开范式, status=0, version=1, tenantId=muyan)
        System.out.println(selectByIdResult);
        System.out.println("-".repeat(30));
        /*
         * Preparing: SELECT pid, name, charge, note, status, version, tenant_id FROM project WHERE status = 0 AND tenant_id = 'muyan'
         * Parameters:
         */
        List<Project> selectAllResult = project.selectAll();
        selectAllResult.forEach(System.out::println);
        System.out.println("-".repeat(30));
        /*
         * Preparing: SELECT pid, name, charge, note, status, version, tenant_id FROM project WHERE status = 0 AND (charge = ?) AND tenant_id = 'muyan'
         * Parameters: 李兴华(String)
         */
        List<Project> projects = project.selectList(new QueryWrapper<Project>().lambda()
                .eq(Project::getCharge, "李兴华"));
        projects.forEach(System.out::println);
    }
}
