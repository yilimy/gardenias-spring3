package com.example.ssm.mvcb.action;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;

/**
 * @author caimeng
 * @date 2024/10/14 15:11
 */
@Slf4j
@Controller
@RequestMapping("/pages/member")
public class MemberAction {

    /**
     * 访问地址 <a href="http://localhost/pages/member/info" />
     * <p>
     *     该接口访问没有任何限制。
     *     返回结果:
     *          AnonymousAuthenticationToken [
     *              Principal=anonymousUser,
     *              Credentials=[PROTECTED],
     *              Authenticated=true,
     *              Details=WebAuthenticationDetails [
     *                  RemoteIpAddress=0:0:0:0:0:0:0:1,
     *                  SessionId=null
     *              ],
     *              Granted Authorities=[ROLE_ANONYMOUS]]
     *     结果显示为匿名用户 (anonymousUser) 访问
     * <p>
     *     先登录 <a href="http://localhost/login" />
     *     再访问 <a href="http://localhost/pages/member/info" />
     *     返回结果:
     *      {
     *          "authorities": [
     *              {"authority": "ROLE_ADMIN"},
     *              {"authority": "ROLE_MESSAGE"},
     *              {"authority": "ROLE_NEWS"},
     *              {"authority": "ROLE_SYSTEM"}],
     *          "details": {
     *              "remoteAddress": "0:0:0:0:0:0:0:1",
     *              "sessionId": "1744B6110D1C40DA2715B5AEE4A1DD67"
     *              },
     *          "authenticated": true,
     *          "principal": {
     *              "password": null,
     *              "username": "yootk",
     *              "authorities": [
     *                  {"authority": "ROLE_ADMIN"},
     *                  {"authority": "ROLE_MESSAGE"},
     *                  {"authority": "ROLE_NEWS"},
     *                  {"authority": "ROLE_SYSTEM"}],
     *              "accountNonExpired": true,
     *              "accountNonLocked": true,
     *              "credentialsNonExpired": true,
     *              "enabled": true
     *          },
     *          "credentials": null,
     *          "name": "yootk"
     *      }
     * @return 简单认证信息获取
     */
    @GetMapping("/info")
    @ResponseBody
    public Object info(){
        // 通过 SecurityContextHolder 获取认证信息
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        log.info("authentication={}", authentication);
        return authentication;
    }

    /**
     * 直接获取已认证的用户信息
     * <p>
     *     匿名用户
     *          <a href="http://localhost/pages/member/principal" />
     *          结果:
     *              匿名用户
     *     登录用户
     *          <a href="http://localhost/login" />
     *          <a href="http://localhost/pages/member/principal" />
     *          结果:
     *              principal=UsernamePasswordAuthenticationToken [Principal=org.springframework.security.core.userdetails.User [Username=yootk, Password=[PROTECTED], Enabled=true, AccountNonExpired=true, credentialsNonExpired=true, AccountNonLocked=true, Granted Authorities=[ROLE_ADMIN, ROLE_MESSAGE, ROLE_NEWS, ROLE_SYSTEM]], Credentials=[PROTECTED], Authenticated=true, Details=WebAuthenticationDetails [RemoteIpAddress=0:0:0:0:0:0:0:1, SessionId=9045DA8BAAC527489588E3F0D41F1CD6], Granted Authorities=[ROLE_ADMIN, ROLE_MESSAGE, ROLE_NEWS, ROLE_SYSTEM]]
     * @param principal 已认证的用户信息
     * @return 已认证的用户信息
     */
    @GetMapping("/principal")
    @ResponseBody
    public Object principal(Principal principal){
        log.info("principal={}", principal);
        return principal;
    }
}
