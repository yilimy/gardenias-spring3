/**
 * 开始WebService服务端
 * 1. 实现公共接口
 *      {@link com.gardenia.webservice.common.service.IMessageService}
 *      {@link com.gardenia.webservice.service.impl.MessageServiceImpl}
 * 2. 配置安全策略
 *      {@link com.gardenia.webservice.service.interceptor.WebServiceAuthInterceptor}
 * 3. 绑定接口和拦截器
 *      {@link com.gardenia.webservice.service.config.CXFConfig}
 * 由于java版本原因，该项目不运行
 * @author caimeng
 * @date 2025/4/9 11:57
 */
package com.gardenia.webservice.service;