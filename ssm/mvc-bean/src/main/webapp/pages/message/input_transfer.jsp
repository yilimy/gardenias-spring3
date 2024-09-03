<%--
  多参数表单提交项
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<html>
<head>
    <title>SpringMVC开发框架</title>
</head>
<body>
    <%--注意：从MessageAction跳转过来，自带了contextPath--%>
    <form action="${request.contextPath}/pages/message/transfer" method="post">
        消息内容: <input type="text" name="message" value="www.yootk.com"><br>
        消息级别: <select id="level" name="level">
                    <option value="0">紧急</option>
                    <option value="1" selected>普通</option>
                    <option value="2">延迟</option>
                </select><br>
        发布日期: <input type="date" id="pupdate" name="pupdate" value="2262-01-21"><br>
        消息标签: <input type="checkbox" name="tags" value="策划" checked>策划
                <input type="checkbox" name="tags" value="关卡" checked>关卡
                <input type="checkbox" name="tags" value="数值" checked>数值<br>
        <button type="submit">发送</button>
        <button type="reset">重置</button>
    </form>
</body>
</html>
