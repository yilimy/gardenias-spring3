package com.example.boot3.threadlocal.delegation;

import org.springframework.util.ObjectUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * 可传递的 ThreadLocal
 * 线程隔离模式
 * 父线程的 ThreadLocal 可传递到子线程，但是需要线程池支持
 * 该类只实现了 ThreadLocal 的存取功能，作为核心的 map-key-ThreadLocal，由线程池指定
 * 数据传递
 * @author caimeng
 * @date 2024/5/30 10:12
 */
public class DelegatingWithExecutorThreadLocal<T> extends ThreadLocal<T> {
    /**
     * 以 ThreadLocal 作为共享数据空间，子线程的 ThreadLocal 作为key
     * key 为生成该 ThreadLocal 的线程，一般为主线程。
     * 可以理解为 ThreadLocal 就是个 hash + 方法的组合
     * 主线程
     * holder : {
     *     thread-main: {
     *         map: {
     *             thread-main: value
     *         }
     *     }
     * }
     * 子线程调用 copyFrom 方法后
     * holder : {
     *     thread-extra: {
     *         map: {
     *             thread-main: value
     *         }
     *     }
     * }
     * holder 虽然是静态的，但是获取数据是通过 ThreadLocal.get 动态获取的，得到的map是相互隔离的
     * map的值是通过 ThreadLocal.set 赋值的，线程销毁后 ThreadLocal 里面的值也销毁了
     */
    private final static ThreadLocal<Map<DelegatingWithExecutorThreadLocal<?>, Object>> holder;


    static {
        holder = new ThreadLocal<>();
    }

    public DelegatingWithExecutorThreadLocal() {
        // 创建该对象时，在线程中绑定map
        holder.set(new HashMap<>());
    }

    @Override
    @SuppressWarnings("unchecked")
    public T get() {
        /*
         * this 为主线程中创建的 ThreadLocal 对象
         * currentThread (main|extra) -> map -> treadLocalId (main) -> value
         */
        return (T) holder.get().get(this);
    }

    @Override
    public void set(T value) {
        /*
         * currentThread (main|extra) -> map -> treadLocalId (main) -> value
         */
        holder.get().put(this, value);
    }

    /**
     * 获取主线程 ThreadLocal 的快照
     * @return 数据快照
     */
    public static Map<DelegatingWithExecutorThreadLocal<?>, Object> snapshot(){
        Map<DelegatingWithExecutorThreadLocal<?>, Object> contextMap = holder.get();
        return new HashMap<>(contextMap);
    }

    public static void copyFrom(Map<DelegatingWithExecutorThreadLocal<?>, Object> snapshot) {
        // 子线程此处获取的是null
        Map<DelegatingWithExecutorThreadLocal<?>, Object> contextMap = holder.get();
        if (ObjectUtils.isEmpty(contextMap)) {
            /*
             * 在此创建子线程的 ThreadLocal
             * 通过 ThreadLocal 的hash，在线程上创建了一个hashmap
             * 在子线程的 map 中存放了指向主线程TreadLocal对象的value值
             */
            holder.set(snapshot);
            return;
        }
        snapshot.forEach((key, value) -> {
            if (contextMap.containsKey(key)) {
                contextMap.put(key, value);
            }
        });
    }

}
