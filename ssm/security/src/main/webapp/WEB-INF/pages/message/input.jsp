<%--
  Created by IntelliJ IDEA.
  User: EDY
  Date: 2024/10/17
  Time: 12:38
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%!
    public static final String ECHO_URL = "/pages/message/echo";    // 请求路径
%>
<form action="<%=ECHO_URL%>" method="post">
    <label>
        消息内容:
        <input type="text" name="msg" value="江湖小李" >
        <input type="hidden" name="${_csrf.parameterName }" value="${_csrf.token }" >
        <button type="submit">发送</button>
    </label>
</form>
