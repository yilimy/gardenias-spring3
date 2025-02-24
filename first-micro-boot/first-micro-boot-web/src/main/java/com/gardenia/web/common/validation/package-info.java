/**
 * 自定义验证注解
 * 1. 创建一个自定义验证注解
 *      {@link com.gardenia.web.common.validation.annotation.RegexValidator}
 * 2. 创建一个自定义验证器
 *      {@link com.gardenia.web.common.validation.handler.RegexConstraintValidator}
 * 3. 新增错误消息提示 ValidationMessages.properties
 * 4. 在属性上配置注解
 *      {@link com.gardenia.web.vo.MessageForRegex#flag}
 * 5. 访问接口
 *      {@link com.gardenia.web.action.ValidateAction#jsr303ForRegex(com.gardenia.web.vo.MessageForRegex)}
 *      <a hrf="https://localhost/validate/jsr/regex?title=【沐言科技】&pubDate=2025-01-23&content=www.yootk.com&email=123@yootk.com&level=1&flag=yootk-101" />
 * @author caimeng
 * @date 2025/2/24 10:56
 */
package com.gardenia.web.common.validation;