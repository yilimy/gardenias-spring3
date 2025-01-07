/**
 * 批处理作业
 * <p>
 *     逻辑单元
 *     1. 作业启动
 *     2. 作业参数
 *     3. 对账作业
 *     4. Step
 *     5. ItemReader        数据读入
 *     6. LineMapper        行映射和数据分割（设置lineTokenizer）
 *     7. FieldSetMapper    属性映射
 *     8. ItemProcessor     数据处理
 *     9. ItemWriter        数据写入到数据库
 * <p>
 *     ===  一次完整的批处理流程  ===
 *     在 Spring Batch 中会有大量的逻辑单元，这些逻辑单元如上所示。
 *     如果要接收外部传入的参数，需要在进行Bean定义的时候，使用注解 “@StepScope”(步骤内有效)
 *     {@link com.ssm.batch.config.SpringBatchConfig#billMultiScopeReader(String)}
 *     定义步骤
 *     {@link com.ssm.batch.config.SpringBatchConfig#scopeStep(ItemReader, ItemWriter)}
 *     创建作业
 *     {@link com.ssm.batch.config.SpringBatchConfig#billJob(org.springframework.batch.core.Step)}
 *     测试类: com.ssm.batch.BillJobTest
 * @author caimeng
 * @date 2025/1/7 15:07
 */
package com.ssm.batch;

import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;