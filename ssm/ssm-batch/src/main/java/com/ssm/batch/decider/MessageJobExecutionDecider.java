package com.ssm.batch.decider;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.job.flow.FlowExecutionStatus;
import org.springframework.batch.core.job.flow.JobExecutionDecider;
import org.springframework.lang.NonNull;

/**
 * 决策器
 * <p>
 *     现在所配置的作业Step或者Flow，多是按照配置的顺序依次执行的，
 *     但是这时候会有新的问题：如果因为某些逻辑出现了变更，导致某些步骤或者Flow不执行，该怎么办？
 *     此时就需要为程序追加一个分支功能，
 *     由此诞生了决策器。
 * @author caimeng
 * @date 2025/1/2 11:34
 */
public class MessageJobExecutionDecider implements JobExecutionDecider {
    /*
     * 计数标记
     * 整个Spring容器中只会有一个 MessageJobExecutionDecider 对象实例，
     * count值也会持续累加，持续进行判断。
     */
    private int count = 0;
    @SuppressWarnings("NullableProblems")
    @Override
    public FlowExecutionStatus decide(@NonNull JobExecution jobExecution, StepExecution stepExecution) {
        if (count ++ % 2 == 0) {
            // 返回自定义的执行名称
            return new FlowExecutionStatus("Handler");
        }
        return new FlowExecutionStatus("Write");
    }
}
