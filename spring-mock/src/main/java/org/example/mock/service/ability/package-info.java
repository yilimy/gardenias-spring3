/**
 * 该包下的类会被扫描为bean，并作为spring的特殊功能而存在
 * <p>
 *     后置处理器 {@link org.example.mock.spring.IBeanPostProcessor}
 *     用于对bean的实例化前后做增强
 * <p>
 *     AOP
 *     产生代理对象，对bean做增强
 *     如果正在创建的bean需要进行AOP的话，最终创建得到的bean应该是个AOP之后的代理对象
 * @author caimeng
 * @date 2024/6/13 18:34
 */
package org.example.mock.service.ability;