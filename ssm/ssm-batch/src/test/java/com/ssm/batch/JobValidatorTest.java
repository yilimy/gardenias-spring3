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
 * @date 2024/12/30 16:58
 */
@Slf4j
@ContextConfiguration(classes = StartSpringBatchApplication.class)
@ExtendWith(SpringExtension.class)
public class JobValidatorTest {
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
    public void jobParameterValidatorFailedTest() {
        /*
         * 参数验证器生效了
         * org.springframework.batch.core.JobParametersInvalidException:
         *      The JobParameters contains keys that are not explicitly optional or required: [dataResource, createDate]
         * 此时传递的参数过多，所以不满足作业参数校验的要求格式，无法进行批处理作业。
         */
        JobParameters jobParameters = new JobParametersBuilder()
                .addString("project", "muyan-yootk-spring-batch")
                .addString("dataResource", "file:d:/data")
                .addString("developer", "小李老师")
                .addLong("timestamp", System.currentTimeMillis())
                .addDate("createDate", new Date())
                .toJobParameters();
        //noinspection TestFailedLine
        jobLauncher.run(messageJson, jobParameters);
        TimeUnit.SECONDS.sleep(Long.MAX_VALUE);
    }

    @SneakyThrows
    @Test
    public void jobParameterValidatorSuccessTest() {
        JobParameters jobParameters = new JobParametersBuilder()
                .addString("project", "muyan-yootk-spring-batch")
                .addString("developer", "小李老师")
                .addLong("timestamp", System.currentTimeMillis())
                .toJobParameters();
        jobLauncher.run(messageJson, jobParameters);
        TimeUnit.SECONDS.sleep(Long.MAX_VALUE);
    }
}
