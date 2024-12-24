package com.ssm.mybatis.plus.plugin;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ssm.mybatis.plus.StartMyBatisPlusApplication;
import com.ssm.mybatis.plus.dao.IProjectDAO;
import com.ssm.mybatis.plus.vo.Project;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.stream.IntStream;

/**
 * 分页查询测试
 * @author caimeng
 * @date 2024/12/24 9:53
 */
@Slf4j
@ContextConfiguration(classes = {StartMyBatisPlusApplication.class})
@ExtendWith(SpringExtension.class)
public class PaginationTest {
    @Autowired
    private IProjectDAO projectDAO;

    /**
     * 批量插入测试数据
     */
    @Test
    public void addBatchTest() {
        IntStream.range(0, 20).forEach(i -> {
            Project project = Project.builder()
                    .name("李兴华Java就业编程_" + i)
                    .charge("李兴华")
                    .note("系统讲解Java程序员与Java架构师的完整技术图书视频")
                    .build();
            projectDAO.insert(project);
        });
    }

    /**
     * 测试分页查询
     */
    @Test
    public void pageSplitTest() {
        Page<Project> page = new Page<>();
        // 当前页起始：1
        page.setCurrent(1).setSize(10).setSearchCount(true);
        System.out.println(page);
        /*
         * Preparing: SELECT COUNT(*) AS total FROM project WHERE status = 0 AND (charge = ?)
         * Parameters: 李兴华(String)
         * ----
         * SELECT pid,name,charge,note,status FROM project WHERE status=0 AND (charge = ?) LIMIT ?
         * Parameters: 李兴华(String), 10(Long)
         */
        Page<Project> pageResult = projectDAO.selectPage(
                page, new QueryWrapper<Project>().lambda().eq(Project::getCharge, "李兴华"));
        // 是否同一个对象: true
        System.out.println("是否同一个对象: " + (pageResult == page));
        System.out.println("-".repeat(30));
        // 【分页统计】总页数: 3
        System.out.println("【分页统计】总页数: " + pageResult.getPages());
        // 【分页统计】总记录数: 24
        System.out.println("【分页统计】总记录数: " + pageResult.getTotal());
        System.out.println("-".repeat(30));
        /*
         * Project(pid=61, name=李兴华Java就业编程, charge=李兴华, note=系统讲解Java程序员与Java架构师的完整技术图书视频, status=0)
         * Project(pid=63, name=李兴华Java就业编程, charge=李兴华, note=系统讲解Java程序员与Java架构师的完整技术图书视频, status=0)
         * Project(pid=1055879171332374588, name=李兴华Java就业编程, charge=李兴华, note=系统讲解Java程序员与Java架构师的完整技术图书视频, status=0)
         * ...
         */
        pageResult.getRecords().forEach(System.out::println);
    }
}
