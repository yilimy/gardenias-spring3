/**
 * JSR 303 数据验证规范
 * <a href="https://www.bilibili.com/video/BV1mTNheTE7p/" />
 * <p>
 *     步骤
 *     1. 引入依赖 org.hibernate.validator:hibernate-validator
 *     2. 请求对象添加注解
 *          {@link com.gardenia.web.vo.MessageForJSR303}
 *     3. 访问接口添加 @Valid
 *          {@link com.gardenia.web.action.ValidateAction#jsr303(MessageForJSR303)}
 * <p>
 *     JSR 303 是一系列的注解组成的，在代码中通过注解的形式进行各种验证规则的配置。
 * @author caimeng
 * @date 2025/2/21 15:22
 */
package com.gardenia.web.jsr303;

import com.gardenia.web.vo.MessageForJSR303;