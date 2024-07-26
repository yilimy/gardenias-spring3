/**
 * SpringCache多级缓存策略
 * <a href="https://www.bilibili.com/video/BV1QE421A73c/" />
 * <p>
 *     {@link com.example.boot3.spring.cache.service.IEmp2Service#edit(Emp2)}
 *     edit方法中的缓存更新操作是以雇员的ID为主的，但是根据姓名查询的时候，
 *     {@link com.example.boot3.spring.cache.service.IEmp2Service#getByEname(String)}
 *     这个更新操作无法针对根据姓名缓存的更新。
 * <p>
 *     多级缓存的实现： 通过 {@link org.springframework.cache.annotation.Caching} 注解进行归集
 *          {@link com.example.boot3.spring.cache.service.IEmp2Service#editCascade(Emp2)}
 * @author caimeng
 * @date 2024/7/25 19:46
 */
package com.example.boot3.spring.cache.cascade;

import com.example.boot3.spring.cache.po.Emp2;