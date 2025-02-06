package com.gardenia.web.action;

import cn.hutool.core.io.unit.DataSize;
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
     *     4. jar 启动
     *          切换到java17 的环境， D:\Program Files\Java\corretto-17.0.10\bin
     *          .\java.exe -jar -Xms8g -Xss256K -Xlog:gc -XX:+UseG1GC -Xmx30g D:\Gardenias\SpringBoot3Demo\first-micro-boot\first-micro-boot-web\target\first-micro-boot-web-0.0.1-SNAPSHOT.jar
     *          大内存环境下，建议用G1
     *          ms: 总内存
     *          mx: 最大内存
     *          ss: 空闲内存
     * @return 运行参数
     */
    @RequestMapping("memory")
    public Object memory() {
        // 获取运行时对象实例
        Runtime runtime = Runtime.getRuntime();
        return Map.of(
                // 一般为物理内存的 1 /4
                "MaxMemory", DataSize.ofBytes(runtime.maxMemory()).toGigabytes() + "GB",
                // 一般为物理内存的 1 / 64
                "TotalMemory", DataSize.ofBytes(runtime.totalMemory()).toMegabytes() + "MB",
                "FreeMemory", DataSize.ofBytes(runtime.freeMemory()).toMegabytes() + "MB");
    }
}
