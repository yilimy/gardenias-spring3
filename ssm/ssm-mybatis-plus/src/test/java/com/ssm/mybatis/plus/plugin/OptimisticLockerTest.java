package com.ssm.mybatis.plus.plugin;

import com.ssm.mybatis.plus.StartMyBatisPlusApplication;
import com.ssm.mybatis.plus.dao.IProjectDAO;
import com.ssm.mybatis.plus.vo.Project;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.text.MessageFormat;
import java.util.concurrent.TimeUnit;

/**
 * 测试：乐观锁
 * @author caimeng
 * @date 2024/12/24 10:53
 */
@Slf4j
@ContextConfiguration(classes = {StartMyBatisPlusApplication.class})
@ExtendWith(SpringExtension.class)
public class OptimisticLockerTest {
    @Autowired
    private IProjectDAO projectDAO;

    /**
     * 测试：乐观锁
     * <p>
     *     不设置version的值，乐观锁不会生效
     */
    @Test
    public void editTest() {
        Project project = Project.builder()
                .pid(1055879171332374588L)
                .name("Node.JS就业编程")
                .charge("李兴华")
                .note("前端应用整合")
                /*
                 * 设置了version值，才能触发乐观锁
                 * Preparing: UPDATE project SET name=?, charge=?, note=?, version=? WHERE pid=? AND version=? AND status=0
                 * Parameters: Node.JS就业编程(String), 李兴华(String), 前端应用整合(String), 2(Integer), 1055879171332374588(Long), 1(Integer)
                 */
//                .version(1)
                .build();
        // Preparing: UPDATE project SET name=?, charge=?, note=? WHERE pid=? AND status=0
        int update = projectDAO.updateById(project);
        // 更新数据行数: 1
        System.out.println("更新数据行数: " + update);
    }

    /**
     * 测试：乐观锁
     * <p>
     *     使用先查询后更新的方式触发乐观锁
     */
    @Test
    public void editOptimisticTest() {
        // Preparing: SELECT pid,name,charge,note,status,version FROM project WHERE pid=? AND status=0
        Project project = projectDAO.selectById(1055879171332374588L);
        project.setName("乐观Node.JS就业编程");
        /*
         * Preparing: UPDATE project SET name=?, charge=?, note=?, version=? WHERE pid=? AND version=? AND status=0
         * Parameters: 乐观Node.JS就业编程(String), 李兴华(String), 前端应用整合(String), 3(Integer), 1055879171332374588(Long), 2(Integer)
         */
        int update = projectDAO.updateById(project);
        // 更新数据行数: 1
        System.out.println("更新数据行数: " + update);
    }

    /**
     * 测试：多线程下的乐观锁
     * <p>
     *     日志
     *     11:46:44.496 [第二个更新线程] Preparing: SELECT pid,name,charge,note,status,version FROM project WHERE pid=? AND status=0
     *     11:46:44.497 [第一个更新线程] Preparing: SELECT pid,name,charge,note,status,version FROM project WHERE pid=? AND status=0
     *     ...
     *     11:46:44.655 [第二个更新线程] Preparing: UPDATE project SET name=?, charge=?, note=?, version=? WHERE pid=? AND version=? AND status=0
     *     11:46:44.657 [第二个更新线程] Parameters: SSM编程_第二个更新线程(String), 李兴华(String), 系统讲解Java程序员与Java架构师的完整技术图书视频(String), 2(Integer), 1871105454528552961(Long), 1(Integer)
     *     【第二个更新线程】数据更新: 1
     *     ...
     *     11:46:49.626 [第一个更新线程] Preparing: UPDATE project SET name=?, charge=?, note=?, version=? WHERE pid=? AND version=? AND status=0
     *     11:46:49.626 [第一个更新线程] Parameters: Java多线程编程_第一个更新线程(String), 李兴华(String), 系统讲解Java程序员与Java架构师的完整技术图书视频(String), 2(Integer), 1871105454528552961(Long), 1(Integer)
     *     【第一个更新线程】数据更新: 0
     */
    @SneakyThrows
    @Test
    public void threadUpdateTest() {
        Long updateId = 1871105454528552961L;
        new Thread(() -> {
            Project project = projectDAO.selectById(updateId);
            String tName = Thread.currentThread().getName();
            project.setName("Java多线程编程_" + tName);
            try {
                // 延迟5秒执行更新，第二个线程的更新一定导致版本号改变，从而导致第一个线程更新失败
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println(MessageFormat.format("【{0}】数据更新: {1}", tName, projectDAO.updateById(project)));
        }, "第一个更新线程").start();
        new Thread(() -> {
            Project project = projectDAO.selectById(updateId);
            String tName = Thread.currentThread().getName();
            project.setName("SSM编程_" + tName);
            System.out.println(MessageFormat.format("【{0}】数据更新: {1}", tName, projectDAO.updateById(project)));
        }, "第二个更新线程").start();
        TimeUnit.SECONDS.sleep(10);
    }
}
