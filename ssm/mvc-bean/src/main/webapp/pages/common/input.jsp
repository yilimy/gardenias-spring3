<%--
  Created by IntelliJ IDEA.
  User: EDY
  Date: 2024/9/18
  Time: 11:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>文件上传</title>
</head>
<body>
<form action="${request.contextPath}/pages/common/upload" method="post" enctype="multipart/form-data">
    文件编号: <input type="text" name="id" value="88293131"><br>
    上传图片: <input type="file" name="photo" ><br>
    <button type="submit">发送</button>
    <button type="reset">重置</button>
</form>
</body>
</html>
