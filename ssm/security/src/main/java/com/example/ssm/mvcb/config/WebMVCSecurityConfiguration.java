package com.example.ssm.mvcb.config;

import com.example.ssm.mvcb.context.config.SpringWebContextConfig;
import com.example.ssm.mvcb.filter.CaptchaAuthenticationFilter;
import lombok.SneakyThrows;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authorization.AuthenticatedAuthorizationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

/**
 * @author caimeng
 * @date 2024/9/29 14:55
 */
@Configuration
@EnableWebSecurity  // 启用 Spring Security 的支持
public class WebMVCSecurityConfiguration {  // WEB配置类
    public static final String LOGIN_PAGE = "/login_page";
    public static final String LOGIN_ACTION = "/yootk-login";
    public static final String LOGOUT_PAGE = "/logout_page";
    public static final String ERROR_403 = "/error_403";

    @Bean(name = "mvcHandlerMappingIntrospector")
    public HandlerMappingIntrospector mvcHandlerMappingIntrospector() {
        return new HandlerMappingIntrospector();
    }

    @Bean
    public HttpSessionEventPublisher httpSessionEventPublisher() {
        // 剔除已登录用户的事件注册
        return new HttpSessionEventPublisher();
    }

    @Bean
    public SimpleUrlAuthenticationFailureHandler authenticationFailureHandler() {
        // 认证失败
        SimpleUrlAuthenticationFailureHandler handler = new SimpleUrlAuthenticationFailureHandler();
        // 错误的跳转路径
        handler.setDefaultFailureUrl(LOGIN_PAGE + "?error=true");
        return handler;
    }

    /**
     * 注册 Spring Security 拦截链
     * <p>
     *     注意视图资源配置 {@link ResourceViewConfig#resourceViewResolver()}
     * <p>
     *     因为 Spring Security 版本不一致，导致 SecurityFilterChain 的注入有区别.
     *     当前注入还需要额外注入 HandlerMappingIntrospector 对象
     *     <a href="https://www.bilibili.com/video/BV1BStJeEE6g/" />
     *     本地版本: 6.0.1
     *     参考版本: 6.0.0-M3
     *     {@link HttpSecurity#createMvcMatchers(String...)}
     * <p>
     *     用户信息配置
     *     {@link com.example.ssm.mvcb.service.YootkUserDetailsService#loadUserByUsername(String)}
     * @param http HttpSecurity
     * @return security 认证链
     */
    @SneakyThrows
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http,
                                           // 免登录时添加的参数
                                           UserDetailsService userDetailsService,
                                           // 持久化时添加的参数
                                           JdbcTokenRepositoryImpl jdbcTokenRepository,
                                           // 验证失败的处理类
                                           SimpleUrlAuthenticationFailureHandler failureHandler) {
        // 配置session管理器
        http.sessionManagement()
                // 一个账户并行数量为1
                .maximumSessions(1)
                /*
                 * 剔除之前登录过的.
                 * 一旦某个账户被剔除，SpringSecurity会认为需要为其添加一个Session处理事件
                 * httpSessionEventPublisher()
                 */
                .maxSessionsPreventsLogin(false)
                // session失效后的显示页面
                .expiredUrl("/?invalidate=true");
        // 配置认证的访问请求
        http
                .authorizeHttpRequests((authorizeHttpRequests) ->
                        authorizeHttpRequests
                                // 配置认证目录
                                .requestMatchers("/pages/message/**")
                                // 普通认证
//                                .authenticated()
                                // 强制认证
                                .fullyAuthenticated()
                                /*
                                 * access(方法) 等同于直接调用 authenticated()
                                 * 只允许认证后的用户访问
                                 */
                                .requestMatchers("/pages/authenticated/**")
                                .access(AuthenticatedAuthorizationManager.authenticated())
                                /*
                                 * 用户详情中配置了角色
                                 * com.example.ssm.mvcb.service.YootkUserDetailsService.loadUserByUsername
                                 * 注意: 角色 ADMIN， 配置的值为 ROLE_ADMIN，"ROLE_"为前缀
                                 */
                                .requestMatchers("/pages/role/info")
                                .hasRole("ADMIN")
                                // 任意角色，命中其一
                                .requestMatchers("/pages/role/any")
                                .hasAnyRole("NEWS", "ADMIN")
                                /*
                                 * 配置一个不存在的路径
                                 * http://localhost/pages/message/info
                                 * 先认证，再跳转到404
                                 */
                                .requestMatchers("/pages/news/**")
                                .hasAnyRole("NEWS")
                                // 任意访问
                                .requestMatchers("/**")
                                .permitAll()
                );
//        http.formLogin();   // SpringSecurity 中自带的登录表单
                // 配置自定义登录事项
        http.formLogin()
                // 自定义表单的用户名参数名称
                .usernameParameter("mid")
                // 自定义表单的密码参数名称
                .passwordParameter("pwd")
                // 登录成功后的
                .successForwardUrl("/")
                // 登录表单的路径
                .loginPage(LOGIN_PAGE)
                // 表单提交路径, 见 /pages/login.jsp
                .loginProcessingUrl(LOGIN_ACTION)
                // 登录失败页，见 /pages/login.jsp
                .failureForwardUrl(LOGIN_PAGE + "?error=登录失败，错误的用户名或者密码！")
                .and()
                // 配置注销事项
                .logout()
                // 注销路径
                .logoutUrl("/yootk-logout")
                // 注销后的显示路径
                .logoutSuccessUrl(LOGOUT_PAGE)
                // 注销后删除 cookies
                .deleteCookies("JSESSIONID", "yootk-cookie-rememberme")
                .and()
                // 认证错误的配置
                .exceptionHandling()
                // 失败处理
                .accessDeniedPage(ERROR_403);
        // 关闭 CSRF 功能
        http.csrf().disable();
        // 配置免登录
        http.rememberMe()
                // 如果要持久化，设置持久化仓库
                .tokenRepository(jdbcTokenRepository)
                // 获取用户信息
                .userDetailsService(userDetailsService)
                /*
                 * 参数名称
                 * login.jsp中下拉框的提交参数
                 */
                .rememberMeParameter("rme")
                // 数据加密密钥
                .key("yootk-lixinhua")
                /*
                 * Cookie保存时间，单位秒.30天免登录
                 * 2_592_000 是 2,592,000 的另一种写法，等于 2592000
                 */
                .tokenValiditySeconds(2_592_000)
                /*
                 * Cookie的名称
                 * 注销环节(deleteCookies)也要删除该Cookie值
                 */
                .rememberMeCookieName("yootk-cookie-rememberme");
        CaptchaAuthenticationFilter captchaAuthenticationFilter = new CaptchaAuthenticationFilter();
        captchaAuthenticationFilter.setAuthenticationFailureHandler(failureHandler);
        // 验证码应该在用户登录认证之前进行检查
        http.addFilterBefore(captchaAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    /**
     * 自定义配置，一般针对图片等资源目录
     * 注意映射配置 {@link SpringWebContextConfig#addResourceHandlers(ResourceHandlerRegistry)}
     * @return 自定义配置
     */
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.ignoring().requestMatchers(
                "/yootk-images/**",
                "/yootk-js/**",
                "/yootk-css/**"
        );
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        // 密码加密器
        return new BCryptPasswordEncoder();
    }

}
