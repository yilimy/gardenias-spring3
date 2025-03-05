package com.example.boot3.io.channel;

import lombok.SneakyThrows;
import org.junit.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.util.concurrent.TimeUnit;

/**
 * 测试：NIO的文件锁
 * <p>
 *     文件锁不是java层的锁，是操作系统的锁。
 * @author caimeng
 * @date 2025/3/3 13:56
 */
public class FileChannelTest {
    @SneakyThrows
    @Test
    public void lockTest() {
        String filePath = "E:\\tmp\\pdf\\channel.txt";
        File file = new File(filePath);
        // 追加状态，打开文件输出流
        try (FileOutputStream fos = new FileOutputStream(file)) {
            // 获取文件通道
            FileChannel channel = fos.getChannel();
            // 获取文件锁
            FileLock fileLock = channel.tryLock();
            if (fileLock != null) {
                System.out.println(file.getName() + ", 文件被锁定30秒");
                TimeUnit.SECONDS.sleep(30);
                fileLock.release();
                System.out.println(file.getName() + ", 文件解除锁定");
            }
            channel.close();
        }
    }
}
