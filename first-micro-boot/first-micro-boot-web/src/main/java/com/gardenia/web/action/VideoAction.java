package com.gardenia.web.action;

import com.gardenia.web.config.handler.VideoResourceHttpRequestHandler;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 返回视频资源
 * <p>
 *     1. 配置视频资源获取器
 *          {@link VideoResourceHttpRequestHandler#getResource(HttpServletRequest)}
 *     2. 注入资源管理器 VideoResourceHttpRequestHandler
 * <p>
 *     只需要在项目中配置好相应的资源路径后，就可以实现资源的加载，同时可以在页面上进行显式。
 * @author caimeng
 * @date 2025/1/24 15:22
 */
@Controller
@RequestMapping("/data/*")
public class VideoAction {
    @Autowired
    private VideoResourceHttpRequestHandler videoResourceHttpRequestHandler;

    @SneakyThrows
    @GetMapping("video")
    public void createVideoData(HttpServletRequest request, HttpServletResponse response) {
        videoResourceHttpRequestHandler.handleRequest(request, response);
    }
}
