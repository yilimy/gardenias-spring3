package com.ssm.mybatis.vo;

import com.ssm.mybatis.util.MybatisSessionFactory;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author caimeng
 * @date 2024/12/16 17:18
 */
public class StudentTest {
    public static final String NAME_SPACE = "com.ssm.mybatis.mapper.StudentMapper";

    /**
     * 测试：多对对查询
     */
    @Test
    public void findByIdTest() {
        // Preparing: SELECT sid, name FROM student where sid=?
        StudentPO student = MybatisSessionFactory.getSqlSession()
                .selectOne(NAME_SPACE + ".findById", "lee");
        // student : StudentPO(sid=lee, name=李兴华)
        System.out.println("student : " + student);
        System.out.println("-".repeat(30));
        // Preparing: SELECT sid, name FROM student WHERE sid IN ( SELECT sid FROM student_course WHERE cid=?)
        List<CoursePO> courses = student.getCourses();
        /*
         * 如果使用 System.out::println 会执行三次SQL查询
         */
//        courses.forEach(System.out::println);
        /*
         * 没有发现查询课程(courses)的sql打印
         */
        courses.forEach(item ->
                System.out.println(
                        "【课程信息】课程编号cid=" + item.getCid()
                        + ", 课程名称cname=" + item.getCname()
                        + ", 课程学分credit=" + item.getCredit()));
        MybatisSessionFactory.close();
    }

    /**
     * 测试：多对多 - 新增
     */
    @Test
    public void doStudentCreateTest() {
        StudentPO student = new StudentPO();
        student.setSid("yootk" + UUID.randomUUID());
        student.setName("沐言 - 随机");
        List<StudentCourseLink> linkList = new ArrayList<>();
        StudentCourseLink link;
        CoursePO coursePO;
        for (Long cid : new Long[]{37L, 38L, 39L}) {
            link = new StudentCourseLink();
            link.setStudent(student);
            coursePO = new CoursePO();
            coursePO.setCid(cid);
            link.setCourse(coursePO);
            // 配置学生和课程的关系
            linkList.add(link);
        }
        SqlSession sqlSession = MybatisSessionFactory.getSqlSession();
        /*
         * Preparing: INSERT INTO student(sid, name) VALUES (?, ?)
         * Parameters: yootk90ca8b83-20fe-4f5f-b21a-304ff4b6debb(String), 沐言 - 随机(String)
         */
        int insertStudent = sqlSession.insert(NAME_SPACE + ".doCreate", student);
        if (insertStudent > 0) {
            /*
             * Preparing: INSERT INTO student_course(sid, cid) values (?, ?) , (?, ?) , (?, ?)
             * Parameters: yootk90ca8b83-20fe-4f5f-b21a-304ff4b6debb(String), 37(Long), yootk90ca8b83-20fe-4f5f-b21a-304ff4b6debb(String), 38(Long), yootk90ca8b83-20fe-4f5f-b21a-304ff4b6debb(String), 39(Long)
             */
            int insertLink = sqlSession.insert("com.ssm.mybatis.mapper.StudentCourseLinkMapper.doCreate", linkList);
            System.out.println("【更新数据】关联数据行数: " + insertLink);
        }
        sqlSession.commit();
        MybatisSessionFactory.close();
    }

    /**
     * 测试：关联数据批量删除
     */
    @Test
    public void doRemoveTest() {
        String[] sids = new String[]{"muyan", "lee"};
        SqlSession sqlSession = MybatisSessionFactory.getSqlSession();
        /*
         * Preparing: DELETE FROM student WHERE sid IN ( ? , ? )
         * Parameters: muyan(String), lee(String)
         */
        int deleteStu = sqlSession.delete(NAME_SPACE + ".doRemove", sids);
        if (deleteStu > 0) {
            // 【student删除数据】删除条数: 2
            System.out.println("【student删除数据】删除条数: " + deleteStu);
            /*
             * Preparing: DELETE FROM student_course WHERE sid IN ( ? , ? )
             * Parameters: muyan(String), lee(String)
             */
            int deleteLink = sqlSession.delete("com.ssm.mybatis.mapper.StudentCourseLinkMapper.doRemoveByStudent", sids);
            // 【link删除数据】删除条数: 5
            System.out.println("【link删除数据】删除条数: " + deleteLink);
        }
        sqlSession.commit();
        MybatisSessionFactory.close();
    }
}
