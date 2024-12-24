package com.ssm.mybatis.plus.plugin;

import com.ssm.mybatis.plus.StartMyBatisPlusApplication;
import com.ssm.mybatis.plus.dao.IProjectDAO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * 测试：全表操作防护
 * <p>
 *     在使用 update 和 delete 时，如果没有 where 条件，就应该报错
 *     该功能是 Mybatis-Plus 的功能，对 Mybatis 也生效
 * @author caimeng
 * @date 2024/12/24 13:55
 */
@Slf4j
@ContextConfiguration(classes = {StartMyBatisPlusApplication.class})
@ExtendWith(SpringExtension.class)
public class BlockAttackTest {
    @Autowired
    private IProjectDAO projectDAO;

    /**
     * 测试全表更新
     */
    @Test
    public void doUpdateAllTest() {
        long count = projectDAO.doUpdateAll();
        /*
         * org.mybatis.spring.MyBatisSystemException
         *      at org.mybatis.spring.MyBatisExceptionTranslator.translateExceptionIfPossible(MyBatisExceptionTranslator.java:97)
         * Error updating database.  Cause: com.baomidou.mybatisplus.core.exceptions.MybatisPlusException: Prohibition of table update operation
         *      Cause: com.baomidou.mybatisplus.core.exceptions.MybatisPlusException: Prohibition of table update operation
         *
         * Prohibition（禁止）
         */
        System.out.println("【更新全部数据】count= " + count);
    }

    /**
     * 测试全表删除
     */
    @Test
    public void doDeleteAllTest() {
        /*
         * org.mybatis.spring.MyBatisSystemException
         *  	at org.mybatis.spring.MyBatisExceptionTranslator.translateExceptionIfPossible(MyBatisExceptionTranslator.java:97)
         * Error updating database.  Cause: com.baomidou.mybatisplus.core.exceptions.MybatisPlusException: Prohibition of full table deletion
         *      Cause: com.baomidou.mybatisplus.core.exceptions.MybatisPlusException: Prohibition of full table deletion
         */
        long count = projectDAO.doDeleteAll();
        System.out.println("【删除全部数据】count= " + count);
    }
}
