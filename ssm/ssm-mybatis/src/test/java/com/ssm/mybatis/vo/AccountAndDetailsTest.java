package com.ssm.mybatis.vo;

import com.ssm.mybatis.util.MybatisSessionFactory;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.Test;

/**
 * @author caimeng
 * @date 2024/12/16 13:56
 */
public class AccountAndDetailsTest {
    public static final String NAME_SPACE = "com.ssm.mybatis.mapper.AccountAndDetailsMapper";

    /**
     * 测试：一对一数据关联查询
     */
    @Test
    public void findByIdTest() {
        SqlSession sqlSession = MybatisSessionFactory.getSqlSession();
        /*
         * 【JDBC连接对象】org.apache.ibatis.logging.jdbc.ConnectionLogger@352c44a8
         * Preparing: SELECT aid, name, status FROM account where aid=?
         * 【JDBC连接对象】org.apache.ibatis.logging.jdbc.ConnectionLogger@75d4a80f
         * Preparing: SELECT aid, rmb, dollar, euro FROM account_details where aid=?
         */
        AccountAndDetails accountAndDetails = sqlSession.selectOne(NAME_SPACE + ".findById", "lixinghua");
        // accountAndDetails = AccountAndDetails(aid=lixinghua, name=李兴华, status=true)
        System.out.println("accountAndDetails = " + accountAndDetails);
        // details = Details(aid=lixinghua, rmb=8000.02, dollar=500.23, euro=62.25)
        System.out.println("details = " + accountAndDetails.getDetails());
        MybatisSessionFactory.close();
    }
}
