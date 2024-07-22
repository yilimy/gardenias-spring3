package com.example.boot3.spring.cache.service;

import com.example.boot3.spring.cache.StartSpringCache;
import com.example.boot3.spring.cache.po.Emp2;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

/**
 * 测试： 查询缓存
 * @author caimeng
 * @date 2024/7/22 13:57
 */
@Slf4j
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = StartSpringCache.class)
public class IEmp2ServiceCacheTest {
    @Autowired
    private IEmp2Service iEmp2Service;

    /**
     * 测试缓存
     * <p>
     *     连续两次接口调用，只有一次数据库连接
     *     Hibernate: select e1_0.eid,e1_0.ename,e1_0.job,e1_0.salary from Emp2 e1_0 where e1_0.eid=?
     *     【第一次】雇员数据查询: emp2 = Emp2(eid=muyan, ename=包可爱的小李老师, job=作者兼讲师, salary=3600.0)
     *     【第二次】雇员数据查询: emp2 = Emp2(eid=muyan, ename=包可爱的小李老师, job=作者兼讲师, salary=3600.0)
     * <p>
     *     但是查询返回null，也只执行一次数据库连接
     */
    @Test
    public void getTestCache() {
        Emp2 empA = iEmp2Service.get("muyan");
        System.out.printf("【第一次】雇员数据查询: emp2 = %s\n", empA);
        Emp2 empB = iEmp2Service.get("muyan");
        System.out.printf("【第二次】雇员数据查询: emp2 = %s\n", empB);
    }

    /**
     * Hibernate: select e1_0.eid,e1_0.ename,e1_0.job,e1_0.salary from Emp2 e1_0 where e1_0.ename=?
     * 【第一次】雇员数据查询: emp = Emp2(eid=muyan, ename=包可爱的小李老师, job=作者兼讲师, salary=3600.0)
     * 【第二次】雇员数据查询: emp = Emp2(eid=muyan, ename=包可爱的小李老师, job=作者兼讲师, salary=3600.0)
     */
    @Test
    public void getByEnameCacheTest() {
        Emp2 empA = iEmp2Service.getByEname("包可爱的小李老师");
        System.out.printf("【第一次】雇员数据查询: emp = %s\n", empA);
        Emp2 empB = iEmp2Service.getByEname("包可爱的小李老师");
        System.out.printf("【第二次】雇员数据查询: emp = %s\n", empB);
    }

    /**
     * 测试缓存
     * <p>
     *     Hibernate: select e1_0.eid,e1_0.ename,e1_0.job,e1_0.salary from Emp2 e1_0 where e1_0.eid=?
     *     【第一次】雇员数据查询: emp = Emp2(eid=muyan, ename=包可爱的小李老师, job=作者兼讲师, salary=3600.0)
     *     【第二次】雇员数据查询: emp = Emp2(eid=muyan, ename=包可爱的小李老师, job=作者兼讲师, salary=3600.0)
     *     Hibernate: select e1_0.eid,e1_0.ename,e1_0.job,e1_0.salary from Emp2 e1_0 where e1_0.eid=?
     *     【第三次】雇员数据查询: emp = Emp2(eid=yootk, ename=李木纹, job=讲师, salary=5000.0)
     * <p>
     *     执行了两次查询，说明缓存与方法的入参有关
     */
    @Test
    public void getTestCache2() {
        Emp2 empA = iEmp2Service.get("muyan");
        System.out.printf("【第一次】雇员数据查询: emp = %s\n", empA);
        Emp2 empB = iEmp2Service.get("muyan");
        System.out.printf("【第二次】雇员数据查询: emp = %s\n", empB);
        Emp2 empC = iEmp2Service.get("yootk");
        System.out.printf("【第三次】雇员数据查询: emp = %s\n", empC);
    }

    /**
     * 测试：条件缓存
     * <p>
     *     Hibernate: select e1_0.eid,e1_0.ename,e1_0.job,e1_0.salary from Emp2 e1_0 where e1_0.eid=?
     *     【第一次】雇员数据查询: emp = Emp2(eid=muyan, ename=包可爱的小李老师, job=作者兼讲师, salary=3600.0)
     *     Hibernate: select e1_0.eid,e1_0.ename,e1_0.job,e1_0.salary from Emp2 e1_0 where e1_0.eid=?
     *     【第二次】雇员数据查询: emp = Emp2(eid=muyan, ename=包可爱的小李老师, job=作者兼讲师, salary=3600.0)
     *     Hibernate: select e1_0.eid,e1_0.ename,e1_0.job,e1_0.salary from Emp2 e1_0 where e1_0.eid=?
     *     【第三次】雇员数据查询: emp = Emp2(eid=yootk, ename=李木纹, job=讲师, salary=5000.0)
     *     【第三次】雇员数据查询: emp = Emp2(eid=yootk, ename=李木纹, job=讲师, salary=5000.0)
     * <p>
     *     说明条件生效：只有 yootk 的key进行了缓存
     */
    @Test
    public void getCacheConditionTest() {
        Emp2 empA = iEmp2Service.getWithCondition("muyan");
        System.out.printf("【第一次】雇员数据查询: emp = %s\n", empA);
        Emp2 empB = iEmp2Service.getWithCondition("muyan");
        System.out.printf("【第二次】雇员数据查询: emp = %s\n", empB);
        Emp2 empC = iEmp2Service.getWithCondition("yootk");
        System.out.printf("【第三次】雇员数据查询: emp = %s\n", empC);
        Emp2 empD = iEmp2Service.getWithCondition("yootk");
        System.out.printf("【第三次】雇员数据查询: emp = %s\n", empD);
    }

    /**
     * 测试：满足 unless 条件的将会被排除
     * <p>
     *     Hibernate: select e1_0.eid,e1_0.ename,e1_0.job,e1_0.salary from Emp2 e1_0 where e1_0.eid=?
     *     【第一次】雇员数据查询: emp = Emp2(eid=muyan, ename=包可爱的小李老师, job=作者兼讲师, salary=3600.0)
     *     【第二次】雇员数据查询: emp = Emp2(eid=muyan, ename=包可爱的小李老师, job=作者兼讲师, salary=3600.0)
     *     Hibernate: select e1_0.eid,e1_0.ename,e1_0.job,e1_0.salary from Emp2 e1_0 where e1_0.eid=?
     *     【第三次】雇员数据查询: emp = Emp2(eid=yootk, ename=李木纹, job=讲师, salary=5000.0)
     *     Hibernate: select e1_0.eid,e1_0.ename,e1_0.job,e1_0.salary from Emp2 e1_0 where e1_0.eid=?
     *     【第四次】雇员数据查询: emp = Emp2(eid=yootk, ename=李木纹, job=讲师, salary=5000.0)
     */
    @Test
    public void getWithSalaryTest() {
        Emp2 empA = iEmp2Service.getWithSalary("muyan");
        System.out.printf("【第一次】雇员数据查询: emp = %s\n", empA);
        Emp2 empB = iEmp2Service.getWithSalary("muyan");
        System.out.printf("【第二次】雇员数据查询: emp = %s\n", empB);
        Emp2 empC = iEmp2Service.getWithSalary("yootk");
        System.out.printf("【第三次】雇员数据查询: emp = %s\n", empC);
        Emp2 empD = iEmp2Service.getWithSalary("yootk");
        System.out.printf("【第四次】雇员数据查询: emp = %s\n", empD);
    }

    /**
     * 测试：同步缓存
     * 开启了10个异步线程，查询同一条待缓存数据
     * <p>
     *     Thread: ForkJoinPool.commonPool-worker-1
     *     Thread: ForkJoinPool.commonPool-worker-2
     *     Thread: ForkJoinPool.commonPool-worker-3
     *     Thread: ForkJoinPool.commonPool-worker-4
     *     Thread: ForkJoinPool.commonPool-worker-5
     *     Thread: ForkJoinPool.commonPool-worker-6
     *     Thread: ForkJoinPool.commonPool-worker-7
     *     Hibernate: select e1_0.eid,e1_0.ename,e1_0.job,e1_0.salary from Emp2 e1_0 where e1_0.eid=?
     *     Thread: ForkJoinPool.commonPool-worker-6
     *     Thread: ForkJoinPool.commonPool-worker-3
     *     Thread: ForkJoinPool.commonPool-worker-1
     *     ------------------------------
     *     雇员数据查询: emp = Emp2(eid=muyan, ename=包可爱的小李老师, job=作者兼讲师, salary=3600.0)
     *     雇员数据查询: emp = Emp2(eid=muyan, ename=包可爱的小李老师, job=作者兼讲师, salary=3600.0)
     *     雇员数据查询: emp = Emp2(eid=muyan, ename=包可爱的小李老师, job=作者兼讲师, salary=3600.0)
     *     雇员数据查询: emp = Emp2(eid=muyan, ename=包可爱的小李老师, job=作者兼讲师, salary=3600.0)
     *     雇员数据查询: emp = Emp2(eid=muyan, ename=包可爱的小李老师, job=作者兼讲师, salary=3600.0)
     *     雇员数据查询: emp = Emp2(eid=muyan, ename=包可爱的小李老师, job=作者兼讲师, salary=3600.0)
     *     雇员数据查询: emp = Emp2(eid=muyan, ename=包可爱的小李老师, job=作者兼讲师, salary=3600.0)
     *     雇员数据查询: emp = Emp2(eid=muyan, ename=包可爱的小李老师, job=作者兼讲师, salary=3600.0)
     *     雇员数据查询: emp = Emp2(eid=muyan, ename=包可爱的小李老师, job=作者兼讲师, salary=3600.0)
     *     雇员数据查询: emp = Emp2(eid=muyan, ename=包可爱的小李老师, job=作者兼讲师, salary=3600.0)
     */
    @SneakyThrows
    @Test
    public void getSyncTest() {
        CompletableFuture<Emp2>[] completableFutures = Stream
                .generate(() -> CompletableFuture.supplyAsync(() -> {
                    System.out.println("Thread: " + Thread.currentThread().getName());
                    return iEmp2Service.getSync("muyan");
                }))
                .limit(10)
                .<CompletableFuture<Emp2>>toArray(CompletableFuture[]::new);
        CompletableFuture.allOf(completableFutures).join();
        System.out.println("-".repeat(30));
        for (CompletableFuture<Emp2> completableFuture : completableFutures) {
            Emp2 emp = completableFuture.get();
            System.out.printf("雇员数据查询: emp = %s\n", emp);
        }
    }
}
