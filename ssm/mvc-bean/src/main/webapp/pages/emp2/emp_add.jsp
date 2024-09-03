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
<form action="${request.contextPath}/pages/emp2/add" method="post">
    雇员编号: <input type="text" name="empno" value="7369"><br>
    雇员姓名: <input type="text" name="ename" value="李兴华"><br>
    雇佣日期: <input type="date" name="hiredate" value="2050-09-19"><br>
    <%-- 设置了对象的结构关联 --%>
    部门编号: <input type="text" name="dept.deptno" value="10"><br>
    部门名称: <input type="text" name="dept.dname" value="教学研发部"><br>
    部门位置: <input type="text" name="dept.loc" value="洛阳"><br>
    <button type="submit">发送</button>
    <button type="reset">重置</button>
</form>
</body>
</html>
