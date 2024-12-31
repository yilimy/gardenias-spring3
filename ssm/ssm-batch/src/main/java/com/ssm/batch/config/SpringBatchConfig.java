package com.ssm.batch.config;

//import com.ssm.batch.listener.MessageJobExecutionListener;

import com.ssm.batch.listener.AbortExecutionListener;
import com.ssm.batch.listener.MessageJobExecutionByAnnotationListener;
import com.ssm.batch.listener.MessageJobExecutionListener;
import com.ssm.batch.tasklet.MessageTasklet;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.DefaultJobParametersValidator;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
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
    public static final String JOB_NAME = "messageJob";

    /**
     * @return 测试：含有若干个功能的作业
     */
//    @Bean
    public Job messageJob() {
        return new JobBuilder(JOB_NAME, jobRepository)
                //define job flow as needed
                .start(messageStep())
                // 追加批处理的验证器
                .validator(jobParametersValidator())
                // 消息作业的监听器（实现类的方式）
//                .listener(messageJobExecutionListener())
                // 消息作业的监听器（注解标记的方式）
                .listener(messageJobExecutionAnnotationListener())
                // 配置中断监听
                .listener(abortExecutionListener())
                .build();
    }

    /**
     * @return 测试: 包含有若干处理步骤的作业
     */
    @Bean
    public Job messageStepJob() {
        return new JobBuilder(JOB_NAME, jobRepository)
                // 设置作业的处理步骤
                .start(messagePrepareStep())
                .next(messageReadStep())
                .next(messageHandlerStep())
                .next(messageWriteStep())
                .build();
    }

    /**
     * @return 一个包含有暂停功能的监听器
     */
    @Bean
    public AbortExecutionListener abortExecutionListener() {
        return new AbortExecutionListener();
    }

    /**
     * @return 通过实现接口的方式，创建的监听器
     */
    @Bean
    public MessageJobExecutionListener messageJobExecutionListener() {
        return new MessageJobExecutionListener();
    }

    /**
     * @return 通过使用注解的方式实现的监听器
     */
    @Bean
    public MessageJobExecutionByAnnotationListener messageJobExecutionAnnotationListener() {
        return new MessageJobExecutionByAnnotationListener();
    }

    /**
     * @return 自定义的验证器，包含有必需参数和可选参数。这两者之外的参数会报错。
     */
    @Bean
    public DefaultJobParametersValidator jobParametersValidator() {
        // 定义参数验证器
        DefaultJobParametersValidator validator =  new DefaultJobParametersValidator();
        // 设置必选参数
        validator.setRequiredKeys(new String[]{"project"});
        // 设置可选参数
        validator.setOptionalKeys(new String[]{"developer", "timestamp"});
        return validator;
    }

    /**
     * @return 自定义的消息步骤
     */
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

    /**
     * @return 步骤：准备部分
     */
    @Bean
    public Step messagePrepareStep() {
        // 有可能此时需要进行某些特定服务器的连接，或者是执行一些数据库额DDL操作
        return new StepBuilder("messagePrepareStep", jobRepository)
                .tasklet((stepContribution, chunkContext) -> {
                    log.info("【Step-0,消息准备步骤】初始化系统环境，连接服务器接口等。");
                    return RepeatStatus.FINISHED;
                }, batchTransactionManager)
                .build();
    }

    /**
     * @return 步骤：消息的读取部分
     */
    @Bean
    public Step messageReadStep() {
        return new StepBuilder("messageReadStep", jobRepository)
                .tasklet((stepContribution, chunkContext) -> {
                    log.info("【Step-1,消息读取步骤】通过输入源，读取消息内容");
                    return RepeatStatus.FINISHED;
                }, batchTransactionManager)
                .build();
    }

    /**
     * @return 步骤：消息的处理步骤
     */
    @Bean
    public Step messageHandlerStep() {
        return new StepBuilder("messageHandlerStep", jobRepository)
                .tasklet((stepContribution, chunkContext) -> {
                    log.info("【Step-2,消息处理步骤】检索出包含有yootk的数据内容");
                    return RepeatStatus.FINISHED;
                }, batchTransactionManager)
                .build();
    }

    /**
     * @return 步骤：消息的写入步骤
     */
    @Bean
    public Step messageWriteStep() {
        return new StepBuilder("messageWriteStep", jobRepository)
                .tasklet((stepContribution, chunkContext) -> {
                    log.info("【Step-3,消息写入步骤】将合法的消息写入数据终端");
                    return RepeatStatus.FINISHED;
                }, batchTransactionManager)
                .build();
    }

}
