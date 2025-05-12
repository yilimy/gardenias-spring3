package com.gardenia.rsocket.type;

/**
 * 文件上传状态
 * @author caimeng
 * @date 2025/5/12 10:44
 */
public enum UploadStatus {
    CHUNK_COMPLETED,    // 文件上传处理中
    FAILED,             // 文件上传失败
    COMPLETED,          // 文件上传完成
}
