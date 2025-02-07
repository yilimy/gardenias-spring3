package com.gardenia.web.action;

import com.gardenia.common.action.abs.AbstractBaseAction;
import com.gardenia.web.vo.Message;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;
import java.util.Optional;

/**
 * @author caimeng
 * @date 2025/2/7 16:13
 */
@RestController
@RequestMapping("/form/*")
public class UploadAction extends AbstractBaseAction {

    /**
     * 测试文件上传
     * <code>
     *     curl -X POST -F "photo=@E:\Pictures\IMG_0669.png" -F "title=沐言科技" -F "content=www.yootk.com" -F "pubDate=2025-01-23" http://localhost:8080/form/upload
     * </code>
     * <p>
     *     经测试，windows的powerShell不支持该curl命令，windows的git客户端支持该curl命令（但是客户端的中文解码有问题）
     * @param message 附加的请求参数
     * @param photo 上传的文件
     * @return 返回临时文件信息等
     */
    @SuppressWarnings("JavadocLinkAsPlainText")
    @PostMapping("upload")
    public Object uploadHandler(Message message, MultipartFile photo) {
        return Map.of(
                "message", message,
                "photoName", photo.getName(),
                "photoOriginalFilename", Optional.of(photo).map(MultipartFile::getOriginalFilename).orElse(""),
                "photoContentType", Optional.of(photo).map(MultipartFile::getContentType).orElse(""),
                "photoSize", photo.getSize()
        );
    }
}
