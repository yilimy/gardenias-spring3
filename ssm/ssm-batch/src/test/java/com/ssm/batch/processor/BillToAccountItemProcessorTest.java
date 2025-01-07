package com.ssm.batch.processor;

import com.ssm.batch.StartSpringBatchApplication;
import com.ssm.batch.vo.Account;
import com.ssm.batch.vo.Bill;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.file.MultiResourceItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.text.MessageFormat;

/**
 * @author caimeng
 * @date 2025/1/7 9:48
 */
@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
@Slf4j
@ContextConfiguration(classes = StartSpringBatchApplication.class)
@ExtendWith(SpringExtension.class)
public class BillToAccountItemProcessorTest {
    @Autowired
    private ItemProcessor<Bill, Account> itemProcessor;
    @Autowired
    private MultiResourceItemReader<Bill> multiReader;

    @SneakyThrows
    @Test
    public void processTest() {
        multiReader.open(new ExecutionContext());
        Bill bill;
        while ((bill = multiReader.read()) != null) {
            Account account = itemProcessor.process(bill);
            if (account != null) {
                /*
                 * 【账户信息】账户ID: 9,197,276,813,101, 金额: 9,820.22
                 * 【账户信息】账户ID: 9,197,276,813,101, 金额: 1,276.19
                 * 【账户信息】账户ID: 9,197,276,813,101, 金额: 3,982.76
                 * 【账户信息】账户ID: 9,197,276,813,101, 金额: -1,600.25
                 * 【账户信息】账户ID: 9,197,276,813,101, 金额: -2,983.88
                 * 【账户信息】账户ID: 29,197,276,813,101, 金额: 9,820.22
                 * 【账户信息】账户ID: 29,197,276,813,101, 金额: 1,276.19
                 * 【账户信息】账户ID: 29,197,276,813,101, 金额: 3,982.76
                 * 【账户信息】账户ID: 29,197,276,813,101, 金额: -1,600.25
                 * 【账户信息】账户ID: 29,197,276,813,101, 金额: -2,983.88
                 */
                System.out.println(MessageFormat.format("【账户信息】账户ID: {0}, 金额: {1}", account.getId(), account.getAmount()));
            }
        }
    }
}
