/**
 * JPA使用Criteria数据查询
 * Criteria - 标准
 * <a href="https://www.bilibili.com/video/BV15b421v7B4/" />
 * <p>
 *     JPQL语句的查询操作是一种较为直观的处理操作，但是这样的查询不符合面向对象的设计模式。
 *     在面向对象的编程开发中，所有内容的组成操作需要由对象发起，所以按照面向对象的设计要求来说，
 *     如果想要实现查询，应该采用一系列的方法调用的形式完成，但是最终所执行的依旧是传统的SQL语句。
 * <p>
 *     Criteria的查询操作
 *     {@link jakarta.persistence.EntityManager#getCriteriaBuilder()}
 *     {@link jakarta.persistence.EntityManager#createQuery(CriteriaQuery)}
 *     {@link jakarta.persistence.EntityManager#createQuery(CriteriaUpdate)}
 *     {@link jakarta.persistence.EntityManager#createQuery(CriteriaDelete)}
 *     对于数据的查询、删除以及修改，均可以通过面向对象的方式来进行完整的操作处理。
 * @author caimeng
 * @date 2024/6/12 14:47
 */
package com.example.boot3.jpa.criteria;

import jakarta.persistence.criteria.CriteriaDelete;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.CriteriaUpdate;