/**
 * RequestContextHolder
 * <a href="https://www.bilibili.com/video/BV1Z3H5e7E7w/" />
 * <p>
 *     在传统 SpringMVC 中，进行web开发会涉及很多内置对象 (pageContext, request, session, application)
 *          {@link org.springframework.ui.Model}
 *          {@link org.springframework.ui.ModelMap}
 *     但是现在这些操作大多封装在 SpringMVC 内部。
 *     在一些特殊情况下，开发者需要基于内置对象完成某些功能，可以借助于 RequestContextHolder
 *          {@link org.springframework.web.context.request.RequestContextHolder}
 * @see com.example.ssm.mvcb.action.InnerObjAction
 *
 * RequestHeader
 * <p>
 *     在Http协议中，数据分为两个部分进行存储
 *          - 头信息
 *          - 提交内容
 *     在每一次进行头信息处理的时候，都会使用到request或者response内置对象
 *     spring 6 =====>  jakarta
 * @see com.example.ssm.mvcb.action.HeaderAction
 *
 * CookieValue
 * <a href="https://www.bilibili.com/video/BV1o3H5e7EUK/" />
 * @see com.example.ssm.mvcb.action.CookieAction
 *
 * @author caimeng
 * @date 2024/9/14 16:45
 */
package com.example.ssm.mvcb.holder;
