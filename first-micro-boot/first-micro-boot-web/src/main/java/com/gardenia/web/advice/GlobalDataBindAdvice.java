package com.gardenia.web.advice;

import com.gardenia.web.vo.Message;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.HashMap;
import java.util.Map;

/**
 * 全局数据绑定拦截器
 * @author caimeng
 * @date 2025/2/20 15:13
 */
@ControllerAdvice
public class GlobalDataBindAdvice {
    public static final String BIND_MODEL_KEY = "bindModel";
    private final Message message;

    public GlobalDataBindAdvice(Message message) {
        this.message = message;
    }

    /**
     * 全局数据绑定拦截器
     * <p>
     *     此时绑定的是一个数据的内容，至于这个内容具体的KEY是什么，是需要在整个程序开发中协商的。
     * <p>
     *     在进行标准程序开发的时候，不可能后端随意返回数据，除了真正所需要返回的内容外，也一定有大量的附加信息，
     *     这些附加信息就可以通过数据绑定操作处理完成。
     * @return 拦截器返回的数据
     */
    @ModelAttribute(name = "bindModel")
    public Object bindData() {
        System.out.println("全局数据绑定拦截器");
        Map<String, Object> map = new HashMap<>();
        map.put("title", "【YOOTK】" + message.getTitle());   // 所有的响应都需要提供有此类数据
        map.put("content", "【YOOTK】" + message.getTitle());   // 所有的响应都需要提供有此类数据
        return map;
    }
}
