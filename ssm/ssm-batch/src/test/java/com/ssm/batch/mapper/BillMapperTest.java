package com.ssm.batch.mapper;

import com.ssm.batch.StartSpringBatchApplication;
import com.ssm.batch.config.SpringBatchConfig;
import com.ssm.batch.vo.Bill;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * 测试: FieldSetMapper
 * <p>
 *     数据映射步骤:
 *     1. 创建数据实体
 *          {@link Bill}
 *     2. 创建和注入映射器
 *          {@link BillMapper}
 *          {@link SpringBatchConfig#billMapper()}
 *     3. 创建和注入数据行映射
 *          {@link SpringBatchConfig#lineMapper()}
 *     4. 应用
 *          lineMapper.mapLine(lineData, 0)
 * @author caimeng
 * @date 2025/1/3 17:35
 */
@Slf4j
@ContextConfiguration(classes = StartSpringBatchApplication.class)
@ExtendWith(SpringExtension.class)
public class BillMapperTest {
    @Autowired
    private LineMapper<Bill> lineMapper;

    @SneakyThrows
    @Test
    public void splitTest() {
        // 含有错误数据
        String lineData = "9197276813101xxx,李兴华,9820.22a,20252-10-11 11:21:21,洛阳支行";
        Bill bill = lineMapper.mapLine(lineData, 0);
        // bill = Bill(id=9197276813101, name=李兴华, amount=9820.22, transaction=Mon Oct 11 11:21:21 CST 20252, location=洛阳支行)
        System.out.println("bill = " + bill);
    }
}
