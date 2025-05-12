package com.gardenia.rsocket.server.config;

import com.gardenia.rsocket.type.UploadStatus;
import com.gardenia.rsocket.utils.Constants;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.codec.cbor.Jackson2CborDecoder;
import org.springframework.http.codec.cbor.Jackson2CborEncoder;
import org.springframework.messaging.rsocket.RSocketStrategies;
import org.springframework.util.MimeType;

/**
 * @author caimeng
 * @date 2025/5/12 10:55
 */
@Configuration
public class RSocketConfig {

    /**
     * 为文件上传配置的策略
     * @return RSocket配置策略
     */
    @Bean
    public RSocketStrategies  rSocketStrategies() {
        return RSocketStrategies.builder()
                // 编码器
                .encoders(encoders -> encoders.add(new Jackson2CborEncoder()))
                // 解码器
                .decoders(decoders -> decoders.add(new Jackson2CborDecoder()))
                // 元数据配置
                .metadataExtractorRegistry(registry -> {
                            registry.metadataToExtract(
                                    MimeType.valueOf(Constants.MIME_FILE_NAME), String.class, Constants.FILE_NAME);
                            registry.metadataToExtract(
                                    MimeType.valueOf(Constants.MIME_FILE_EXTENSION), String.class, Constants.FILE_EXT);
                        })
                .build();
    }
}
