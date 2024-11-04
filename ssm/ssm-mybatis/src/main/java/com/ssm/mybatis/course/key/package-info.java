/**
 * Mybatis获取生成的主键
 * <a href="https://www.bilibili.com/video/BV1WVxLe6Ett/" />
 * <p>
 *     直接调用 com.ssm.mybatis.vo.BookTest#doCreateWithFactoryTest() 尝试获取主键
 *          结果为： null
 *     Mybatis 并不像JPA那样智能，默认情况下用户无法直接获取到已增加数据的主键值，
 *     如果想要获取就需要修改SQL映射文件的配置项。
 *     <code>
 *         keyProperty="bid" keyColumn="bid" useGeneratedKeys="true"
 *     </code>
 *     在MyBatis内部实现了JDBC的操作封装，可以通过Statement来获取当前增长后的主键内容，
 *     设置VO类的属性名称(keyProperty="bid"),
 *     数据表对应的列名称(keyColumn="bid"),
 *     要获取增长后的主键项(useGeneratedKeys="true").
 * <p>
 *     对于一些其他的数据库，可能就没有这种自动的主键获取了，例如Oracle，
 *          - 你永远不可能在开发中回避掉Oracle数据库的存在，尽管在当前的互联网开发行业内会大量采用MySql数据库。
 *     Oracle数据库里使用的是Sequence实现了主键数据的生成，它不是直接包装在数控列上的控制。
 *     在Mybatis中可以单独设计一个查询（增加后的数据查询）
 *          - com.ssm.mybatis.mapper.BookMapper.doCreateAdapterOracle
 *     在Mysql数据库内部是可以通过 LAST_INSERT_ID() 获取当前会话之中增长后的数据ID项的，
 *     在执行了insert语句之后执行了数据主键的查询。
 *     批注：
 *          这种处理形式，需要在代码中进行两次的数据库操作，按照性能的设计来讲，肯定是不符合要求的。
 *          如果不是这种传统的序列型(Sequence)数据库，一般还是建议采用JDBC的方式比较方便。
 * @author caimeng
 * @date 2024/10/30 18:53
 */
package com.ssm.mybatis.course.key;