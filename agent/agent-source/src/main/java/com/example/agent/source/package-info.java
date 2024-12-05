/**
 * java agent 代理服务
 * <p>
 *     <a href="https://bytejava.cn/md/jvm/jvm/javaagent.html" />
 * <p>
 *     1. 添加 maven-assembly-plugin 插件
 *     2. 引入 org.ow2.asm » asm-all, 该项目转移到 org.ow2.asm » asm
 *     3. 添加 MANIFEST.MF 文件
 *          文件中虽然定义了 Can-Redefine-Classes，但是没有使用 Can-Redefine-Classes 的能力，该配置默认是false
 *     4. 打包
 *     5. 使用代理
 *          -javaagent:D:\Gardenias\SpringBoot3Demo\agent\agent-source\target\agent-source-0.0.1-SNAPSHOT-jar-with-dependencies.jar=com.example.agent.target.service.BusinessService
 *          javaagent后面接的是 agent.jar包的绝对路径, =后面是传入的参数(指要切的类全路径)
 * <p>
 *     但是有些环境不适合使用 -javaagent，需要在服务启动是 attach
 *     1. 添加 bytebuddy 依赖
 *     修改类的字节码有两个时机:
 *          a. 一个javaagent通过Instrumentation.addTransformer方法注入ClassFileTransformer
 *              在类加载时，jvm会调用各个ClassFileTransformer，ClassFileTransformer可以修改类的字节码
 *          b. redefine和 reTransform
 * <p>
 *     2024-12-05 bytebuddy没有实现拦截功能，问题待排查 (intercept)
 *     {@link com.example.agent.source.bytebuddy.BuddyAgent#agentBytes(String, Instrumentation)}
 * @author caimeng
 * @date 2024/11/15 9:56
 */
package com.example.agent.source;

import java.lang.instrument.Instrumentation;