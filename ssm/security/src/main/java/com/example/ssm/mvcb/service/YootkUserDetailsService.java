package com.example.ssm.mvcb.service;

import com.example.ssm.mvcb.config.WebMVCSecurityConfiguration;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 该类实现了 Spring Security 的用户详情接口，
 * 是业务用户与 Security 用户的桥梁。
 *
 * @author caimeng
 * @date 2024/9/29 19:46
 */
@Service
public class YootkUserDetailsService implements UserDetailsService {
    /**
     * 测试用户登录
     * <p>
     *     访问路径
     *     <a href="http://localhost/login" />
     *     or
     *     <a href="http://localhost/pages/message/info" />
     *     用户名密码  yootk / hello
     * <p>
     *     该密码使用了 bcrypt 加密，因此需要注入加密器
     *     {@link WebMVCSecurityConfiguration#passwordEncoder()}
     * @param username the username identifying the user whose data is required.
     * @return 用户信息
     * @throws UsernameNotFoundException UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        /*
         * 本次的开发将使用默认的用户名“yootk”。
         * 如果使用的是数据库，此处通过数据库加载.
         * Spring Security 使用 GrantedAuthority 描述用户具备的权限
         */
        if ("yootk".equals(username)) {
            return loadYootk();
        } else if ("lee".equals(username)) {
            return loadLee();
        }
        return null;
    }

    private User loadYootk() {
        List<GrantedAuthority> authorityList = new ArrayList<>();
        // 在进行授权配置的时候，Spring Security 权限必须使用“ROLE_”作为前缀配置
        authorityList.add(new SimpleGrantedAuthority("ROLE_NEWS")); // 授权项, 新用户
        authorityList.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        authorityList.add(new SimpleGrantedAuthority("ROLE_SYSTEM"));
        // 所有用户都具有的公共权限，用于访问“/pages/message/**”路径下的资源
        authorityList.add(new SimpleGrantedAuthority("ROLE_MESSAGE"));
        return new User("yootk",
                // 加密后的密码, 明文：hello，加密算法 bcrypt
                "$2a$10$ncT6ccP.CoVlum8W5f8H8uG5sxejkxxxOZlk6twbwhvDCnqW1w/5q",
                authorityList);
    }

    private User loadLee() {
        List<GrantedAuthority> authorityList = new ArrayList<>();
        // 在进行授权配置的时候，Spring Security 权限必须使用“ROLE_”作为前缀配置
        authorityList.add(new SimpleGrantedAuthority("ROLE_NEWS")); // 授权项, 新用户
        return new User("lee",
                // 加密后的密码, 明文：hello，加密算法 bcrypt
                "$2a$10$ncT6ccP.CoVlum8W5f8H8uG5sxejkxxxOZlk6twbwhvDCnqW1w/5q",
                authorityList);
    }
}
