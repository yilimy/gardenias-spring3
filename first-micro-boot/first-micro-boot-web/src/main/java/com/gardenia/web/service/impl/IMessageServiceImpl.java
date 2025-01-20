package com.gardenia.web.service.impl;

import com.gardenia.web.service.IMessageService;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

/**
 * @author caimeng
 * @date 2025/1/20 16:39
 */
@Service    // 原始的Spring开发
@Primary
public class IMessageServiceImpl implements IMessageService {
    @Override
    public String echo(String msg) {
        return "【echo】" + msg;
    }
}
