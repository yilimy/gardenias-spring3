package com.ssm.batch;

import com.ssm.batch.config.SpringBatchConfig;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobInstance;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.text.MessageFormat;
import java.util.List;

/**
 * 测试：作业浏览器
 * @author caimeng
 * @date 2024/12/27 18:30
 */
@Slf4j
@ContextConfiguration(classes = StartSpringBatchApplication.class)
@ExtendWith(SpringExtension.class)
public class JobExplorerTest {
    /**
     * @see org.springframework.batch.core.configuration.annotation.EnableBatchProcessing
     * @see org.springframework.batch.core.configuration.annotation.BatchRegistrar#registerJobExplorer(BeanDefinitionRegistry, EnableBatchProcessing, String)
     */
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private JobExplorer jobExplorer;
    @SneakyThrows
    @Test
    public void jobRunTest() {
        // 获取作业名称列表
        List<String> jobNames = jobExplorer.getJobNames();
        System.out.println("【作业信息列表】----------");
        // messageJob
        jobNames.forEach(System.out::println);
        System.out.println("-".repeat(30));
        long count = jobExplorer.getJobInstanceCount(SpringBatchConfig.JOB_NAME);
        // 【作业信息】作业数量: 2
        System.out.println("【作业信息】作业数量: " + count);
        System.out.println("-".repeat(30));
        JobInstance lastJobInstance = jobExplorer.getLastJobInstance(SpringBatchConfig.JOB_NAME);
        // 【作业信息】最后一次执行作业的ID: 2, 作业名称: messageJob
        //noinspection DataFlowIssue
        System.out.println(MessageFormat.format("【作业信息】最后一次执行作业的ID: {0}, 作业名称: {1}",
                lastJobInstance.getInstanceId(), lastJobInstance.getJobName()));
        System.out.println("-".repeat(30));
        // 获取作业的执行信息
        List<JobExecution> jobExecutions = jobExplorer.getJobExecutions(lastJobInstance);
        jobExecutions.forEach(item ->
                // 【作业信息】作业状态: COMPLETED, 作业参数: {'project':'{value=muyan-yootk-spring-batch, type=class java.lang.String, identifying=true}','developer':'{value=小李老师, type=class java.lang.String, identifying=true}','dataResource':'{value=file:d:/data, type=class java.lang.String, identifying=true}','timestamp':'{value=1735291127667, type=class java.lang.Long, identifying=true}','createDate':'{value=Sat Dec 28 07:18:47 CST 2024, type=class java.util.Date, identifying=true}'}
                System.out.println(MessageFormat.format("【作业信息】作业状态: {0}, 作业参数: {1}",
                        item.getStatus(), item.getJobParameters()))
        );
    }
}
