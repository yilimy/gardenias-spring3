package com.ssm.batch.config;

//import com.ssm.batch.listener.MessageJobExecutionListener;

import com.ssm.batch.decider.MessageJobExecutionDecider;
import com.ssm.batch.listener.AbortExecutionListener;
import com.ssm.batch.listener.AccountItemWriterListener;
import com.ssm.batch.listener.BillStepChunkListener;
import com.ssm.batch.listener.BillStepSkipListener;
import com.ssm.batch.listener.MessageJobExecutionByAnnotationListener;
import com.ssm.batch.listener.MessageJobExecutionListener;
import com.ssm.batch.listener.MessageStepExecutionListener;
import com.ssm.batch.mapper.BillMapper;
import com.ssm.batch.processor.BillToAccountItemProcessor;
import com.ssm.batch.tasklet.MessageTasklet;
import com.ssm.batch.vo.Account;
import com.ssm.batch.vo.Bill;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.DefaultJobParametersValidator;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.SimpleStepBuilder;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.skip.AlwaysSkipItemSkipPolicy;
import org.springframework.batch.core.step.skip.SkipPolicy;
import org.springframework.batch.core.step.tasklet.CallableTaskletAdapter;
import org.springframework.batch.core.step.tasklet.MethodInvokingTaskletAdapter;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.MultiResourceItemReader;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.support.ScriptItemProcessor;
import org.springframework.batch.repeat.CompletionPolicy;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.batch.repeat.policy.SimpleCompletionPolicy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.dao.DuplicateKeyException;
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
     * @return 指定分隔符的分割器
     */
    @Bean
    public DelimitedLineTokenizer lineTokenizer() {
        return new DelimitedLineTokenizer(",");
    }

    /**
     * 注入映射器
     * @return 映射器
     */
    @Bean
    public BillMapper billMapper() {
        return new BillMapper();
    }

    @Bean
    public LineMapper<Bill> lineMapper() {
        DefaultLineMapper<Bill> mapper = new DefaultLineMapper<>();
        // 设置数据分割器
        mapper.setLineTokenizer(lineTokenizer());
        // 设置数据映射器
        mapper.setFieldSetMapper(billMapper());
        return mapper;
    }

    @Bean
    public ItemReader<Bill> billReader() {
        /*
         * 此时的数据是批量读取进来，但是会按照既定的格式进行拆分（每行读一次）
         * 当前:
         *      使用单个文件读取，使用的是 FlatFileItemReader
         *      后续有适合于目录的多文件读取，使用 MultiResourceItemReader
         */
        FlatFileItemReader<Bill> reader = new FlatFileItemReader<>();
        // 每行读取到的数据，需要设置专属的数据行处理映射实例
        reader.setLineMapper(lineMapper());
        /*
         * 当前数据在 CALSSPATH 资源下，需要使用Resource读取
         * 考虑到读取的可能不只一个文件，
         * 因此做资源的匹配
         */
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        String filePath = "classpath:data/bill.txt";
        reader.setResource(resolver.getResource(filePath));
        return reader;
    }

    @SneakyThrows
    @Bean("billMultiReader")
    public ItemReader<Bill> billMultiReader() {
        // 多资源读取数据
        MultiResourceItemReader<Bill> reader = new MultiResourceItemReader<>();
        // 单资源读取
        FlatFileItemReader<Bill> itemReader = new FlatFileItemReader<>();
        itemReader.setLineMapper(lineMapper());
        reader.setDelegate(itemReader);
        // 路径匹配器
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        String filePath = "classpath:data/*.txt";
        // 匹配资源
        reader.setResources(resolver.getResources(filePath));
        return reader;
    }

    /**
     * 数据读取器
     * <p>
     *     在步骤中读取参数
     *     {@link JobExecution#getJobParameters()}
     * @param path 文件读取路径
     * @return 数据读取器
     */
    @SneakyThrows
    @Bean("billMultiScopeReader")
    @StepScope  // 接收作业执行的参数
    public ItemReader<Bill> billMultiScopeReader(
            // 读取作业参数中的
            @SuppressWarnings("SpringElInspection")
            @Value("#{jobParameters[path]}") String path
    ) {
        MultiResourceItemReader<Bill> reader = new MultiResourceItemReader<>();
        FlatFileItemReader<Bill> itemReader = new FlatFileItemReader<>();
        itemReader.setLineMapper(lineMapper());
        reader.setDelegate(itemReader);
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        reader.setResources(resolver.getResources(path));
        return reader;
    }

    /**
     * @return 通过自定义实现的实例
     */
//    @Bean
    public ItemProcessor<Bill, Account> itemProcessor() {
        return new BillToAccountItemProcessor();
    }

    /**
     * 脚本处理转换器
     * <p>
     *     通过JS脚本实现数据的处理逻辑，理论上功能会更加强悍。
     *     而且适合于不同的开发者进行编写，毕竟不同的语言开发者，大多数都会JS语法。
     * @return 通过使用提供的脚本处理类来注入实例
     */
    @Bean
    public ItemProcessor<Bill, Account> jsItemProcessor() {
        ScriptItemProcessor<Bill, Account> itemProcessor = new ScriptItemProcessor<>();
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        String filePath = "classpath:/js/billToAccount.js";
        itemProcessor.setScript(resolver.getResource(filePath));
        // 脚本里面绑定的属性名称
        itemProcessor.setItemBindingVariableName("item");
        return itemProcessor;
    }

    /**
     * 数据输出
     * @param yootkDataSource 数据库连接
     * @return 数据输出对象
     */
    @Bean
    public ItemWriter<Account> itemWriter(
            @Autowired @Qualifier("yootkDataSource") DataSource yootkDataSource) {
        JdbcBatchItemWriter<Account> itemWriter = new JdbcBatchItemWriter<>();
        // 数据库的连接
        itemWriter.setDataSource(yootkDataSource);
        // 通过冒号的形式表示类型的属性
        String sql = "INSERT INTO account(id, amount) VALUES (:id, :amount)";
        itemWriter.setSql(sql);
        // SQL参数的配置定义
        itemWriter.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>());
        return itemWriter;
    }

    /**
     * @return 测试：含有若干个功能的作业
     */
//    @Bean("messageJob")
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
//    @Bean("messageStepJob")
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
//    @Bean("messageFlowJob")
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
//    @Bean("messageMixJob")
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
//    @Bean("asyncJob")
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
//    @Bean("callableJob")
    public Job callableJob() {
        return new JobBuilder(JOB_NAME, jobRepository)
                .start(callableStep())
                .build();
    }

    /**
     * 通过调用自定义方法实现类的方式实现异步任务调用
     * @return {@link MethodInvokingTaskletAdapter} 的作业
     */
    @Bean("methodInvokeJob")
    @Primary
    public Job methodInvokeJob() {
        return new JobBuilder(JOB_NAME, jobRepository)
                .start(methodInvokeStep())
                .build();
    }

    /**
     * 批处理：对账作业
     * @return 作业对象
     */
    @Bean("billJob")
    public Job billJob(@Autowired @Qualifier("scopeStep") Step step) {
        return new JobBuilder("billJob", jobRepository)
                // 设置作业步骤
                .start(step)
                // 构建作业
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

    @Bean
    public BillStepChunkListener billStepChunkListener() {
        return new BillStepChunkListener();
    }

    @Bean
    public BillStepSkipListener billStepSkipListener() {
        return new BillStepSkipListener();
    }

    /**
     * @return 通过使用注解的方式实现的监听器
     */
    @Bean
    public MessageJobExecutionByAnnotationListener messageJobExecutionAnnotationListener() {
        return new MessageJobExecutionByAnnotationListener();
    }

    /**
     * @return 数据写入监听
     */
    @Bean
    public AccountItemWriterListener accountItemWriterListener() {
        return new AccountItemWriterListener();
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
        validator.setOptionalKeys(new String[]{"developer", "timestamp", "path"});
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

    @Bean("scopeStep")
    public Step scopeStep(
            // 因为 billMultiScopeReader 的创建是需要有参数的，这里使用自己注入自己的方式
            @Autowired @Qualifier("billMultiScopeReader") ItemReader<Bill> itemReader,
            @Autowired ItemWriter<Account> itemWriter) {
        SimpleStepBuilder<Bill, Account> builder = new SimpleStepBuilder<>(
                new StepBuilder("billStep", jobRepository));
        return builder
                // 事务管理
                .transactionManager(batchTransactionManager)
                // 每次处理5行数据，与完成策略互斥，因为完成策略中也设置了执行数量
//                .chunk(5)
                // 定义完成策略
                .chunk(completionPolicy())
                // 设置数据写入监听
                .listener(accountItemWriterListener())
                // 数据读取
                .reader(itemReader)
                // 数据处理
                .processor(jsItemProcessor())
                // 数据输出
                .writer(itemWriter)
                // 失败处理操作
                .faultTolerant()
                // 跳过指定的异常
                .skip(NumberFormatException.class)
                // 跳过重复主键的异常
                .skip(DuplicateKeyException.class)
                // 跳过的极限
                .skipLimit(Integer.MAX_VALUE)
                // 设置跳过策略
                .skipPolicy(skipPolicy())
                // 设置Chunk监听
                .listener(billStepChunkListener())
                // 设置跳过监听
                .listener(billStepSkipListener())
                // 构建
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

    /**
     * @return 完成策略
     */
    @Bean
    public CompletionPolicy completionPolicy() {
        // 每次执行3条处理
        return new SimpleCompletionPolicy(3);
    }

    /**
     * @return 跳过策略
     */
    @Bean
    public SkipPolicy skipPolicy() {
        // 总是跳过
        return new AlwaysSkipItemSkipPolicy();
    }

}
