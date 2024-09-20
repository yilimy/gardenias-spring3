/**
 * 统一的异常处理
 * <a href="https://www.bilibili.com/video/BV1LLH5e1EeF/" />
 * <p>
 *     配置 security.enable=true
 *          {@link com.example.ssm.mvcb.config.ResourceViewConfig}
 *     错误页面： /WEB-INF/pages/error.jsp
 *     全局异常捕获类： {@link com.example.ssm.mvcb.advice.ErrorAdvice}
 *     将这个捕获类添加到容器扫描
 *          {@link com.example.ssm.mvcb.context.config.SpringApplicationContextConfig}
 *     模拟一次异常：
 *          {@link com.example.ssm.mvcb.action.ErrorAction#echo(String)}
 *          <a href="http://localhost/pages/error/echo?message=error" />
 * @author caimeng
 * @date 2024/9/20 15:26
 */
package com.example.ssm.mvcb.advice;