package com.gardenia.web.event.listener;

import com.gardenia.web.event.YootkEvent;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.lang.NonNull;

/**
 * 通过配置文件的方式进行事件监听
 * <p>
 *     1. 该对象由 {@link org.springframework.boot.context.config.DelegatingApplicationListener} 添加进监听器集合
 *     2. 该对象通过反射直接添加到监听器，没有执行完整的 bean 生命周期
 * @author caimeng
 * @date 2025/4/1 16:54
 */
@Slf4j
public class PropertiesListener implements ApplicationListener<YootkEvent> {
    @PostConstruct
    public void init() {
        log.info("PropertiesListener 执行了实例化");
    }
    @Override
    public void onApplicationEvent(@NonNull YootkEvent event) { // 事件监听后得到事件的对象
        log.info("【配置监听器】事件处理: {}", event);
        // 指定自定义的方法调用
        event.fire();
    }
}
