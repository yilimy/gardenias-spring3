/**
 * 在 Mybatis 中使用 Redis 作为二级缓存
 * <a href="https://www.bilibili.com/video/BV1w31qYcEam/" />
 * <p>
 *     如果想要使用Redis进行缓存的保存，那么就需要使用特定的接口来完成，
 *     这个接口是Mybatis提供的，名称为Cache，需要自定义Cache的子类。
 * <p>
 *     1. 导入依赖，redis or lettuce ? lettuce
 *     2. 实现 Cache，注意是 ibatis 的 Cache，不是 Spring 的 Cache
 *          {@link com.ssm.mybatis.cache.MybatisRedisCache}
 *          {@link com.ssm.mybatis.cache.MybatisRedisSerializeDefinedCache}
 *     3. mapper.xml文件的 cache 标签中添加 type 值
 * @author caimeng
 * @date 2024/12/11 16:53
 */
package com.ssm.mybatis.cache;