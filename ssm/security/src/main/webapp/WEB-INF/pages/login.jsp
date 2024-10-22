<%--
  Created by IntelliJ IDEA.
  User: EDY
  Date: 2024/10/17
  Time: 16:03
  To change this template use File | Settings | File Templates.
--%>
<%--
自定义的登录表单，替换掉 Spring Security 的默认登录页。
详见: com.example.ssm.mvcb.config.WebMVCSecurityConfiguration.filterChain 中 http.formLogin() 项
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%!
  public static final String ECHO_URL = "/yootk-login";    // 自定义登录路径
%>
<h1>
  ${param.error ? "用户登录失败，错误的用户名和密码" : ""}
</h1>
<form action="<%=ECHO_URL%>" method="post">
  <label>
    用户名:<input type="text" name="mid" value="yootk"><br>
    密码:<input type="password" name="pwd" value="hello"><br>
    <input type="checkbox" id="rme" name="rme" value="true" checked>下次免登录<br>
    验证码:<input type="text" name="code" maxlength="4" size="4"><img alt="验证码" src="/captcha"><br>
    <button type="submit">登录</button><button type="reset">重置</button>
  </label>
</form>

