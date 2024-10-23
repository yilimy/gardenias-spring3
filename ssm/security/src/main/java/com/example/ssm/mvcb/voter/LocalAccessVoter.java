package com.example.ssm.mvcb.voter;

import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.vote.AuthenticatedVoter;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

import java.util.Collection;

/**
 * 本地投票器
 * @author caimeng
 * @date 2024/10/23 15:33
 */
public class LocalAccessVoter implements AccessDecisionVoter<FilterInvocation> {
    // 本地访问的标记
    public static final String LOCAL_FLAG = "LOCAL_IP";
    @Override
    public boolean supports(ConfigAttribute attribute) {
        return attribute != null && attribute.toString().contains(LOCAL_FLAG);
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return FilterInvocation.class.equals(clazz);
    }

    @Override
    public int vote(Authentication authentication, FilterInvocation object, Collection<ConfigAttribute> attributes) {
        if (authentication.getDetails() instanceof WebAuthenticationDetails webDetails) {
            // 获取用户的ip
            String ip = webDetails.getRemoteAddress();
            for (ConfigAttribute next : attributes) {
                // 获取配置属性
                if (LOCAL_FLAG.equals(next.toString())) {
                    if ("127.0.0.1".equals(ip) || "0:0:0:0:0:0:0:1".equals(ip)) {
                        return AuthenticatedVoter.ACCESS_GRANTED;   // 通过
                    }
                }
            }
            return AuthenticatedVoter.ACCESS_ABSTAIN;   // 弃权
        } else {
            // 访问不是来自于web
            return AuthenticatedVoter.ACCESS_DENIED;    // 反对
        }

    }
}
