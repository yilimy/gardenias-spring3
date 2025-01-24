package com.gardenia.web.config.handler;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;

import java.io.IOException;

/**
 * 视频请求处理器
 * <p>
 *     本例中使用了软链接
 *     ``` cmd
 *          mklink /D linkFolder D:\mnt\logs\nas\video
 *     ```
 * @author caimeng
 * @date 2025/1/24 10:54
 */
@Component
public class VideoResourceHttpRequestHandler extends ResourceHttpRequestHandler {

    @Override
    protected Resource getResource(@NonNull HttpServletRequest request) {
        return new ClassPathResource("/video/linkFolder/1b12174477bd5a32799ed7ba6d83522d.mp4");
    }
}
