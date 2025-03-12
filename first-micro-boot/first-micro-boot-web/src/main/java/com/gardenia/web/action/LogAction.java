package com.gardenia.web.action;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试日志级别
 * <a href="https://docs.spring.io/spring-boot/reference/features/logging.html">Spring Log 官方文档</a>
 * @author caimeng
 * @date 2025/3/12 14:12
 */
@Slf4j
@RestController
@RequestMapping("/log/*")
public class LogAction {
    @RequestMapping("echo")
    public String echo(String message) {
        log.error("【ERROR】{}", message);
        log.warn("【WARN】{}", message);
        log.info("【INFO】{}", message);
        log.debug("【DEBUG】{}", message);
        log.trace("【TRACE】{}", message);
        return "echo:" + message;
    }
}
