package com.gardenia.web.actuator;

import org.springframework.boot.actuate.info.Info;
import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.stereotype.Component;

/**
 * 自定义 actuator 模块信息
 * @author caimeng
 * @date 2025/3/11 11:12
 */
@Component
public class MicroServiceInfoContributor implements InfoContributor {   // 自定义Info构建器
    @Override
    public void contribute(Info.Builder builder) {
        builder.withDetail("company.name", "yootk.com");
    }
}
