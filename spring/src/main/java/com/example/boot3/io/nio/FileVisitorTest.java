package com.example.boot3.io.nio;

import lombok.SneakyThrows;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;

/**
 * @author caimeng
 * @date 2025/3/5 17:18
 */
public class FileVisitorTest {

    /**
     * 测试：访问文件夹中的所有文件和子文件夹中的所有文件
     * <p>
     *     1. 打印访问的目录
     *     2. 打印所有的java文件
     */
    @SneakyThrows
    @Test
    public void iterateTest () {
        // 待遍历路径
        Path path = Path.of("d:", "mnt", "logs", "nas");
        FileVisitor<Path> fileVisitor = new FileVisitor<>() {
            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) {
                System.out.println("【目录】" + dir);
                if (dir.getFileName().toString().startsWith(".")) {
                    // 目录以点开头，跳过
                    return FileVisitResult.SKIP_SUBTREE;
                }
                // 继续访问
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
                // 如果是文件
                if (file.toString().endsWith("java")) {
                    /*
                     * 如果是java文件，打印一下
                     */
                    System.out.println("【文件】" + file);
                }
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFileFailed(Path file, IOException exc) {
                // 访问文件失败，不影响，继续
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult postVisitDirectory(Path dir, IOException exc) {
                // 目录访问失败，则跳过子目录
                return FileVisitResult.SKIP_SIBLINGS;
            }
        };
        Path resultPath = Files.walkFileTree(path, fileVisitor);
        // walk结果: d:\mnt\logs\nas
        System.out.println("walk结果: " + resultPath);
    }
}
