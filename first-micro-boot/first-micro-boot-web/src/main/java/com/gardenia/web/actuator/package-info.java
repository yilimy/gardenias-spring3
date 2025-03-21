/**
 * Actuator 模块
 * <p>
 *     1. 导入依赖
 *          org.springframework.boot:spring-boot-starter-actuator
 *     2. 访问地址
 *          <a href="http://localhost/actuator" />
 *     3. 打开端口
 *          management.endpoints.web.exposure.include="*"
 * <p>
 *     关于heap dump文件
 *     在JDK 1.9以前一直存在一个工具 visualvm，但是从JDK 1.9开始，这个工具被移除.
 *     Oracle提供了下载地址: <a href="http://visualvm.github.io/" />
 * <p>
 *     远程关闭应用（该功能默认是关闭的）
 *     1. 端口分离 management.server.port=9090，应用端口: 8080
 *     2. 开启远程关闭 management.endpoint.shutdown.enabled=true
 *     3. 注意，不能使用 profile-active=https 的配置文件，因为该配置文件的Ssl配置不符合 actuator 的要求
 *          {@link com.gardenia.web.config.HttpConnectorConfig}
 *          {@link org.springframework.boot.actuate.autoconfigure.web.server.ManagementServerProperties}
 *          Caused by: org.springframework.beans.FatalBeanException:
 *              ManagementContextWebServerFactory implementation com.gardenia.web.config.HttpConnectorConfig$1 cannot be instantiated.To allow a separate management port to be used, a top-level class or static inner class should be used instead
 * <p>
 *     自定义 endpoint，通过 @Endpoint 注解来实现
 *     1. 【Get】 @ReadOperation, 获取数据
 *     2. 【Post】 @WriteOperation, 写入数据
 *     3. 【Delete】 @DeleteOperation, 删除数据
 *     {@link com.gardenia.web.actuator.YootkEndPoint#endpoint(String)}
 *
 * <p>
 *     动态修改日志级别
 *     使用 post-json 的方式修改
 *     <code>
 *         curl -X POST -H "Content-Type: application/json" \
 *         -d '{"configuredLevel":"trace"}' \
 *         http://localhost:9090/actuator/loggers/com.gardenia.web.action.LogAction
 *     </code>
 * @author caimeng
 * @date 2025/3/7 17:36
 */
package com.gardenia.web.actuator;