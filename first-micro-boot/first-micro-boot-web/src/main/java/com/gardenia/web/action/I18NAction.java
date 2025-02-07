package com.gardenia.web.action;

import cn.hutool.core.map.MapUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;

/**
 * @author caimeng
 * @date 2025/2/7 14:59
 */
@RestController
@RequestMapping("/i18n/*")
public class I18NAction {
    @Autowired
    private MessageSource messageSource;    // 消息资源
    @RequestMapping("base")
    public Object showBase() {
        return MapUtil.builder()
                // 没有占位符，args=null
                .put("message", messageSource.getMessage("yootk.message", null, Locale.getDefault()))
                .put("url", messageSource.getMessage("yootk.url", null, Locale.getDefault()))
                .build();
    }

    @RequestMapping("locale")
    public Object showLocal(Locale loc) {
        return MapUtil.builder()
                // 没有占位符，args=null
                .put("message", messageSource.getMessage("yootk.message", null, loc))
                .put("url", messageSource.getMessage("yootk.url", null, loc))
                .build();
    }
}
