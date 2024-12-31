package com.ssm.batch.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.annotation.BeforeJob;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.launch.support.SimpleJobOperator;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

/**
 * 中断作业执行的监听
 * <p>
 *     不自动注入 JobOperator 代理对象，手工注入替代
 * @author caimeng
 * @date 2024/12/31 9:53
 */
@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
@Slf4j
public class AbortExecutionListener {
    @Autowired
    private JobExplorer jobExplorer;
    @Autowired
    private JobRepository jobRepository;
    @Autowired
    private JobRegistry jobRegistry;

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
                            // 手工设置 JobOperator
                            SimpleJobOperator jobOperator = new SimpleJobOperator();
                            jobOperator.setJobExplorer(jobExplorer);
                            jobOperator.setJobRegistry(jobRegistry);
                            jobOperator.setJobRepository(jobRepository);
                            // 作业暂停
                            jobOperator.stop(jobExecution.getId());
                        } catch (Exception e) {
                            log.error("任务停止失败", e);
                        }
                    });
        }
    }
}
