package com.ssm.batch;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.concurrent.TimeUnit;

/**
 * 批处理测试：对账作业
 * @author caimeng
 * @date 2025/1/7 16:08
 */
@Slf4j
@ContextConfiguration(classes = StartSpringBatchApplication.class)
@ExtendWith(SpringExtension.class)
public class BillJobTest {
    // 定义作业
    @Autowired
    @Qualifier("billJob")
    private Job billJob;
    // 定义作业执行器
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private JobLauncher jobLauncher;

    /**
     * 测试：对账作业
     */
    @SneakyThrows
    @Test
    public void billTest() {
        JobParameters jobParameters = new JobParametersBuilder()
                // 项目参数
                .addString("project", "muyan-yootk-spring-batch")
                .addString("developer", "小李老师")
                .addLong("timestamp", System.currentTimeMillis())
                .addString("path", "classpath:data/*.txt")
                // 构建作业参数
                .toJobParameters();
        jobLauncher.run(billJob, jobParameters);
        // 等待作业完毕
        TimeUnit.SECONDS.sleep(Long.MAX_VALUE);
    }
}
