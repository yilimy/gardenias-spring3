/**
 * mockito加junit搞定单元测试
 * 乐之java <a href="https://www.bilibili.com/video/BV1P14y1k7Hi/" />
 * <p>
 *     单元测试的特点
 *     1. 配合断言使用，杜绝 System.out
 *     2. 可重复执行
 *     3. 不依赖环境
 *     4. 不会对数据产生影响
 *     5. spring的上下文环境不是必须的
 *     6. 一般都要配合mock类框架使用
 * <p>
 *     mock类框架使用的场景
 *     要进行测试的方法存在外部的依赖，比如：db，redis，第三方调用等
 *     为了能够专注的对于该方法进行调用，就希望虚拟出外部的依赖，避免外部依赖成为测试的阻碍。
 *     一般都是测试service层即可。
 * <p>
 *     mock类框架 - mockito (mock input to output)
 *     官网地址:  <a href="https://site.mockito.org/" />
 *     官方文档地址: <a href="https://javadoc.io/doc/org.mockito/mockito-core/latest/org/mockito/Mockito.html" />
 *     老版本对 final | static | private 支持不太友好，最新版本已支持前两个，private依旧不支持, e.g.
 *          <a href="https://javadoc.io/doc/org.mockito/mockito-core/latest/org/mockito/Mockito.html">
 *              39. Mocking final types, enums and final methods (Since 2.1.0) Link icon </a>
 *     其他mock类框架:
 *          - easymock
 *          - powermock     支持 private
 *          - JMockit
 * <p>
 *     初始化
 *     junit4 @RunWith
 *     junit5 @ExtendWith
 * <p>
 *     断言工具
 *     Junit4 : hamcrest, 该工具在junit5之后移除
 *     assertJ : 常用的额断言工具
 *     Junit4原生断言
 *     Junit5原生断言
 * @author caimeng
 * @date 2024/6/20 18:24
 */
package com.example.junit;