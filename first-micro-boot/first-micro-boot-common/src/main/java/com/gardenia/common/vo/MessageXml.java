package com.gardenia.common.vo;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

import java.util.Date;

/**
 * 该对象将以xml的方式返回
 * <p>
 *     版本差异:
 *     XmlRootElement  -->  JacksonXmlRootElement
 *     XmlRootElement  -->  JacksonXmlProperty
 * @author caimeng
 * @date 2025/1/22 12:00
 */
@Data
@JacksonXmlRootElement      // 配置XML根元素
public class MessageXml {
    @JacksonXmlProperty
    private String title;
    @JacksonXmlProperty
    private Date pubDate;
    @JacksonXmlProperty
    private String content;
}
