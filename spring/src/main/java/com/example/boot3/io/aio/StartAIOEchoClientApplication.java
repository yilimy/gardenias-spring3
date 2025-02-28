package com.example.boot3.io.aio;

import com.example.boot3.io.InputUtil;
import com.example.boot3.io.aio.client.EchoClient;
import com.example.boot3.io.aio.server.EchoServer;

import java.util.concurrent.TimeUnit;

/**
 * @author caimeng
 * @date 2025/2/28 17:16
 */
public class StartAIOEchoClientApplication {

    public static void main(String[] args) throws Exception {
        EchoClient echoClient = new EchoClient("127.0.0.1", EchoServer.PORT);
        while (echoClient.send(InputUtil.getString("请输入要发送的消息: "))) {
            TimeUnit.MILLISECONDS.sleep(100);
        }
    }
}
