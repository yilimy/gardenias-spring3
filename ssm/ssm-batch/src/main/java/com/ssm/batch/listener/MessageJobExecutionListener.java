package com.ssm.batch.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.lang.NonNull;

/**
 * 作业监听器
 * @author caimeng
 * @date 2024/12/30 18:21
 */
@Slf4j
public class MessageJobExecutionListener implements JobExecutionListener {

    @Override
    public void beforeJob(@NonNull JobExecution jobExecution) {
        // 【作业执行前】作业ID: 5, 作业名称: messageJob
        log.info("【作业执行前】作业ID: {}, 作业名称: {}",
                jobExecution.getJobId(), jobExecution.getJobInstance().getJobName());
        // 【作业执行前】作业参数: {'project':'{value=muyan-yootk-spring-batch, type=class java.lang.String, identifying=true}','developer':'{value=小李老师, type=class java.lang.String, identifying=true}','timestamp':'{value=1735554716747, type=class java.lang.Long, identifying=true}'}
        log.info("【作业执行前】作业参数: {}", jobExecution.getJobParameters());
    }

    @Override
    public void afterJob(@NonNull JobExecution jobExecution) {
        // 【作业执行后】作业状态: COMPLETED
        log.info("【作业执行后】作业状态: {}", jobExecution.getStatus());
    }

}
