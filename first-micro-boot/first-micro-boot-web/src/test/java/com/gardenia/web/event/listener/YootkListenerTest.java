package com.gardenia.web.event.listener;

import com.gardenia.web.MicroBootWebApplication;
import com.gardenia.web.event.YootkEvent;
import com.gardenia.web.vo.MessageForEvent;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * 测试事件监听和发布
 * @author caimeng
 * @date 2025/4/1 16:42
 */
@WebAppConfiguration    // 启用web运行环境
@SpringBootTest(classes = MicroBootWebApplication.class)
public class YootkListenerTest {
    @Autowired  // 由Spring容器提供
    private ApplicationEventPublisher publisher;

    @Test
    public void publishTest() {
        /*
         * 事件处理: com.gardenia.web.event.YootkEvent[source=com.gardenia.web.event.listener.YootkListenerTest@204a02a4]
         * message=MessageForEvent(title=测试事件, url=www.yootk.com)
         */
        publisher.publishEvent(new YootkEvent(
                this,
                new MessageForEvent("测试事件1", "www.yootk.com")));
    }
}
