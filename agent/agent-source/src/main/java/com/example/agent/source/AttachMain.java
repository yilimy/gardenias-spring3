package com.example.agent.source;

import com.example.agent.source.bytebuddy.BuddyAgent;
import com.sun.tools.attach.VirtualMachine;
import lombok.SneakyThrows;

/**
 * @author caimeng
 * @date 2024/11/25 15:42
 */
public class AttachMain {

    @SneakyThrows
    public static void main(String[] args) {
        /*
         * agent-target.jar运行的PID，
         * agent-target 启动时会打印
         */
        String pid = "13096";
        String agentPath = "D:\\Gardenias\\SpringBoot3Demo\\agent\\agent-source\\target\\agent-source-0.0.1-SNAPSHOT-jar-with-dependencies.jar";
        String agentArgs = "com.example.agent.target.service.BusinessService";
        VirtualMachine vm = VirtualMachine.attach(pid);
        try {
//            agentArgs += "," + BuddyAgent.AOP;
            agentArgs += "," + BuddyAgent.BYTES;
            vm.loadAgent(agentPath, agentArgs);
            System.out.println("load success .");
//            TimeUnit.SECONDS.sleep(Integer.MAX_VALUE);
        } finally {
            System.out.println("detach ...");
            vm.detach();
        }
    }
}
