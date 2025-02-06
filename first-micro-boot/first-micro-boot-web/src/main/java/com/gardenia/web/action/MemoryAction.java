package com.gardenia.web.action;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author caimeng
 * @date 2025/2/6 15:22
 */
@RestController
@RequestMapping("/jvm/*")
public class MemoryAction {
    /**
     * 查询jvm运行时参数
     * <p>
     *     为了公平起见，脱离IDEA来运行项目
     *     1. 发布依赖:
     *          first-micro-boot
     *          first-micro-boot-common
     *     2. 通过命令行来启动项目
     *          Plugins -> spring-boot -> spring-boot:run
     *     3. 查询运行参数
     *          <a href="http://localhost:8080/jvm/memory" />
     * @return 运行参数
     */
    @RequestMapping("memory")
    public Object memory() {
        // 获取运行时对象实例
        Runtime runtime = Runtime.getRuntime();
        return Map.of(
                "MaxMemory", runtime.maxMemory(),
                "TotalMemory", runtime.totalMemory(),
                "FreeMemory", runtime.freeMemory());
    }
}
