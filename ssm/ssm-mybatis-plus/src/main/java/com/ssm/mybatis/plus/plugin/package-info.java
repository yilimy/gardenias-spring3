/**
 * MyBatis 自定义拦截器开发
 * <a href="https://www.bilibili.com/video/BV1H8DZYKExq/" />
 * <p>
 *     在使用MyBatis开发框架的时候，其实也可以基于切面的操作方式，实现插件配置。
 *     这种插件的出现可以采用无侵入式的方式实现代码相关的拦截处理操作。
 *     Mybatis-Plus里为了更好的提供拦截的操作，也创建了属于自己的插件标准。
 *     {@link com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor}
 *     它本质是对MyBatis的拦截器提供一个新的模型
 *     {@link org.apache.ibatis.plugin.Interceptor}
 *     在使用 Mybatis-Plus 的时候，就不要再使用 Mybatis 的 Interceptor 了，而应该使用 InnerInterceptor
 *     {@link com.baomidou.mybatisplus.extension.plugins.inner.InnerInterceptor}
 * <p>
 *     自定义MyBatis-Plus拦截器
 *     1. 定义 InnerInterceptor 的子类
 *          {@link com.ssm.mybatis.plus.interceptor.MyInnerInterceptor}
 *     2. 修改配置，增加拦截功能
 *          {@link com.ssm.mybatis.plus.config.MyBatisPlusConfig#mybatisPlusInterceptor()}
 *          {@link com.ssm.mybatis.plus.config.MyBatisPlusConfig#sqlSessionFactoryBean(DataSource, GlobalConfig, MybatisPlusInterceptor)}
 *
 * 分页插件
 * <a href="https://www.bilibili.com/video/BV1VmDZY9EH3/" />
 * <p>
 *     所谓分页插件，指的是在进行数据查询的同时，可以将数据对应的统计信息一并查询出来，
 *     其实就是在拦截器里利用当前的数据库连接，多创建一次查询。
 * <p>
 *     乐观锁
 *     1. 实体和数据表字段中添加字段保存乐观锁，INT类型
 *     2. 实体类添加乐观锁注解，@version
 *     3. 添加乐观锁拦截器（插件）
 *          {@link com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor}
 *          {@link com.ssm.mybatis.plus.config.MyBatisPlusConfig#sqlSessionFactoryBean(DataSource, GlobalConfig, MybatisPlusInterceptor)}
 * @author caimeng
 * @date 2024/12/23 17:48
 */
package com.ssm.mybatis.plus.plugin;

import com.baomidou.mybatisplus.core.config.GlobalConfig;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;

import javax.sql.DataSource;