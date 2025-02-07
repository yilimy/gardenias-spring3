package com.ssm.batch.tasklet;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.lang.NonNull;

import java.util.concurrent.TimeUnit;

/**
 * 创建一个批处理的任务
 * <p>
 *     在一个批处理的作业中，最顶层有两个核心接口：
 *          - Job（作业）
 *          - step（步骤）
 *     一个完整的作业内部会包含有多个处理的步骤
 *          - 第一步：进行数据读取
 *          - 第二步：进行数据检测
 *          - 第三步：
 *          - 第四步：
 * <p>
 *     在 Spring Batch 里，最为重要的结构是作业，作业中是由一个个具体的步骤处理的，每一个步骤由 Tasklet 定义具体的代码逻辑
 * @author caimeng
 * @date 2024/12/27 14:18
 */
@Slf4j
public class MessageTasklet implements Tasklet {
    @SneakyThrows
    @Override
    public RepeatStatus execute(@NonNull StepContribution stepContribution,
                                @NonNull ChunkContext chunkContext) {
        // 为了测试任务的停止操作，添加延时代码
        for (int i = 0; i < 10; i++) {
            log.info("【数据批处理操作】沐言科技 - 李兴华Java高薪就业编程训练营");
            TimeUnit.SECONDS.sleep(1);
        }
        /*
         * 每一个步骤都会包含一个完整的执行状态，这个状态通过RepeatStatus表示
         */
        return RepeatStatus.FINISHED;   // 处理结束
    }
}
