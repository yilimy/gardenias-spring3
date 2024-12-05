/**
 * 数据库的更新
 * <a href="https://www.bilibili.com/video/BV1CM1qYwEa8/" />
 * 更新操作分为三种：
 * - 增加
 * - 修改
 * - 删除
 * 只有增加是安全的，修改和删除会因为操作不当而造成死锁。
 * Mybatis的所有数据库操作都是由SqlSession接口提供的。
 * @see org.apache.ibatis.session.SqlSession
 * @author caimeng
 * @date 2024/11/4 18:46
 */
package com.ssm.mybatis.course.update;