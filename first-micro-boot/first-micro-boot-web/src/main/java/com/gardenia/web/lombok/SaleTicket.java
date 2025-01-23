package com.gardenia.web.lombok;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.SneakyThrows;
import lombok.Synchronized;

import java.text.MessageFormat;
import java.util.concurrent.TimeUnit;

/**
 * @author caimeng
 * @date 2025/1/22 17:20
 */
@Data
@AllArgsConstructor
public class SaleTicket {
    /**
     * 票数
     */
    private int ticket;
    /**
     * 售票
     * <p>
     *     Synchronized上同步锁，这个同步有死锁的安全隐患，使用的时候慎用。
     * </p>
     *
     * ``` java
     *      private final Object $lock = new Object[0];
     *      public void sale() {
     *          try {
     *              synchronized(this.$lock) {...}
     *          } catch (Throwable var4) {
     *              throw var4;
     *          }
     *      }
     * ```
     */
    @Synchronized   // 同步处理
    @SneakyThrows
    public void sale() {
        while (ticket > 0) {    // 此时有票
            TimeUnit.SECONDS.sleep(1);
            System.out.println(MessageFormat.format("【{0}】售票, ticket = {1}",
                    Thread.currentThread().getName(), ticket --));
        }
    }
}
