package com.example.boot3.io.nio;

import lombok.SneakyThrows;
import org.junit.Test;

import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 测试 NIO 监控
 * <p>
 *     这种监控是由 NIO 之中直接提供的，本身是调用了操作系统的具体实现形式，属于子工具。
 * @author caimeng
 * @date 2025/3/4 14:00
 */
public class WatchableTest {
    private final AtomicBoolean running = new AtomicBoolean(true);

    @Test
    public void watch() {
        startWatchMission();
        fileOperatorTask();
    }

    @SneakyThrows
    protected void fileOperatorTask() {
        Path filePath = Path.of("d:", "mnt", "logs", "nas", "testGroup", "watchable.txt");
        while (running.get()) {
            if (Files.exists(filePath)) {
                Files.delete(filePath);
            } else {
                Files.createFile(filePath);
            }
            TimeUnit.SECONDS.sleep(2);
        }
    }

    protected void startWatchMission() {
        new Thread(() -> {
            Path path = Path.of("d:", "mnt", "logs", "nas", "testGroup");
            // 获取监控服务
            try {
                WatchService watchService = FileSystems.getDefault().newWatchService();
                // 注册监控服务，对文件的创建和删除进行事件监控
                WatchKey watchKey = path.register(watchService,
                        StandardWatchEventKinds.ENTRY_CREATE,
                        StandardWatchEventKinds.ENTRY_DELETE);
                while (true) {
                    /*
                     * 抓取事件，并进行输出.
                     * 原来，windows的重命名会发送两个事件广播，先删除，再创建
                     */
                    watchKey.pollEvents().forEach(event -> {
                        if (event.kind() == StandardWatchEventKinds.ENTRY_CREATE) {
                            System.out.println("文件创建：" + event.context());
                        } else if (event.kind() == StandardWatchEventKinds.ENTRY_DELETE) {
                            System.out.println("文件删除：" + event.context());
                        } else {
                            System.out.println("其他事件：" + event.context());
                        }
                    });
                    // 重置监控服务，重新进行监控
                    watchKey.reset();
                    TimeUnit.SECONDS.sleep(1);
                }
            } catch (Exception e) {
                running.set(false);
                throw new RuntimeException(e);
            }
        }).start();
    }
}
