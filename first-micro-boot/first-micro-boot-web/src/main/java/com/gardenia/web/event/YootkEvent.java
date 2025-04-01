package com.gardenia.web.event;

import com.gardenia.web.vo.MessageForEvent;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEvent;

/**
 * 事件对象
 * @author caimeng
 * @date 2025/4/1 16:14
 */
@Slf4j
public class YootkEvent extends ApplicationEvent {
    /**
     * 数据的保存对象
     * <p>
     *     外部可以访问到该对象
     */
    @Getter
    private final MessageForEvent message;

    /**
     *
     * @param source 数据源（数据发布方）
     * @param message 产生事件的数据
     */
    public YootkEvent(Object source, MessageForEvent message) {
        super(source);
        this.message = message;
    }

    /**
     * 自定义的方法
     * <p>
     *     当产生事件之后，可以对该方法进行调用
     */
    public void fire() {
        log.info("message={}", message);
    }
}
