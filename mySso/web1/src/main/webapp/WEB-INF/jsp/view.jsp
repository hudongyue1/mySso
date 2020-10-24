<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<p>这是App1</p>
id:${user.id }
name:${user.name }
sex:${user.sex }
age:${user.age }
<p><a href="http://localhost:8080/ssoServer/logout?backUrl=http://localhost:8080/web1">注销</a></p>
</body>
</html>