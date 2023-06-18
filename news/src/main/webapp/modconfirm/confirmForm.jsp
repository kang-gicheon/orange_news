<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	request.setCharacterEncoding("UTF-8");
%>
<c:set var="contextPath" value="${pageContext.request.contextPath }" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>본인 확인 페이지</title>
</head>
<body>
	<form method="post" action="${contextPath }/member/modmemberForm.do">
		<a href="${contextPath }/member/mypage.do">로고 이미지(홈페이지 이동으로 바꿀 예정)</a><br>
		<input type="text" name="id" placeholder="${memberVO.id }" disabled /><br>
		<input type="password" name="pwd" required="required" placeholder="비밀번호" /><br>
		<input type="submit" value="확인" />
	</form>
</body>
</html>