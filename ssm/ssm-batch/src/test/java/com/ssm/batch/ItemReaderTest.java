package com.ssm.batch;

import com.ssm.batch.config.SpringBatchConfig;
import com.ssm.batch.vo.Bill;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.MultiResourceItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * 资源读取
 * <p>
 *     单资源读取: {@link SpringBatchConfig#billReader()}
 *     多资源读取: {@link SpringBatchConfig#billMultiReader()}
 * @author caimeng
 * @date 2025/1/3 18:08
 */
@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
@Slf4j
@ContextConfiguration(classes = StartSpringBatchApplication.class)
@ExtendWith(SpringExtension.class)
public class ItemReaderTest {
    @Autowired
    private FlatFileItemReader<Bill> itemReader;
    @Autowired
    private MultiResourceItemReader<Bill> multiReader;

    /**
     * 测试: itemReader
     * <p>
     *    只读一行
     */
    @SneakyThrows
    @Test
    public void splitTest() {
        // 创建执行上下文
        itemReader.open(new ExecutionContext());
        // 读取数据
        Bill bill = itemReader.read();
        // bill = Bill(id=9197276813101, name=李兴华, amount=9820.22, transaction=Sat Oct 11 11:21:21 CST 2025, location=洛阳支行)
        System.out.println("bill = " + bill);
    }

    /**
     * 测试: itemReader
     * <p>
     *    读取全部
     */
    @SneakyThrows
    @Test
    public void readeAllTest() {
        // 创建执行上下文
        itemReader.open(new ExecutionContext());
        // 保存读取数据
        Bill bill;
        while ((bill = itemReader.read()) != null) {
            /*
             * bill = Bill(id=9197276813101, name=李兴华, amount=9820.22, transaction=Sat Oct 11 11:21:21 CST 2025, location=洛阳支行)
             * bill = Bill(id=9197276813101, name=李兴华, amount=1276.19, transaction=Sun Oct 12 16:16:24 CST 2025, location=洛阳支行)
             * bill = Bill(id=9197276813101, name=李兴华, amount=3982.76, transaction=Tue Nov 11 16:21:44 CST 2025, location=北京支行)
             * bill = Bill(id=9197276813101, name=李兴华, amount=-1600.25, transaction=Wed Nov 12 20:33:21 CST 2025, location=北京ATM)
             * bill = Bill(id=9197276813101, name=李兴华, amount=-2983.88, transaction=Thu Nov 13 13:54:31 CST 2025, location=信用卡)
             */
            System.out.println("bill = " + bill);
        }
    }

    /**
     * 测试: 多资源读取
     */
    @SneakyThrows
    @Test
    public void readeAllFilesTest() {
        multiReader.open(new ExecutionContext());
        Bill bill;
        while ((bill = multiReader.read()) != null) {
            /*
             * bill = Bill(id=9197276813101, name=李兴华, amount=9820.22, transaction=Sat Oct 11 11:21:21 CST 2025, location=洛阳支行)
             * bill = Bill(id=9197276813101, name=李兴华, amount=1276.19, transaction=Sun Oct 12 16:16:24 CST 2025, location=洛阳支行)
             * bill = Bill(id=9197276813101, name=李兴华, amount=3982.76, transaction=Tue Nov 11 16:21:44 CST 2025, location=北京支行)
             * bill = Bill(id=9197276813101, name=李兴华, amount=-1600.25, transaction=Wed Nov 12 20:33:21 CST 2025, location=北京ATM)
             * bill = Bill(id=9197276813101, name=李兴华, amount=-2983.88, transaction=Thu Nov 13 13:54:31 CST 2025, location=信用卡)
             *
             * bill = Bill(id=29197276813101, name=李兴华, amount=9820.22, transaction=Sat Oct 11 11:21:21 CST 2025, location=洛阳支行)
             * bill = Bill(id=29197276813101, name=李兴华, amount=1276.19, transaction=Sun Oct 12 16:16:24 CST 2025, location=洛阳支行)
             * bill = Bill(id=29197276813101, name=李兴华, amount=3982.76, transaction=Tue Nov 11 16:21:44 CST 2025, location=北京支行)
             * bill = Bill(id=29197276813101, name=李兴华, amount=-1600.25, transaction=Wed Nov 12 20:33:21 CST 2025, location=北京ATM)
             * bill = Bill(id=29197276813101, name=李兴华, amount=-2983.88, transaction=Thu Nov 13 13:54:31 CST 2025, location=信用卡)
             */
            System.out.println("bill = " + bill);
        }
    }
}
