/**
 * spring-mvc扫描的包
 * <p>
 *     一般的定义的是与web相关类的扫描。
 *     注意不要重复扫描。
 * <p>
 *     对于SpringMVC来讲，其所有的程序只是在原有的Spring开发的基础上追加了控制器的开发，其他的形式没有任何的区别。
 *     SpringMVC中的控制器一般被成为action，同时会创建一个ModelAndView，并基于ModelAndView进行结果跳转。
 * <p>
 *     {@link org.springframework.web.servlet.ModelAndView}
 *     view 常见有两种表示方式
 *      - 字符串形式，跳转到webapp中的jsp
 *      - View 对象 {@link org.springframework.web.servlet.View}
 *     model 需要传递的数据内容（属性）
 * <p>
 *     在springMVC设计中，如果不希望使用ModelAndView进行数据返回，也可以直接在控制层方法上定义字符串的返回路径，利用Model对象对内容进行封装。
 *     {@link com.example.ssm.mvc.action.MessageAction#echoPath(String, Model)}
 * @author caimeng
 * @date 2024/8/23 18:12
 */
package com.example.ssm.mvc.action;

import org.springframework.ui.Model;