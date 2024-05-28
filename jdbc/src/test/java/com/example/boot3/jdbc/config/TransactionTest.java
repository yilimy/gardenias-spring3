package com.example.boot3.jdbc.config;

import com.example.boot3.jdbc.mapper.BookMapper;
import com.example.boot3.jdbc.pojo.Book;
import lombok.SneakyThrows;
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
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @author caimeng
 * @date 2024/5/27 11:32
 */
@Slf4j
@ContextConfiguration(classes = {SpringJdbcConfig.class, HikariDataSourceConfig.class, TransactionConfig.class})
@ExtendWith(SpringExtension.class)
// hikari 打印的debug太多了，都干掉
@SpringBootTest("debug=false")
public class TransactionTest {
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

    /**
     * 隔离性测试
     * 多线程模拟并发出错的情况
     * 为了保证数据在更新的时候的正确性，需要对数据的同步做有效的管理，这属于数据库隔离级别的概念。
     * {@link org.springframework.transaction.TransactionDefinition#ISOLATION_DEFAULT}
     * 数据重置 {@link this#dataReset()}
     * <p>
     *     事务B执行更新后，事务A获取到的事务仍然是原始的数据内容，所以这里出现了数据不同步。
     *     为了保证并发状态下的数据读取的正确性，就需要通过数据的隔离级别来进行控制。
     *     实际上控制的是脏读、幻读以及不可重复读的问题。
     * <p>
     *     脏读：
     *          事务A在读取数据时，读取到了事务B的未提交数据，由于事务B有可能回滚，所以该数据有可能是一个无效的数据。
     *     不可重复读：
     *          事务A对一个数据进行了两次读取，返回了不同的内容。有可能在两次读取之间，事务B对该数据进行了修改。
     *          一般出现在数据修改操作之中。
     *          重点在于修改，行内数据。
     *     幻读：
     *          事务A在进行两次数据库查询时产生了不一样的结果，有可能是事务B在事务A第二次查询之前增加或删除了数据内容导致的。
     *          重点在于增加或删除，数据行数的变更。
     * <p>
     *     Spring可指定的隔离级别
     *          ISOLATION_DEFAULT : 默认隔离级别，由数据库控制
     *          ISOLATION_READ_UNCOMMITTED : 其他事务可以读取到未提交的数据，会产生脏数据、不可重复读数据、以及幻读
     *          ISOLATION_READ_COMMITTED : 其他事务只允许读取已经提交事务的数据，可以避免脏读，但是会有不可重复读和幻读的问题
     *          ISOLATION_REPEATABLE_READ : 数据可重复读，防止脏读脏读、不可重复读，但可能出现幻读
     *          ISOLATION_SERIALIZABLE : 最高隔离级别，完整的解决了脏读、幻读、不可重复读的问题，会花费较大代价
     *     Mysql中查询数据命令 (REPEATABLE-READ)
     *     <code>
     *         SHOW VARIABLES LIKE 'TRANSACTION_ISOLATION';
     *     </code>
     * 对于 Spring 建议使用默认的隔离级别。
     */
    @SneakyThrows
    @Test
    public void transactionIsolationTest() {
        Integer bid = 8;
        String querySql = "select bid, title, author, price from book where bid=?";
        String updateSql = "update book set title=?, price=? where bid=?";
        BookMapper bookMapper = new BookMapper();
        /*
         * 每一个线程应该都应该拥有一个事务，
         * 事务应该是存在于ThreadLocal中
         */
        new Thread(() -> {
            /*
             * 开启事务进行查询
             * 不开启事务的结果：
             *      第一次数据查询：Book(bid=8, title=SSM开发实战, author=NPC, price=429.0753061963884)
             *      第二次数据查询：Book(bid=8, title=Netty实战开发, author=NPC, price=888.88)
             * 开启事务的结果：
             *      第一次数据查询：Book(bid=8, title=SSM开发实战, author=NPC, price=429.0753061963884)
             *      第二次数据查询：Book(bid=8, title=SSM开发实战, author=NPC, price=429.0753061963884)
             * 结论：
             *      查询开启事务后，同一线程获取的结果一致，虽然有其他线程更改了数据库的值；
             *      不开启事务，执行的是实时查询。
             */
            DefaultTransactionDefinition transactionDefinition = new DefaultTransactionDefinition();
            /*
             * 设置隔离级别 TransactionDefinition.ISOLATION_READ_COMMITTED
             * 结果: 第二次数据查询：Book(bid=8, title=Netty实战开发, author=NPC, price=888.88)
             */
//            transactionDefinition.setIsolationLevel(TransactionDefinition.ISOLATION_READ_COMMITTED);
            transactionManager.getTransaction(transactionDefinition);
            Book book = jdbcTemplate.queryForObject(querySql, bookMapper, bid);
            log.info("【{}】第一次数据查询：{}", Thread.currentThread().getName(), book);
            try {
                // 为了让线程B更新，强制休眠5秒
                TimeUnit.SECONDS.sleep(5);
                book = jdbcTemplate.queryForObject(querySql, bookMapper, bid);
                log.info("【{}】第二次数据查询：{}", Thread.currentThread().getName(), book);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } finally {
//                transaction.flush();
            }
        }, "JDBC操作线程 - A").start();
        new Thread(() -> {
            TransactionStatus transaction = transactionManager.getTransaction(new DefaultTransactionDefinition());
            Book book = jdbcTemplate.queryForObject(querySql, bookMapper, bid);
            log.info("【{}】第一次数据查询：{}", Thread.currentThread().getName(), book);
            try {
                // 适当进行休眠后，执行更新
                TimeUnit.MILLISECONDS.sleep(200);
                // 执行数据更新
                int count = jdbcTemplate.update(updateSql, "Netty实战开发", 888.88, bid);
                log.info("【{}】数据更新完成, 影响更新的行数:{}", Thread.currentThread().getName(), count);
                transactionManager.commit(transaction);
            } catch (InterruptedException e) {
                log.error("【{}】数据更新出错, 错误信息: {}", Thread.currentThread().getName(), e.getMessage());
                transactionManager.rollback(transaction);
            }
        }, "JDBC操作线程 - B").start();
        // 主线程休眠，大于子线程执行实现，延迟处理
        TimeUnit.SECONDS.sleep(10);
    }

    /**
     * 数据重置
     */
    @Test
    public void dataReset() {
        Integer bid = 8;
        String updateSql = "update book set title=?, price=? where bid=?";
        jdbcTemplate.update(updateSql, "SSM开发实战", 429.0753061963884, bid);
        String querySql = "select bid, title, author, price from book where bid=?";
        BookMapper bookMapper = new BookMapper();
        Book book = jdbcTemplate.queryForObject(querySql, bookMapper, bid);
        log.info("数据重置结果: book={}", book);
    }
}
