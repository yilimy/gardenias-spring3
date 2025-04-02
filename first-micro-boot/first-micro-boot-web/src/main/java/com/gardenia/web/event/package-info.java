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
 *          优势：
 *              1. 可通过条件的触发事件
 *              2. 数据对象不局限于 {@link org.springframework.context.ApplicationEvent} 的实现，可以是业务数据本身
 * <p>
 *     注意事项:
 *     Spring 事件处理机制在解耦业务逻辑方面非常有用，但在使用时需要注意其潜在的缺陷和限制。
 *     为了避免这些问题，建议在设计系统时仔细考虑事件的发布时机、监听器的注册顺序以及事务管理等问题。
 *     对于强一致性的业务场景，可能需要考虑其他解决方案，如使用分布式事务或消息队列。
 *     另外，Spring 事件处理是同步的，即发布者会阻塞直到所有监听器处理完成。
 *     如果需要异步处理事件，需要配置 ApplicationEventMulticaster 并设置线程池
 *
 * @author caimeng
 * @date 2025/4/1 16:13
 */
package com.gardenia.web.event;