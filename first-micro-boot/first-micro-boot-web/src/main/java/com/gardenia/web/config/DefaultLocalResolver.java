package com.gardenia.web.config;

import com.gardenia.web.action.I18NAction;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.LocaleResolver;

import java.util.Locale;

/**
 * 类型转换器，将请求参数转换为 {@link java.util.Locale}
 * @see I18NAction#showLocal(Locale)
 * @author caimeng
 * @date 2025/2/7 15:22
 */
@Configuration  // 配置Bean
public class DefaultLocalResolver implements LocaleResolver {
    @SuppressWarnings("NullableProblems")
    @Override
    public Locale resolveLocale(@NonNull HttpServletRequest request) {
        /*
         * 此时接收的参数名称肯定是 locale .
         * 传递的参数值举例: zh_CN
         */
        String loc = request.getParameter("loc");
        if (StringUtils.hasLength(loc)) {
            // 数据拆分
            String[] split = loc.split("_");
            // 手工实例化Local对象
            return split.length > 1 ? new Locale(split[0], split[1]) : new Locale(split[0]);
        } else {
            return Locale.getDefault();
        }
    }

    @Override
    public void setLocale(@NonNull HttpServletRequest request, HttpServletResponse response, Locale locale) {

    }

    @Bean
    public LocaleResolver localeResolver() {        // Bean注入
        return new DefaultLocalResolver();
    }
}
