package com.ssm.mybatis.plus.plugin;

import com.baomidou.mybatisplus.extension.plugins.inner.BlockAttackInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.IllegalSQLInnerInterceptor;
import com.ssm.mybatis.plus.StartMyBatisPlusApplication;
import com.ssm.mybatis.plus.dao.IProjectDAO;
import com.ssm.mybatis.plus.vo.Project;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

/**
 * @author caimeng
 * @date 2024/12/24 16:41
 */
@Slf4j
@ContextConfiguration(classes = {StartMyBatisPlusApplication.class})
@ExtendWith(SpringExtension.class)
public class IllegalTest {
    @Autowired
    private IProjectDAO projectDAO;

    /**
     * 测试全表更新
     * <p>
     *     Cause: com.baomidou.mybatisplus.core.exceptions.MybatisPlusException: 非法SQL，SQL未使用到索引, table:project, columnName:tenant_id
     * <p>
     *     性能检测插件和阻断插件在功能上有重叠的地方
     *     {@link BlockAttackInnerInterceptor}
     *     {@link IllegalSQLInnerInterceptor}
     */
    @Test
    public void doUpdateAllTest() {
        @SuppressWarnings("TestFailedLine")
        long count = projectDAO.doUpdateAll();
        System.out.println("【更新全部数据】count= " + count);
    }

    @Test
    public void findAllByChargeTest() {
        // Cause: com.baomidou.mybatisplus.core.exceptions.MybatisPlusException: 非法SQL，SQL未使用到索引, table:project, columnName:charge
        @SuppressWarnings("TestFailedLine")
        List<Project> projectList = projectDAO.findAllByCharge("李兴华");
        projectList.forEach(System.out::println);
    }
}
