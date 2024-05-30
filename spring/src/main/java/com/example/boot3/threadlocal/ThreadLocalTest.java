package com.example.boot3.threadlocal;

import com.example.boot3.threadlocal.delegation.DelegatingExecutorService;
import com.example.boot3.threadlocal.delegation.DelegatingSharedThreadLocal;
import com.example.boot3.threadlocal.delegation.DelegatingWithExecutorThreadLocal;
import lombok.SneakyThrows;
import org.junit.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 测试：ThreadLocal
 * 参考: <a href="https://www.bilibili.com/video/BV1US421P7vt/">如何在线程池中传递 ThreadLocal的值</a>
 * @author caimeng
 * @date 2024/5/30 9:23
 */
public class ThreadLocalTest {
    /**
     * 定义一个普通的 ThreadLocal
     */
    public static ThreadLocal<String> localNormal = new ThreadLocal<>();
    /**
     * 定义一个父子共享数据的 ThreadLocal
     */
    public static ThreadLocal<String> localDelegating = new DelegatingSharedThreadLocal<>();
    /**
     * 可传递的 ThreadLocal
     * 从主线程传递到子线程
     */
    public static DelegatingWithExecutorThreadLocal<String> localAcross = new DelegatingWithExecutorThreadLocal<>();

    ExecutorService executorService = Executors.newFixedThreadPool(2);
    /**
     * 自定义线程池，
     * 配合 {@link ThreadLocalTest#localAcross}
     */
    ExecutorService delegatingExecutorService = new DelegatingExecutorService(Executors.newFixedThreadPool(2));

    /**
     * ThreadLocal 没有在父子线程中传递的情况
     */
    @Test
    public void unReachableTest() {
        localNormal.set("In silence hopes we share .");
        // 【主】线程ID：1, localNormal=In silence hopes we share .
        System.out.printf("【主】线程ID：%s, localNormal=%s\n", Thread.currentThread().getId(), localNormal.get());
        executorService.submit(() -> {
            // 线程ID：16, localNormal=null
            System.out.printf("线程ID：%s, localNormal=%s\n", Thread.currentThread().getId(), localNormal.get());
        });
        executorService.submit(() -> {
            // 线程ID：17, localNormal=null
            System.out.printf("线程ID：%s, localNormal=%s\n", Thread.currentThread().getId(), localNormal.get());
        });
    }

    /**
     * 测试：父子线程共享数据
     */
    @SneakyThrows
    @Test
    public void delegatingMainTest() {
        localDelegating.set("Across the plains in valleys deep .");
        // 【主】线程ID-0：1, localDelegating=Across the plains in valleys deep .
        System.out.printf("【主】线程ID-0：%s, localDelegating=%s\n", Thread.currentThread().getId(), localDelegating.get());
        executorService.submit(() -> {
            // 线程ID-1：16, localDelegating=Across the plains in valleys deep .
            System.out.printf("线程ID-1：%s, localDelegating=%s\n", Thread.currentThread().getId(), localDelegating.get());
        });
        executorService.submit(() -> {
            // 线程ID-2：17, localDelegating=Across the plains in valleys deep .
            System.out.printf("线程ID-2：%s, localDelegating=%s\n", Thread.currentThread().getId(), localDelegating.get());
            // 重置 ThreadLocal 的值
            localDelegating.set("Heads up ! A steady rhythm .");
            // 线程ID-2-2：17, localDelegating=Heads up ! A steady rhythm .
            System.out.printf("线程ID-2-2：%s, localDelegating=%s\n", Thread.currentThread().getId(), localDelegating.get());
        });
        TimeUnit.SECONDS.sleep(2);
        // 主线程中的 ThreadLocal 值已发生变化
        // 【主】线程ID-0-1：1, localDelegating=Heads up ! A steady rhythm .
        System.out.printf("【主】线程ID-0-1：%s, localDelegating=%s\n", Thread.currentThread().getId(), localDelegating.get());
    }

    /**
     * 封装后的 ThreadLocal
     * 对比 {@link this#unReachableTest()}
     * 对比 {@link this#delegatingMainTest()}
     */
    @SneakyThrows
    @Test
    public void delegatingThreadLocalTest() {
        localAcross.set("In life we stand and strive our victory is found .");
        // 【主】线程ID-0：1, localDelegating=Across the plains in valleys deep .
        System.out.println("localAcross = " + localAcross.toString());
        System.out.printf("【主】线程ID-0：%s, localAcross=%s\n", Thread.currentThread().getId(), localAcross.get());
        delegatingExecutorService.submit(() -> {
            // 线程ID-1：16, localDelegating=Across the plains in valleys deep .
            System.out.printf("线程ID-1：%s, localAcross=%s\n", Thread.currentThread().getId(), localAcross.get());
        });
        delegatingExecutorService.submit(() -> {
            // 线程ID-2：17, localDelegating=Across the plains in valleys deep .
            System.out.printf("线程ID-2：%s, localAcross=%s\n", Thread.currentThread().getId(), localAcross.get());
            // 重置线程17的 ThreadLocal 的值
            localAcross.set("Heads up ! A steady rhythm .");
            // 线程ID-2-2：17, localDelegating=Heads up ! A steady rhythm .
            System.out.printf("线程ID-2-2：%s, localAcross=%s\n", Thread.currentThread().getId(), localAcross.get());
        });
        TimeUnit.SECONDS.sleep(2);
        // 主线程中的 ThreadLocal 值已隔离，没有发生变化
        // 【主】线程ID-0-1：1, localAcross=In life we stand and strive our victory is found .
        System.out.printf("【主】线程ID-0-1：%s, localAcross=%s\n", Thread.currentThread().getId(), localAcross.get());
    }

}
