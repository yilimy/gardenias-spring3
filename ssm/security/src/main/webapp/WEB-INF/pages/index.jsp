<%--
  Created by IntelliJ IDEA.
  User: EDY
  Date: 2024/10/17
  Time: 16:03
  To change this template use File | Settings | File Templates.
--%>
<%-- 自定义首页 --%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%!
  public static final String login_page = "/login_page";
  public static final String logout_url = "/yootk-logout";
%>
<h3>
${msg}
</h3>
<h3>
  ${param.invalidate ? "当前账户已在其他设备登录，为了您的安全已将该账户注销" : ""}
</h3>
<security:authorize access="isAuthenticated()">
  <h3>
    【已登录】登录用户名: <security:authentication property="principal.username" />
  </h3>
  <h3>
    登录成功，欢迎回来，可以选择<a href="<%=logout_url%>">注销</a>!
  </h3>
</security:authorize>
<h3>
  如果您还未登录，请先<a href="<%=login_page%>">登录</a>。
</h3>

