package com.ssm.mybatis.plus.plugin;

import com.ssm.mybatis.plus.StartMyBatisPlusApplication;
import com.ssm.mybatis.plus.dao.IProjectDAO;
import com.ssm.mybatis.plus.vo.Project;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.text.MessageFormat;

/**
 * 测试：多租户
 * @author caimeng
 * @date 2024/12/24 14:54
 */
@Slf4j
@ContextConfiguration(classes = {StartMyBatisPlusApplication.class})
@ExtendWith(SpringExtension.class)
public class TenantIdTest {
    @Autowired
    private IProjectDAO projectDAO;

    @Test
    public void addTest() {
        Project project = Project.builder()
                .name("多租户的入驻护理")
                .charge("小王")
                .note("下雨打雷收衣服咯")
                .build();
        /*
         * original SQL: INSERT INTO project  ( pid, name, charge, note, status )  VALUES  ( ?, ?, ?, ?, ? )
         * SQL to parse, SQL: INSERT INTO project  ( pid, name, charge, note, status )  VALUES  ( ?, ?, ?, ?, ? )
         * ...
         * Preparing: INSERT INTO project (pid, name, charge, note, status, tenant_id) VALUES (?, ?, ?, ?, ?, 'muyan')
         * Parameters: 1056223701009170526(Long), 多租户的入驻护理(String), 小王(String), 下雨打雷收衣服咯(String), 0(Integer)
         */
        int insert = projectDAO.insert(project);
        // 更新数据行数: 1, 当前项目ID:1056223701009170526
        System.out.println("更新数据行数: " + insert + ", 当前项目ID:" + project.getPid());
    }

    @Test
    public void selectIdTest() {
        // Preparing: SELECT pid, name, charge, note, status, version, tenant_id FROM project WHERE pid = ? AND status = 0 AND tenant_id = 'muyan'
        // Parameters: 1056223701009170526(Long)
        Project project = projectDAO.selectById(1056223701009170526L);
        // 【项目信息】项目ID: 1,056,223,701,009,170,526, 项目名称: 多租户的入驻护理, 负责人: 小王, 说明: 下雨打雷收衣服咯, 状态: 0
        System.out.println(MessageFormat.format(
                "【项目信息】项目ID: {0}, 项目名称: {1}, 负责人: {2}, 说明: {3}, 状态: {4,number}",
                project.getPid(), project.getName(), project.getCharge(), project.getNote(), project.getStatus()));
    }
}
