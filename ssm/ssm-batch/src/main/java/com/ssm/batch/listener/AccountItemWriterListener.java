package com.ssm.batch.listener;

import com.ssm.batch.vo.Account;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ItemWriteListener;
import org.springframework.batch.item.Chunk;
import org.springframework.lang.NonNull;

/**
 * 数据写入监听
 * @author caimeng
 * @date 2025/1/7 16:42
 */
@Slf4j
public class AccountItemWriterListener implements ItemWriteListener<Account> {  // 数据的写入监听

    @Override
    public void beforeWrite(@NonNull Chunk<? extends Account> items) {
        /*
         * 16:50:32.027 [main] INFO com.ssm.batch.listener.AccountItemWriterListener - 【数据写入监听】数据写入的长度: 5
         * ...
         * 16:50:34.508 [main] INFO com.ssm.batch.listener.AccountItemWriterListener - 【数据写入监听】数据写入的长度: 5
         */
        log.info("【数据写入监听】数据写入的长度: {}", items.size());
        /*
         * 将chunk(3)替换成成完成策略.chunk(completionPolicy())后
         * 17:17:17.674 [main] INFO com.ssm.batch.listener.AccountItemWriterListener - 【数据写入监听】数据写入的长度: 3
         * 17:17:18.414 [main] INFO com.ssm.batch.listener.AccountItemWriterListener - 【数据写入监听】数据写入的长度: 3
         * 17:17:18.828 [main] INFO com.ssm.batch.listener.AccountItemWriterListener - 【数据写入监听】数据写入的长度: 3
         * 17:17:19.218 [main] INFO com.ssm.batch.listener.AccountItemWriterListener - 【数据写入监听】数据写入的长度: 1
         */
    }
}
