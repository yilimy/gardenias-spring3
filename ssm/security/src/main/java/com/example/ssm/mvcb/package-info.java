/**
 * 从 module:mvc-bean 中拷贝的文件列表
 * <p>
 *     1. DispatchServlet
 *          定义 notfound 跳转配置的支持
 *          {@link com.example.ssm.mvcb.servlet.YootkDispatcherServlet}
 *     2. SpringApplicationContextConfig
 *          Spring容器的核心配置类，定义程序扫描包
 *          {@link com.example.ssm.mvcb.context.config.SpringApplicationContextConfig}
 *     3. SpringWebContextConfig
 *          SpringWeb开发所需要的配置类
 *          {@link com.example.ssm.mvcb.context.config.SpringWebContextConfig}
 *     4. ResourceViewConfig
 *          实现视图的前置和后置配置
 *          {@link com.example.ssm.mvcb.config.ResourceViewConfig}
 *          由于需要存在有REST显示模式，需要定义json解析配置
 *          {@link com.example.ssm.mvcb.config.JacksonConfig}
 *     5. StartWebApplication
 *          容器启动类
 *          {@link com.example.ssm.mvcb.web.config.StartWebAnnotationApplication}
 * @author caimeng
 * @date 2024/9/29 14:19
 */
package com.example.ssm.mvcb;