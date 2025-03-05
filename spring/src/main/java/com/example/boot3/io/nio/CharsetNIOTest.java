package com.example.boot3.io.nio;

import lombok.SneakyThrows;
import org.junit.Test;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;

/**
 * @author caimeng
 * @date 2025/3/3 15:00
 */
public class CharsetNIOTest {
    /**
     * 测试：NIO的字符串编解码
     */
    @SneakyThrows
    @Test
    public void charsetTest() {
        // 获取指定的编码集
        //noinspection CharsetObjectCanBeUsed
        Charset charset = Charset.forName("UTF-8");
        // 获取编码器
        CharsetEncoder encoder = charset.newEncoder();
        // 获取解码器
        CharsetDecoder decoder = charset.newDecoder();
        // 创建字符串缓冲区
        CharBuffer cb = CharBuffer.wrap("沐言科技：www.yootk.com");
        // 编码器针对缓冲区进行编码
        ByteBuffer buffer = encoder.encode(cb);
        // 对缓冲区进行解码
        System.out.println(decoder.decode(buffer));
    }
}
