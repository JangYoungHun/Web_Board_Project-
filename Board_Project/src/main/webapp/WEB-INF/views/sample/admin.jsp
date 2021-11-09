<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<h1>Admin</h1>

<p> <sec:authentication property="principal"/> </p>
<p> memberVO : <sec:authentication property="principal.member"/> </p>
<p> 사용자 ID :  <sec:authentication property="principal.member.username"/> </p>
<p> 사용자 이름 : <sec:authentication property="principal.username"/> </p>
<p> 사용자 권한 : <sec:authentication property="principal.member.authList"/> </p>
<a href="/sample/customLogout">Logout</a>
</body>
</html>