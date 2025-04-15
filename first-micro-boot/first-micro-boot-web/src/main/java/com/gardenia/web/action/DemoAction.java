package com.gardenia.web.action;

import cn.hutool.core.map.MapUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author caimeng
 * @date 2025/4/3 20:10
 */
@RestController
public class DemoAction {
    @RequestMapping("/gaexchange/v1/appInfoToAnonymousID")
    public Object get() {
        return MapUtil.of("data", "hello");
    }
}
