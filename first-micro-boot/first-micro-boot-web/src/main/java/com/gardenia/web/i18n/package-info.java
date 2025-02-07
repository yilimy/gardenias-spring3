/**
 * 资源目录中的 i18n 包一般用于国际化
 * <p>
 *     1. 在 src/main/resources 目录中创建目录 i18n
 *     2. 创建 i18n.Message.properties 文件（BaseName文件内容）
 *          Message.properties 为公共资源文件
 *     3. 创建不同区域的资源文件
 *          中文资源文件 : Message_zh_CN.properties
 *          英文资源文件 : Message_en_US.properties
 *     4. i18n包自动识别为 "Resource Bundle 'Message'"
 *     5. 资源与SpringBoot容器进行关联，配置 spring.messages.basename
 *     6. 资源文件读取
 *          {@link com.gardenia.web.action.I18NAction#showBase()}
 *          {@link com.gardenia.web.action.I18NAction#showLocal(Locale)}
 *     7. 定义一个转换器，用来接收
 *          {@link java.util.Locale}
 *          {@link com.gardenia.web.config.DefaultLocalResolver}
 *     8. 执行请求
 *          <a href="http://localhost:8080/i18n/locale?loc=zh_CN" />
 *          <a href="http://localhost:8080/i18n/locale?loc=en_US" />
 * <p>
 *     国际化资源文件的优劣
 *     最早写资源文件是为了修改方便，但是在K8S/Docker形式部署之后，是否写资源文件意义不大了
 *     替代方案为 CI/CD (持续集成 | 持续交付)，一般是指自动化部署
 * @author caimeng
 * @date 2025/2/7 14:45
 */
package com.gardenia.web.i18n;

import java.util.Locale;