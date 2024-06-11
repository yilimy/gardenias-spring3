/**
 * JPQL语句
 * Java Persistence Query Language
 * <p>
 *     在进行数据库操作过程中，常规的手段就是SQL命令了。
 *     在早期的时候，SQL语法标准还没有推广开来，各个数据库的开发命令真的很多。
 *     现在JPA属于ORM开发组件，所以现在也都需要实现各类CURD的处理，对应的在Hibernate里面就提供了JPQL查询操作语法，
 *     这种语法类似于SQL的语法机构。
 * <p>
 *     JPQL的语法结构非常类似于SQL，主要目的是帮开发者简化学习成本。
 *     如果想要使用JPQL查询操作，则需要通过 EntityManager 接口获取查询实例。
 *     方法模板: Query create...
 *     在JPQL里，考虑到实际查询之中所可能产生的各种繁琐的查询操作问题，
 *     支持原生SQL命令的同时，也要使用JPQL查询，主要依靠两个接口：
 *          {@link jakarta.persistence.Query}、
 *          {@link jakarta.persistence.TypedQuery}
 *     TypedQuery 支持泛型，可以直接获取到指定类型的查询结果。
 * <p>
 *     在 EntityManager 接口里面实际上提供有merge()和remove()两个方法进行数据的更新以及数据的删除操作，
 *     但是这样的更新处理需要完整的数据提供，
 *     不允许单字段的更新，要想这样的更新处理，需要先查询再更新。
 *     remove方法更是需要维护对象的状态，所以也需要进行查询后更新。
 *     这样的开发操作一定是非常麻烦的，并且性能不高。
 *     最佳的做法是在JPA里面使用SQL的方式进行更新处理，所以在Hibernate2.1版本之后针对JPQL语法结构进行了修改，
 *     让其可以实现update以及delete命令的定义。
 * <p>
 *     在实际的JPA开发过程中，应该以JPQL的操作为主，但是在一些较为特殊的环境中，很多开发者可能只能够编写SQL命令，
 *     所以在JPA开发之中，也是提供有原生SQL执行支持的，这样就需要创建原始SQL操作。
 *     方法模板: Query createNative...
 *     由于这种SQL的原生操作，会与具体的数据库捆绑在一起，那么将丧失掉可移植性的设计，
 *     在使用之前请尽量慎用，同时一定要评估好再使用。
 * @author caimeng
 * @date 2024/6/11 10:33
 */
package com.example.boot3.jpa.jpql;