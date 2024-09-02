/**
 * 矩阵参数
 * <p>
 *     矩阵参数是一种 SpringMVC 扩展的参数形式，
 *     这类参数的传递模式“key=value;key=value”,可以一次性传递多个参数，所以成为矩阵参数。
 *     需要手工开启。 {@link com.example.ssm.mvcb.context.config.SpringWebContextConfig}
 *     {@link com.example.ssm.mvcb.action.MessageAction#matrix(String, String, String, int)}
 * <p>
 *     矩阵参数接收的第二种形式，使用Map接收
 * 初始化绑定
 * <a href="https://www.bilibili.com/video/BV16hp2emEZL/" />
 * <p>
 *     {@link com.example.ssm.mvcb.action.MessageAction#pageInputMany()}
 *     spring内部有大量的数据转换的支持，SpringMVC继承自Spring框架的使用特点，也包含了这样的处理。
 *     但是日期时间的转换比较难控制，需要约定一个时间格式，不约定时间格式将无法进行格式化操作。
 *     数据绑定 {@link com.example.ssm.mvcb.action.abs.AbstractAction#initBinder(WebDataBinder)}
 * @author caimeng
 * @date 2024/8/29 11:18
 */
package com.example.ssm.mvcb.context.config;

import org.springframework.web.bind.WebDataBinder;