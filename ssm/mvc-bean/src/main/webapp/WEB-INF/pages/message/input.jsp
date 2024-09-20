<%--
  Created by IntelliJ IDEA.
  User: EDY
  Date: 2024/8/28
  Time: 10:03
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<html>
<head>
    <title>SpringMVC开发框架</title>
</head>
<body>
    <form action="${request.contextPath}/pages/message/echo2" method="post">
        请输入信息: <input type="text" name="message" value="www.yootk.com">
        <button type="submit">发送</button>
    </form>
</body>
</html>
