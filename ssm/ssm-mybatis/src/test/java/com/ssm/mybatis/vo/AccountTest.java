package com.ssm.mybatis.vo;

import com.ssm.mybatis.util.MybatisSessionFactory;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.Test;

import java.util.List;

/**
 * @author caimeng
 * @date 2024/12/16 10:46
 */
public class AccountTest {
    public static final String NAME_SPACE = "com.ssm.mybatis.mapper.AccountMapper";

    /**
     * 测试：类型转换 - 新增
     */
    @Test
    public void doCreateTest() {
        SqlSession sqlSession = MybatisSessionFactory.getSqlSession();
        Account account = new Account();
        account.setAid("muyan-yootk");
        account.setName("沐言优拓");
        account.setStatus(false);   // 活跃的账户信息
        /*
         * Preparing: insert into account(aid, name, status) values (?, ?, ?)
         * Parameters: muyan-yootk(String), 沐言优拓(String), 0(Integer)
         */
        int insert = sqlSession.insert(NAME_SPACE + ".doCreate", account);
        System.out.println("【数据增加】新增数据行数" + insert);
        sqlSession.commit();
        MybatisSessionFactory.close();
    }

    @Test
    public void findAllTest() {
        SqlSession sqlSession = MybatisSessionFactory.getSqlSession();
        List<Account> accountList = sqlSession.selectList(NAME_SPACE + ".findAll");
        accountList.forEach(System.out::println);
        MybatisSessionFactory.close();
    }
}
