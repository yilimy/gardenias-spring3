/**
 * 开始
 * <a href="https://docs.spring.io/spring-boot/docs/2.4.3/reference/htmlsingle/#getting-started-first-application-code" />
 * <p>
 *     在实际的开发中，一个项目肯定会有多个控制层，而且每个类也会有不同的路径，这样会比较繁琐。
 *     所以，此时就必须采用SpringBoot本身的约定来进行代码结构的优化。
 *          > 父包启动类和子包组件类
 *          > 父包 : com.gardenia
 *          > 子包 : com.gardenia.*
 *     1. action包
 *          {@link com.gardenia.action.MessageAction}
 *     2. 创建新启动类
 *          {@link com.gardenia.StartSpringBootApplication}
 *     3. 删掉无用的启动类
 *          {@link com.gardenia.FirstSpringBootApplication}
 *          删掉指：去掉 EnableAutoConfiguration 注解，不被spring容器扫描
 * @author caimeng
 * @date 2025/1/17 17:03
 */
package com.gardenia;