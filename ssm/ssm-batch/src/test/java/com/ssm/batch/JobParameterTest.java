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
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @author caimeng
 * @date 2024/12/27 17:07
 */
@Slf4j
@ContextConfiguration(classes = StartSpringBatchApplication.class)
@ExtendWith(SpringExtension.class)
public class JobParameterTest {
    /**
     * 定义作业
     */
    @Autowired
    private Job messageJson;
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private JobLauncher jobLauncher;

    @SneakyThrows
    @Test
    public void jobParameterTest() {
        /*
         * 直接使用有参构造器
         * {@link org.springframework.batch.core.JobParameters.JobParameters(java.util.Map<java.lang.String,org.springframework.batch.core.JobParameter<?>>)}
         * 进行 JobParameters 对象的构建很麻烦，
         * 使用 JobParametersBuilder 来构建就简单多了。
         */
        JobParameters jobParameters = new JobParametersBuilder()
                // 项目参数
                .addString("project", "muyan-yootk-spring-batch")
                .addString("dataResource", "file:d:/data")
                .addString("developer", "小李老师")
                .addLong("timestamp", System.currentTimeMillis())
                .addDate("createDate", new Date())
                // 构建作业参数
                .toJobParameters();
        jobLauncher.run(messageJson, jobParameters);
        // 等待作业完毕
        TimeUnit.SECONDS.sleep(Long.MAX_VALUE);
        /*
         * select * from batch_job_instance;
         * 作业名已经出现重复: messageJob
         * select * from batch_job_execution_params;
         * 2 project java.lang.String muyan-yootk-spring-batch Y
         * 可以在随后的作业处理中获取到此参数，以达到动态批处理操作的目的
         */
    }
}
