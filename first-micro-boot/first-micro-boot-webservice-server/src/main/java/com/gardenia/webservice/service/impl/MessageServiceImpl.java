package com.gardenia.webservice.service.impl;

import com.gardenia.webservice.common.service.IMessageService;
import org.springframework.stereotype.Service;

import javax.jws.WebService;

/**
 * @author caimeng
 * @date 2025/4/9 11:52
 */
@WebService(
        name = "MessageService",
        // 接口的命名空间
        targetNamespace = "http://service.common.webservice.gardenia.com",
        // 终端描述，匹配的是接口的名称
        endpointInterface = "com.gardenia.webservice.common.service.IMessageService"
)
@Service    // 进行bean注册
public class MessageServiceImpl implements IMessageService {
    @Override
    public String echo(String msg) {
        return "【echo】" + msg;
    }
}
