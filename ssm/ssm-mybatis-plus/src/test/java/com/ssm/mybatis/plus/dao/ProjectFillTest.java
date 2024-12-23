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

/**
 * @author caimeng
 * @date 2024/12/23 14:21
 */
@Slf4j
@ContextConfiguration(classes = {StartMyBatisPlusApplication.class})
@ExtendWith(SpringExtension.class)
public class ProjectFillTest {
    @Autowired
    private IProjectDAO projectDAO;

    @Test
    public void addTest() {
        Project project = Project.builder()
                .name("李兴华Java就业编程")
                .charge("李兴华")
                .note("系统讲解Java程序员与Java架构师的完整技术图书视频")
                // 不设置值，selectIdTest() 查不到，设置非 0|1 也查不到
//                .status(5)
                .build();
        /*
         * Preparing: INSERT INTO project ( name, charge, note, status ) VALUES ( ?, ?, ?, ? )
         * Parameters: 李兴华Java就业编程(String), 李兴华(String), 系统讲解Java程序员与Java架构师的完整技术图书视频(String), 0(Integer)
         */
        int insert = projectDAO.insert(project);
        // 更新数据行数: 1, 当前项目ID:63
        System.out.println("更新数据行数: " + insert + ", 当前项目ID:" + project.getPid());
    }

    @Test
    public void selectIdTest() {
        /*
         * Preparing: SELECT pid,name,charge,note,status FROM project WHERE pid=? AND status=0
         * Parameters: 62(Long)
         */
        Project project = projectDAO.selectById(62L);
        if (project != null) {
            System.out.println(MessageFormat.format(
                    "【项目信息】项目ID: {0}, 项目名称: {1}, 负责人: {2}, 说明: {3}, 状态: {4,number}",
                    project.getPid(), project.getName(), project.getCharge(), project.getNote(), project.getStatus()));
        } else {
            // 查询项目的结果为空
            System.out.println("查询项目的结果为空");
        }

    }

}
