package com.gardenia.database.action;

import com.gardenia.database.service.IMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author caimeng
 * @date 2025/5/20 18:12
 */
@RestController
@RequestMapping("/message/*")
public class MessageAction {
    @Autowired
    private IMessageService messageService;

    @RequestMapping("echo")
    public Object echo(String msg){
        return IntStream.range(0, 10).mapToObj(i -> "【ECHO】" + i + " " + msg).collect(Collectors.toList());
    }

    /**
     * 测试 Druid-Spring 监控
     * @param msg 参数
     * @return 测试结果
     */
    @RequestMapping("monitor")
    public Object monitor(String msg){
        return messageService.echo(msg);
    }
}
