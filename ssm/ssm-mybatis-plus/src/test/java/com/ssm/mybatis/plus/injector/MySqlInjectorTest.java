package com.ssm.mybatis.plus.injector;

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
 * @date 2024/12/23 17:26
 */
@Slf4j
@ContextConfiguration(classes = {StartMyBatisPlusApplication.class})
@ExtendWith(SpringExtension.class)
public class MySqlInjectorTest {
    @Autowired
    private IProjectDAO projectDAO;

    @Test
    public void findAllTest() {
        // Preparing: SELECT * FROM project
        List<Project> result = projectDAO.findAll();        // 扩充的数据方法
        result.forEach(System.out::println);
    }
}
