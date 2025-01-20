package com.gardenia.web.action;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author caimeng
 * @date 2025/1/20 11:18
 */
@Slf4j
@RestController     // SpringMVC中的注解，基于Rest架构的处理
@RequestMapping("/message/*")   // 添加父路径
public class MessageAction {
    @RequestMapping("/echo")    // 子路径
    public String echo(String msg) {    // 进行请求参数的接收和请求内容的回应
        log.info("接收msg的请求参数，内容为:{}", msg);
        return "【ECHO】" + msg;
    }
    @RequestMapping("/home")    // 映射目录
    public String home() {
        return "Hello World!";
    }   // 响应的数据信息
}
