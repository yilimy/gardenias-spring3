package com.ssm.batch.processor;

import com.ssm.batch.vo.Account;
import com.ssm.batch.vo.Bill;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.support.CompositeItemProcessor;
import org.springframework.batch.item.support.ScriptItemProcessor;
import org.springframework.lang.NonNull;

/**
 * 转换器 ItemProcessor
 * <p>
 *     自带的 ItemProcessor 处理类:
 *     {@link CompositeItemProcessor} 压缩处理
 *     {@link ScriptItemProcessor} JS脚本处理
 * @author caimeng
 * @date 2025/1/7 9:42
 */
public class BillToAccountItemProcessor implements ItemProcessor<Bill, Account> {   // I : Bill,输入; O : Account,输出.
    @Override
    public Account process(@NonNull Bill item) {
        Account account = new Account();
        account.setId(item.getId());
        account.setAmount(item.getAmount());
        return account;
    }
}
