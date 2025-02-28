package com.example.boot3.io.aio.client.handle;

import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

/**
 * @author caimeng
 * @date 2025/2/28 16:59
 */
public class ClientReadHandler implements CompletionHandler<Integer, ByteBuffer> {
    private final AsynchronousSocketChannel asynchronousSocketChannel;

    public ClientReadHandler(AsynchronousSocketChannel asynchronousSocketChannel) {
        this.asynchronousSocketChannel = asynchronousSocketChannel;
    }

    @Override
    public void completed(Integer result, ByteBuffer buffer) {
        // 缓冲区翻转，准备读取数据
        buffer.flip();
        // 读取数据
        String message = new String(buffer.array(), 0, buffer.remaining());
        System.out.println("【ClientReadHandler.completed】客户端收到消息：" + message);
    }

    @Override
    public void failed(Throwable exc, ByteBuffer buffer) {
        try {
            asynchronousSocketChannel.close();
            System.out.println("【ClientReadHandler.failed()】读取客户端数据失败,关闭channel, message=" + exc.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
