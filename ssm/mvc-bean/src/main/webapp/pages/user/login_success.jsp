<%--
  Created by IntelliJ IDEA.
  User: EDY
  Date: 2024/9/18
  Time: 10:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>SpringMVC开发框架</title>
</head>
<body>
    <h1>用户登录成功</h1>
    <h2>用户信息：${user}</h2>
    <%-- 尝试使用sessionScope获取属性信息，表明属性在session中，直接使用role也是可以的 --%>
    <h2>角色信息：${sessionScope.role}</h2>
</body>
</html>
