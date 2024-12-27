package com.ssm.batch;

import com.ssm.batch.config.SpringBatchTransactionManagerConfig;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.context.annotation.ComponentScan;

import javax.sql.DataSource;

/**
 * 启动类
 * <p>
 *     配置说明
 *      - EnableBatchProcessing 启动 SpringBatch 的环境
 *      - dataSourceRef {@link com.ssm.batch.config.BatchDataSourceConfig#batchDataSource()}
 *      - batchTransactionManager {@link SpringBatchTransactionManagerConfig#batchTransactionManager(DataSource)}
 * @author caimeng
 * @date 2024/12/27 15:40
 */
@EnableBatchProcessing(dataSourceRef = "batchDataSource", transactionManagerRef = "batchTransactionManager")
@ComponentScan("com.ssm.batch")
public class StartSpringBatchApplication {
}
