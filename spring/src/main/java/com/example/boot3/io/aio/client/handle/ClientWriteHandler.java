package com.example.boot3.io.aio.client.handle;

import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

/**
 * 客户端异步写入handler
 * @author caimeng
 * @date 2025/2/28 16:54
 */
public class ClientWriteHandler implements CompletionHandler<Integer, ByteBuffer> {
    private final AsynchronousSocketChannel asynchronousSocketChannel;

    public ClientWriteHandler(AsynchronousSocketChannel asynchronousSocketChannel) {
        this.asynchronousSocketChannel = asynchronousSocketChannel;
    }

    @Override
    public void completed(Integer result, ByteBuffer buffer) {
        if (buffer.hasRemaining()) {
            // 发送完成之后，继续找本类进行处理
            asynchronousSocketChannel.write(buffer, buffer, this);
        } else {    // 缓冲区没有数据了,准备异步从客户端读取数据
            // 分配缓冲区
            ByteBuffer readBuffer = ByteBuffer.allocate(50);
            // 要开始读数据
            asynchronousSocketChannel.read(readBuffer, readBuffer, new ClientReadHandler(asynchronousSocketChannel));
        }
    }

    @Override
    public void failed(Throwable exc, ByteBuffer attachment) {
        try {
            asynchronousSocketChannel.close();
            System.out.println("【ClientWriteHandler.failed()】发送数据失败,关闭channel, message=" + exc.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
