package com.ssm.mybatis.vo;

import com.ssm.mybatis.util.MybatisSessionFactory;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.Test;

import java.util.List;

/**
 * @author caimeng
 * @date 2024/12/16 17:22
 */
public class CourseTest {
    public static final String NAME_SPACE = "com.ssm.mybatis.mapper.CourseMapper";

    /**
     * 测试：多对对查询
     */
    @Test
    public void findByIdTest() {
        // Preparing: SELECT cid, cname, credit FROM course where cid=?
        CoursePO course = MybatisSessionFactory.getSqlSession()
                .selectOne(NAME_SPACE + ".findById", 11L);
        // course : CoursePO(cid=11, cname=Redis开发实战, credit=4)
        System.out.println("course : " + course);
        System.out.println("-".repeat(30));
        List<StudentPO> students = course.getStudents();
        // 【学生信息】学生编号sid=lee, 学生名称cname=李兴华
        students.forEach(item ->
                System.out.println(
                        "【学生信息】学生编号sid=" + item.getSid()
                                + ", 学生名称cname=" + item.getName()));
        MybatisSessionFactory.close();
    }

    /**
     * 测试：关联数据批量删除
     */
    @Test
    public void doRemoveTest() {
        Long[] cids = new Long[]{37L, 38L, 39L};
        SqlSession sqlSession = MybatisSessionFactory.getSqlSession();
        /*
         * Preparing: DELETE FROM course WHERE cid IN ( ? , ? , ? )
         * Parameters: 37(Long), 38(Long), 39(Long)
         */
        int deleteStu = sqlSession.delete(NAME_SPACE + ".doRemove", cids);
        if (deleteStu > 0) {
            // 【course删除数据】删除条数: 3
            System.out.println("【course删除数据】删除条数: " + deleteStu);
            /*
             * Preparing: DELETE FROM student_course WHERE cid IN ( ? , ? , ? )
             * Parameters: 37(Long), 38(Long), 39(Long)
             */
            int deleteLink = sqlSession.delete("com.ssm.mybatis.mapper.StudentCourseLinkMapper.doRemoveByCourse", cids);
            // 【link删除数据】删除条数: 3
            System.out.println("【link删除数据】删除条数: " + deleteLink);
        }
        sqlSession.commit();
        MybatisSessionFactory.close();
    }
}
