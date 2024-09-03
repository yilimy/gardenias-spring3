<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>SpringMVC开发框架</title>
</head>
<body>
    【雇员信息】<br>
    编号：     ${emp.empno}<br>
    姓名：     ${emp.ename}<br>
    雇佣日期：  <fmt:formatDate value="${emp.hiredate}" pattern="yyyy年MM月dd日" /><br>
    【部门信息】<br>
    编号：     ${emp.dept.deptno}<br>
    名称：     ${emp.dept.dname}<br>
    位置：     ${emp.dept.loc}<br>
</body>
</html>