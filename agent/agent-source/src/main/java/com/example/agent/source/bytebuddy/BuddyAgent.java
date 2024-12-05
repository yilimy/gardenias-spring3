package com.example.agent.source.bytebuddy;

import com.example.agent.source.intercept.BytebuddyInterceptor;
import com.example.agent.source.transformer.AOPTransformer;
import lombok.SneakyThrows;
import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.matcher.ElementMatchers;
import net.bytebuddy.utility.JavaModule;

import java.lang.instrument.Instrumentation;
import java.util.Optional;

/**
 * ByteBuddy 可以实现三个功能
 * 1. 动态代理
 * 2. AOP（面向切面编程）
 * 3. 字节码增强（修改字节码）
 * @author caimeng
 * @date 2024/11/29 9:31
 */
//@Slf4j
public class BuddyAgent {

    public static final String PROXY = "proxy";
    public static final String AOP = "aop";
    public static final String BYTES = "bytes";

    /**
     * 通过bytebuddy代理在运行时加载的class
     * <p>
     *     失败：BytebuddyInterceptor中的方法没有执行
     * @param agentOps 代理参数
     * @param inst 代理容器
     */
    public static void agentmain(String[] agentOps, Instrumentation inst) {
        String agentOpt = agentOps[0];
        System.out.println("agentOpt=" + agentOpt);
        String action = Optional.of(agentOps).filter(ao -> ao.length > 1).map(ao -> ao[1]).orElse(BYTES);
        System.out.println("action=" + action);
        switch (action) {
            case PROXY, AOP -> agentAOPWithJavassist(agentOpt, inst);
            case BYTES -> agentBytes(agentOpt, inst);
        }
    }

    /**
     * 实现运行修改代码，但是使用的是 javassist 技术，而不是 Buddy 技术
     * @param agentOps 代理参数
     * @param inst 代理容器
     */
    public static void agentAOPWithJavassist(String agentOps, Instrumentation inst) {
        System.out.println(BuddyAgent.class.getName() + "#agentmain_aop, param=" + agentOps);
        // 如果不设定 canRetransform = true， Transformer中的内容将不会生效
        inst.addTransformer(new AOPTransformer(agentOps), true);
        try {
            assistClass(agentOps, inst);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 重新加载类
     * @param agentOps 代理参数
     * @param inst 代理容器
     */
    @SneakyThrows
    public static void assistClass(String agentOps, Instrumentation inst) {
        Class<?>[] clazzArray = inst.getAllLoadedClasses();
        for (Class<?> clazz : clazzArray) {
            String name = clazz.getName();
            if (name.equals(agentOps)) {
                System.out.println("重新加载：" + name);
                // 重新加载类
                inst.retransformClasses(clazz);
            }
        }
    }

    /**
     * 该方法没有生效
     * @param agentOps 参数
     * @param inst 容器
     */
    public static void agentBytes(String agentOps, Instrumentation inst) {
        // 该处在target中打印
        System.out.println(BuddyAgent.class.getName() + "#agentmain_bytes, param=" + agentOps);
        // 该处的拦截器没有触发
        new AgentBuilder.Default()
//                .with(AgentBuilder.InitializationStrategy.NoOp.INSTANCE)
//                .disableClassFormatChanges()
                // 匹配特定的类
                .type(ElementMatchers.named(agentOps))
//                .type(ElementMatchers.any())
                .transform((builder, typeDescription, classLoader, javaModule) ->
                                builder
                                        // 匹配所有
                                        .method(ElementMatchers.any())
//                                .method(ElementMatchers.named("doService"))
                                        /*
                                         * 自定义拦截方法 (没有生效，原因不明。可能是有多个 Agent 对同一个类进行增强)
                                         * 理论上，增强的方法签名要与原方法签名一致。
                                         * 但可以使用 RuntimeType 注解，在匹配方法时不进行严格的参数类型检查，而是在参数匹配失败时尝试使用运行时类型转换
                                         */
                                        .intercept(MethodDelegation.to(BytebuddyInterceptor.class))
//                                        .intercept(Advice.to(BytebuddyInterceptor.class))
                )
                /*
                 * Discovery class :com.example.agent.source.transformer.AOPTransformer
                 * Complete class : com.example.agent.source.transformer.AOPTransformer
                 * Discovery class :javassist.ClassPath
                 * Complete class : javassist.ClassPath
                 * Find com.example.agent.target.service.BusinessService !
                 * before invoke doService
                 * doing business
                 * after invoke doService
                 * ...
                 * ... （发送JVM停止指令）
                 * Discovery class :jdk.internal.misc.Signal$1
                 * Complete class : jdk.internal.misc.Signal$1
                 * Discovery class :java.lang.Shutdown
                 * Complete class : java.lang.Shutdown
                 * Discovery class :java.lang.Shutdown$Lock
                 * Complete class : java.lang.Shutdown$Lock
                 */
                .with(new AgentBuilder.Listener() {
                    @Override
                    public void onDiscovery(String typeName, ClassLoader classLoader, JavaModule module, boolean loaded) {
                        // 类发现时的回调
                        System.out.println("Discovery class :" + typeName);
                    }

                    @Override
                    public void onTransformation(TypeDescription typeDescription, ClassLoader classLoader, JavaModule module, boolean loaded, DynamicType dynamicType) {
                        // 类转换时的回调
                        System.out.println("Transformation class");
                    }

                    @Override
                    public void onIgnored(TypeDescription typeDescription, ClassLoader classLoader, JavaModule module, boolean loaded) {

                    }

                    @Override
                    public void onError(String typeName, ClassLoader classLoader, JavaModule module, boolean loaded, Throwable throwable) {
                        // 错误处理
                        System.out.println("Error class : " + typeName);
                        System.out.println("throwable : " + throwable);
                    }

                    @Override
                    public void onComplete(String typeName, ClassLoader classLoader, JavaModule module, boolean loaded) {
                        // 类加载完成后的回调
                        System.out.println("Complete class : " + typeName);
                    }
                })
                // 添加类文件转换器
                .installOn(inst);
        // 添加自定义的 ClassFileTransformer
        inst.addTransformer(new AOPTransformer(agentOps), true);
//        inst.addTransformer(new ClassFileTransformer() {}, true);
        // 如果要影响已经加载的类，需要使用 retransformClasses
        try {
            Class<?> targetClass = Class.forName(agentOps);
            inst.retransformClasses(targetClass);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
