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
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import javax.sql.DataSource;

/**
 * 测试：事务传播
 * {@link org.springframework.transaction.TransactionDefinition}
 * <p>
 *     spring 共定义了七种传播机制
 *     1. PROPAGATION_REQUIRED
 *          默认的事务传播机制，
 *          子业务直接支持当前的父级事务，如果当前父业务之中没有父业务，则创建一个新事务，
 *          如果当前父业务之中存在有事务，则合并成一个完整的事务。（加入该事务）
 *          必定存在子事务。
 *          ==> 不管任何时候，只要执行了业务调用，都会创建出一个新的事务，这种机制是最为常见的事务传播机制的配置。
 *     2. PROPAGATION_SUPPORTS
 *          如果当前父业务存在有事务，则加入该父级事务，
 *          如果当前父业务没有业务，则以非事务的方式运行。
 *          可有可无。
 *          ==> 如果有事务的支持就使用事务的处理方式，如果没有事务的支持，那么就采用事务裸奔的方式运行。
 *     3. PROPAGATION_MANDATORY
 *          如果当前存在父级事务，则运行在父级事务中，
 *          如果当前没有事务，则抛异常。
 *          必须存在有父级事务。
 *          MANDATORY: 强制的
 *          ==> 必须要有父级事务。
 *     4. PROPAGATION_REQUIRES_NEW
 *          建立一个新的子业务事务，
 *          如果存在父级事务，则会挂起父级事务，
 *          该操作可实现子业务事务的独立提交，不受调用者影响，即便父业务事务异常，也可以正常提交。
 *          子事务独立运行。
 *          ==> 子业务拥有独立的事务，即便父业务出现了问题，也不影响子业务的处理。
 *     5. PROPAGATION_NOT_SUPPORTED
 *          以非事务的方式运行，如果有父级事务，则先自动挂起父级事务后运行。
 *          子事务一定不存在。
 *          ==> 在进行其他业务调用的时候，不管是否存在事务，统一关闭。
 *     6. PROPAGATION_NEVER
 *          以非事务的方式运行，如果当前存在事务则抛异常。
 *          子事务一定不存在，存在事务抛异常。
 *     7. PROPAGATION_NESTED
 *          嵌套事务内执行
 *          如果当前存在父级事务，则当前子业务中的事务会自动成为该父级事务中的一个子事务，只有在父级事务提交后才会提交子事务。
 *          如果子事务产生异常则可以交由父级调用进行异常处理，如果父级事务产生异常，则其也会回滚。
 *          如果当前没有事务，则执行与 PROPAGATION_REQUIRED 相似的操作。
 *          总会存在子事务。
 *          NESTED : 嵌套
 *          ==> 所有的事务统一交给调用业务处处理。
 * @author caimeng
 * @date 2024/5/29 10:58
 */
@Slf4j
@ContextConfiguration(classes = {SpringJdbcConfig.class, HikariDataSourceConfig.class, TransactionConfig.class})
@ExtendWith(SpringExtension.class)
@SpringBootTest("debug=false")
public class PropagationTest {
    /**
     * 数据库连接对象
     * @see SpringJdbcConfig#jdbcTemplate(DataSource)
     */
    @Autowired
    private JdbcTemplate jdbcTemplate;
    /**
     * 数据库事务
     * @see TransactionConfig#transactionManager(DataSource)
     */
    @Autowired
    private PlatformTransactionManager transactionManager;

    /**
     * 模拟更新的业务
     */
    @Test
    public void updateService() {
        Integer bid = 47;
        /*
         * 注意：
         *      此处如果写成 UPDATE book SET title=? AND price=? WHERE bid=?
         *      执行更新不会出错，因为该sql解析成了 UPDATE book SET title=0 WHERE bid=?
         *      错误的拼写方式导致条件字符串当成数字0处理了
         */
        String updateSql = "UPDATE book SET title=?, price=? WHERE bid=?";
        DefaultTransactionDefinition transactionDefinition = new DefaultTransactionDefinition();
        // 默认事务传播方式
        transactionDefinition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        // 开启事务
        TransactionStatus status = transactionManager.getTransaction(transactionDefinition);
        try {
            // 执行嵌套事务
            this.insertService(TransactionDefinition.PROPAGATION_NESTED);
            // 新增一条数据，成功
//            this.insertService(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
            // 执行一段错误代码， title不能为空
            jdbcTemplate.update(updateSql, null, 22.33, bid);
            // 事务提交
            transactionManager.commit(status);
        } catch (Exception e) {
            log.error("更新操作出现异常, {}", e.getMessage());
            transactionManager.rollback(status);
        }
    }

    /**
     * 模拟添加的业务
     */
    public void insertService(int propagationBehavior) {
        String insertSql = "INSERT INTO book(title, author, price) VALUES (?, ?, ?)";
        DefaultTransactionDefinition transactionDefinition = new DefaultTransactionDefinition();
        transactionDefinition.setPropagationBehavior(propagationBehavior);
        // 开启事务
        TransactionStatus status = transactionManager.getTransaction(transactionDefinition);
        // 执行一段正确的sql插入
        jdbcTemplate.update(insertSql, "Redis开发实战", "Andy", 77.32);
        // 事务提交
        transactionManager.commit(status);
    }

    /**
     * 测试：强制事务传播
     * 运行出错:
     *      org.springframework.transaction.IllegalTransactionStateException:
     *              No existing transaction found for transaction marked with propagation 'mandatory'
     */
    @Test
    public void mandatoryTest() {
        Integer bid = 47;
        String updateSql = "UPDATE book SET title=?, price=? WHERE bid=?";
        // 此时没有父级的事务
        this.insertService(TransactionDefinition.PROPAGATION_MANDATORY);
        jdbcTemplate.update(updateSql, null, 22.33, bid);
    }
}
