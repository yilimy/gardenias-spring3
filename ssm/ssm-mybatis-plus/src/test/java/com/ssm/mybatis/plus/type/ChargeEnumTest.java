package com.ssm.mybatis.plus.type;

import com.baomidou.mybatisplus.core.metadata.TableInfoHelper;
import com.baomidou.mybatisplus.core.toolkit.ClassUtils;
import com.ssm.mybatis.plus.StartMyBatisPlusApplication;
import com.ssm.mybatis.plus.dao.IProjectDAO;
import com.ssm.mybatis.plus.vo.Project;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * 使用枚举来固定控制人
 * <p>
 *     必需要扫描到 VO 类否则会报错
 *     com.baomidou.mybatisplus.core.exceptions.MybatisPlusException: com.ssm.mybatis.plus.vo.Project Not Found TableInfoCache.
 *     {@link TableInfoHelper#getTableInfo(Class)}
 *     在执行到下列代码时，会寻找动态代理对象
 *          tableInfo = TABLE_INFO_CACHE.get(ClassUtils.getUserClass(currentClass));
 *     {@link ClassUtils#getUserClass(Class)}
 *
 * @author caimeng
 * @date 2024/12/25 11:13
 */
@Slf4j
@ContextConfiguration(classes = {StartMyBatisPlusApplication.class})
@ExtendWith(SpringExtension.class)
public class ChargeEnumTest {

    @Autowired
    private IProjectDAO projectDAO;

    @Test
    public void addTest() {
        Project project = Project.builder()
                .name("枚举模式")
                .charge("李兴华")
                .note("枚举模式新增")
                .build();
        /*
         * Preparing: INSERT INTO project (pid, name, charge, note, status, tenant_id) VALUES (?, ?, ?, ?, ?, 'muyan')
         * Parameters: 1056645552109781055(Long), 枚举模式(String), 李兴华(String), 枚举模式新增(String), 0(Integer)
         */
        boolean insertResult = project.insert();
        // 更新数据结果: true, 当前项目ID:1056645552109781055
        System.out.println("更新数据结果: " + insertResult + ", 当前项目ID:" + project.getPid());
    }

    @Test
    public void selectIdTest() {
        // 实例化 Project 操作
        Project project = Project.builder()
                .pid(1056645552109781055L)
                .charge("李兴华")
                .build();
        /*
         * Preparing: SELECT pid, name, charge, note, status, version, tenant_id FROM project WHERE pid = ? AND status = 0 AND tenant_id = 'muyan'
         * Parameters: 1056645552109781055(Long)
         */
        Project selectByIdResult = project.selectById();
        // Project(pid=1056645552109781055, name=枚举模式, charge=Lee, note=枚举模式新增, status=0, version=1, tenantId=muyan)
        System.out.println(selectByIdResult);
    }

    /**
     * 测试：使用 statement 查询，看使用枚举的结果
     */
    @Test
    public void selectId2Test() {
        /*
         * Preparing: SELECT pid, name, charge, note, status, version, tenant_id FROM project WHERE pid = ? AND tenant_id = 'muyan'
         * Parameters: 1056645552109781055(Long)
         */
        Project project = projectDAO.findById2(1056645552109781055L);
        // Project(pid=1056645552109781055, name=枚举模式, charge=Lee, note=枚举模式新增, status=0, version=1, tenantId=muyan)
        System.out.println(project);
    }
}
