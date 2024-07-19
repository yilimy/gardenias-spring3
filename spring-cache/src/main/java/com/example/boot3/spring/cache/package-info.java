/**
 * 1. 添加依赖
 * 2. 创建 database.properties 资源文件，定义数据库连接信息
 * 3. 定义 jpa.properties 资源文件
 * 4. 创建 DataSourceConfig 配置类，定义数据源
 *      {@link com.example.boot3.spring.cache.config.HikariDataSourceConfig}
 * 5. 创建事务配置类，必须使用 JPA 的事务控制类
 *      {@link com.example.boot3.spring.cache.config.TransactionConfig}
 * 6. 创建 TransactionAdviceConfig 切面配置类
 *      {@link com.example.boot3.spring.cache.config.TransactionAdviceConfig}
 * 7. 创建 JPA 的配置类，注意，这里要配置实体类扫描包 setSharedCacheMode，
 *      {@link com.example.boot3.spring.cache.config.SpringDataJPAConfig}
 *      配置文件中要把二级缓存干掉，因为要集成 Spring boot，在业务层中控制缓存，而不是在数据层
 * 8. 创建启动扫描处理类
 *      {@link com.example.boot3.spring.cache.StartSpringCache}
 * 9. 创建 JPA 持久化处理类
 * 10.创建 DAO 接口层，并继承 JpaRepository
 *      {@link org.springframework.data.jpa.repository.JpaRepository}
 * 在进行缓存实现的时候，一般是基于三种缓存方案
 *      - JDK concurrentHashMap
 *      - 本地 Caffeine | EHCache (早期)
 *      - 分布式 Redis | Memcached (早期)
 * concurrentHashMap 是在 J.U.C 之中提供的最为重要的技术实现
 *      整个 J.U.C 里面除了锁和线程池外，最重要的类型就是 concurrentHashMap，它可以保证在更新安全的前提下，提供良好的数据获取性能。
 *      在没有引入额外的配置时，Spring缓存主要使用 concurrentHashMap 进行操作。
 * SpringCache之中为了便于缓存的管理，在“org.springframework.cache”包中提供了两个核心的标准接口：
 *      - Cache 实现接口
 *              {@link org.springframework.cache.Cache}
 *              缓存数据的保存、增加、失效以及清空功能，
 *              要获取Cache的实例，需要通过 CacheManager 接口方法完成（工厂类型）
 *              所有的Cache对象都在CacheManager之中保存
 *              默认实现 {@link org.springframework.cache.concurrent.ConcurrentMapCache}
 *      - CacheManager 管理接口
 *              {@link org.springframework.cache.CacheManager}
 * @author caimeng
 * @date 2024/7/18 15:17
 */
package com.example.boot3.spring.cache;