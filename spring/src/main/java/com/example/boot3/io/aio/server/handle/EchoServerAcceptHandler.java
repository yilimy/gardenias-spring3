package com.example.boot3.io.aio.server.handle;

import com.example.boot3.io.aio.server.EchoServer;

import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

/**
 * <p>
 *     主要用于客户端的连接操作
 * @author caimeng
 * @date 2025/2/27 15:54
 */
public class EchoServerAcceptHandler implements CompletionHandler<
        // 客户端Channel
        AsynchronousSocketChannel,
        // 操作的对象
        EchoServer> {   // AIO的处理程序类
    @Override
    public void completed(AsynchronousSocketChannel channel, EchoServer attachment) {
        // 继续接收客户端的连接请求
        attachment
                // 获取服务端Channel
                .getServerSocketChannel()
                // 接受客户端的连接请求
                .accept(attachment, this);
        // 创建服务端的数据缓冲区
        ByteBuffer buffer = ByteBuffer.allocate(100);
        // 当接收到某些异步的事件之后，需要提供一个具体的功能处理类
        channel.read(buffer, buffer,
                // 请求接收之后，需要一个客户端进行处理
                new EchoMessageHandle(channel));
    }

    @Override
    public void failed(Throwable exc, EchoServer attachment) {
        System.out.println("客户端连接失败");
    }
}