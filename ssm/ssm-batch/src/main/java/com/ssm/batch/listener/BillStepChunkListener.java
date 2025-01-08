package com.ssm.batch.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ChunkListener;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.lang.NonNull;

/**
 * @author caimeng
 * @date 2025/1/8 10:21
 */
@Slf4j
public class BillStepChunkListener implements ChunkListener {
    @Override
    public void beforeChunk(@NonNull ChunkContext context) {
        /*
         * Chunk里面会包含有 ItemReader、ItemProcessor、ItemWriter
         * ChunkContext配置的内容一定可以在这些单元中获取
         */
        context.setAttribute("book", "《SSM开发实战》");
        /*
         * 10:47:24.281 [main] INFO com.ssm.batch.listener.BillStepChunkListener - 【Chunk监听 - beforeChunk】步骤名称:billStep, 完成状态: false
         * ...
         * 10:47:25.803 [main] INFO com.ssm.batch.listener.BillStepChunkListener - 【Chunk监听 - beforeChunk】步骤名称:billStep, 完成状态: false
         * ...
         * 10:47:26.205 [main] INFO com.ssm.batch.listener.BillStepChunkListener - 【Chunk监听 - beforeChunk】步骤名称:billStep, 完成状态: false
         * ...
         * 10:47:26.582 [main] INFO com.ssm.batch.listener.BillStepChunkListener - 【Chunk监听 - beforeChunk】步骤名称:billStep, 完成状态: false
         */
        log.info("【Chunk监听 - beforeChunk】步骤名称:{}, 完成状态: {}",
                context.getStepContext().getStepName(), context.isComplete());
    }

    @Override
    public void afterChunk(@NonNull ChunkContext context) {
        /*
         * 10:47:25.764 [main] INFO com.ssm.batch.listener.BillStepChunkListener - 【Chunk监听 - afterChunk】步骤名称:billStep, 完成状态: true
         * ...
         * 10:47:26.177 [main] INFO com.ssm.batch.listener.BillStepChunkListener - 【Chunk监听 - afterChunk】步骤名称:billStep, 完成状态: true
         * ...
         * 10:47:26.557 [main] INFO com.ssm.batch.listener.BillStepChunkListener - 【Chunk监听 - afterChunk】步骤名称:billStep, 完成状态: true
         * ...
         * 10:47:26.791 [main] INFO com.ssm.batch.listener.BillStepChunkListener - 【Chunk监听 - afterChunk】步骤名称:billStep, 完成状态: true
         */
        log.info("【Chunk监听 - afterChunk】步骤名称:{}, 完成状态: {}",
                context.getStepContext().getStepName(), context.isComplete());
    }

    @Override
    public void afterChunkError(@NonNull ChunkContext context) {
        log.error("【Chunk监听 - afterChunkError】步骤名称:{}, 完成状态: {}",
                context.getStepContext().getStepName(), context.isComplete());
    }
}
