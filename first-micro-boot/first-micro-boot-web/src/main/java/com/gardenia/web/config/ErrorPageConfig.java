package com.gardenia.web.config;

import com.gardenia.web.action.ErrorAction;
import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.ErrorPageRegistrar;
import org.springframework.boot.web.server.ErrorPageRegistry;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;

/**
 * 自定义404页面: {@link ErrorAction#errorCode404()}
 * @author caimeng
 * @date 2025/2/18 11:13
 */
@Configuration
public class ErrorPageConfig implements ErrorPageRegistrar {    // 错误页的注册
    @Override
    public void registerErrorPages(ErrorPageRegistry registry) {
        ErrorPage errorPage404 = new ErrorPage(HttpStatus.NOT_FOUND, "/errors/error_404");
        ErrorPage errorPage500 = new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/errors/error_500");
        registry.addErrorPages(errorPage404, errorPage500);
    }
}
