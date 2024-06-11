/**
 * EntityManager 操作数据库
 * <a href="https://jakarta.ee/specifications/platform/9/apidocs/">jakarta 官方文档</a>
 * <p>
 *     EntityManager 是jpa规范中进行数据处理的主要接口，在该接口中提供；额实体类对象的更新、删除、ID查询的支持
 *     1. persist
 *          持久化PO实例
 *     2. merge
 *          数据更新处理，如果不存在则增加
 *     3. remove
 *          删除当前实体对象
 *     4. find
 *          根据主键查询对象，不存在则返回null
 *     5. getReference
 *          根据ID查询数据，数据不存在则抛出异常
 *     6. getTransaction
 *          获取数据库事务
 * @author caimeng
 * @date 2024/6/11 9:32
 */
package com.example.boot3.jpa.manager;