package com.gardenia.web.lombok;

import org.junit.jupiter.api.Test;

import java.util.concurrent.CompletableFuture;
import java.util.stream.IntStream;

/**
 * @author caimeng
 * @date 2025/1/22 17:44
 */
public class SaleTicketTest {
    @Test
    public void syncTest() {
        long start = System.currentTimeMillis();
        SaleTicket st = new SaleTicket(20);
        CompletableFuture<Void>[] completableFutures = IntStream.range(0, 10)
                .mapToObj(i -> CompletableFuture.runAsync(st::sale))
                .<CompletableFuture<Void>>toArray(CompletableFuture[]::new);
        CompletableFuture.allOf(completableFutures).join();
        long end = System.currentTimeMillis();
        System.out.println("【异步任务执行完成】耗时时间" + (end - start) + "ms");
    }
}
