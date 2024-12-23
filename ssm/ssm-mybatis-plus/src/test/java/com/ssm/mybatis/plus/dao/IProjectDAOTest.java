package com.ssm.mybatis.plus.dao;

import com.ssm.mybatis.plus.StartMyBatisPlusApplication;
import com.ssm.mybatis.plus.vo.Project;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.text.MessageFormat;
import java.util.List;
import java.util.Map;

/**
 * @author caimeng
 * @date 2024/12/23 10:09
 */
@Slf4j
@ContextConfiguration(classes = {StartMyBatisPlusApplication.class})
@ExtendWith(SpringExtension.class)
public class IProjectDAOTest {
    @Autowired
    private IProjectDAO projectDAO;

    @Test
    public void addTest() {
        Project project = Project.builder()
                .name("李兴华Java就业编程")
                .charge("李兴华")
                .note("系统讲解Java程序员与Java架构师的完整技术图书视频")
                .status(0)
                .build();
        // Preparing: INSERT INTO project ( name, charge, note, status ) VALUES ( ?, ?, ?, ? )
        int insert = projectDAO.insert(project);
        // 更新数据行数: 1, 当前项目ID:54
        System.out.println("更新数据行数: " + insert + ", 当前项目ID:" + project.getPid());
    }

    @Test
    public void editTest() {
        Project project = Project.builder()
                .pid(54L)
                .name("李兴华GoLang就业编程")
                .charge("李兴华")
                .note("高并发应用设计")
                .status(0)
                .build();
        // Preparing: UPDATE project SET name=?, charge=?, note=?, status=? WHERE pid=?
        int update = projectDAO.updateById(project);
        // 更新数据行数: 1
        System.out.println("更新数据行数: " + update);
    }

    @Test
    public void selectIdTest() {
        // Preparing: SELECT pid,name,charge,note,status FROM project WHERE pid=?
        Project project = projectDAO.selectById(54L);
        // 【项目信息】项目ID: 54, 项目名称: 李兴华GoLang就业编程, 负责人: 李兴华, 说明: 高并发应用设计, 状态: 0
        System.out.println(MessageFormat.format(
                "【项目信息】项目ID: {0}, 项目名称: {1}, 负责人: {2}, 说明: {3}, 状态: {4,number}",
                project.getPid(), project.getName(), project.getCharge(), project.getNote(), project.getStatus()));
    }

    @Test
    public void selectMapTest() {
        Map<String, Object> params = Map.of("pid", 54L, "charge", "李兴华", "status", 0);
        // Preparing: SELECT pid,name,charge,note,status FROM project WHERE pid = ? AND status = ? AND charge = ?
        List<Project> projects = projectDAO.selectByMap(params);
        // Project(pid=54, name=李兴华GoLang就业编程, charge=李兴华, note=高并发应用设计, status=0)
        projects.forEach(System.out::println);
    }

    @Test
    public void delTest() {
        // Preparing: DELETE FROM project WHERE pid IN ( ? , ? )
        int del = projectDAO.deleteBatchIds(List.of(56L, 57L));
        // 批量删除数据行数: 2
        System.out.println("批量删除数据行数: " + del);
    }
}
