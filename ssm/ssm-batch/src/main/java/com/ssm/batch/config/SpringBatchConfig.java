package com.ssm.batch.config;

//import com.ssm.batch.listener.MessageJobExecutionListener;

import com.ssm.batch.decider.MessageJobExecutionDecider;
import com.ssm.batch.listener.AbortExecutionListener;
import com.ssm.batch.listener.MessageJobExecutionByAnnotationListener;
import com.ssm.batch.listener.MessageJobExecutionListener;
import com.ssm.batch.listener.MessageStepExecutionListener;
import com.ssm.batch.tasklet.MessageTasklet;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.DefaultJobParametersValidator;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.CallableTaskletAdapter;
import org.springframework.batch.core.step.tasklet.MethodInvokingTaskletAdapter;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
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
@SuppressWarnings("unused")
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
     * 异步任务执行器
     * <p>
     *     1. 创建异步任务执行器 (TaskExecutor)
     *     2. 在任务 (Job) 中配置异步任务执行器
     *          {@link SpringBatchConfig#asyncJob()}
     * @return 异步任务执行器
     */
    @Bean
    public TaskExecutor asyncTaskExecutor() {
        /*
         * 异步任务执行器
         * spring_batch_1
         * ...
         * spring_batch_2
         */
        SimpleAsyncTaskExecutor executor = new SimpleAsyncTaskExecutor("spring_batch_");
        /*
         * 并行数量
         * 一般由物理CPU来决定
         * 同时按照常规的优化方式：内核数量 * 2
         * Netty 中就是这么做的
         */
        executor.setConcurrencyLimit(Runtime.getRuntime().availableProcessors() * 2);
        return executor;
    }

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
//    @Bean
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
     * @return 测试: 使用FLow定义的作业
     */
//    @Bean
    public Job messageFlowJob() {
        return new JobBuilder(JOB_NAME, jobRepository)
                .start(messeageFlow())
                // 如果设置的步骤是Flow，必需使用 end() 返回Builder
                .end()
                .build();
    }

    /**
     * @return 测试: 混搭的作业配置
     */
//    @Bean
    public Job messageMixJob() {
        return new JobBuilder(JOB_NAME, jobRepository)
                .start(messageReadStep())
                // 配置决策器
                .next(messageDecider())
                // 执行处理的分支
                .from(messageDecider()).on("Handler").to(messageHandlerStep())
                .from(messageDecider()).on("Write").to(messageWriteStep())
                // 决策器配置完成，调用 end()
                .end()
                .build();
    }

    /**
     * @return 测试: 异步执行的任务
     */
//    @Bean
    public Job asyncJob() {
        return new JobBuilder(JOB_NAME, jobRepository)
                .start(messageReadStep())
                // 配置异步任务执行器
                .split(asyncTaskExecutor())
                .add(messeageFlow())
                .end()
                .build();
    }

    /**
     * 通过调用Callable的方式实现异步任务调用
     * @return callableTasklet的作业
     */
//    @Bean
    public Job callableJob() {
        return new JobBuilder(JOB_NAME, jobRepository)
                .start(callableStep())
                .build();
    }

    /**
     * 通过调用自定义方法实现类的方式实现异步任务调用
     * @return {@link MethodInvokingTaskletAdapter} 的作业
     */
    @Bean
    public Job methodInvokeJob() {
        return new JobBuilder(JOB_NAME, jobRepository)
                .start(methodInvokeStep())
                .build();
    }

    /**
     * 决策器的操作是依据作业的信息处理的，所以要在作业配置上决策器
     * @return 决策器
     */
    @Bean
    public MessageJobExecutionDecider messageDecider() {
        return new MessageJobExecutionDecider();
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

    /**
     * Tasklet已有方案应用其一
     * 通过已有的模式 {@link CallableTaskletAdapter} 创建 tasklet，而不是通过实现 {@link Tasklet} 或者匿名内部类的方式创建
     * @return CallableTasklet
     */
    @Bean
    public Step callableStep() {
        CallableTaskletAdapter callableTaskletAdapter = new CallableTaskletAdapter();
        callableTaskletAdapter.setCallable(() -> {
            log.info("【消息批处理任务】Hello: 《SSM开发实战》");
            return RepeatStatus.FINISHED;
        });
        return new StepBuilder("messageStep", jobRepository)
                .tasklet(callableTaskletAdapter, batchTransactionManager)
                .build();
    }

    /**
     * Tasklet已有应用方案其二
     * {@link Tasklet}
     * {@link MethodInvokingTaskletAdapter}
     * @return MethodInvokingTaskletAdapter
     */
    @Bean
    @SuppressWarnings("DanglingJavadoc")
    public Step methodInvokeStep() {
        /**
         * 自定义的结构类
         * <p>
         *     该类主要实现 {@link org.springframework.batch.core.step.tasklet.Tasklet} 的功能
         */
        class MessageHandler {
            // 一定要注意返回的结果，与 Tasklet 定义的返回结果类型一致
            public RepeatStatus exec() {
                log.info("【消息处理任务】Hello: 《Spring Boot开发实战》");
                return RepeatStatus.FINISHED;
            }
        }
        // 创建一个自定义的方法调用的任务处理
        MethodInvokingTaskletAdapter adapter = new MethodInvokingTaskletAdapter();
        // 设置方法调用实例
        adapter.setTargetObject(new MessageHandler());
        // 设置方法调用名称
        adapter.setTargetMethod("exec");
        return new StepBuilder("messageStep", jobRepository)
                .tasklet(adapter, batchTransactionManager)
                .build();
    }

    public Tasklet messageTasklet() {
        // 任务的具体处理逻辑
        return new MessageTasklet();
    }

    /**
     * 步骤组
     * <p>
     *     几个步骤需要一起定义管理的时候，可以通过Flow来进行配置
     * @return 步骤组
     */
    @Bean
    public Flow messeageFlow() {
        // 将消息的读-处理-写，作为一套完整的逻辑，使用Flow作为封装
        // 定义一组步骤，命名为: muyanMessageFlow
        FlowBuilder<Flow> flowBuilder = new FlowBuilder<>("muyanMessageFlow");
        // 定义flow中的执行步骤
        return flowBuilder.start(messageReadStep())
                .next(messageHandlerStep())
                .next(messageWriteStep())
                .build();
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
                // 定义步骤监听
                .listener(stepExecutionListener())
                .build();
    }

    /**
     * @return 消息的步骤监听
     */
    @Bean
    public MessageStepExecutionListener stepExecutionListener() {
        return new MessageStepExecutionListener();
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
