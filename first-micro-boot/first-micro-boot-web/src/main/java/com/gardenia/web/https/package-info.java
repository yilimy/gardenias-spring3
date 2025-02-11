/**
 * SpringBoot与https安全访问
 * <a href="https://www.bilibili.com/video/BV1jRFdeCEcA/" />
 * <p>
 *      D:\"Program Files"\Java\corretto-17.0.10\bin\keytool.exe -genkey -alias yootkServer -storetype PKCS12 -keyalg RSA -keysize 2048 -validity 3650 -keystore keystore.p12 -dname "CN=YootkWebServer,OU=Yootk,O=MuYanYootk,L=BeiJing,S=BeiJing,C=China" -storepass yootkjava
 * <p>
 *     1. 生成的 keystore.p12 证书拷贝到resources目录中
 *     2. 在配置文件中定义，参考 application-https.properties
 *     3. 浏览器访问
 *          <a href="https://localhost:443/message/transStrToDate?title=消息标题&pubDate=2025-01-23&content=消息内容" />
 *          注意：使用undertow不能测试g该功能
 * <p>
 *     配置连接器，实现从端口80跳转到端口443 {@link com.gardenia.web.config.HttpConnectorConfig}
 * @author caimeng
 * @date 2025/2/11 14:06
 */
package com.gardenia.web.https;