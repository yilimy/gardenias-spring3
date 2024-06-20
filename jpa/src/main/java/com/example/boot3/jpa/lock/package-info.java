/**
 * JPA数据锁
 * <p>
 *     锁的出现主要体现在多线程修改数据的操作情况下，
 *     所有的数据库资源都是放在公共代码之中的，那么就意味着此部分的代码有可能属于非线程安全的，
 *     这样就会同时有多个线程修改同一条数据的可能性，那么到底是哪一个线程的修改会生效呢。
 * <p>
 *     传统的数据库概念中，每一条数据都会存在有一个行锁，为了可以让这个锁的操作更加程序化，
 *     行锁只有当前操作线程可以解除，
 *     在JPA中提供有两种锁的处理机制：悲观锁和乐观锁
 *     {@link jakarta.persistence.EntityManager#find(Class, Object, LockModeType)}
 *     {@link jakarta.persistence.EntityManager#find(Class, Object, LockModeType, Map)}
 *     {@link jakarta.persistence.EntityManager#lock(Object, LockModeType)}
 *     {@link jakarta.persistence.EntityManager#lock(Object, LockModeType, Map)}
 * <p>
 *     对于 {@link jakarta.persistence.EntityManager} 来讲，可以直接在查询数据的时候进行上锁处理。
 *     在获取到PO实例后，利用其所提供的lock方法进行加锁处理。
 *     前提：所有的锁一定要运行在事务之中
 * <p>
 *     {@link jakarta.persistence.LockModeType}
 *     READ                             乐观读锁
 *     WRITE                            乐观写锁
 *     OPTIMISTIC                       乐观读锁
 *     OPTIMISTIC_FORCE_INCREMENT       乐观写锁
 *     PESSIMISTIC_READ                 悲观读锁
 *     PESSIMISTIC_WRITE                悲观写锁
 *     PESSIMISTIC_FORCE_INCREMENT      悲观写锁
 *     NONE                             无锁
 * <p>
 *     悲观锁
 *     <a href="https://www.bilibili.com/video/BV1Ax4y1b7mU/" />
 *     认为任何情况下都可能有数据同步的操作问题，任何时候都会有多个线程访问同一条数据的可能性。
 *     - PESSIMISTIC_READ
 *          只要事务读实体，实体管理器就锁定实体，直到事务完成（提交或者回滚）后，锁才会解锁处理，
 *          这种锁模式不会阻碍其他事务读取数据。
 *     - PESSIMISTIC_WRITE
 *          只要事务更新实体，实体管理器就会锁定实体，
 *          这种锁模式强制尝试修改实体数据的事务串行化，当多个并发更新事务出现更新失败几率较高时使用这种锁。
 *     - PESSIMISTIC_FORCE_INCREMENT
 *          当事务读取实体时，实体管理器就锁定实体，当事务结束时会增加实体的版本属性，即时实体没有修改。
 * <p>
 *     乐观锁
 *     <a href="https://www.bilibili.com/video/BV12Z421p7P6/" />
 *     悲观锁是无时无刻不认为会存在有高并发的机制，而乐观锁只是认为有可能会存在有高并发的机制，
 *     所以从性能上来讲，乐观锁要比悲观锁要高，也可以更好的面对高并发下的数据更新需要。
 *     乐观锁的实现主要是基于程序逻辑算法的方法来完成的，
 *     在进行数据表定义时除了要定义有核心的字段外，还需要提供有一个版本的编号，
 *     在每次读取的时候都会读取到版本编号，并且在每次更新的时候，也都会根据版本号实现更新处理。
 *     由于版本号的操作字段不属于核心的控制逻辑，所以在每次进行版本号处理的时候，全部由JPA负责完成，
 *     在进行数据查询的时候，也会自动的返回有该版本号的数据，同时在每次更新后版本号也会发生改变。
 * <p>
 *     假如现在有两个线程，同时读取乐ID的内容，在没有其他线程更新之前，所获取到的所有版本编号为 1，
 *     如果现在有一个线程要进行数据更新，那么在更新优先对版本号进行匹配（线程中的版本号要与指定ID的版本号一致），
 *     如果版本号一致，则予以更新；如果版本号不一致，则不予以更新。
 *     更新完成后，版本号也会随着增长。
 *     update table set version=2 where xx=.. and version=1 (原子化操作)
 *     此时，如果其他读取到该数据的线程也需要更新，但是发现版本号不一致了，出现更新失败，终止更新。
 * <p>
 *     所有数据表中定义的字段需要映射到PO类上，但是版本号是由JPA维护的，所以应该追加一些特殊的注解。
 * @author caimeng
 * @date 2024/6/19 17:01
 */
package com.example.boot3.jpa.lock;

import jakarta.persistence.LockModeType;

import java.util.Map;