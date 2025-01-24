package com.gardenia.web.action;

import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.InputStream;

/**
 * @author caimeng
 * @date 2025/1/24 15:53
 */
@RestController
@RequestMapping("/data/*")
public class DownloadAction {

    @SneakyThrows
    @GetMapping("download")
    public void fileDownload(HttpServletResponse response) {
        /*
         * 1. 强制文件下载
         * 2. 非标准的 MIME 类型，它通过欺骗浏览器，使其认为文件类型未知，从而强制下载
         * 3. 在某些设备或浏览器，比如某些版本的 Android 浏览器中，可能会导致下载失败
         * 4. 替代方案: 使用标准的 MIME 类型（如 application/octet-stream）结合 Content-Disposition 来实现文件下载
         */
        response.setContentType("application/force-download");
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION,
                ContentDisposition.attachment().filename("data_" + System.currentTimeMillis() + ".rar").build().toString());
        ClassPathResource resource = new ClassPathResource("/files/data.rar");
        // 通过IO流读取文件内容，进行文件下载操作
        InputStream is = resource.getInputStream();     // 获取资源的输入流
        byte[] data = new byte[1024];  // 每次读取1024字节
        int len;    // 每次读取的长度
        while ((len = is.read(data)) != -1) {
            // 追加写入 data[0, len]
            response.getOutputStream().write(data, 0, len);
        }
    }
}
