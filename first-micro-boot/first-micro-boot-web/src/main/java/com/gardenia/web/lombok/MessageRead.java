package com.gardenia.web.lombok;

import lombok.Cleanup;
import lombok.Data;
import lombok.SneakyThrows;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

/**
 * @author caimeng
 * @date 2025/1/22 17:06
 */
@Data
public class MessageRead {
    // 文件名
    private String fileName;
    // 文件路径
    private String filePath;

    @SuppressWarnings("unused")
    @SneakyThrows   // 自动处理异常
    public String load() {
        @Cleanup    // 自动关闭流
        InputStream input = new FileInputStream(new File(filePath, fileName));
        byte[] data = new byte[1024];
        int len = input.read(data);
        return new String(data, 0, len);
    }
}
