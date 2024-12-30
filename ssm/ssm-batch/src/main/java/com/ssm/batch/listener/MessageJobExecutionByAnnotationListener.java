package com.ssm.batch.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.annotation.AfterJob;
import org.springframework.batch.core.annotation.BeforeJob;

/**
 * 使用注解的方式实现监听
 * <p>
 *     比较: 实现类的方式 {@link MessageJobExecutionListener}
 * @author caimeng
 * @date 2024/12/30 18:38
 */
@SuppressWarnings("unused")
@Slf4j
public class MessageJobExecutionByAnnotationListener {
    /**
     * 使用注解的方式实现“作业前”
     * @param jobExecution 作业执行器
     */
    @BeforeJob
    public void beforeJobHandler(JobExecution jobExecution) {
        log.info("【作业执行前Handler】作业ID: {}, 作业名称: {}",
                jobExecution.getJobId(), jobExecution.getJobInstance().getJobName());
        log.info("【作业执行前Handler】作业参数: {}", jobExecution.getJobParameters());
    }

    /**
     * 使用注解的方式实现“作业后”
     * @param jobExecution 作业执行器
     */
    @AfterJob
    public void afterJobHandler(JobExecution jobExecution) {
        // 【作业执行后Handler】作业状态: COMPLETED
        log.info("【作业执行后Handler】作业状态: {}", jobExecution.getStatus());
    }
}
