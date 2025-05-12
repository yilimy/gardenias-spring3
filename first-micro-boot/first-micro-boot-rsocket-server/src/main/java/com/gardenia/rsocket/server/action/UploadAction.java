package com.gardenia.rsocket.server.action;

import com.gardenia.rsocket.type.UploadStatus;
import com.gardenia.rsocket.utils.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.io.IOException;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Map;

/**
 * 文件上传控制层
 * @author caimeng
 * @date 2025/5/12 11:08
 */
@Slf4j
@Controller
public class UploadAction {
    // 文件默认保存在项目目录 /upload/，该目录需要开发者手工创建：D:\\Gardenias\\SpringBoot3Demo\\upload
    @Value("${output.file.path:upload}")
    private Path outputPath;

    /**
     * 文件上传接口
     * <p>
     *     文件上传一次完成不了，每一次返回一个操作的状态
     * @param metadata 请求头信息
     * @param content 二进制数据，最终需要的数据
     * @return 操作状态
     */
    @MessageMapping(Constants.URI_UPLOAD)
    public Flux<UploadStatus> uploadFile(@Headers Map<String, Object> metadata,
                                         @Payload Flux<DataBuffer> content) {
        log.info("【上传路径】outputPath={}", outputPath);
        // 获取文件名
        var fileName = metadata.get(Constants.FILE_NAME);
        // 获取文件后缀
        var fileExt = metadata.get(Constants.FILE_EXT);
        // 获取文件操作路径
        var path = Paths.get(fileName + "." + fileExt);
        log.info("【文件上传】fileName={}, fileExt={}, Path={}", fileName, fileExt, path);
        // 创建一个异步的文件通道
        AsynchronousFileChannel fileChannel;
        try {
            //noinspection BlockingMethodInNonBlockingContext
            fileChannel = AsynchronousFileChannel.open(
                    // 解析文件输出的路径
                    outputPath.resolve(path),
                    // 文件不存在时创建, 但是文件夹不存在时不会自动创建
                    StandardOpenOption.CREATE,
                    // 允许文件写入
                    StandardOpenOption.WRITE
            );
        } catch (IOException e) {
            return Flux.just(UploadStatus.FAILED).doOnError(ex -> log.error("无法打开文件通道", ex));
        }
        return Flux.concat(
                    DataBufferUtils.write(content, fileChannel).map(dataBuffer -> UploadStatus.CHUNK_COMPLETED),
                    Mono.just(UploadStatus.COMPLETED)
                )
                .publishOn(Schedulers.boundedElastic())
                .doOnError(ex -> log.error("文件上传过程中发生异常", ex))
                .doOnComplete(() -> {
                    try {
                        if (fileChannel.isOpen()) {
                            fileChannel.close();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                })
                .onErrorReturn(UploadStatus.FAILED);
    }
}
