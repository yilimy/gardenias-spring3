/**
 * SpringSecurity注解支持
 * <a href="https://www.bilibili.com/video/BV1fMtJejEM8/" />
 * <p>
 *     SpringSecurity
 *          - @PreAuthorize("SpEL")
 *              在业务调用前使用安全表达式进行验证
 *          - @PostAuthorize("SpEL")
 *              在业务方法调用后使用安全表达式进行验证，
 *              该操作适用于验证带有返回值的授权检查，在数据返回时如果发现用户有此授权则进行返回，
 *              如果未有此授权则不返回数据，
 *              同时也可以通过 returnObject 在表达式语言中获取返回内容。
 *          - @PreFilter(filterTarget="参数名", value="SpEL")
 *              在业务方法调用前进行数据过滤，该注解需要接收的参数类型为Collection集合，
 *              在条件表达式中可以通过filterObject表示集合的每一个元素，最终只保留过滤后的数据。
 *          - @PostFilter("SpEL")
 *              对业务方法调用后的返回结果进行过滤，此时的业务方法返回的必须是一个Collection
 *          - @Secured({"角色", "角色", ...})
 *              SpringSecurity2.0旧版本提供的授权检测，不支持SpEL表达式，
 *              在授权检查时需要写上授权项的全称
 *     JSR-250
 *          - @DenyAll
 *              拒绝全部访问
 *          - @PermitAll
 *              允许全部访问
 *          - @RolesAllowed({"角色", "角色", ...})
 *              授权检测，拥有其中一项授权即可访问
 * <p>
 *     为了迎合这两种不同的机制，需要使用特定的注解
 *     视频中使用的是 org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity, 该类已废弃
 *     推荐使用 {@link org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity}
 *     代码示例
 *          {@link com.example.ssm.mvcb.config.MethodSecurityConfig}
 *          {@link com.example.ssm.mvcb.service.IAdminService}
 * <p>
 *     所有的授权检查的注解只允许出现在业务层，控制层添加是没有用的，控制层注入业务接口实例。
 *     业务层 {@link com.example.ssm.mvcb.service.IAdminService}
 *     控制层是通过配置添加的权限校验 {@link com.example.ssm.mvcb.config.WebMVCSecurityConfiguration#filterChain(HttpSecurity)}
 * <p>
 *     对于权限的控制，可以使用路径匹配的模式，也可以在业务层中进行配置，考虑到代码开发的简洁性，建议大家还是基于公共的路径匹配模式。
 * @author caimeng
 * @date 2024/10/14 17:11
 */
package com.example.ssm.security.annotation;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;