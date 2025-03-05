package com.example.boot3.io.nio;

import lombok.SneakyThrows;
import org.junit.Test;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.DosFileAttributeView;
import java.nio.file.attribute.DosFileAttributes;
import java.nio.file.attribute.FileAttribute;
import java.nio.file.attribute.FileOwnerAttributeView;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.PosixFilePermissions;
import java.nio.file.attribute.UserDefinedFileAttributeView;
import java.nio.file.attribute.UserPrincipal;
import java.util.Set;

/**
 * @author caimeng
 * @date 2025/3/5 13:48
 */
public class FileAttributeTest {

    /**
     * 创建一个文件，并设置文件的权限
     * <p>
     *     该方法在 windows 环境下运行失败
     */
    @SneakyThrows
    @Test
    public void createAndPermissionTest() {
        // 如果想要观察权限的配置，最佳的做法是在Linux系统上执行，MacOS系统上也可以
        Path path = Path.of("d:", "mnt", "logs", "nas", "happy.txt");
        FileAttribute<Set<PosixFilePermission>> fileAttribute =
                PosixFilePermissions.asFileAttribute(Set.of(
                        PosixFilePermission.OWNER_READ,
                        PosixFilePermission.OWNER_WRITE,
                        PosixFilePermission.OWNER_EXECUTE,
                        PosixFilePermission.GROUP_READ,
                        PosixFilePermission.GROUP_WRITE,
                        PosixFilePermission.OTHERS_READ,
                        PosixFilePermission.OTHERS_WRITE,
                        PosixFilePermission.OTHERS_EXECUTE
                ));
        if (!Files.exists(path.getParent())) {  // 如果父目录不存在，则创建
            Files.createDirectories(path.getParent(), fileAttribute);
        }
        if (!Files.exists(path)) {  // 如果文件不存在，则创建
            Files.createFile(path, fileAttribute);
        }
    }

    @SneakyThrows
    @Test
    public void getOwnerTest() {
        Path path = Path.of("d:", "mnt", "logs", "nas", "happy.txt");
        // 获取文件所有者
        UserPrincipal owner = Files.getOwner(path);
        System.out.println("文件所有者：" + owner);
    }

    @SneakyThrows
    @Test
    public void attributeViewTest() {
        Path path = Path.of("d:", "mnt", "logs", "nas", "testGroup", "watchable2.txt");
        FileOwnerAttributeView fileAttributeView = Files.getFileAttributeView(path, FileOwnerAttributeView.class);
        System.out.println("属性视图的名称：" + fileAttributeView.name());
        System.out.println("文件拥有者：" + fileAttributeView.getOwner());
        // 获取文件基础属性信息
        BasicFileAttributeView baseFileAttributeView = Files.getFileAttributeView(path, BasicFileAttributeView.class);
        BasicFileAttributes basicFileAttributes = baseFileAttributeView.readAttributes();
        // 文件创建时间：2025-03-04T10:24:57.6007627Z, 实际上 2025年3月4日，18:24:57
        System.out.println("文件创建时间：" + basicFileAttributes.creationTime());
        System.out.println("最后访问时间：" + basicFileAttributes.lastAccessTime());
        System.out.println("最后修改时间：" + basicFileAttributes.lastModifiedTime());
        // 45，指字节
        System.out.println("文件长度：" + basicFileAttributes.size());
        // 用户定义的属性项
        UserDefinedFileAttributeView userFileAttributeView = Files.getFileAttributeView(path, UserDefinedFileAttributeView.class);
        userFileAttributeView.write("作者", Charset.defaultCharset().encode("法天象地"));
        userFileAttributeView.write("机构", Charset.defaultCharset().encode("天庭"));
        // 用户定义的属性项, name: user
        System.out.println("用户定义的属性项, name: " + userFileAttributeView.name());
        // 用户定义的属性项, list: [作者, 机构]
        System.out.println("用户定义的属性项, list: " + userFileAttributeView.list());
        for (String name : userFileAttributeView.list()) {
            // 根据读取到的属性，分配缓冲大小
            ByteBuffer buffer = ByteBuffer.allocate(userFileAttributeView.size(name));
            // 将用户属性读取到缓冲区
            userFileAttributeView.read(name, buffer);
            // 缓冲区翻转
            buffer.flip();
            /*
             * 用户定义的属性项, name: 作者, value: 法天象地
             * 用户定义的属性项, name: 机构, value: 天庭
             */
            System.out.println("用户定义的属性项, name: " + name + ", value: " + Charset.defaultCharset().decode(buffer));
        }
    }

    @SneakyThrows
    @Test
    public void dosTest() {
        Path path = Path.of("d:", "mnt", "logs", "nas", "testGroup", "watchable2.txt");
        DosFileAttributeView fileAttributeView = Files.getFileAttributeView(path, DosFileAttributeView.class);
        DosFileAttributes dosFileAttributes = fileAttributeView.readAttributes();
        System.out.println("【文件的隐藏状态】" + dosFileAttributes.isHidden());
        System.out.println("【文件的只读状态】" + dosFileAttributes.isReadOnly());
        // 配置状态
        fileAttributeView.setHidden(false);
        fileAttributeView.setReadOnly(false);
    }
}
