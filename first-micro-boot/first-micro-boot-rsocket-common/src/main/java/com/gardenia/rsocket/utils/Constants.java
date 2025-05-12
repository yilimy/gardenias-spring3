package com.gardenia.rsocket.utils;

/**
 * @author caimeng
 * @date 2025/5/6 11:14
 */
public final class Constants {
    private Constants() {}

    public static final String URI_ECHO = "message.echo";
    public static final String URI_DELETE = "message.delete";
    public static final String URI_LIST = "message.list";
    public static final String URI_REPEAT = "message.repeat";
    public static final String URI_GET_LIST = "message.getList";
    // ------------------ 文件上传常量设定 -----------------------
    // 文件类型
    public static final String MIME_FILE_NAME = "message/x.upload.file.name";
    // 文件的扩展类型
    public static final String MIME_FILE_EXTENSION = "message/x.upload.file.extension";
    // 文件名
    public static final String FILE_NAME = "file.name";
    // 文件扩展名
    public static final String FILE_EXT = "file.ext";
    public static final String URI_UPLOAD = "message.upload";
}
