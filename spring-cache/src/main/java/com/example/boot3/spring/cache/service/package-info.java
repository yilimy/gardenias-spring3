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
 * @author caimeng
 * @date 2024/7/18 17:47
 */
package com.example.boot3.spring.cache.service;