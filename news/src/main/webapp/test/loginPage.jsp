<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<%
  response.sendRedirect("/news/member");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>교환소</title>
</head>
<body>
			<!-- MemberController와 NewsController를 이어주는 페이지 -->
</body>
</html>