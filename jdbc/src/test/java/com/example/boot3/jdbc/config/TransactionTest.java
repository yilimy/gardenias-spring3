package com.example.boot3.jdbc.config;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.util.Objects;

/**
 * @author caimeng
 * @date 2024/5/27 11:32
 */
@Slf4j
@ContextConfiguration(classes = {SpringJdbcConfig.class, HikariDataSourceConfig.class, TransactionConfig.class})
@ExtendWith(SpringExtension.class)
// hikari 打印的debug太多了，都干掉
//@SpringBootTest("debug=false")
public class TransactionTest {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private PlatformTransactionManager transactionManager;

    /**
     * 测试：数据库事务控制
     */
    @Test
    public void transactionTest() {
        String sql = "INSERT INTO book(title, author, price) VALUES (?, ?, ?)";
        /*
         * 开启事务
         * 该处代码返回的 TransactionStatus 默认子类 {@link org.springframework.transaction.support.DefaultTransactionStatus}
         * DefaultTransactionStatus 并不是直接实例化的，而是通过事务管理器负责实例化的
         * status 所得到的是一个事务处理标记，Spring依照此标记管理事务。
         */
        TransactionStatus status = transactionManager.getTransaction(new DefaultTransactionDefinition());
        try {
            jdbcTemplate.update(sql, "Spring开发实战_001", "Crick No.1", 69.81);
            jdbcTemplate.update(sql, null, "Crick No.2", 69.82);
            jdbcTemplate.update(sql, "Spring开发实战_002", null, 69.83);
            jdbcTemplate.update(sql, "Spring开发实战_003", "Crick No.3", null);
            // 事务提交
            transactionManager.commit(status);
            log.info("数据库事务提交");
        } catch (Exception e) {
            // 事务回滚
            transactionManager.rollback(status);
            log.error("数据库更新错误: {}", e.getMessage());
        }
    }

    /**
     * 测试： 只允许回滚事务
     * <p>
     *     该测试的结果为：
     *          如果执行的sql没有问题，程序运行不报错，打印了“数据库事务提交”，但是数据库没有更新，该事务没有被提交
     *          如果执行的sql有问题，程序运行也不报错，打印了“数据库更新错误”，但是数据库没有更新，该事务没有被提交
     * <p>
     *     事务的执行是否完成，决定于 commit 或者 rollback 方法是否正常执行。
     *     执行过了，就表示事务执行完成。
     * <p>
     *     在进行事务处理的时候，如果产生异常，默认情况下是回滚到最初的状态。
     *     在一个庞大的更新操作业务中，有可能只希望回滚到指定的更新语句上，所以需要savePoint机制。
     */
    @Test
    public void rollBackOnlyTest() {
        String sql = "INSERT INTO book(title, author, price) VALUES (?, ?, ?)";
        TransactionStatus status = transactionManager.getTransaction(new DefaultTransactionDefinition());
        // 设置只允许回滚
        status.setRollbackOnly();
        log.info("【事务执行前】当前事务是否开启：{}, 当前事务是否完成：{}", status.isNewTransaction(), status.isCompleted());
        try {
            jdbcTemplate.update(sql, "Spring开发实战_001", "Crick No.1", 69.81);
            // 模拟：产生一个错误
            jdbcTemplate.update(sql, null, "Crick No.2", 69.82);
            // 事务提交
            transactionManager.commit(status);
            // 如果没有错误，该句正常打印，但是数据库不会更新
            log.info("数据库事务提交");
        } catch (Exception e) {
            // 事务回滚
            transactionManager.rollback(status);
            // 如果有错误，该句打印，但是数据库不会更新
            log.error("数据库更新错误: {}", e.getMessage());
        }
        log.info("【事务执行后】当前事务是否开启：{}, 当前事务是否完成：{}", status.isNewTransaction(), status.isCompleted());
    }

    /**
     * 测试：savePoint
     * 不知道为啥，回滚到了保存点B
     * <p>
     *     此时数据库没有全部回滚，而是回滚到保存点上，
     *     这样就可以保证部分数据可以执行正常的更新机制，但是一般使用这样的处理业务逻辑都非常的繁琐。
     *     一般不这么做，要么集体提交，要么集体失败
     */
    @Test
    public void savePointTest() {
        String sql = "INSERT INTO book(title, author, price) VALUES (?, ?, ?)";
        TransactionStatus status = transactionManager.getTransaction(new DefaultTransactionDefinition());
        // 设置记录点A
        Object savePointA = null;
        // 设置记录点B
        Object savePointB = null;
        try {
            jdbcTemplate.update(sql, "Spring开发实战_save_001", "Crick No.1", 69.81);
            jdbcTemplate.update(sql, "Spring开发实战_save_001.1", "Crick No.2", 69.82);
            // 创建第一个保存点
            savePointA = status.createSavepoint();
            // com.mysql.cj.jdbc.MysqlSavepoint@3481ff98
            log.info("savePointA = {}", savePointA);
            // 这一条是能正常执行的语句，实际上该条数据也正常提交了
            jdbcTemplate.update(sql, "Spring开发实战_survive", "Crick No.2", 69.82);
            // 创建第二个保存点
            savePointB = status.createSavepoint();
            // com.mysql.cj.jdbc.MysqlSavepoint@3c91530d
            log.info("savePointB = {}", savePointB);
            jdbcTemplate.update(sql, "Spring开发实战_save_003", null, 69.83);
            jdbcTemplate.update(sql, "Spring开发实战_save_004", "Crick No.3", null);
            transactionManager.commit(status);
            log.info("数据库事务提交");
        } catch (Exception e) {
            if (Objects.nonNull(savePointA)) {
                try {
                    // 回滚到指定位置
                    status.releaseSavepoint(savePointA);
                } catch (Exception e1) {
                    log.error("回滚到保存点A失败", e1);
                } finally {
                    try {
                        // 正常事务提交
                        transactionManager.commit(status);
                    } catch (Exception e2) {
                        log.error("回滚到保存点A事务提交失败", e2);
                    }
                }
            } else {
                transactionManager.rollback(status);
            }
            log.error("数据库更新错误: {}", e.getMessage());
        }
    }
}
