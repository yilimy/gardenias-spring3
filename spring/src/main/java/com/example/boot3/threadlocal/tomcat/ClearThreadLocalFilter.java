package com.example.boot3.threadlocal.tomcat;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Collections;
import java.util.Map;

/**
 * 解绑 ThreadLocal
 * @author caimeng
 * @date 2024/8/15 18:36
 */
public class ClearThreadLocalFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        try {
            filterChain.doFilter(servletRequest, servletResponse);
        } finally {
//            clearThreadLocals();
            ThreadLocalController.LOCAL_MULTIPLEX.remove();
        }
    }

    /**
     * 不能将所有的ThreadLocal去掉，因为很多第三方组件有自己的逻辑
     */
    @Deprecated
    private void clearThreadLocals() {
        // 清空ThreadLocals，这里使用ThreadLocalMap来实现
        Map<Thread, Map<ThreadLocal<?>, Object>> threadLocalMap =
                ThreadLocalMap.getThreadLocalMap(Thread.currentThread());
        if (threadLocalMap != null) {
            threadLocalMap.clear();
        }
    }

    private static class ThreadLocalMap {
        static Map<Thread, Map<ThreadLocal<?>, Object>> getThreadLocalMap(Thread thread) {
            try {
                Field threadLocalsField = Thread.class.getDeclaredField("threadLocals");
                threadLocalsField.setAccessible(true);
                Object threadLocalTable = threadLocalsField.get(thread);
                Field tableField = threadLocalTable.getClass().getDeclaredField("table");
                tableField.setAccessible(true);
                Map<ThreadLocal<?>, Object> table = (Map<ThreadLocal<?>, Object>) tableField.get(threadLocalTable);
                return Collections.singletonMap(thread, table);
            } catch (ReflectiveOperationException e) {
                e.printStackTrace();
                return null;
            }
        }
    }
}
