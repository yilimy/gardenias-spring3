package com.example.boot3.io.channel;

import lombok.SneakyThrows;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;

/**
 * @author caimeng
 * @date 2025/2/28 18:09
 */
public class ChannelTest {

    /**
     * 测试通过通道写入数据
     */
    @SneakyThrows
    @Test
    public void writeTest() {
        // 内存输出流
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        // 将内存输出流转换为通道
        WritableByteChannel channel = Channels.newChannel(output);
        // 字节数据
        byte[] bytes = "沐言科技：www.yootk.com".getBytes();
        // 开辟字节缓冲区
        ByteBuffer buffer = ByteBuffer.allocate(bytes.length);
        // 缓冲区存储数据
        buffer.put(bytes);
        // 缓冲区翻转
        buffer.flip();
        // 数据写入
        channel.write(buffer);
        // 关闭缓冲区
        channel.close();
        // 沐言科技：www.yootk.com
        System.out.println(output);
    }

    /**
     * 测试通过通道读取数据
     */
    @SneakyThrows
    @Test
    public void readTest() {
        // 数据保存在内存流之中
        ByteArrayInputStream inputStream = new ByteArrayInputStream("沐言科技：www.yootk.com".getBytes());
        // 将内存输入流转换为通道
        ReadableByteChannel channel = Channels.newChannel(inputStream);
        // 分配缓冲区
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        // 通道读取数据
        int count = channel.read(buffer);
        // 缓冲区翻转
        buffer.flip();
        // 沐言科技：www.yootk.com
        System.out.println(new String(buffer.array(), 0, count));
    }
}
