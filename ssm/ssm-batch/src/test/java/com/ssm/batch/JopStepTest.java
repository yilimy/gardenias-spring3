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
 * 测试：作业步骤
 * @author caimeng
 * @date 2024/12/31 15:51
 */
@Slf4j
@ContextConfiguration(classes = StartSpringBatchApplication.class)
@ExtendWith(SpringExtension.class)
public class JopStepTest {
    @Autowired
    private Job job;
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private JobLauncher jobLauncher;

    /**
     * 测试：步骤
     */
    @SneakyThrows
    @Test
    public void stepTest() {
        JobParameters jobParameters = new JobParametersBuilder()
                .addString("project", "muyan-yootk-spring-batch")
                .addString("dataResource", "file:d:/data")
                .addString("developer", "小李老师")
                .addLong("timestamp", System.currentTimeMillis())
                .addDate("createDate", new Date())
                .toJobParameters();
        /*
         * 【Step-0,消息准备步骤】初始化系统环境，连接服务器接口等。
         * ...
         * 【Step-1,消息读取步骤】通过输入源，读取消息内容
         * ...
         * 【Step-2,消息处理步骤】检索出包含有yootk的数据内容
         * ...
         * 【Step-3,消息写入步骤】将合法的消息写入数据终端
         * ...
         */
        jobLauncher.run(job, jobParameters);
        TimeUnit.SECONDS.sleep(Long.MAX_VALUE);
    }
}
