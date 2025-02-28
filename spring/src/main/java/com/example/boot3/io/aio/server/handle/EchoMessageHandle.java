package com.example.boot3.io.aio.server.handle;

import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

/**
 * 返回：读取到的字节数
 * 输入：Buffer
 * @author caimeng
 * @date 2025/2/27 16:07
 */
public class EchoMessageHandle implements CompletionHandler<Integer, ByteBuffer>, AutoCloseable {
    /**
     * 客户端Channel
     */
    private final AsynchronousSocketChannel socketChannel;
    /*
     * 按照原始的BIO、NIO、AIO的模型，这里使用一个boolean变量来标识是否退出，
     * 控制交互的循环
     */
    private boolean exit;
    public static final String EXIT = "exit";
    public EchoMessageHandle(AsynchronousSocketChannel socketChannel) {
        this.socketChannel = socketChannel;
    }
    @Override
    public void completed(Integer result, ByteBuffer attachment) {
        // 数据在缓冲区中，所以需要翻转一下
        attachment.flip();
        // 获取客户端发送来的请求数据
        String message = new String(attachment.array(), 0, attachment.remaining()).trim();
        System.out.println("【EchoMessageHandle.completed()】接收客户端数据: " + message);
        // 拼凑响应结果
        String response = "【Echo】" + message;
        if (EXIT.equalsIgnoreCase(message)) { // 如果客户端发送的是exit，则退出交互
            System.out.println("【EchoMessageHandle.completed()】客户端退出");
            response = "【Echo】拜拜，下次再见！";
            exit = true;
        }
        echoWrite(response);
    }

    @Override
    public void failed(Throwable exc, ByteBuffer attachment) {
        try {
            close();
            System.out.println("【EchoMessageHandle.failed()】读取客户端数据失败, message=" + exc.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void echoWrite(String result) {   // 进行响应内容的输出
        // 申请缓冲区
        ByteBuffer buffer = ByteBuffer.allocate(100);
        // 向缓冲区中写入数据
        buffer.put(result.getBytes());
        // 缓冲区翻转
        buffer.flip();
        // 向客户端写入数据
        socketChannel.write(
                buffer,
                buffer,
                // 写入数据的后续处理（回调处理）
                new CompletionHandler<>() {
                    @Override
                    public void completed(Integer result, ByteBuffer attachment) {
                        if (attachment.hasRemaining()) {    // 有数据
                            EchoMessageHandle.this.socketChannel.write(buffer, buffer, this);
                        } else {
                            // 如果没有数据了
                            if (!exit) {    // 没有退出，则继续读取客户端数据
                                // 分配缓冲区
                                ByteBuffer buffer = ByteBuffer.allocate(100);
                                // 开始新一轮的读取
                                // @see com.example.boot3.io.aio.server.handle.EchoServerAcceptHandler.completed
                                socketChannel.read(buffer, buffer, new EchoMessageHandle(socketChannel));
                            }
                        }
                    }

                    @Override
                    public void failed(Throwable exc, ByteBuffer attachment) {
                        System.out.println("【EchoMessageHandle.echoWrite.failed()】读取客户端数据失败, message=" + exc.getMessage());
                        EchoMessageHandle.this.failed(exc, attachment);
                    }
                });
    }

    @Override
    public void close() throws Exception {
        socketChannel.close();
        System.out.println("【EchoMessageHandle.close()】关闭消息处理通道");
    }
}
