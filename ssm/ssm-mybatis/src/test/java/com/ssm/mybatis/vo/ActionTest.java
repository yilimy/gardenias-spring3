package com.ssm.mybatis.vo;

import com.ssm.mybatis.util.MybatisSessionFactory;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.Test;

/**
 * @author caimeng
 * @date 2024/12/16 15:57
 */
public class ActionTest {
    public static final String NAME_SPACE = "com.ssm.mybatis.mapper.ActionMapper";

    @Test
    public void findByIdTest() {
        SqlSession sqlSession = MybatisSessionFactory.getSqlSession();
        /*
         * Preparing: SELECT aid, name, rid FROM action WHERE aid=?
         */
        Action action = sqlSession.selectOne(NAME_SPACE + ".findById", "member:lock");
        // action : Action(aid=member:lock, name=用户锁定)
        System.out.println("action : " + action);
        // 触发了懒加载的查询  Preparing: SELECT aid, name, rid FROM action WHERE rid=?
        // role : Role(rid=member, name=用户管理)
        System.out.println("role : " + action.getRole());
        MybatisSessionFactory.close();
    }
}
