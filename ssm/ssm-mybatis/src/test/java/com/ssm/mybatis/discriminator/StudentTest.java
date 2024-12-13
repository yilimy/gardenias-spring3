package com.ssm.mybatis.discriminator;

import com.ssm.mybatis.util.MybatisSessionFactory;
import com.ssm.mybatis.vo.Student;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Random;
import java.util.UUID;

/**
 * 测试：鉴别器
 * @author caimeng
 * @date 2024/12/13 17:54
 */
public class StudentTest {

    /**
     * 测试：新增子类数据
     */
    @Test
    public void doCreateTest() {
        SqlSession sqlSession = MybatisSessionFactory.getSqlSession();
        Student student = new Student();
        student.setMid("muyan_stu_" + UUID.randomUUID());
        student.setName("沐言_stu");
        student.setAge(18 + new Random().nextInt(51 - 18));
        student.setSex("男");
        student.setMajor("计算机技术与科学");
        student.setScore(50 + Math.round(new Random().nextDouble() * 50d * 100.0) / 100.0);
        // Preparing: INSERT INTO member2(mid, name, age, sex, score, major, type) VALUES (?, ?, ?, ?, ?, ?, 'stu')
        int insert = sqlSession.insert(MemberTest.NAME_SPACE + ".doCreateStudent", student);
        // 【数据增加】更新数据行数: 1
        System.out.println("【数据增加】更新数据行数: " + insert);
        sqlSession.commit();
        MybatisSessionFactory.close();
    }

    @Test
    public void findAllStudentTest() {
        SqlSession sqlSession = MybatisSessionFactory.getSqlSession();
        // Preparing: select mid, name, age, sex, score, major, type from member2 where type = 'stu'
        List<Student> studentList = sqlSession.selectList(MemberTest.NAME_SPACE + ".findAllStudent");
        studentList.forEach(System.out::println);
        MybatisSessionFactory.close();
    }
}
