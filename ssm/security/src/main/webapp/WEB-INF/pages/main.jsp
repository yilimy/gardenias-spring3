<%--
  Created by IntelliJ IDEA.
  User: EDY
  Date: 2024/9/20
  Time: 15:29
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%-- 导入security的标签 --%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<html>
<head>
    <title>SpringMVC开发框架</title>
</head>
<%--
    通过表达式判断用户是否登录，
    表达式解析类 org.springframework.security.access.expression.SecurityExpressionRoot
--%>
<security:authorize access="isAuthenticated()">
    <h1>
        <%--
            详见 com.example.ssm.mvcb.action.MemberAction.info
                或者
                com.example.ssm.mvcb.action.MemberAction.principal
            Action地址 com.example.ssm.mvcb.action.MainAction.main
            因为 authentication 是需要根据用户登录成功的状态来决定最终能否进行登录用户数据的获取，
            为了保证页面代码不出现错误，应该先进行登录状态的判断
         --%>
        登录用户名: <security:authentication property="principal.username" />
    </h1>
</security:authorize>
<security:authorize access="hasRole('MESSAGE')">
    <h1>
        该用户拥有"MESSAGE"消息管理的权限！
    </h1>
</security:authorize>
<security:authorize access="hasRole('ADMIN')">
    <h1>
        该用户拥有"ADMIN"最高系统管理权限！
    </h1>
</security:authorize>
</html>
