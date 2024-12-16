package com.ssm.mybatis.vo;

import com.ssm.mybatis.util.MybatisSessionFactory;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.Test;

/**
 * @author caimeng
 * @date 2024/12/16 15:54
 */
public class RoleTest {
    public static final String NAME_SPACE = "com.ssm.mybatis.mapper.RoleMapper";

    /**
     * 测试：一对多查询
     */
    @Test
    public void findByIdTest() {
        SqlSession sqlSession = MybatisSessionFactory.getSqlSession();
        /*
         * Preparing: SELECT rid, name FROM role WHERE rid=?
         */
        Role role = sqlSession.selectOne(NAME_SPACE + ".findById", "member");
        // role : Role(rid=member, name=用户管理)
        System.out.println("role : " + role);
        System.out.println("actions : --------------");
        /*
         * 没有发现角色查询的日志，因为使用了懒加载的模式，fetchType=lazy
         * 使用 role.getActions() 会触发懒加载中的数据查询
         * 但是要在连接关闭（MybatisSessionFactory.close()）前调用，否则可能会出错。
         *
         * Action(aid=member:delete, name=用户删除)
         * Action(aid=member:lock, name=用户锁定)
         * Action(aid=member:verify, name=用户验证)
         */
        role.getActions().forEach(System.out::println);
        MybatisSessionFactory.close();
    }
}
