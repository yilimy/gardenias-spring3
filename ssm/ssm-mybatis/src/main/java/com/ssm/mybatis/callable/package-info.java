/**
 * Mysql存储过程
 * <a href="https://www.bilibili.com/video/BV1Y6yqYjEVa/" />
 * <p>
 *     对于存储过程来讲，在20年前的项目开发中很常见，因为可以极大的简化程序代码。
 *     但是随着互联网编程的到来，由于库表分离的设计，由于高并发的处理问题等，存储过程已经很少见了。
 *     Mybatis的技术跨越周期又很长，所以Mybatis内部提供有对存储过程的支持。
 * <p>
 *     Mybatis之中的存储过程调用，也是由JDBC支持的，在JDBC里面提供了一个CallStatement存储过程的接口，
 *     可以通过 IN、INOUT 进行参数传递，
 *     这样在最终存储过程处理完成之后，就可以直接返回业务数据了。
 * <p>
 *     1. 定义存储过程：
 *          book_select_proc.sql（单个返回结果）
 *          book_multi_select_proc.sql（多个返回结果）
 *     2. 修改 BookMapper.xml 配置文件，定义SQL调用
 *     3. 注意多返回结果的接收
 * <p>
 *     建议在正式开发中不要使用存储过程，因为很难进行后期的扩展和结构上的设计。
 * @author caimeng
 * @date 2024/12/13 11:15
 */
package com.ssm.mybatis.callable;