package com.ssm.batch.config;

import com.ssm.batch.tasklet.MessageTasklet;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

/**
 * Spring批处理的配置类
 * <p>
 * <a href="https://docs.spring.io/spring-batch/docs/5.0.6/reference/html/whatsnew.html#new-attributes-enable-batch-processing">
 *     New annotation attributes in EnableBatchProcessing
 * </a>
 * @author caimeng
 * @date 2024/12/27 15:14
 */
@Slf4j
@Configuration
public class SpringBatchConfig {
    /**
     * SpringBatch数据库的操作
     * {@link org.springframework.batch.core.configuration.annotation.EnableBatchProcessing}
     * {@link org.springframework.batch.core.configuration.annotation.BatchRegistrar#registerBeanDefinitions(AnnotationMetadata, BeanDefinitionRegistry)}
     */
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired  // 由SpringBatch自动提供好的支持
    private JobRepository jobRepository;
    /**
     * 任务的事务管理器
     * {@link SpringBatchTransactionManagerConfig#batchTransactionManager(DataSource)}
     */
    @Autowired
    private PlatformTransactionManager batchTransactionManager;
    @Bean
    public Job messageJob() {
        return new JobBuilder("messageJob", jobRepository)
                //define job flow as needed
                .start(messageStep())
                .build();
    }

    @Bean
    public Step messageStep() {     // 定义消息步骤
        return new StepBuilder("messageStep", jobRepository)
                // 创建任务的步骤
                .tasklet(messageTasklet(), batchTransactionManager)
                .build();
    }

    public Tasklet messageTasklet() {
        // 任务的具体处理逻辑
        return new MessageTasklet();
    }
}