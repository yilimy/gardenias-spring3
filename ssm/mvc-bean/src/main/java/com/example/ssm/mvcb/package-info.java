/**
 * 使用bean的方式启动容器，而不是web.xml的方式
 * <a href="https://www.bilibili.com/video/BV1fnYse3E3P/" />
 * {@link org.springframework.web.SpringServletContainerInitializer}
 * <p>
 *     在当前SpringMVC里面，只要提供了 {@link org.springframework.web.WebApplicationInitializer} 的接口实例，
 *     就可以通过该接口的实例进行Spring容器的初始化。
 * @author caimeng
 * @date 2024/8/27 16:26
 */
package com.example.ssm.mvcb;