package com.ssm.batch;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.concurrent.TimeUnit;

/**
 * @author caimeng
 * @date 2024/12/27 15:46
 */
@Slf4j
@ContextConfiguration(classes = StartSpringBatchApplication.class)
@ExtendWith(SpringExtension.class)
public class SpringBatchStartTest {
    /**
     * 定义作业
     */
    @Autowired
    private Job messageJson;
    /**
     * 定义作业执行器
     * {@link org.springframework.batch.core.configuration.annotation.EnableBatchProcessing}
     * {@link org.springframework.batch.core.configuration.annotation.BatchRegistrar#registerBeanDefinitions(AnnotationMetadata, BeanDefinitionRegistry)}
     */
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private JobLauncher jobLauncher;

    /**
     * 测试：作业启动
     */
    @SneakyThrows
    @Test
    public void jobRunTest() {
        /*
         * 作业运行
         * Executing prepared SQL statement [SELECT JOB_INSTANCE_ID, JOB_NAME from BATCH_JOB_INSTANCE where JOB_NAME = ? and JOB_KEY = ?]
         * ...
         * Executing prepared SQL statement [SELECT JOB_INSTANCE_ID, JOB_NAME from BATCH_JOB_INSTANCE where JOB_NAME = ? and JOB_KEY = ?]
         * ...
         * Executing prepared SQL statement [SELECT JOB_INSTANCE_ID, JOB_NAME from BATCH_JOB_INSTANCE where JOB_NAME = ? and JOB_KEY = ?]
         * ...
         * Executing prepared SQL statement [INSERT into BATCH_JOB_INSTANCE(JOB_INSTANCE_ID, JOB_NAME, JOB_KEY, VERSION) values (?, ?, ?, ?)]
         * ...
         * Executing prepared SQL statement [INSERT into BATCH_JOB_EXECUTION(JOB_EXECUTION_ID, JOB_INSTANCE_ID, START_TIME, END_TIME, STATUS, EXIT_CODE, EXIT_MESSAGE, VERSION, CREATE_TIME, LAST_UPDATED) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)]
         * ...
         * Executing prepared SQL statement [INSERT INTO BATCH_JOB_EXECUTION_CONTEXT (SHORT_CONTEXT, SERIALIZED_CONTEXT, JOB_EXECUTION_ID) VALUES(?, ?, ?)]
         * ...
         * Executing prepared SQL statement [SELECT VERSION FROM BATCH_JOB_EXECUTION WHERE JOB_EXECUTION_ID=?]
         * ...
         * Executing prepared SQL statement [SELECT COUNT(*) FROM BATCH_JOB_EXECUTION WHERE JOB_EXECUTION_ID = ?]
         * ...
         * Executing prepared SQL statement [UPDATE BATCH_JOB_EXECUTION set START_TIME = ?, END_TIME = ?,  STATUS = ?, EXIT_CODE = ?, EXIT_MESSAGE = ?, VERSION = ?, CREATE_TIME = ?, LAST_UPDATED = ? where JOB_EXECUTION_ID = ? and VERSION = ?]
         * ...
         * Executing prepared SQL statement [SELECT  SE.STEP_EXECUTION_ID, SE.STEP_NAME, SE.START_TIME, SE.END_TIME, SE.STATUS, SE.COMMIT_COUNT, SE.READ_COUNT, SE.FILTER_COUNT, SE.WRITE_COUNT, SE.EXIT_CODE, SE.EXIT_MESSAGE, SE.READ_SKIP_COUNT, SE.WRITE_SKIP_COUNT, SE.PROCESS_SKIP_COUNT, SE.ROLLBACK_COUNT, SE.LAST_UPDATED, SE.VERSION, SE.CREATE_TIME, JE.JOB_EXECUTION_ID, JE.START_TIME, JE.END_TIME, JE.STATUS, JE.EXIT_CODE, JE.EXIT_MESSAGE, JE.CREATE_TIME, JE.LAST_UPDATED, JE.VERSION from BATCH_JOB_EXECUTION JE join BATCH_STEP_EXECUTION SE      on SE.JOB_EXECUTION_ID = JE.JOB_EXECUTION_ID where JE.JOB_INSTANCE_ID = ?      and SE.STEP_NAME = ? order by SE.CREATE_TIME desc, SE.STEP_EXECUTION_ID desc]
         * ...
         * Executing prepared SQL statement [SELECT COUNT(*)  from BATCH_JOB_EXECUTION JE JOIN BATCH_STEP_EXECUTION SE       on SE.JOB_EXECUTION_ID = JE.JOB_EXECUTION_ID where JE.JOB_INSTANCE_ID = ?      and SE.STEP_NAME = ?]
         * ...
         * Executing prepared SQL statement [INSERT into BATCH_STEP_EXECUTION(STEP_EXECUTION_ID, VERSION, STEP_NAME, JOB_EXECUTION_ID, START_TIME, END_TIME, STATUS, COMMIT_COUNT, READ_COUNT, FILTER_COUNT, WRITE_COUNT, EXIT_CODE, EXIT_MESSAGE, READ_SKIP_COUNT, WRITE_SKIP_COUNT, PROCESS_SKIP_COUNT, ROLLBACK_COUNT, LAST_UPDATED, CREATE_TIME) values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)]
         * ...
         * Executing prepared SQL statement [INSERT INTO BATCH_STEP_EXECUTION_CONTEXT (SHORT_CONTEXT, SERIALIZED_CONTEXT, STEP_EXECUTION_ID) VALUES(?, ?, ?)]
         * ...
         * Executing prepared SQL statement [UPDATE BATCH_STEP_EXECUTION set START_TIME = ?, END_TIME = ?, STATUS = ?, COMMIT_COUNT = ?, READ_COUNT = ?, FILTER_COUNT = ?, WRITE_COUNT = ?, EXIT_CODE = ?, EXIT_MESSAGE = ?, VERSION = ?, READ_SKIP_COUNT = ?, PROCESS_SKIP_COUNT = ?, WRITE_SKIP_COUNT = ?, ROLLBACK_COUNT = ?, LAST_UPDATED = ? where STEP_EXECUTION_ID = ? and VERSION = ?]
         * ...
         * Executing prepared SQL statement [SELECT VERSION FROM BATCH_JOB_EXECUTION WHERE JOB_EXECUTION_ID=?]
         * ...
         * Executing prepared SQL statement [UPDATE BATCH_STEP_EXECUTION_CONTEXT SET SHORT_CONTEXT = ?, SERIALIZED_CONTEXT = ? WHERE STEP_EXECUTION_ID = ?]
         * ...
         * 【数据批处理操作】沐言科技 - 李兴华Java高薪就业编程训练营
         * ...
         * Executing prepared SQL statement [UPDATE BATCH_STEP_EXECUTION_CONTEXT SET SHORT_CONTEXT = ?, SERIALIZED_CONTEXT = ? WHERE STEP_EXECUTION_ID = ?]
         * Saving step execution before commit: StepExecution: id=1, version=1, name=messageStep, status=STARTED, exitStatus=EXECUTING, readCount=0, filterCount=0, writeCount=0 readSkipCount=0, writeSkipCount=0, processSkipCount=0, commitCount=1, rollbackCount=0, exitDescription=
         * ...
         * Executing prepared SQL statement [UPDATE BATCH_STEP_EXECUTION set START_TIME = ?, END_TIME = ?, STATUS = ?, COMMIT_COUNT = ?, READ_COUNT = ?, FILTER_COUNT = ?, WRITE_COUNT = ?, EXIT_CODE = ?, EXIT_MESSAGE = ?, VERSION = ?, READ_SKIP_COUNT = ?, PROCESS_SKIP_COUNT = ?, WRITE_SKIP_COUNT = ?, ROLLBACK_COUNT = ?, LAST_UPDATED = ? where STEP_EXECUTION_ID = ? and VERSION = ?]
         * Executing prepared SQL statement [SELECT VERSION FROM BATCH_JOB_EXECUTION WHERE JOB_EXECUTION_ID=?]
         * ...
         * Executing prepared SQL statement [UPDATE BATCH_STEP_EXECUTION_CONTEXT SET SHORT_CONTEXT = ?, SERIALIZED_CONTEXT = ? WHERE STEP_EXECUTION_ID = ?]
         * ...
         * Executing prepared SQL statement [UPDATE BATCH_STEP_EXECUTION set START_TIME = ?, END_TIME = ?, STATUS = ?, COMMIT_COUNT = ?, READ_COUNT = ?, FILTER_COUNT = ?, WRITE_COUNT = ?, EXIT_CODE = ?, EXIT_MESSAGE = ?, VERSION = ?, READ_SKIP_COUNT = ?, PROCESS_SKIP_COUNT = ?, WRITE_SKIP_COUNT = ?, ROLLBACK_COUNT = ?, LAST_UPDATED = ? where STEP_EXECUTION_ID = ? and VERSION = ?]
         * Executing prepared SQL statement [SELECT VERSION FROM BATCH_JOB_EXECUTION WHERE JOB_EXECUTION_ID=?]
         * ...
         * Executing prepared SQL statement [UPDATE BATCH_JOB_EXECUTION_CONTEXT SET SHORT_CONTEXT = ?, SERIALIZED_CONTEXT = ? WHERE JOB_EXECUTION_ID = ?]
         * ...
         * Upgrading JobExecution status: StepExecution: id=1, version=3, name=messageStep, status=COMPLETED, exitStatus=COMPLETED, readCount=0, filterCount=0, writeCount=0 readSkipCount=0, writeSkipCount=0, processSkipCount=0, commitCount=1, rollbackCount=0, exitDescription=
         * ...
         * Executing prepared SQL statement [SELECT VERSION FROM BATCH_JOB_EXECUTION WHERE JOB_EXECUTION_ID=?]
         * Executing prepared SQL statement [SELECT COUNT(*) FROM BATCH_JOB_EXECUTION WHERE JOB_EXECUTION_ID = ?]
         * Executing prepared SQL statement [UPDATE BATCH_JOB_EXECUTION set START_TIME = ?, END_TIME = ?,  STATUS = ?, EXIT_CODE = ?, EXIT_MESSAGE = ?, VERSION = ?, CREATE_TIME = ?, LAST_UPDATED = ? where JOB_EXECUTION_ID = ? and VERSION = ?]
         *
         */
        jobLauncher.run(messageJson, new JobParameters());
        // 等待作业完毕
        TimeUnit.SECONDS.sleep(Long.MAX_VALUE);
    }
}
