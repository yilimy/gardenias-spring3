/**
 * session管理
 * <a href="https://www.bilibili.com/video/BV1o3H5e7ECc/" />
 * <p>
 *     只要涉及到用户的授权管理（认证与授权），或者保存单个用户信息，就会使用到session.
 *     HTTP也有session，只不过是用来解决http无状态的问题。
 * {@link com.example.ssm.mvcb.action.UserAction}
 * {@link com.example.ssm.mvcb.action.SessionAction}
 * <p>
 *     在维护session数据的过程中，可以使用 SessionStatus 的类来控制。
 * @author caimeng
 * @date 2024/9/18 9:48
 */
package com.example.ssm.mvcb.session;