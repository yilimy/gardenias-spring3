package com.gardenia.web.event.listener;

import com.gardenia.web.event.YootkEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

/**
 * 通过注解完成的监听
 * <p>
 *     可添加自定义的判断
 * @author caimeng
 * @date 2025/4/1 18:02
 */
@Slf4j
@Component
public class AnnotationListener {
    /**
     * 事件处理
     * <p>
     *     可以通过”#root.args[0].message“来获取到参数对象
     * @param event 事件
     */
    @EventListener(condition = "#root.args[0].message.title == '测试事件'")
    public void onApplicationEvent(@NonNull YootkEvent event) {
        log.info("【注册监听器】通过参数判断: {}", event);
        // 指定自定义的方法调用
        event.fire();
    }

    /**
     * 事件处理
     * <p>
     *     也可以通过”#event.message.title“来获取到参数对象
     * @param event 事件
     */
    @EventListener(condition = "@myConditionChecker.checkMessage(#event.message.title)")
    public void onApplicationEvent2(@NonNull YootkEvent event) {
        log.info("【注册监听器】通过判断器判断: {}", event);
        // 指定自定义的方法调用
        event.fire();
    }
}
