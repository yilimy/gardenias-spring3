/**
 * WEB模块
 * <p>
 *     这个模块主要引用“first-micro-boot-web”子模块，并且实现WEB程序的开发
 * <p>
 *     1. 数据绑定和Date类型转换
 *          {@link com.gardenia.common.action.abs.AbstractBaseAction#initBinder(WebDataBinder)}
 *     2. fastjson替换jackson
 *          {@link com.gardenia.web.config.WebConfig#configureMessageConverters(List)}
 *     3. 请求举例: micro_boot.http
 * @author caimeng
 * @date 2025/1/20 14:43
 */
package com.gardenia.web;

import org.springframework.web.bind.WebDataBinder;

import java.util.List;