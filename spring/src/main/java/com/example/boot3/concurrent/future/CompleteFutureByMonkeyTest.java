package com.example.boot3.concurrent.future;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * 测试：CompletableFuture
 * 参考: <a href="https://www.bilibili.com/video/BV1AN411S72m/">Monkey老师的课程： CompletableFuture极简教程</a>
 * <p>
 *     Future: 在java并发中可以获取到异步执行任务的结果
 *     CompletableFuture: 升级版的Future，它实现了 CompletionStage
 *     CompletionStage: 代表着任务的步骤，大任务会拆分成小任务进行执行，可能要对小任务进行编排。CompletableFuture实现了CompletionStage。
 * <p>
 *     {@link CompletableFuture#runAfterEither(CompletionStage, Runnable)}
 *              两个任务中任意执行完成，就执行回调
 *     {@link CompletableFuture#runAfterBoth(CompletionStage, Runnable)}
 *              两个任务都完成，才执行回调
 *     {@link CompletableFuture#getNow(Object)}
 *              立即获得结果，如果任务还没有完成，则返回 valueIfAbsent
 *     {@link CompletableFuture#whenComplete(BiConsumer)}
 *              任务正常结束，第一个参数传的是结果，任务异常结束，第二个参数传入的是异常。没有返回值。
 *     {@link CompletableFuture#exceptionally(Function)}
 *              入参为异常，可返回其他值，如果任务没有出现异常则不会执行
 *     {@link CompletableFuture#complete(Object)}
 *              直接让任务完成
 *     {@link CompletableFuture#cancel(boolean)}
 *              如果任务还没有开启，或正在执行，则能取消，设置取消标记为true
 *              如果任务已经完成，设置取消标记为false
 *     {@link CompletableFuture#allOf(CompletableFuture[])}
 *              所有任务执行完成后才执行下一个任务
 *     {@link CompletableFuture#anyOf(CompletableFuture[])}
 *              任意一个执行完成后，可以执行下一个任务
 * @author caimeng
 * @date 2024/6/3 17:55
 */
@Slf4j
public class CompleteFutureByMonkeyTest {

    /**
     * 测试：异步执行任务，任务有返回值
     */
    @SneakyThrows
    @Test
    public void supplyAsyncTest() {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        /*
         * CompletableFuture 会开启一个异步任务，在线程池 executorService 中执行
         * 如果不传 executorService 则会使用CompletableFuture内部指定的默认线程池(ForkJoinPool.commonPool-worker-1)执行，
         * 但是这样会导致服务内部使用同一个线程池来执行任务，这样不妥，建议还是把线程池隔离开来
         */
        CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(() -> {
            System.out.println(Thread.currentThread().getName());
            return "To chase our dreams that we have declared .";
        }, executorService);
        // pool-1-thread-1
        // To chase our dreams that we have declared .
        System.out.println(completableFuture.get());
    }

    /**
     * 测试：异步执行任务，没有有返回值
     */
    @SneakyThrows
    @Test
    public void runAsyncTest() {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        CompletableFuture<Void> completableFuture = CompletableFuture.runAsync(
                () -> System.out.println(Thread.currentThread().getName()), executorService);
        // pool-1-thread-1
        // null  方法可以调用，但是没有返回值
        System.out.println(completableFuture.get());
    }

    /**
     * 测试：顺序执行异步任务
     */
    @SneakyThrows
    @Test
    public void thenTest() {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        Supplier<String> taskA = () -> {
            System.out.printf("%s : %s\n", Thread.currentThread().getName(), "And the storms we have weathered through");
            return "A";
        };
        Function<String, String> taskB = s -> {
            System.out.printf("%s : %s\n", Thread.currentThread().getName(), "And we hope in the light of truth");
//            sleep(3);
//            System.out.println(s + "B");
            return s + "B";
        };
        CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(taskA, executorService);
        /*
         * 开启休眠后：
         *      pool-1-thread-1 : And the storms we have weathered through
         *      main : And we hope in the light of truth
         *      main : Done
         * 休眠后 completableFuture 仍然持有 taskA 的返回结果
         * 不开启休眠时：
         *      pool-1-thread-1 : And the storms we have weathered through
         *      main : Done
         *      pool-1-thread-1 : And we hope in the light of truth
         * 总结：(completableFuture 的子线程存在就追加，不存在则在主线程中执行)
         *      1. 使用 thenApply 的时候，如果前异步线程已经执行完毕(isDone)，则由主线程执行追加的线程任务
         *      2. 如果主线程执行到 thenApply 的时候，前异步任务仍在执行，则将后线程追加到子线程的线程池去执行
         *      3. 如果是追加到子线程池执行的，主线程执行完毕后，进程会退出，没执行完的子线程也会退出（不会报错？！）
         * thenApply的核心逻辑是: taskB 任务一定保证在 taskA 之后再执行
         */
//        sleep(1);
        boolean done = completableFuture.isDone();
        System.out.println("taskA 执行完毕: " + done);
        /*
         * taskA 的执行结果会作为 taskB 的参数进行传入
         * 所有的then都有个相同的逻辑：保证在上一个任务执行完毕后，再执行下一个任务，但使用哪个线程处理下一个任务，并不保证
         * then*Async   会利用 CompletableFuture 中的共享线程池 ForkJoinPool 来处理后线程
         * 其他的then：
         *      thenAccept  有入参，没有返回值
         *      thenRun     无入参，没有返回值
         */
        completableFuture.thenApply(taskB);
//        sleep(5);
        System.out.printf("%s : %s\n", Thread.currentThread().getName(), "Done");
    }

    /**
     * 测试：thenCompose
     */
    @SneakyThrows
    @Test
    public void thenComposeTest() {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            String msg = "It comes Stride to our Kingdom. ";
            System.out.printf("%s : %s\n", Thread.currentThread().getName(), msg);
            return msg;
        }, executorService);
        /*
         * thenCompose 与 thenApply 的区别是：前者入参是一个 CompletionStage(CompletableFuture), 后者的入参是一个普通方法
         * 该方法会返回一个新的 CompletableFuture, 继续使用 future.get() 会获取首个线程的执行结果
         */
        future.thenCompose(value -> CompletableFuture.supplyAsync(() -> {
            String msg = value + "And see the light of day.";
            System.out.printf("%s : %s\n", Thread.currentThread().getName(), msg);
            sleep(1);
            return msg;
        }));
        sleep(2);
        /*
         * pool-1-thread-1 : It comes Stride to our Kingdom .
         * ForkJoinPool.commonPool-worker-1 : It comes Stride to our Kingdom. And see the light of day.
         * main : It comes Stride to our Kingdom .
         */
        System.out.printf("%s : %s\n", Thread.currentThread().getName(), future.get());
        System.out.println("----------------------------------------");
        // 对照组: 使用一个 CompletableFuture 来接收 thenCompose 的结果
        CompletableFuture<String> futureToCompare = CompletableFuture.supplyAsync(() -> {
            String msg = "It comes Stride to our Kingdom. ";
            System.out.printf("%s : %s\n", Thread.currentThread().getName(), msg);
            return msg;
        }, executorService).thenCompose(value -> CompletableFuture.supplyAsync(() -> {
            String msg = value + "And see the light of day.";
            System.out.printf("%s : %s\n", Thread.currentThread().getName(), msg);
            sleep(1);
            return msg;
        }, executorService));
        sleep(2);
        /*
         * pool-1-thread-2 : It comes Stride to our Kingdom.
         * pool-1-thread-3 : It comes Stride to our Kingdom. And see the light of day.
         * main : It comes Stride to our Kingdom. And see the light of day.
         */
        System.out.printf("%s : %s\n", Thread.currentThread().getName(), futureToCompare.get());
    }

    /**
     * 测试：异步组合测试
     */
    @SneakyThrows
    @Test
    public void combinationTest() {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        CompletableFuture<String> futureA = CompletableFuture.supplyAsync(() -> {
            String msg = "We face the darkness and our trials are yet untold, ";
            System.out.printf("%s : %s\n", Thread.currentThread().getName(), msg);
            sleep(1);
            return msg;
        }, executorService);
        CompletableFuture<String> futureB = CompletableFuture.supplyAsync(() -> {
            String msg = "We run we stride .";
            System.out.printf("%s : %s\n", Thread.currentThread().getName(), msg);
            sleep(1);
            return msg;
        }, executorService);
        /*
         * futureResult 会等待 futureA 和 futureB 都执行完毕后再执行
         * 有点类似于 countDownLatch, 但 CompletableFuture 有点类似于 枚举 + 责任链，而 countDownLatch 是计数器
         */
        CompletableFuture<String> futureResult = futureA.thenCombine(futureB, (resultA, resultB) -> {
            System.out.printf("%s : %s\n", Thread.currentThread().getName(),
                    "A destination that is ever near .");
            return resultA + resultB;
        });
        /*
         * pool-1-thread-1 : We face the darkness and our trials are yet untold,
         * pool-1-thread-2 : We run we stride .
         * pool-1-thread-1 : A destination that is ever near .
         * We face the darkness and our trials are yet untold, We run we stride .
         */
        System.out.println(futureResult.get());
    }

    /**
     * 测试：任意任务完成后执行
     */
    @Test
    public void runAfterEitherTest() {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        CompletableFuture<String> futureA = CompletableFuture.supplyAsync(() -> {
            String msg = "We face the darkness and our trials are yet untold, ";
            System.out.printf("%s : %s\n", Thread.currentThread().getName(), msg);
            sleep(3);
            return msg;
        }, executorService);
        CompletableFuture<String> futureB = CompletableFuture.supplyAsync(() -> {
            String msg = "We run we stride .";
            System.out.printf("%s : %s\n", Thread.currentThread().getName(), msg);
            sleep(3);
            return msg;
        }, executorService);
        sleep(4);
        // futureA 和 futureB 并行执行，任意执行完毕后进行回调
        futureB.runAfterEither(futureA, () -> System.out.println("Done"));
    }

    @Test
    public void getNowTest() {
        CompletableFuture<String> futureA = CompletableFuture.supplyAsync(() -> {
            sleep(1);
            String msg = "We face the darkness and our trials are yet untold, ";
            System.out.printf("%s : %s\n", Thread.currentThread().getName(), msg);
            return msg;
        });
        /*
         * 立即获取结果，如果获取不到返回默认值
         * 和 futureA.complete("Default voice") 比较类似，但是 complete 会设置任务状态为完成
         */
        String result = futureA.getNow("Default voice");
        // result = Default voice
        System.out.println("result = " + result);
    }

    @SneakyThrows
    @Test
    public void whenCompleteTest() {
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            String msg = "We face the darkness and our trials are yet untold, ";
            // ForkJoinPool.commonPool-worker-1 : We face the darkness and our trials are yet untold,
            System.out.printf("%s : %s\n", Thread.currentThread().getName(), msg);
//            throw new RuntimeException("抛异常啦");
            return msg;
        });
        sleep(1);
        future = future.whenComplete((r, e) -> {
            /*
             * 正常执行: 任务执行完毕：We face the darkness and our trials are yet untold,
             * 异常抛出: 任务执行完毕：null
             */
            System.out.println("任务执行完毕：" + r);
            if (Objects.nonNull(e)) {
                // 异常抛出: 任务发生了异常:java.lang.RuntimeException: 抛异常啦
                log.error("任务发生了异常:{}", e.getMessage());
            }
        });
        /*
         * 正常执行(T == String): future get We face the darkness and our trials are yet untold,
         * 异常抛出(T == Exception): 打印异常对象（异常堆栈）
         */
        System.out.println("future get " + future.get());
    }

    @Test
    public void allOfTest() {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        CompletableFuture<String> futureA = CompletableFuture.supplyAsync(() -> {
            String msg = "We face the darkness and our trials are yet untold, ";
            System.out.printf("%s : %s\n", Thread.currentThread().getName(), msg);
            sleep(1);
            return msg;
        }, executorService);
        CompletableFuture<String> futureB = CompletableFuture.supplyAsync(() -> {
            String msg = "We run we stride .";
            System.out.printf("%s : %s\n", Thread.currentThread().getName(), msg);
            sleep(2);
            return msg;
        }, executorService);
        // join 表示 futureA 和 futureB 都执行完毕后，才往下走
        CompletableFuture.allOf(futureA, futureB).join();
        /*
         * pool-1-thread-1 : We face the darkness and our trials are yet untold,
         * pool-1-thread-2 : We run we stride .
         * main : Done!
         */
        System.out.printf("%s : %s\n", Thread.currentThread().getName(), "AllOf Done!");
    }

    @Test
    public void anyOfTest() {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        CompletableFuture<String> futureA = CompletableFuture.supplyAsync(() -> {
            sleep(1);
            String msg = "We face the darkness and our trials are yet untold, ";
            System.out.printf("%s : %s\n", Thread.currentThread().getName(), msg);
            return msg;
        }, executorService);
        CompletableFuture<String> futureB = CompletableFuture.supplyAsync(() -> {
            sleep(2);
            String msg = "We run we stride .";
            System.out.printf("%s : %s\n", Thread.currentThread().getName(), msg);
            return msg;
        }, executorService);
        // anyOf 表示任何一个任务完成后，就会往下走
        CompletableFuture.anyOf(futureA, futureB).join();
        /*
         * pool-1-thread-1 : We face the darkness and our trials are yet untold,
         * main : AnyOf Done!
         */
        System.out.printf("%s : %s\n", Thread.currentThread().getName(), "AnyOf Done!");
    }

    @SneakyThrows
    private void sleep(long timeout) {
        TimeUnit.SECONDS.sleep(timeout);
    }
}
