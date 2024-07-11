/**
 * Caffeine 缓存概述
 * <a href="https://www.bilibili.com/video/BV1S6421Z737/" />
 * <p>
 *     对现在的开发来讲，高并发属于核心话题，在这个话题中有个最为重要的技术就是缓存。
 *     通过缓存可以减少数据库查询压力。
 *     按照正常的设计来讲，数据库在进行数据查询的时候是要进行寻址的。
 *     寻址会有严重的性能开销，可以通过索引的技术来提升部分性能，但仅仅是一个提升。
 * <p>
 *     缓存是一个庞大的话题，并不是说在整个项目中配置一个内存或者是分布式的缓存就能解决问题。
 *     由于计算机体系结构设计的问题，所有程序都会在CPU中进行运算。
 *     然而考虑到数据的完整性，所有的数据不会通过磁盘加载，而是会通过内存进行数据的缓存，最终才会被加载到CPU中。
 *     这样一来，在整个项目的运行过程中，如果磁盘IO的操作性能较差，那么最终会导致程序变慢。
 * <p>
 *     关于科班的专业课：
 *          计算机这门学科，软件开发只是它的一个分支，如果想要完全理解所谓的程序开发，不仅仅要掌握编程语言，
 *          同时也要对计算机体系有所认知，包括：《操作系统》《数据结构》《编译原理》，千万不要忽略。
 * <p>
 *     硬件发展史
 *          作为CPU的设计者，曾经将许多的程序直接写在CPU里面，目的是为了节约计算机的体积。
 *          但是后来发现，这样的实现方式不利于扩展。
 * <p>
 *     数据加载
 *          所有程序中的数据都是通过内存获取到的，但是数据内存没有数据，就通过IO通道进行磁盘数据加载。
 *          实际上就会有性能问题了，以下两个性能问题：
 *          - 磁盘带宽，带宽越大，数据的加载速度越快，但是带宽的设计是需要由IO接口来决定的。
 *          - 磁盘的速度问题呢，传统的机械硬盘是需要根据转速来解决数据读取问题的，因为需要进行磁盘磁道的寻址。
 *          - 内存的性能，内存的频率在逐步增加，内存的标准也在逐步完善。
 *     只有IO发挥到了极致，电脑的速度才会变得极快，但是并不意味着所有的用户都需要有如此快的电脑，更多的时候是以稳定为主。
 * <p>
 *     数据库之中的数据考虑到安全性，一般都会使用传统的HDD，这类磁盘即便出现了问题，也可以考虑开盘进行数据恢复操作，所以可操作性更强。
 *     考虑到磁盘的寻址数据问题，可以将一些热点数据保存在数据缓存中，每当数据查询的时候，会首先判断缓存中是否有缓存数据，
 *     如果不存在则通过数据库加载，如果存在了数据，则直接通过缓存返回，这样就减少了数据库的查询操作，从而提升了整个程序的运行性能。
 * <p>
 *     所有的ORM框架基本都支持缓存，但是这个缓存是数据层上的缓存，而此处提及的缓存（caffeine）是业务层上的缓存。
 *     甚至在早期的时候，在JSP里面追加缓存操作。
 * <p>
 *     缓存组件 (单机)
 *     - EHCache组件
 *          一个随Hibernate框架同时推广的组件，也是Hibernate中默认的缓存实现，其属于一个纯粹的java缓存框架。
 *          具有快速、简单等操作特点，同时支持有更多的缓存处理功能。
 *     - Google Guava组件
 *          是一个非常方便易用的本地化缓存组件，基于LRU算法实现，支持多种缓存过期策略。
 *     - Caffeine组件
 *          是对Guava缓存组件的重写版本，虽然功能不如EHCache多，但是其提供了最优的缓存命中率。
 *          特点：
 *          1. 可以自动将数据加载到缓存种，也可以采用异步的方式加载；
 *          2. 当基于频率和最近访问缓存的容量达到最大容量时，该组件会自动切换到基于大小的模式；
 *          3. 可以根据上一个次缓存访问或上一次数据写入，来决定缓存的过期时间；
 *          4. 当一条缓存数据出现过期访问后，可以自动进行异步刷新；
 *          5. 考虑到JVM内存的管理机制，所有的缓存key自动包含在弱引用之中，value包含在弱引用或者软引用种；
 *          6. 当缓存数据被清理后，将会收到相应的通知信息；
 *          7. 缓存数据的写入可以传播到外部的存储；
 *          8. 自动记录缓存数据的访问次数。
 * <p>
 *     缓存组件 (分布式)
 *     将数据存储到其他服务器上去
 *     - Memcached
 *     - Redis
 * <p>
 *     Caffeine 是一套完整的开发组件，其内部也提供有大量的程序类以及操作方法。
 *     <a href="https://github.com/ben-manes/caffeine" />
 *     核心的接口 {@link com.github.benmanes.caffeine.cache.Cache} 所有与缓存有关的处理方法都在这个接口中定义。
 * <p>
 *     通过 {@link com.github.benmanes.caffeine.cache.Caffeine#newBuilder()} 方法，每次调用返回一个新的实例，用于以后的扩展。
 *     通过 {@link com.github.benmanes.caffeine.cache.Caffeine#build()} 返回实例
 *     return isBounded()
 *         ? new BoundedLocalCache.BoundedLocalManualCache<>(self) // 创建有边界的缓存对象
 *         : new UnboundedLocalCache.UnboundedLocalManualCache<>(self); // 创建无边界的缓存对象
 *     - 最大数量
 *     - 最大权重
 *     - 过期访问
 *     - 过期写入
 *     - 失效配置
 *     - key的长度
 *     - value的长度
 *     以上几个配置项，都属于Caffeine中关于缓存存储的配置处理，每一个配置项都有其特定的处理方法。
 *     所有的缓存数据都是保存在内存中的，如果无限的让缓存始终进行存储，那么必然造成内存的溢出。
 *     内存一旦溢出，应用程序有可能直接崩溃，所以缓存的清理是所有组件必须要提供的支持。
 * <p>
 *     在默认情况下，一旦缓存数据消失之后，Cache接口返回的内容就是null数据了。
 *     有些人认为，空数据不利于标注，那么可以考虑进行数据的控制。
 * <p>
 *     在进行缓存查询的时候，曾经使用过Cache接口中提供的get方法，这个方法结合Function接口在缓存数据失效后进行数据加载，类似于map中的defaultValue
 *     {@link com.github.benmanes.caffeine.cache.Cache#get(Object, Function)}
 *     {@link java.util.HashMap#getOrDefault(Object, Object)}
 *     然而，在数据不存在时，还有另外一种数据加载操作
 *     {@link com.github.benmanes.caffeine.cache.CacheLoader}
 *     该接口是Cache的子接口
 * <p>
 *     异步加载
 *     <a href="https://www.bilibili.com/video/BV1Vm42157jX/" />
 *     在 Cache 接口的同级，有个异步缓存接口
 *     {@link com.github.benmanes.caffeine.cache.AsyncCache}
 *     这个接口实现的数据异步加载操作，与 Cache 接口类似，存在有无参的build和有参 CacheLoader 的接口
 *     不一样的是，此时可以接收异步的的 CacheLoader
 *     {@link com.github.benmanes.caffeine.cache.CacheLoader}
 *     {@link com.github.benmanes.caffeine.cache.AsyncCacheLoader}
 *     CacheLoader 是 AsyncCacheLoader 子接口
 * <p>
 *     缓存数据的驱逐
 *     <a href="https://www.bilibili.com/video/BV1Ln4y1X7u9/" />
 *     缓存数据不可能一直存在，涉及到清理多余数据
 *     专属驱逐策略？四种？
 *     - JVM回收策略，弱引用和软引用设置
 *     - 容量的回收策略，比如在caffeine创建时指定的最大容量 maximumSize
 *     - 时效的回收策略，比如caffeine 创建时，指定的失效时间 expireAfterAccess
 *     - 自定义的回收策略,
 *          {@link com.github.benmanes.caffeine.cache.Caffeine#expireAfter(Expiry)}
 *          {@link com.github.benmanes.caffeine.cache.Expiry#expireAfterCreate(Object, Object, long)}
 *          {@link com.github.benmanes.caffeine.cache.Expiry#expireAfterUpdate(Object, Object, long, long)}
 *          {@link com.github.benmanes.caffeine.cache.Expiry#expireAfterRead(Object, Object, long, long)}
 * <p>
 *     缓存数据的删除
 *     <a href="https://www.bilibili.com/video/BV1sf421Q755/" />
 *     缓存数据的删除：
 *     - 自动驱逐
 *     - 手工删除
 *     手工删除：使用 {@link com.github.benmanes.caffeine.cache.Cache#invalidate(Object)}
 *     在组件设计的时候一般设置有回调的操作
 *          在Caffeine组件里面提供了一个删除监听的操作，删除数据之前可以通过监听获取到一些信息
 * <p>
 *     缓存数据的状态
 *     <a href="https://www.bilibili.com/video/BV1jx4y1t7Pt/" />
 *     Caffeine自带数据统计功能，包括查询次数，命中率。
 *     默认情况下是没有开启该信息的。
 *     {@link com.github.benmanes.caffeine.cache.Caffeine#recordStats()}
 *     观察状态的方法 {@link com.github.benmanes.caffeine.cache.Cache#stats()}
 *     类{@link com.github.benmanes.caffeine.cache.stats.CacheStats} 提供了很多数据统计的方法
 *     关于命中率：
 *          在实际的开发中需要用到这个命中率进行key的优化，包括过期处理方案的优化
 *          如果结合到数据库上去使用，命中率低会导致数据查询猛增，降低整体性能。
 * @author caimeng
 * @date 2024/7/1 14:20
 */
package com.example.boot3.yootk.cache.caffeine;

import com.github.benmanes.caffeine.cache.Expiry;

import java.util.function.Function;