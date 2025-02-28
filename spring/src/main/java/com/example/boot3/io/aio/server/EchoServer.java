package com.example.boot3.io.aio.server;

import com.example.boot3.io.aio.server.handle.EchoServerAcceptHandler;
import lombok.Getter;

import java.nio.channels.AsynchronousServerSocketChannel;

/**
 * @author caimeng
 * @date 2025/2/27 15:53
 */
public class EchoServer implements AutoCloseable {
    /**
     * AIO 的服务端Channel。
     * 只有获取到服务端Channel，才能进行客户端Channel的种种操作
     */
    @Getter
    private AsynchronousServerSocketChannel serverSocketChannel;
    // 定义服务监听端口
    public static final int PORT = 9090;

    public EchoServer() {
        this(PORT);
    }

    public EchoServer(int port) {
        try {
            // 创建服务端异步Channel
            serverSocketChannel = AsynchronousServerSocketChannel.open()
                    // 绑定端口
                    .bind(new java.net.InetSocketAddress(port));
            System.out.println("服务端启动成功, 监听端口port=" + port + "等待客户端连接...");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 监听客户端Channel
     */
    public void start() {
        // 执行连接
        serverSocketChannel.accept(this, new EchoServerAcceptHandler());
    }

    @Override
    public void close() throws Exception {
        serverSocketChannel.close();
    }
}
