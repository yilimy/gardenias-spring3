package com.ssm.mybatis.plus.dao;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
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
 * @date 2024/12/23 11:29
 */
@Slf4j
@ContextConfiguration(classes = {StartMyBatisPlusApplication.class})
@ExtendWith(SpringExtension.class)
public class IProjectWrapperTest {
    @Autowired
    private IProjectDAO projectDAO;

    @Test
    public void selectByIdTest() {
        // 定义查询包装器
        QueryWrapper<Project> wrapper = new QueryWrapper<>();
        wrapper.eq("pid", 58L);
        // Preparing: SELECT pid,name,charge,note,status FROM project WHERE (pid = ?)
        Project project = projectDAO.selectOne(wrapper);
        // 【项目信息】项目ID: 58, 项目名称: 李兴华Java就业编程, 负责人: 李兴华, 说明: 系统讲解Java程序员与Java架构师的完整技术图书视频, 状态: 0
        System.out.println(MessageFormat.format(
                "【项目信息】项目ID: {0}, 项目名称: {1}, 负责人: {2}, 说明: {3}, 状态: {4,number}",
                project.getPid(), project.getName(), project.getCharge(), project.getNote(), project.getStatus()));
    }

    @Test
    public void selectChargeTest() {
        QueryWrapper<Project> wrapper = new QueryWrapper<>();
        // 此时需要编写两个条件，并且同时满足，肯定要使用and函数
        wrapper.and(c -> c.eq("charge", "李兴华").like("name", "编程"));
        // Preparing: SELECT pid,name,charge,note,status FROM project WHERE ((charge = ? AND name LIKE ?))
        List<Project> projectList = projectDAO.selectList(wrapper);
        /*
         * Project(pid=54, name=李兴华GoLang就业编程, charge=李兴华, note=高并发应用设计, status=0)
         * Project(pid=55, name=李兴华Java就业编程, charge=李兴华, note=系统讲解Java程序员与Java架构师的完整技术图书视频, status=0)
         * Project(pid=58, name=李兴华Java就业编程, charge=李兴华, note=系统讲解Java程序员与Java架构师的完整技术图书视频, status=0)
         */
        projectList.forEach(System.out::println);
    }

    @Test
    public void delTest() {
        QueryWrapper<Project> wrapper = new QueryWrapper<>();
        // 此时使用OR进行多个条件的连接，采用的是第二种写法（代码链）
        wrapper.eq("charge", "李兴华").or().between("pid", 10, 20);
        /*
         * Preparing: DELETE FROM project WHERE (charge = ? OR pid BETWEEN ? AND ?)
         * 根据条件删除项目: 3
         */
        System.out.println("根据条件删除项目: " + projectDAO.delete(wrapper));
    }

    @Test
    public void groupTest() {
        QueryWrapper<Project> wrapper = new QueryWrapper<>();
        // 定义SELECT子句, 枚举出所有要查询的数据项
        wrapper.select("charge", "COUNT(*) AS count");
        // 分组列
        wrapper.groupBy("charge");
        // 1个以上的项目
        wrapper.having("count >= 1");
        // 根据数量降序排列
        wrapper.orderByDesc("count");
        // Preparing: SELECT charge,COUNT(*) AS count FROM project GROUP BY charge HAVING count >= 1 ORDER BY count DESC
        List<Map<String, Object>> result = projectDAO.selectMaps(wrapper);
        // 【分组统计查询】负责人姓名:李兴华, 项目数量:3
        result.forEach(item -> System.out.println(MessageFormat.format(
                "【分组统计查询】负责人姓名:{0}, 项目数量:{1}", item.get("charge"), item.get("count"))));
    }

    @Test
    public void groupLLambdaTest(){
        LambdaQueryWrapper<Project> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Project::getPid, 61L).or().eq(Project::getStatus, 0);
        // Preparing: SELECT pid,name,charge,note,status FROM project WHERE (pid = ? OR status = ?)
        List<Project> projects = projectDAO.selectList(wrapper);
        /*
         * Project(pid=59, name=李兴华Java就业编程, charge=李兴华, note=系统讲解Java程序员与Java架构师的完整技术图书视频, status=0)
         * Project(pid=60, name=李兴华Java就业编程, charge=李兴华, note=系统讲解Java程序员与Java架构师的完整技术图书视频, status=0)
         * Project(pid=61, name=李兴华Java就业编程, charge=李兴华, note=系统讲解Java程序员与Java架构师的完整技术图书视频, status=0)
         */
        projects.forEach(System.out::println);
    }

    @Test
    public void updateTest() {
        QueryWrapper<Project> wrapper = new QueryWrapper<>();
        // 数据更新的查询条件
        wrapper.and(c -> c.eq("pid", 59L).eq("status", 0));
        // 更新的内容
        Project project = Project.builder()
                .note("Python训练营")
                .charge("小李老师")
                .build();
        /*
         * Preparing: UPDATE project SET charge=?, note=? WHERE ((pid = ? AND status = ?))
         * Parameters: 小李老师(String), Python训练营(String), 59(Long), 0(Integer)
         */
        int update = projectDAO.update(project, wrapper);
        // 数据更新：1
        System.out.println("数据更新：" + update);
    }
}
