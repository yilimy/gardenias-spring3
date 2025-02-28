package com.example.boot3.io.aio;

import com.example.boot3.io.aio.server.EchoServer;

import java.util.concurrent.TimeUnit;

/**
 * @author caimeng
 * @date 2025/2/28 16:46
 */
public class StartAIOEchoServerApplication {
    public static void main(String[] args) throws Exception {
        try (EchoServer echoServer = new EchoServer()) {
            echoServer.start();
            // 阻塞主线程
            TimeUnit.MILLISECONDS.sleep(Long.MAX_VALUE);
        }
    }
}
