package com.gardenia.web.event;

import com.gardenia.web.event.listener.AnnotationListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 自定义条件判断对象
 *
 * @author caimeng
 * @date 2025/4/1 18:11
 */
@SuppressWarnings("unused")
@Slf4j
@Component
public class MyConditionChecker {

    /**
     * 在监听器中进行判断
     * @see AnnotationListener#onApplicationEvent2(YootkEvent)
     *
     * @param message 判断参数
     * @return 判断结果
     */
    public boolean checkMessage(String message) {
        boolean ret = "测试事件".equals(message);
        if (!ret) {
            log.info("判断器失败，不执行业务");
        }
        return ret;
    }
}
