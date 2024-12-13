/**
 * 鉴别器
 * <p>
 *     类似于java中的继承。
 *     定义一个表 member2，
 *          1. 前四个字段是公共信息
 *              mid、name、age、sex
 *          2. 接下来两个字段是学生信息
 *              score、major
 *          3. 再然后的两个字段是雇员信息
 *              salary、dept
 *          4. type 是用来区别是学生还是雇员的标记位
 * <p>
 *     1. 创建 Member 公共字段 {@link com.ssm.mybatis.vo.Member}
 *     2. 创建 Student 子类 {@link com.ssm.mybatis.vo.Student}
 *     3. 创建 Employee 子类 {@link com.ssm.mybatis.vo.Employee}
 *     4. 创建 Member.xml 映射文件
 *     5. 修改 mybatis.cfg.xml 配置文件，追加 mappers 路径定义
 * @author caimeng
 * @date 2024/12/13 17:16
 */
package com.ssm.mybatis.discriminator;