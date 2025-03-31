package com.gardenia.web.task;

import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * 动态任务表达式的bean
 * @author caimeng
 * @date 2025/3/31 10:02
 */
@Component
@Data
public class DynamicCronExpression {
    /*
     * 定义 cron 表达式
     * 可以使用关系型数据库或者NoSQL 存储，这里使用内存存储
     * 默认: 每10秒一次
     */
    private String cronExpression = "*/10 * * * * ?";
}
