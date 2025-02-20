package com.gardenia.web.action;

import com.gardenia.web.advice.GlobalDataBindAdvice;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * 测试：全局数据绑定
 * <a href="https://www.bilibili.com/video/BV1j9Fde5EUR/" />
 * <p>
 *     每次 请求，都会执行一次全局数据绑定，所以，可以在全局数据绑定中，添加一些通用的数据，比如：
 *     1. HTTP状态码
 *     2. SessionID
 *     3. 认证数据
 *     那么，可以使用全局数据绑定，进行统一处理，减少重复编写。
 * @see GlobalDataBindAdvice#bindData()
 * @author caimeng
 * @date 2025/2/20 14:49
 */
@RestController
@RequestMapping("/global/*")
public class GlobalDataAction {

    /**
     * 实现了 Model 接口，可以获取全局数据模型
     * @param message 业务参数
     * @param model 模型参数
     * @return 方法调用结果
     */
    @RequestMapping("echo")
    public Object echo(String message, Model model) {
        //noinspection unchecked
        Map<String, Object> map = (Map<String, Object>) model.asMap().get(GlobalDataBindAdvice.BIND_MODEL_KEY);
        map.put("data", "【echo】" + message);    // 不同的处理，有可能返回不同的结果
        return map;
    }

    /**
     * 该方法没有绑定模型，不会触发全局数据绑定
     * 对照组 {@link GlobalDataAction#echo(String, Model)}
     * @param x 参数一
     * @param y 参数二
     * @return 计算结果
     */
    @RequestMapping("calc")
    public Object calc(int x, int y) {
        Map<String, Object> map = new HashMap<>();
        map.put("title", "沐言科技");   // 所有的响应都需要提供有此类数据
        map.put("content", "www.yootk.com");   // 所有的响应都需要提供有此类数据
        map.put("data", "【calc】" + (x / y));    // 不同的处理，有可能返回不同的结果
        return map;
    }
}
