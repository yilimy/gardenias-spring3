/**
 * SpringCache整合Caffeine缓存管理
 * <a href="https://www.bilibili.com/video/BV15T421k7Z5/" />
 * 使用 ConcurrentMap 实现的缓存的处理性能一定不如Caffeine好，因为其内部在数据结构上会更为优秀
 *      {@link com.example.boot3.spring.cache.config.CacheConfig#cacheManager()}
 *      ConcurrentMap，例如： @Cacheable("emp")
 *      {@link com.example.boot3.spring.cache.service.IEmp2Service#get(String)}
 * ThreadLocal其实也是使用的 ConcurrentMap，可以归为一类。
 * 既然现在要使用Spring Cache，最佳的实践是使用Caffeine进行单机缓存。
 * <p>
 *     SpringCache缓存更新策略
 *     <a href="https://www.bilibili.com/video/BV1NM4m127gB/"/ >
 *     并不是所有的数据都要进行缓存，一般是热点数据
 *     缓存数据非必要不更新
 *          因为有可能造成缓存热点数据失效，从而导致数据库查询压力激增，导致系统崩溃。
 *     SpringCache支持更新，一般也是和业务层直接联系
 *     {@link org.springframework.cache.annotation.CachePut}
 *     这种缓存的更新操作其实并没有发生另一次的数据查询
 *          （按照基本的做法，缓存的数据修改应该先删除，再查询，最后存放新的缓存数据）
 *     而现在仅仅是在缓存的内容上做了处理，这一点作为缓存的功能上已经足够，
 *     但是考虑到性能问题，在高并发的处理下一般还是不建议修改缓存数据。
 *     而是应该以缓存中的数据不变为主。
 * <p>
 *     SpringCache缓存清除策略
 *     <a href="https://www.bilibili.com/video/BV1Ay411i7TS/" />
 *     数据库中的数据删除之后，理论上缓存中的数据也要做删除。
 *     缓存的更新相比较数据来讲肯定较慢，同时放置在缓存中的很多数据一般不会轻易变更。
 *     {@link org.springframework.cache.annotation.CacheEvict}
 * <p>
 *     在并发量小的情况下，各种缓存的操作维护，可以随意搞。
 *     但是在高并发的场景里，缓存删除或更新的操作不要轻易去做，会暴露终端数据操作。
 * @author caimeng
 * @date 2024/7/22 16:21
 */
package com.example.boot3.spring.cache.caffeine;