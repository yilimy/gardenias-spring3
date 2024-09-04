/**
 * 针对请求中 ThreadLocal 的懒处理
 * <p>
 *     使用方不需要关系 ThreadLocal 的回收，缺点是数据在整个线程中一直存在。
 *     另外，因为是通过过滤器实现的，ThreadLocal 最好不要定义在过滤器中，或者过滤器之前。
 *     如果定义在过滤器中，
 *          请确保 ClearThreadLocalFilter 的Order值小于ThreadLocal业务所在的Order值；
 *          或者 ClearThreadLocalFilter 在filterChain链中先于ThreadLocal业务所在的filter。
 *              - 不是 ThreadLocal定义所在的filter，因为有可能定义在后，而使用在前。
 *              - 因此，要根据 ThreadLocal 所在的业务来决定。
 *     {@link com.example.boot3.threadlocal.tomcat.ClearThreadLocalFilter}
 * @author caimeng
 * @date 2024/9/4 11:12
 */
package com.example.boot3.threadlocal.tomcat;