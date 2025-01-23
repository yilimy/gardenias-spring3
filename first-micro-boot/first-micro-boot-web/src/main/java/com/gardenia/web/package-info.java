/**
 * WEB模块
 * <p>
 *     这个模块主要引用“first-micro-boot-web”子模块，并且实现WEB程序的开发
 * <p>
 *     数据转换和fastjson整合
 *     1. 数据绑定和Date类型转换
 *          {@link com.gardenia.common.action.abs.AbstractBaseAction#initBinder(WebDataBinder)}
 *     2. fastjson替换jackson
 *          {@link com.gardenia.web.config.WebConfig#configureMessageConverters(List)}
 *     3. 请求举例: micro_boot.http
 * <p>
 *     返回xml数据
 *     1. 想要实现该功能，一般使用jackson组件来完成，fastjson还不具备该能力
 *     2. 导入依赖包
 *          com.fasterxml.jackson.dataformat:jackson-dataformat-xml
 *          com.fasterxml.jackson.core:jackson-databind
 *          com.fasterxml.jackson.core:jackson-annotations
 *     3. 数据对象添加注解
 *          {@link com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement}
 *          {@link com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty}
 *     4. 发送请求进行验证
 *          {@link com.gardenia.web.action.MessageAction#aboutXml(MessageXml)}
 * <p>
 *     返回pdf数据
 *     1. 导入依赖包
 *          com.itextpdf:itextpdf
 *     2. 字体库
 *          a. 在资源文件中创建fonts子目录
 *          b. 拷贝字体库时，最好使用公版字体，不要使用私版字体
 *          c. 使用阿里普惠体， AlibabaPuHuiTi-3-45-Light.ttf
 *     3. 生成pdf
 *          {@link com.gardenia.web.action.PDFController#createPDFData(HttpServletResponse)}
 * @author caimeng
 * @date 2025/1/20 14:43
 */
package com.gardenia.web;

import com.gardenia.web.vo.MessageXml;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.WebDataBinder;

import java.util.List;