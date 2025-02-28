package com.example.boot3.io.aio.client;

import com.example.boot3.io.aio.client.handle.ClientWriteHandler;
import com.example.boot3.io.aio.server.handle.EchoMessageHandle;

import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.util.concurrent.TimeUnit;

/**
 * @author caimeng
 * @date 2025/2/27 15:56
 */
public class EchoClient {
    private AsynchronousSocketChannel asynchronousSocketChannel;

    public EchoClient(String host, int port) {
        try {
            // 打开客户端通道
            asynchronousSocketChannel = AsynchronousSocketChannel.open();
            // 客户端连接服务器
            asynchronousSocketChannel.connect(new java.net.InetSocketAddress(host, port)).get();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 发送消息
     * @param message 消息
     */
    public boolean send(String message) throws Exception {
        ByteBuffer buffer = ByteBuffer.allocate(100);
        buffer.put(message.getBytes());     // 添加消息
        // 缓冲区翻转，准备写入数据
        buffer.flip();
        /*
         * 发送消息
         * 应该由Write来处理后续操作，由Write的回调来触发Read的回调
         */
        asynchronousSocketChannel.write(buffer, buffer, new ClientWriteHandler(asynchronousSocketChannel));
        if (EchoMessageHandle.EXIT.equalsIgnoreCase(message)) {
            // 等待异步响应的结果
            TimeUnit.SECONDS.sleep(1);
            return false;
        }
        return true;
    }
}
