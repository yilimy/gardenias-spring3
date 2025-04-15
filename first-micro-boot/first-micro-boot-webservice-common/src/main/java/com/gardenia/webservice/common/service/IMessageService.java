package com.gardenia.webservice.common.service;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

/**
 * 定义业务接口
 * @author caimeng
 * @date 2025/4/9 11:44
 */
@WebService(
        name = "MessageService",
        // 命名空间与包名反向
        targetNamespace = "http://service.common.webservice.gardenia.com"
)
public interface IMessageService {

    @SuppressWarnings("unused")
    @WebMethod  // 必须写，否则报错: 进行WebService的方法标注
    String echo(@WebParam(name = "msg") String msg);
}
