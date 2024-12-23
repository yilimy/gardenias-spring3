/**
 * 主键生成策略
 * <p>
 *     为什么不用UUID
 *     1. 长度
 *          UUID为16字节，128位数据，3.4x10(123次方)
 *     2. 数据类型
 *          使用数字创建索引是最快的，但是UUID使用的是字符串
 *     3. UUID内部会包含网卡Mac信息，所以安全性也有问题。
 *     4. 无法按生成时间（insert时要用）排序
 * <p>
 *     雪花算法
 *     {@link com.imadcn.framework.idworker.algorithm.Snowflake}
 *     Twitter提供的开源算法，按时间自增排序，便于生成数据库唯一索引，每秒可产生26W个ID。
 *     构成
 *     1. 最高位标识，
 *          1位，long是有符号的，所以第一位用于表示符号位，不使用；
 *     2. 时间戳，
 *          毫秒级，占位41位，保存时间戳差值（当前时间戳 - 开始时间戳）
 *     3. 数据中心ID
 *          5位
 *     4. 工作主机ID
 *          5位
 *     5. 序列ID
 *          12位，毫秒内的计数，每毫秒可以产生4096个ID序号
 * @author caimeng
 * @date 2024/12/23 15:27
 */
package com.ssm.mybatis.plus.generator;