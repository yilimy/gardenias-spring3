/**
 * 拦截器管理
 * <a href="https://www.bilibili.com/video/BV1NfHSeAEXz/" />
 * <p>
 *     传统的 javaWeb 开发中多使用过滤器，但在SpringMVC之后多使用拦截器，
 *     因为 filter 是servlet级别（组件）的，而 interceptor 是业务层面的。
 * <p>
 *     拦截器的标准接口： {@link org.springframework.web.servlet.HandlerInterceptor}
 *     - preHandle  在控制层（Action）执行之前调用
 *     - postHandle 控制层（Action）方法执行完成，并且要返回 ModelAndView 对象实例
 *     - afterCompletion    控制层（Action）处理完成之后调用
 * <p>
 *     自定义拦截器
 *          {@link com.example.ssm.mvcb.interceptor.YootkHandlerInterceptor}
 *     配置拦截器
 *          {@link com.example.ssm.mvcb.context.config.SpringWebContextConfig#addInterceptors(InterceptorRegistry)}
 *     访问 <a href="http://localhost/pages/message/web_info_config/echo?message=123456" /> 查看结果
 * @author caimeng
 * @date 2024/9/20 16:47
 */
package com.example.ssm.mvcb.interceptor;

import org.springframework.web.servlet.config.annotation.InterceptorRegistry;