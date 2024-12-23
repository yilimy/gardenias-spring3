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

/**
 * @author caimeng
 * @date 2024/12/23 14:11
 */
@Slf4j
@ContextConfiguration(classes = {StartMyBatisPlusApplication.class})
@ExtendWith(SpringExtension.class)
public class ProjectLogicDelTest {
    @Autowired
    private IProjectDAO projectDAO;

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
    public void delTest() {
        /*
         * Preparing: UPDATE project SET status=1 WHERE pid IN ( ? , ? ) AND status=0
         * Parameters: 59(Long), 60(Long)
         */
        int del = projectDAO.deleteBatchIds(List.of(59L, 60L));
        // 批量删除数据行数: 2
        System.out.println("批量删除数据行数: " + del);
    }
}
