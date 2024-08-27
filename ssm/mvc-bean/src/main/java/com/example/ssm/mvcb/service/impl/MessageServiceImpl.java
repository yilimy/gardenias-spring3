package com.example.ssm.mvcb.service.impl;

import com.example.ssm.mvcb.service.IMessageService;
import org.springframework.stereotype.Service;

/**
 * @author caimeng
 * @date 2024/8/27 14:16
 */
@Service
public class MessageServiceImpl implements IMessageService {
    @Override
    public String echo(String message) {
        return "【ECHO】" + message;
    }
}
