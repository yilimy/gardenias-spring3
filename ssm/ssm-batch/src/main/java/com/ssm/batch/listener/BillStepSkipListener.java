package com.ssm.batch.listener;

import com.ssm.batch.vo.Account;
import com.ssm.batch.vo.Bill;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.SkipListener;
import org.springframework.lang.NonNull;

/**
 * 异常跳过的监听
 * @author caimeng
 * @date 2025/1/8 11:10
 */
@Slf4j
public class BillStepSkipListener implements SkipListener<Bill, Account> {

    @Override
    public void onSkipInRead(@NonNull Throwable t) {
        // 11:30:31.029 [main] INFO com.ssm.batch.listener.BillStepSkipListener - 【SkipInRead】数据读取错误, 异常信息: Parsing error at line: 6 in resource=[file [D:\Gardenias\SpringBoot3Demo\ssm\ssm-batch\target\classes\data\bill.txt]], input=[9197276813101,李兴华,xxx2983,2025-11-14 14:54:31,信用卡]
        log.info("【SkipInRead】数据读取错误, 异常信息: {}", t.getMessage());
    }

    @Override
    public void onSkipInWrite(@NonNull Account item, @NonNull Throwable t) {
        log.info("【SkipInWrite】数据写入错误, 数据项: {}, 异常信息: {}", item, t.getMessage());
    }

    @Override
    public void onSkipInProcess(@NonNull Bill item, @NonNull Throwable t) {
        log.info("【SkipInProcess】数据处理错误, 数据项: {}, 异常信息: {}", item, t.getMessage());
    }
}
