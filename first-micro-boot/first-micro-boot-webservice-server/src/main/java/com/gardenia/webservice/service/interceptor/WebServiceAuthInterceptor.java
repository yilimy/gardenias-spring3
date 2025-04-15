package com.gardenia.webservice.service.interceptor;

import javax.xml.soap.SOAPException;
import lombok.extern.slf4j.Slf4j;
import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.binding.soap.saaj.SAAJInInterceptor;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.phase.Phase;
import org.springframework.stereotype.Component;

import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPMessage;
import org.w3c.dom.NodeList;

/**
 * 授权拦截器
 * @author caimeng
 * @date 2025/4/9 13:44
 */
@Slf4j
@Component
public class WebServiceAuthInterceptor extends AbstractPhaseInterceptor<SoapMessage> {
    // 用户认证标识
    public static final String AUTH_HEADER = "Authorization";
    public static final String PWD_HEADER = "password";
    // 用户名
    public static final String USER_NAME = "muyan";
    // 密码
    public static final String USER_PASSWORD = "yootk";
    // 创建拦截器
    private final SAAJInInterceptor saa = new SAAJInInterceptor();

    public WebServiceAuthInterceptor() {
        super(Phase.PROTOCOL);
        // 添加拦截器
        super.getAfter().add(SAAJInInterceptor.class.getName());
    }

    @Override
    public void handleMessage(SoapMessage message) throws Fault {
        // 获取指定的消息
        SOAPMessage soapMessage = message.getContent(SOAPMessage.class);
        if (soapMessage == null) {  // 没有消息内容
            // 走默认处理
            saa.handleMessage(message);
            // 再次尝试获取消息
            soapMessage = message.getContent(SOAPMessage.class);
        }
        SOAPHeader header = null;
        try {
            // 通过消息获取头信息
            header = soapMessage.getSOAPHeader();
        } catch (SOAPException e) {
            e.printStackTrace();
        }
        if (header == null) {   // 没有头信息
            throw new Fault(new IllegalArgumentException("找不到Header信息, 无法实现用户认证处理！"));
        }
        // SOAP是基于XML文件结构进行传输的，所以想要进行认证，需要进行相关的结构约定
        NodeList userNodeList = header.getElementsByTagName(AUTH_HEADER);
        NodeList passwordNodeList = header.getElementsByTagName(PWD_HEADER);
        if (userNodeList.getLength() == 0 || passwordNodeList.getLength() == 0) {
            throw new Fault(new IllegalArgumentException("认证失败，用户名霍密码为空"));
        }
        // 获取用户名
        String username = userNodeList.item(0).getFirstChild().getNodeValue().trim();
        // 获取密码
        String password = passwordNodeList.item(0).getFirstChild().getNodeValue().trim();
        if (!USER_NAME.equals(username) || !USER_PASSWORD.equals(password)) {
            log.error("用户认证失败！");
            throw new Fault(new SOAPException("用户认证失败！"));
        }
        log.debug("用户访问认证成功");
    }
}
