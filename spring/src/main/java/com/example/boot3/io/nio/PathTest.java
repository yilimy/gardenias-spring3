package com.example.boot3.io.nio;

import lombok.SneakyThrows;
import org.junit.Test;

import java.nio.file.FileStore;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * 测试 java.nio.file 包中的相关功能
 * @author caimeng
 * @date 2025/3/3 16:17
 */
public class PathTest {

    @SneakyThrows
    @Test
    public void test() {
        Path path = Path.of("e:", "tmp", "pdf", "channel.txt");
        // 文件的完整路径: e:\tmp\pdf\channel.txt
        System.out.println("文件的完整路径: " + path);
        // 文件的根路径: e:\
        System.out.println("文件的根路径: " + path.getRoot());
        // 文件系统类型: sun.nio.fs.WindowsFileSystem@566776ad
        System.out.println("文件系统类型: " + path.getFileSystem());
        if (Files.exists(path)) {
            System.out.println("文件存在");
            // 要求文件夹 D:\mnt\logs\nas\testGroup 必须存在，目标文件 D:\mnt\logs\nas\testGroup\channel.txt 不存在
            Path targetPath = Path.of("d:", "mnt", "logs", "nas", "testGroup", "channel.txt");
            // 文件拷贝
            Files.copy(path, targetPath);
        }
    }

    /**
     * 测试：通过NIO获取磁盘信息
     */
    @Test
    @SneakyThrows
    public void fileStoreTest() {
        Path path = Path.of("e:", "tmp", "pdf", "channel.txt");
        FileStore fileStore = Files.getFileStore(path);
        // 【FileStore】存储格式: NTFS
        System.out.println("【FileStore】存储格式: " + fileStore.type());
        // 【FileStore】磁盘空间: 179769962496
        System.out.println("【FileStore】磁盘空间: " + fileStore.getTotalSpace());
        // 【FileStore】空闲空间: 127871451136
        System.out.println("【FileStore】空闲空间: " + fileStore.getUnallocatedSpace());
    }
}
