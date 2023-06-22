

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<%
Cookie[] cookies = request.getCookies();
String cookieName = "";
String cookieValue = "";

if (cookies != null) {
	for (int i = 0; i < cookies.length; i++) {
		if (cookies[i].getName().equals("cookieName")) {
			cookieName = cookies[i].getName();
			cookieValue = cookies[i].getValue();
		}
	}
}
%>

<%!private String getCookieValue(Cookie[] cookies, String name) {

		String value = null;

		if (cookies == null)
			return null;
		for (Cookie cookie : cookies) {
			if (cookie.getName().equals(name))
				return cookie.getValue();
		}
		return null;
	}%>

<%
String id = getCookieValue(cookies, "loginId");
%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link href="${contextPath }/style.css" rel="stylesheet" type="text/css" />
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-rbsA2VBKQhggwzxH7pPCaAqO46MgnOM80zW1RWuH61DGLwZJEdK2Kadq2F9CUG65"
	crossorigin="anonymous">
	
<script src="http://code.jquery.com/jquery-3.6.0.min.js"></script>

<title>오렌지 뉴스</title>



</head>
<body id="body">
	<jsp:include page="${contextPath }/common/header.jsp"></jsp:include>

	<menu class="menu">
		<jsp:include page="${contextPath }/common/menu.jsp"></jsp:include>	
	</menu>

	<section>
	
	<!-- 정치 / 경제 탭 -->
	
	여기에 리스트 등록
	
		
	</section>

	<aside>
		<jsp:include page="${contextPath }/common/aside.jsp"></jsp:include>
	</aside>
	<footer class="footer">
		<jsp:include page="${contextPath }/common/footer.jsp"></jsp:include>
	</footer>
</body>
</html>