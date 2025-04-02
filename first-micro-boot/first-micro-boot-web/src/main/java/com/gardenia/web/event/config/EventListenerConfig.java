package com.gardenia.web.event.config;

import com.gardenia.web.event.YootkEvent;
import com.gardenia.web.vo.MessageForEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.lang.NonNull;

/**
 * @author caimeng
 * @date 2025/4/2 9:57
 */
@Slf4j
@Configuration
public class EventListenerConfig {  // 事件监听配置类
    @EventListener // 事件监听，没有强制性的接口实现
    public void handleAllEvent(Object event) {
        // 监听所有的事件
        log.info("【HandleAllEvent】监听到事件：{}", event);
    }

    @EventListener // 绑定指定的事件
    public void handleYootkEvent(@NonNull YootkEvent event) {
        log.info("【handleYootkEvent】{}", event);
    }

    @EventListener(condition = "#event.message.title == '测试事件'") // 有条件的触发事件
    public void handleYootkEventByCondition(@NonNull YootkEvent event) {
        log.info("【handleYootkEventByCondition】{}", event);
    }

    /**
     * 绑定的对象跟事件无关，
     * 跟数据包保存对象相关
     * <p>
     *     绑定的是数据保存对象，即构造器中的第二参数
     *     {@link YootkEvent#YootkEvent(Object, MessageForEvent)}
     * <p>
     *     但是，此时对应的发布对象也要变成 {@link MessageForEvent}
     * @param message 数据的保存对象
     */
    @EventListener // 绑定的对象跟事件无关，跟数据包保存对象相关
    public void handleYootkEventByContent(@NonNull MessageForEvent message) {
        log.info("【handleYootkEventByContent】{}", message);
    }
}
