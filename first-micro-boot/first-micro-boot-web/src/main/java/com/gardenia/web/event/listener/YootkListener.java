package com.gardenia.web.event.listener;

import com.gardenia.web.event.YootkEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

/**
 * 事件监听组件
 * @author caimeng
 * @date 2025/4/1 16:22
 */
@Slf4j
@Component
public class YootkListener implements ApplicationListener<YootkEvent> {
    @Override
    public void onApplicationEvent(@NonNull YootkEvent event) { // 事件监听后得到事件的对象
        log.info("【实现监听器】事件处理: {}", event);
        // 指定自定义的方法调用
        event.fire();
    }
}
