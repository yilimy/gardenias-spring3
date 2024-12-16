/**
 * 数据类型转换器
 * <a href="https://www.bilibili.com/video/BV1Y6yqYjEnx/" />
 * {@link org.apache.ibatis.type.TypeHandler}
 * <p>
 *     现在所使用的关系型数据库，随着时代的发展，很多支持的数据类型也是非常繁多的，
 *     只不过按照常规的开发来讲，常见的数据类型包括：
 *          - 数字
 *          - 字符串
 *          - 日期时间
 *          - 大文本
 *     而像一些新兴的数据库（PGSQL），它支持的数据类型会更多
 * <p>
 *     数据类型转换器，是指可以将程序之中的属性类型与非一致性的数据表中的列类型进行映射。
 *      - setParameter  设置的时候，对应的处理
 *      - getResult     数据获取的时候，对应的处理
 * <p>
 *     1. 定义类型转换器
 *     2. 在xml的结果映射中指定类型转换器
 *     3. 在mybatis.cfg.xml中注册mapper和类型转换器
 * @author caimeng
 * @date 2024/12/16 9:54
 */
package com.ssm.mybatis.type;