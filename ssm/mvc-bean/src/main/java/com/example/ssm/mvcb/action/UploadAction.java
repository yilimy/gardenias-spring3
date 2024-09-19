package com.example.ssm.mvcb.action;

import cn.hutool.core.map.MapUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.UUID;

/**
 * 上传文件的控制器
 * @author caimeng
 * @date 2024/9/18 11:27
 */
@Slf4j
@Controller
@RequestMapping("/pages/common/*")
public class UploadAction {

    @ResponseBody
    @PostMapping("/upload")
    public Object get(String id, MultipartFile photo) {
        return MapUtil.<String, Object>builder()
                .put("id", id)
                // 上传文件的类型
                .put("ContentType", photo.getContentType())
                // 文件的原始名称
                .put("OriginalFilename", photo.getOriginalFilename())
                // 文件大小
                .put("Size", photo.getSize())
                // 保存的文件名称
                .put("SaveFileName", save(photo))
                .build();
    }

    /**
     * 上传文件的输入页面
     * <a href="http://localhost/pages/common/input" />
     * @return 页面地址
     */
    @GetMapping("/input")
    public String input() {
        return "/pages/common/input.jsp";
    }

    /**
     * 保存文件
     * @param file 上传文件对象
     * @return 文件名称
     */
    @SneakyThrows
    @SuppressWarnings("DataFlowIssue")
    protected String save(MultipartFile file) {
        String contentType = file.getContentType();
        /*
         * 根据MIME类型获取文件的后缀，而文件的名称通过UUID生成
         */
        String fileName = UUID.randomUUID() + "." + contentType.substring(contentType.lastIndexOf("/") + 1);
        log.info("生成文件的名称:{}", fileName);
        String filePath = ContextLoader.getCurrentWebApplicationContext().getServletContext().getRealPath("/upload/") + fileName;
        log.info("文件的存储路径:{}", filePath);
        file.transferTo(new File(filePath));
        return fileName;
    }
}
