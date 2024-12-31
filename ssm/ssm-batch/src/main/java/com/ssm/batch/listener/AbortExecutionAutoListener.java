package com.ssm.batch.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.annotation.BeforeJob;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

/**
 * 中断作业执行的监听
 * <p>
 *     自动注入 JobOperator 代理对象
 * @author caimeng
 * @date 2024/12/31 9:53
 */
@Slf4j
public class AbortExecutionAutoListener {
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private JobOperator jobOperator;

    @SuppressWarnings("unused")
    @BeforeJob      // 作业执行之前处理
    public void beforeJobHandler(JobExecution jobExecution) {
        // 只有运行中的作业，才运行中断
        if (jobExecution.isRunning()) {
            Optional.of(jobExecution)
                    .map(JobExecution::getJobParameters)
                    .map(p -> p.getString("project"))
                    .filter(s -> s.contains("yootk"))
                    .ifPresent(s -> {
                        try {
                            jobOperator.stop(jobExecution.getId());
                        } catch (Exception e) {
                            log.error("任务停止失败", e);
                        }
                    });
        }
    }
}
