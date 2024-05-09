package com.example.boot3.aop.service.impl;

import com.example.boot3.aop.service.IMessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author caimeng
 * @date 2024/5/9 23:09
 */
@Slf4j
@Service
public class IMessageServiceImpl implements IMessageService {
    @Override
    public String echo(String msg) {
        return "【ECHO】: " + msg;
    }
}
