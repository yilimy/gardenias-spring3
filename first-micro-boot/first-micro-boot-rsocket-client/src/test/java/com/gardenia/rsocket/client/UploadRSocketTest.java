package com.gardenia.rsocket.client;

import com.gardenia.rsocket.type.UploadStatus;
import com.gardenia.rsocket.utils.Constants;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.core.io.buffer.DefaultDataBufferFactory;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.util.MimeType;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

/**
 * 测试上传接口
 * <p>
 *     保存在classpath下
 * @author caimeng
 * @date 2025/5/12 14:17
 */
@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@SpringBootTest(classes = StartRSocketClientApplication.class)
public class UploadRSocketTest {
    @Autowired
    private Mono<RSocketRequester> requesterMono;
    @Value("classpath:/pdf/pages20.pdf")    // 资源定位表达式
    private Resource resource;

    @Test
    public void uploadTest() {
        String fileName = "muyan-" + UUID.randomUUID();
        //noinspection DataFlowIssue
        String extName = resource.getFilename().substring(resource.getFilename().lastIndexOf(".") + 1);
        //noinspection ReactiveStreamsUnusedPublisher
        Flux<DataBuffer> fluxData = DataBufferUtils
                .read(resource, new DefaultDataBufferFactory(), 1024)
                /*
                 * IDE提示： Value is never used as Publisher
                 * 实际在 requesterMono 最后调用了 blockLast，实现了订阅
                 * 如果不提示警告，可以在 data(fluxData) 中使用内联调用
                 *
                 * 日志:
                 *      文件上传，s=DefaultDataBuffer (r: 0, w: 723, c: 723)
                 */
                .doOnNext(s -> System.out.println("文件上传，s=" + s));
        // 开始上传
        requesterMono
                .map(requester -> requester
                        .route(Constants.URI_UPLOAD)
                        .metadata(metadataSpec -> {
                            System.out.println("【上传测试】文件名称：" + fileName + "." + extName);
                            // 设置文件名称
                            metadataSpec.metadata(fileName, MimeType.valueOf(Constants.MIME_FILE_NAME));
                            // 设置文件后缀名
                            metadataSpec.metadata(extName, MimeType.valueOf(Constants.MIME_FILE_EXTENSION));
                        })
                        // 设置上传文件资源
                        .data(fluxData)
                )
                .flatMapMany(r -> r.retrieveFlux(UploadStatus.class))
                .doOnNext(status -> System.out.println("【上传测试】上传状态：" + status))
                // 阻塞等待
                .blockLast();
    }
}
