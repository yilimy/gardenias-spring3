package com.ssm.mybatis.plus.dao;

import com.ssm.mybatis.plus.StartMyBatisPlusApplication;
import com.ssm.mybatis.plus.vo.Project;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

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
}
