/**
 * 配置MyBatis结构别名
 * <a href="https://www.bilibili.com/video/BV1fHxLePEQA/" />
 * <p>
 *     在xml映射文件中，比如，存在有parameterType映射一个java类。
 *     如果因为项目变更，导致类的包名发生修改，这样会导致大量的映射文件修正。
 *     常用的做法是为VO类定义一个别名，有两种方式：
 *          - 采用单一的配置
 *          - 采用扫描的配置
 * <p>
 *     单一配置
 *          mybatis.cfg.xml 文件中 configuration.typeAliases.typeAlias
 *          BookMapper.xml 文件中，修改 doCreate 方法的 parameterType com.ssm.mybatis.vo.Book --> Book
 *          com.ssm.mybatis.vo.BookTest#doCreateWithFactoryTest()
 *     但是上述的 BookMapper.xml 改动会导致mybatis-plus插件报错
 * <p>
 *     扫描包的配置
 *          mybatis.cfg.xml 文件中 configuration.package
 *              该配置与 configuration.typeAliases.typeAlias 冲突，二选一
 *          如果配置了扫描包，则MyBatis会将类名称自动设置为别名名称，
 *          VO类的定义也尽量要与数据库表结构保持一致。
 * <p>
 *     建议尽量使用别名（但是插件报错）
 * @author caimeng
 * @date 2024/10/30 18:31
 */
package com.ssm.mybatis.course.alias;