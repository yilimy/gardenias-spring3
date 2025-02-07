package com.gardenia.web.action;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * @author caimeng
 * @date 2025/2/7 18:35
 */
@RestController
@RequestMapping("/orders/*")
public class OrderAction {

    /**
     * 测试：过滤器的执行顺序
     * <p>
     *     测试结果
     *     order[-100]  【YootkFilter】******************************
     *     order[2]  【EduFilter】******************************
     *     order[5]  【MuYanFilter】******************************
     * @return 结果
     */
    @RequestMapping("echo")
    public Object echo() {
        return new Date();
    }
}
