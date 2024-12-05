package com.example.agent.source.intercept;

import net.bytebuddy.asm.Advice;
import net.bytebuddy.implementation.bind.annotation.AllArguments;
import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.SuperCall;

import java.lang.invoke.MethodHandle;
import java.lang.reflect.Method;
import java.util.concurrent.Callable;

/**
 * 由于 RuntimeType 关闭了方法签名检查，可能对性能有影响
 * @author caimeng
 * @date 2024/11/26 18:38
 */
public class BytebuddyInterceptor {
//    @RuntimeType
    public static Object interceptWithParams(@AllArguments Object[] args,
                                   @Origin Method method,
                                   @SuperCall Callable<?> superCall) throws Exception {
        // 在这里添加你的逻辑，例如日志记录或者修改方法参数
        System.out.println("Intercepted method: " + method.getName());
        // 调用原始方法
        return superCall.call();
    }

//    @RuntimeType
    public static void interceptNoParams(@Origin Method method,
                                         @SuperCall Callable<?> superCall) throws Exception {
        System.out.println("Intercepted method: " + method.getName());
        // 调用原始方法
        superCall.call();
    }

//    @RuntimeType
    public void interceptNoParams() {
        System.out.println("Intercepted a void method with no arguments");
    }

    @RuntimeType
    public static void intercept(@Origin Method method, @SuperCall MethodHandle superCall) {
        try {
            // 在这里实现你的逻辑，例如日志记录、性能监控等
            System.out.println("Before method: " + method.getName());

            // 调用原始方法
            superCall.invokeExact();

            // 在原始方法执行后执行的逻辑
            System.out.println("After method: " + method.getName());
        } catch (Throwable throwable) {
            // 处理可能的异常
            throwable.printStackTrace();
        }
    }

    @RuntimeType
    public void doService() {
        System.out.println("Intercepted doService");
    }

    @Advice.OnMethodExit
    public static void exit(@Advice.This Object thiz) {
        System.out.println("FooAdvice executed");
    }
}
