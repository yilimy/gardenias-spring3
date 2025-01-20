package com.gardenia.web.service.impl;

import com.gardenia.web.service.IMessageService;

/**
 * 使用xml进行bean注解
 * <p>
 *     1. 在"src/main/resources"目录中创建"META-INF/spring/spring-service.xml"配置文件
 *     2. 引入相关的Spring配置文件的头部声明
 *          > 通过开发工具: new -> XML Configuration File -> Spring Config
 *     3. 在启动类中追加相应的配置注解，引入要导入的xml配置文件
 *          > @ImportResource(locations = "classpath:META-INF/spring/spring-*.xml")
 * <p>
 *     该方法一般用于在新项目 (使用spring注解注入bean) 中，导入老项目 (使用xml注入bean)，而又不想分析老项目代码的业务逻辑时使用。
 *     也就是说，只需要在启动类中添加导入注解 (@ImportResource) 即可。
 * @author caimeng
 * @date 2025/1/20 16:54
 */
public class IMessageServiceXmlImpl implements IMessageService {
    @Override
    public String echo(String msg) {
        return "【echo-xml】" + msg;
    }
}