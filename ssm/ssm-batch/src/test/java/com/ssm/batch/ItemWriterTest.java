package com.ssm.batch;

import com.ssm.batch.vo.Account;
import com.ssm.batch.vo.Bill;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.MultiResourceItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

/**
 * @author caimeng
 * @date 2025/1/7 11:26
 */
@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
@Slf4j
@ContextConfiguration(classes = StartSpringBatchApplication.class)
@ExtendWith(SpringExtension.class)
public class ItemWriterTest {
    // 资源读取
    @Autowired
    private MultiResourceItemReader<Bill> multiReader;
    // 资源处理
    @Autowired
    private ItemProcessor<Bill, Account> itemProcessor;
    // 数据输出
    @Autowired
    private ItemWriter<Account> itemWriter;

    @SneakyThrows
    @Test
    public void writeTest() {
        List<Account> accounts = new ArrayList<>();
        multiReader.open(new ExecutionContext());
        Bill bill;
        while ((bill = multiReader.read()) != null) {
            Account account = itemProcessor.process(bill);
            if (account != null) {
                accounts.add(account);
            }
        }
        itemWriter.write(new Chunk<>(accounts));
    }
}
