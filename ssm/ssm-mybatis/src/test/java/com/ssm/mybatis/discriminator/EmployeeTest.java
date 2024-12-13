package com.ssm.mybatis.discriminator;

import com.ssm.mybatis.util.MybatisSessionFactory;
import com.ssm.mybatis.vo.Employee;
import com.ssm.mybatis.vo.Student;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Random;
import java.util.UUID;

/**
 * @author caimeng
 * @date 2024/12/13 18:15
 */
public class EmployeeTest {
    /**
     * 测试：新增子类数据
     */
    @Test
    public void doCreateTest() {
        SqlSession sqlSession = MybatisSessionFactory.getSqlSession();
        Employee employee = new Employee();
        employee.setMid("muyan_emp_" + UUID.randomUUID());
        employee.setName("沐言_emp");
        employee.setAge(18 + new Random().nextInt(51 - 18));
        employee.setSex("男");
        employee.setSalary(Math.round(new Random().nextDouble() * 10000d * 100.0) / 100.0);
        employee.setDept("教学部");
        // Preparing: INSERT INTO member2(mid, name, age, sex, salary, dept, type) VALUES (?, ?, ?, ?, ?, ?, 'emp')
        int insert = sqlSession.insert(MemberTest.NAME_SPACE + ".doCreateEmployee", employee);
        // 【数据增加】更新数据行数: 1
        System.out.println("【数据增加】更新数据行数: " + insert);
        sqlSession.commit();
        MybatisSessionFactory.close();
    }

    @Test
    public void findAllStudentTest() {
        SqlSession sqlSession = MybatisSessionFactory.getSqlSession();
        List<Student> studentList = sqlSession.selectList(MemberTest.NAME_SPACE + ".findAllEmployee");
        studentList.forEach(System.out::println);
        MybatisSessionFactory.close();
    }
}
