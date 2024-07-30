/**
 * Memcached 缓存概述
 * <a href ="https://www.bilibili.com/video/BV1UT421r7jH/" />
 * <p>
 *     对于现在的开发来讲，基本上都要使用到分布式缓存。
 *     分布式缓存的组件主要包括两个：Memcached | Redis
 *          - memcached 微小的开发
 *          - redis 每秒的并行吞吐量为10w
 *     行业中，最早流行的 NoSql 分布式缓存数据库就是 Memcached
 * <p>
 *     技术的选型：
 *          1. 如果现在的很多技术有横向的替代品，那么就看其他公司用的什么组件。暂时不要考虑未来升级改造的问题。
 *          2. 尽量使用成熟的产品线，不要使用冷门的技术。
 *              除非当前的技术出现了非常严重的性能问题。
 *              e.g. Apache被nginx取代，就是因为性能问题（高并发）。
 * <p>
 *     不管是何种缓存组件，只要是单机的缓存处理，基本上都要占用整个系统的内存，
 *     e.g. concurrentHashMap、caffeine
 *     受到物理缓存的限制，你的缓存数据量一定会有所减少。
 *     为了发挥出应用服务器的最大性能，可以考虑将缓存数据的内容保存到其他分布式组件之中。
 *     也就是说本地缓存解决的是时间问题，分布式缓存解决的是数据量的问题。
 * @author caimeng
 * @date 2024/7/29 17:46
 */
package com.example.boot3.spring.cache.memcached;