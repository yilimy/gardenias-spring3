/**
 * JPA缓存
 * <p>
 *     内存中的缓存数据命中率越高，程序执行的性能越高。
 *     在JPA中（工作在数据层里）考虑到数据层的性能问题，提供了缓存的支持。
 *     常见的缓存分为两种：
 *          1. 一级缓存
 *              First Level Cache.
 *              是一种始终会存在的缓存结构，这种缓存是绑定在EntityManager接口实例之中的，
 *              也就是说，每一个用户处理线程里面，都会存在有缓存数据信息。
 *          2. 二级缓存
 *
 * @author caimeng
 * @date 2024/6/13 9:46
 */
package com.example.boot3.jpa.cache;