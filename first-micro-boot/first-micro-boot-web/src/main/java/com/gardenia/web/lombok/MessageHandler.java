package com.gardenia.web.lombok;

import lombok.SneakyThrows;

/**
 * @author caimeng
 * @date 2025/1/22 17:00
 */
@SuppressWarnings("unused")
public class MessageHandler {

    @SneakyThrows       // 会自动生成try...catch
    public void print(String message) {
        System.out.println("message = " + message);
    }
}
