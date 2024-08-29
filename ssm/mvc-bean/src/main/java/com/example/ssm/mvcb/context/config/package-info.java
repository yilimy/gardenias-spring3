/**
 * 矩阵参数
 * <p>
 *     矩阵参数是一种 SpringMVC 扩展的参数形式，
 *     这类参数的传递模式“key=value;key=value”,可以一次性传递多个参数，所以成为矩阵参数。
 *     需要手工开启。 {@link com.example.ssm.mvcb.context.config.SpringWebContextConfig}
 *     {@link com.example.ssm.mvcb.action.MessageAction#matrix(String, String, String, int)}
 * <p>
 *     矩阵参数接收的第二种形式，使用Map接收
 *
 * @author caimeng
 * @date 2024/8/29 11:18
 */
package com.example.ssm.mvcb.context.config;