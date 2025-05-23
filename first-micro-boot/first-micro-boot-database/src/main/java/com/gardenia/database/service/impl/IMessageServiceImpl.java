package com.gardenia.database.service.impl;

import com.gardenia.database.service.IMessageService;
import org.springframework.stereotype.Service;

/**
 * @author caimeng
 * @date 2025/5/22 11:11
 */
@Service
public class IMessageServiceImpl implements IMessageService {
    @Override
    public String echo(String msg) {
        return "【ECHO】" + msg;
    }
}
