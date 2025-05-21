package com.gardenia.database.action;

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

    @RequestMapping("echo")
    public Object echo(String msg){
        return IntStream.range(0, 10).mapToObj(i -> "【ECHO】" + i + " " + msg).collect(Collectors.toList());
    }
}
