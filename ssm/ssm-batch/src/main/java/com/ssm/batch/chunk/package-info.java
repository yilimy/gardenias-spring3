/**
 * 关于chunk
 * {@link org.springframework.batch.item.Chunk}
 * <p>
 *     在进行数据批处理的时候，需要有一个完整的数据操作，
 *     但是这个操作面对大批量数据的过程中，往往是需要分块进行的，
 *     例如：每50条的数据执行一次完整的"输入-处理-输出"操作，
 *     在这种情况下就可以利用chunk解决。
 * <p>
 *     既然chunk描述的是一套完整的数据操作逻辑，那么在整个处理逻辑里面就可以有跳过策略、错误重试策略以及事务处理策略。
 *     在新版本里，事务处理已经被提前了。
 *     通过 {@link org.springframework.batch.core.step.builder.SimpleStepBuilder} 进行配置。
 *     完成策略: {@link org.springframework.batch.core.step.builder.SimpleStepBuilder#chunk(CompletionPolicy)}
 * <p>
 *     配置完成策略
 *          {@link com.ssm.batch.config.SpringBatchConfig#completionPolicy()}
 *     完成策略在步骤中配置
 *          {@link com.ssm.batch.config.SpringBatchConfig#scopeStep(ItemReader, ItemWriter)}
 *     配置了Chunk能进行各类容错功能的配置处理。
 * @author caimeng
 * @date 2025/1/7 16:59
 */
package com.ssm.batch.chunk;

import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.repeat.CompletionPolicy;