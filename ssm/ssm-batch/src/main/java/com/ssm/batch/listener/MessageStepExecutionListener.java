package com.ssm.batch.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.AfterStep;
import org.springframework.batch.core.annotation.BeforeStep;

/**
 * 消息步骤监听器
 * <p>
 *     作业监听要与作业绑定，一个作业包含多个步骤，每个步骤包含有步骤监听。
 * @author caimeng
 * @date 2025/1/2 9:49
 */
@SuppressWarnings("unused")
@Slf4j
public class MessageStepExecutionListener {
    /**
     * 步骤前监听
     * @param stepExecution 作业步骤操作类
     */
    @BeforeStep
    public void beforeStepHandler(StepExecution stepExecution) {
        log.info("【步骤执行前】步骤名称: {}, 步骤状态: {}", stepExecution.getStepName(), stepExecution.getStatus());
    }

    /**
     * 步骤执行后监听
     * @param stepExecution 作业步骤操作类
     * @return 退出状态
     */
    @AfterStep
    public ExitStatus afterStepHandler(StepExecution stepExecution) {
        log.info("【步骤执行后】步骤名称: {}, 步骤状态: {}", stepExecution.getStepName(), stepExecution.getStatus());
        /*
         * 可以根据外部作业步骤的状态确定监听器的状态
         * 也可以直接返回: ExitStatus.COMPLETED
         */
        return stepExecution.getExitStatus();
//        return ExitStatus.COMPLETED;
    }
}
