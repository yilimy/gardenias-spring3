package com.example.agent.source;

import com.example.agent.source.bytebuddy.BuddyAgent;
import com.example.agent.source.transformer.AOPTransformer;

import java.lang.instrument.Instrumentation;

/**
 * 代理主入口
 * @author caimeng
 * @date 2024/11/19 9:13
 */
public class AgentMain {

    /**
     * JVM启动前代理
     * <p>
     *     对应 MANIFEST.MF 文件中 Premain-Class 的配置
     * @param agentOps 代理参数（一般传的是被代理的全路径类名）
     * @param inst 容器类，java SE 5 引入的特性
     */
    public static void premain(String agentOps, Instrumentation inst) {
        System.out.println(AgentMain.class.getName() + "#premain, param=" + agentOps);
        // 在目标类加载前，通过 Instrumentation 添加自定义的 ClassFileTransformer
        inst.addTransformer(new AOPTransformer(agentOps));
    }

    /**
     * JVM启动后代理
     * <p>
     *     对应 MANIFEST.MF 文件中 Agent-Class 的配置
     * @param agentOps 代理参数（一般传的是被代理的全路径类名）
     *                 多参数用“,“分割
     *                 [0] : 类的全路径
     *                 [1] : PROXY | AOP | BYTES
     * @param inst 容器类，java SE 5 引入的特性
     */
    public static void agentmain(String agentOps, Instrumentation inst) {
        String[] args = agentOps.split(",");
        BuddyAgent.agentmain(args, inst);
    }
}
