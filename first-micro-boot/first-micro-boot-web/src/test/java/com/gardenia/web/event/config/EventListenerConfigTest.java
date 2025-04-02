package com.gardenia.web.event.config;

import com.gardenia.web.MicroBootWebApplication;
import com.gardenia.web.event.YootkEvent;
import com.gardenia.web.vo.MessageForEvent;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * 测试：EventListener注解的使用
 * @author caimeng
 * @date 2025/4/2 10:01
 */
@WebAppConfiguration    // 启用web运行环境
@SpringBootTest(classes = MicroBootWebApplication.class)
public class EventListenerConfigTest {
    @Autowired  // 由Spring容器提供
    private ApplicationEventPublisher publisher;

    @Test
    public void startEventTest() {
        /*
         * 没有执行任何逻辑，
         * 观察spring启动时，发布的所有事件，并在
         * com.gardenia.web.event.config.EventListenerConfig.HandleAllEvent
         * 中进行监听
         * ---------------
         */
    }

    @Test
    public void publishYootkEventTest() {
        publisher.publishEvent(new YootkEvent(
                this,
                new MessageForEvent("测试事件", "www.yootk.com")));
    }

    /**
     * 测试：直接发布消息对象
     */
    @Test
    public void publishContentTest() {
        // 【handleYootkEventByContent】MessageForEvent(title=测试事件, url=www.yootk.com)
        publisher.publishEvent(new MessageForEvent("测试事件", "www.yootk.com"));
    }
}
