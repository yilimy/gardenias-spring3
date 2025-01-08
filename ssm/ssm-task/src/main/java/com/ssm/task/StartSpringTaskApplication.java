package com.ssm.task;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author caimeng
 * @date 2025/1/8 16:32
 */
@EnableScheduling   // 启用Spring定时调度处理
@ComponentScan("com.ssm.task")
public class StartSpringTaskApplication {
}
