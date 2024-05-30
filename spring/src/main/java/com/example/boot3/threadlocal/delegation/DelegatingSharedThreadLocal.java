package com.example.boot3.threadlocal.delegation;

import java.util.HashMap;
import java.util.Map;

/**
 * 主线程委托模式的ThreadLocal、
 * 数据共享模式
 * 这种模式下，父子共享同一套数据，线程间没有隔离
 * @author caimeng
 * @date 2024/5/30 9:42
 */
public class DelegatingSharedThreadLocal<T> extends ThreadLocal<T> {
    /**
     * 共享数据空间
     * key 为生成该 ThreadLocal 的线程，一般为主线程。
     */
    private final static Map<DelegatingSharedThreadLocal<?>, Object> holder;

    static {
        holder = new HashMap<>();
    }

    @Override
    @SuppressWarnings("unchecked")
    public T get() {
        return (T) holder.get(this);
    }

    @Override
    public final void set(T value) {
        synchronized (holder) {
            /*
             * this 创建于主线程，
             * 子线程对该 ThreadLocal 操作时，是通过主线程的 this 作为key作为媒介的。
             * 所以，父子线程共享同一套数据
             * 但是，这不是很 ThreadLocal，没有实现线程隔离
             */
            holder.put(this, value);
        }
    }
}
