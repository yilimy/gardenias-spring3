/**
 * 事件处理
 * 1. 定义实体 {@link com.gardenia.web.vo.MessageForEvent}
 * 2. 定义事件对象 {@link com.gardenia.web.event.YootkEvent}
 * 3. 定义事件监听器 {@link com.gardenia.web.event.listener.YootkListener}
 * 4. 使用spring提供的发布器对事件进行发布 {@link org.springframework.context.ApplicationEventPublisher}
 *
 * 以上是将事件监听器通过bean的方式注入的，还可以通过yaml的方式进行配置
 *      配置文件中设置 context.listener.classes
 *      {@link com.gardenia.web.event.listener.PropertiesListener}
 * <p>
 *     当有两个监听器同时对一个事件进行监听时，所有的监听器会按配置的顺序进行调用
 *     事件 {@link com.gardenia.web.event.YootkEvent}
 *     监听器：
 *          {@link com.gardenia.web.event.listener.YootkListener}
 *          {@link com.gardenia.web.event.listener.PropertiesListener}
 * <p>
 *     其他：
 *          通过注解添加事件监听器
 *          {@link com.gardenia.web.event.listener.AnnotationListener}
 *
 * @author caimeng
 * @date 2025/4/1 16:13
 */
package com.gardenia.web.event;