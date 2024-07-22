/**
 * 数据缓存最重要的一点应用是在进行数据查询操作的时候使用，
 * 但是在缓存操作的过程中，出现缓存清除，以及缓存更新的处理问题，
 * 所以，本次的业务层的方法就定义了更新、查询以及删除三种操作
 *      {@link com.example.boot3.spring.cache.service.IEmp2Service}
 * <p>
 *     一个简单的注解，可以直接在业务层上使用，就非常轻松地实现了缓存操作的处理。
 *     整体的实现效果是非常简单的，同时也避免了不同数据层之中所带来的不同缓存开发的影响。
 *     {@link com.example.boot3.spring.cache.service.IEmp2Service#get(String)}
 *     该接口方法中使用了 Cacheable，虽然spring不建议在接口方法上使用注解。
 * <p>
 *     在业务层中如果想要使用缓存，在方法上通过 @Cacheable 注解即可启用，
 *     但是实际上，{@link org.springframework.cache.annotation.Cacheable} 注解内部也是提供有很多属性的。
 *     - value
 *          定义缓存名称，可以配置多个缓存名称
 *     - cacheNames
 *          定义缓存名称，作用与 value 相同
 *     - key
 *          定义缓存KEY
 *     - keyGenerator
 *          定义KEY生成器
 *     - cacheManager
 *          定义要使用的缓存管理器的名称
 *     - cacheResolver
 *          定义缓存解析器
 *     - condition
 *          定义缓存应用条件，支持SpEL语法
 *     - unless
 *          定义缓存排除条件，支持SpEL语法
 *     - sync
 *          定义同步缓存，将采用阻塞策略进行缓存更新
 * <p>
 *     关于 condition 和 unless
 *     缓存的逻辑：
 *          1. 缓存的空间没有数据项；
 *          2. 通过数据层进行数据的加载；
 *          3. 加载到缓存空间中。
 *     但是有些数据是不需要缓存的，就必须设置 “生效 | 排除” 的条件
 *     condition    一般从参数中获取，满足条件的，将加入缓存
 *     unless       一般从返回结果中获取，满足条件的，将不会加入缓存
 * <p>
 *     关于SpEL的上下文
 *     - 当前调用的方法名称
 *          #root.methodName
 *     - 当前执行的方法
 *          #root.method.name
 *     - 当前执行的目标对象
 *          #root.target
 *     - 当前执行的目标对象所属的类
 *          #root.targetClass
 *     - 当前调用的方法参数列表
 *          #root.args[0] 或 #root.args[1]
 *     - 当前方法调用使用的缓存列表
 *          #root.caches[0].name
 *     - 当前调用方法的参数，可以是普通参数，或者是对象参数
 *          例如: get(String eid), 则为 “#eid”
 *          例如: edit(Emp vo), 则为 “#vo.eid”
 *     - 方法执行的返回值
 *          #result  (“#result.属性” 表示返回对象的属性)
 * <p>
 *     默认情况下，只要进行了数据查询，那么肯定所有的数据都要进行缓存处理，
 *     可是现在要求对缓存的数据追加一些标记，判断查询的参数里面包含有指定的字符串才允许缓存。
 *     业务层中的缓存是针对所有数据的，但是如果某些数据在设计的时候没有达到缓存的要求，就不应该被缓存。
 *          比如：null, code!=0的错误数据等
 *     {@link com.example.boot3.spring.cache.service.IEmp2Service#getWithSalary(String)}
 * <p>
 *     多线程的缓存缓存穿透问题。
 *     举例：三个异步线程同时查询一条数据，缓存中不存在，那么三个线程会同时查询数据库，此处可以考虑使用同步缓存操作。
 *     {@link com.example.boot3.spring.cache.service.IEmp2Service#getSync(String)}
 *     当启用了同步处理后，会由一个线程向数据库发出查询指令，而后自动进行缓存处理，
 *     而其他等待的线程等到缓存中有数据之后，才会继续进行缓存数据的获取。
 * @author caimeng
 * @date 2024/7/18 17:47
 */
package com.example.boot3.spring.cache.service;