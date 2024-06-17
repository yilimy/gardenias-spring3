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
 *              一级缓存是JPA是默认开启的，它会保存用户在线程中对应的内存空间之中，
 *              但是如果有多个不同的线程进行同一个数据查询，那么此时的一级缓存就没有任何意义了。
 *              以EHCache为例，
 *                  引入jcache缓存标准：org.hibernate.orm::hibernate-jcache::6.1.0.Final
 *                  引入ehcache缓存组件：org.ehcache::ehcache::3.10.0
 *                  导入jdk17相关，java相关依赖库的标准版本：javax.xml.bind::jaxb-api::2.3.1
 *                  导入jaxb-api实现：com.sun.xml.bind::jaxb-impl::2.3.6
 *              Jcache是一个缓存标准的实现库，这个实现库支持JSR107设计规范（java缓存规划方案）
 *              EHCache是一个具体的实现组件。
 * <p>
 *     二级缓存的处理模式
 *     在 persistence.xml 文件中的 shared-cache-mode 标签中配置
 *     1. ALL
 *          所有实体类都会被缓存
 *     2. NONE
 *          所有实体类都不会被缓存
 *     3. ENABLE_SELECTIVE
 *          标识了 @Cacheable(true) 注解的实体类将会被缓存
 *     4. DISABLE_SELECTIVE
 *          缓存除标识 @Cacheable(false) 以外的所有实体类
 *     5. UNSPECIFIED
 *          默认值，JPA产品默认值将会被使用
 * <p>
 *     二级缓存策略：EHCache
 *     在实际开发之中缓存的空间是有限的，所以对于无用的缓存数据，应该及时清理，EHCache有三种缓存清理策略
 *     1. LRU
 *          Least Recently Used 最近最少使用
 *          该策略会将使用次数较少的缓存项进行定期清理，从而只保留缓存热项。
 *     2. LFU
 *          Least Frequently Used 最近最不使用
 *          该策略会将最近一段时间内使用最少的缓存项进行定期清除。
 *     3. FIFO
 *          First Input First Output 先进先出
 *          只要缓存空间不足时会将最早缓存的数据清除，该算法有可能会造成缓存热项被清除，
 *          同时还有可能产生缓存穿透问题，甚至可能造成应用程序崩溃
 * <p>
 *     缓存穿透
 *          缓存数据没有了，要通过数据库进行数据查询，但是在高并发访问下，没有了缓存的保护直接访问数据库，就会造成数据库宕机问题。
 * <p>
 *     查询缓存
 *          查询缓存是二级缓存的一种扩展机制，之前的查询都是通过EntityManager接口实现的，而现在的查询将通过Query接口来完成，
 * <p>
 *     CacheMode
 *          之前的二级缓存仅仅是在数据层的开发实现的，后续会有专门在业务层上实现的缓存。
 *          明显业务层上的缓存更具有实用性。
 *          在默认情况下，只要启用了二级缓存，不同的用户线程在进行数据查询的时候，都可以进行缓存的读写处理。
 *          但是很多的时候，有可能有的线程，仅仅是要求读，或者有的线程强制性读取最新数据，这个时候就需要对缓存模式进行控制。
 *     设置缓存模式 {@link org.hibernate.jpa.HibernateHints#HINT_CACHE_MODE}
 *     可选值由枚举类定义 {@link org.hibernate.CacheMode}
 *     1. NORMAL    可以缓存读写
 *     2. IGNORE    不进行任何缓存操作
 *     3. GET       只能通过缓存读取数据，不能写入缓存数据
 *     4. PUT       只能写入数据，不读取缓存数据
 *     5. REFRESH   不会向缓存读取数据，只会将当前数据强制写入缓存中
 *     相比较原始的缓存模式处理，当前的处理形式属于新版本的功能，
 *     早期的开发采用的是专属方法(setCacheMode)进行配置，现阶段只能使用 setHint 方法。
 *     不同的缓存模式可以带来不同的效果，具体的模式选择由开发者自行决定，但是常规的做法都是 NORMAL
 *
 * @author caimeng
 * @date 2024/6/13 9:46
 */
package com.example.boot3.jpa.cache;