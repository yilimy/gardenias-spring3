package com.gardenia.web.action;

import com.gardenia.web.config.ImageWebConfig;
import lombok.SneakyThrows;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.util.List;

/**
 * 在进行前后端分离设计的时候，需要进行资源的加载，一般会有两种做法：
 *      1. 直接通过远程的文件服务器进行资源的加载操作，例如设置一个fastDFS、七牛云、对象存储等
 *      2. 通过程序来实现加载，方便进行验证上的控制
 * 程序在进行图像返回的时候，需要将返回的类型设置为图片，
 * 但是如果要进行返回处理的时候需要追加有一个转换的配置类，在SpringBoot里最大的特点是能够直接返回。
 * @author caimeng
 * @date 2025/1/24 10:14
 */
@RestController     // 直接响应处理
@RequestMapping("/data/*")
public class ImageAction {

    /**
     * 图像创建
     * <p>
     *     按Spring的原始处理方式，如果此时返回的是一个对象，则会基于Jackson将对象信息转为json数据。
     *     但既然是图像，肯定不能以文本的形式显式，那么就需要追加一个新的转换器。
     *     {@link ImageWebConfig#extendMessageConverters(List)}
     * @return 图片对象<p>
     *     成功时，正常返回图像；失败时，错误码500
     */
    @SneakyThrows
    @RequestMapping(value = "image",
            // 操作形式
            produces = {MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_GIF_VALUE, MediaType.IMAGE_JPEG_VALUE})
    public BufferedImage createImageData() {
        ClassPathResource imageResource = new ClassPathResource("/images/IMG_0669.png");
        return ImageIO.read(imageResource.getInputStream());
    }
}
