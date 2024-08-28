/**
 * RequestParam参数接收
 * <a href="https://www.bilibili.com/video/BV1rhp2emEjk/" />
 * <p>
 *     在SpringMVC的控制层，如果想要接收某些参数，直接将参数定义在方法中，
 *     每次进行HTTP请求提交的时候，会自动的将该请求中指定参数的名称与方法中参数的名称进行匹配，匹配成功后即可实现参数内容的注入。
 * <p>
 *     如果某些参数需要配置默认参数的时候，再去考虑@RequestParam注解，
 *     而如果不需要配置默认值的时候，建议请求参数的名称与方法参数名称保持一致即可。
 * @author caimeng
 * @date 2024/8/28 10:20
 */
package com.example.ssm.mvcb.action;