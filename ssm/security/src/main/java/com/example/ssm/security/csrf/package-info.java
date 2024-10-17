/**
 * SpringMVC与CSRF访问控制
 * <a href="https://www.bilibili.com/video/BV1oGtJeCEnz/" />
 * cross site request f..
 * 示例代码
 *      {@link com.example.ssm.mvcb.action.MessageAction#input()}
 *      表单提交需要添加隐藏域 _csrf.parameterName=_csrf.token
 * 有时在企业内网，不会提供对外服务支持，可以考虑取消掉 CSRF 处理.
 *      在配置中取消
 * to be continued...
 * @author caimeng
 * @date 2024/10/16 15:22
 */
package com.example.ssm.security.csrf;