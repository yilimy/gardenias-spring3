package com.gardenia.web.action;

import com.gardenia.web.task.DynamicCronExpression;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 动态修改 cron 表达式
 * @author caimeng
 * @date 2025/3/31 10:53
 */
@Slf4j
@RestController
@RequestMapping("/cron/*")
public class DynamicCronAction {
    @Autowired
    private DynamicCronExpression dynamicCronExpression;
    @RequestMapping("set")
    public Object get(String cron) {
        log.info("动态修改cron配置: {}", cron);
        // 修改当前触发的 cron 表达式
        dynamicCronExpression.setCronExpression(cron);
        return true;
    }
}
