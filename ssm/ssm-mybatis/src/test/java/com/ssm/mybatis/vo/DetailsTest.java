package com.ssm.mybatis.vo;

import com.ssm.mybatis.util.MybatisSessionFactory;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.Test;

/**
 * @author caimeng
 * @date 2024/12/16 13:59
 */
public class DetailsTest {

    public static final String NAME_SPACE = "com.ssm.mybatis.mapper.DetailsMapper";

    @Test
    public void findByIdTest() {
        SqlSession sqlSession = MybatisSessionFactory.getSqlSession();
        /*
         * Preparing: SELECT aid, rmb, dollar, euro FROM account_details where aid=?
         * Preparing: SELECT aid, name, status FROM account where aid=?
         */
        Details details = sqlSession.selectOne(NAME_SPACE + ".findById", "lixinghua");
        // details = Details(aid=lixinghua, rmb=8000.02, dollar=500.23, euro=62.25)
        System.out.println("details = " + details);
        // account = AccountAndDetails(aid=lixinghua, name=李兴华, status=true)
        System.out.println("account = " + details.getAccountAndDetails());
        MybatisSessionFactory.close();
    }
}
