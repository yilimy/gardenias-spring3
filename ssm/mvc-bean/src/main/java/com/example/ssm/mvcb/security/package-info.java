/**
 * SpringMVC与WEB资源安全访问
 * <a href="https://www.bilibili.com/video/BV1iLH5e1E37/" />
 * <p>
 *     web资源比如css样式表、js脚本、图片、音乐、视频、jsp页面等，一般是存储在根目录之中的。
 *     所有的保存在根目录中的资源，都是可以直接根据路径来访问的。
 *     但是考虑到数据的安全性，最佳的做法是保存在“WEB-INF”中，这个目录中的内容安全级别是最高的，需要经过映射后才可以访问。
 * <p>
 *     在SpringMVC中使用解析器来对“WEB-INF”中的资源进行映射处理
 *     {@link org.springframework.web.servlet.view.InternalResourceViewResolver}
 * <p>
 *     将资源文件移动到 WEB-INF 目录下
 *     {@link com.example.ssm.mvcb.action.MessageAction#echoWebInfo(String)}
 *     {@link com.example.ssm.mvcb.action.MessageAction#inputWebInfo()}
 * <p>
 *     定义视图资源解析的配置
 *     {@link com.example.ssm.mvcb.config.ResourceViewConfig}
 *     开启配置 security.enable=true 来启用该功能
 * <p>
 *     创建 WEB-INF 目录下的 static/{css, images, js} 资源文件
 *     创建web的配置文件
 *     {@link com.example.ssm.mvcb.context.config.SpringWebContextConfig#addResourceHandlers(ResourceHandlerRegistry)}
 * @author caimeng
 * @date 2024/9/20 10:31
 */
package com.example.ssm.mvcb.security;

import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;