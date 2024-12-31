package com.ssm.batch;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.text.MessageFormat;
import java.util.concurrent.TimeUnit;

/**
 * @author caimeng
 * @date 2024/12/31 9:19
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = StartSpringBatchApplication.class)
public class JobOperatorTest {
    @Autowired
    private Job messageJson;
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private JobLauncher jobLauncher;
    /**
     * 作业操作器
     */
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private JobOperator jobOperator;
    @SneakyThrows
    @Test
    public void stopTest() {
        JobParameters jobParameters = new JobParametersBuilder()
                .addString("project", "muyan-yootk-spring-batch")
                .addString("developer", "小李老师")
                .addLong("timestamp", System.currentTimeMillis())
                .toJobParameters();
        JobExecution execution = jobLauncher.run(messageJson, jobParameters);
        Long jobId = execution.getJobId();
        /*
         * 停止当前的作业
         * org.springframework.batch.core.launch.JobExecutionNotRunningException:
         *      JobExecution must be running so that it can be stopped: JobExecution: id=17 ...
         * 在 messageJob 中添加延时操作
         * ----
         * 没有正常停止任务，因为要执行完 jobLauncher.run，再执行 jobOperator.stop，
         * 因此需要将 stop 方法转移到监听器。
         */
        //noinspection TestFailedLine
        jobOperator.stop(jobId);
        TimeUnit.SECONDS.sleep(Long.MAX_VALUE);
    }

    /**
     * 测试：通过 jobOperator 获取作业信息
     */
    @SneakyThrows
    @Test
    public void operatorTest() {
        // jobOperator是一个代理对象
        // 【JobOperator】获取对象的实例:class jdk.proxy2.$Proxy33
        System.out.println(MessageFormat.format("【JobOperator】获取对象的实例:{0}", jobOperator.getClass()));
        // 【JobOperator】获取全部作业信息:[]
        System.out.println(MessageFormat.format("【JobOperator】获取全部作业信息:{0}", jobOperator.getJobNames()));
        // 【JobOperator】获取作业的信息:project=muyan-yootk-spring-batch,java.lang.String,true developer=小李老师,java.lang.String,true timestamp=1735555184182,java.lang.Long,true
        System.out.println(MessageFormat.format("【JobOperator】获取作业的信息:{0}", jobOperator.getParameters(6)));
    }

    @SneakyThrows
    @Test
    public void abortExecutionTest() {
        JobParameters jobParameters = new JobParametersBuilder()
                .addString("project", "muyan-yootk-spring-batch")
                .addString("developer", "小李老师")
                .addLong("timestamp", System.currentTimeMillis())
                .toJobParameters();
        jobLauncher.run(messageJson, jobParameters);
        /*
         * 作业已停止
         * org.springframework.batch.core.job.AbstractJob - Full exception
         *      org.springframework.batch.core.JobInterruptedException:
         *          Job interrupted by step execution
         * MessageJobExecutionByAnnotationListener - 【作业执行后Handler】作业状态: STOPPED
         * 停止状态的Job可以重新启动
         */
        TimeUnit.SECONDS.sleep(Long.MAX_VALUE);
    }
}
