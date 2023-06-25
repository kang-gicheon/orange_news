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
<link href="style.css" rel="stylesheet" type="text/css" />
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
		<div class="home-section">
			<div class="headline-section">
				<p class="headline-text"> 오늘의 헤드라인 </p>
				<div class="headline">
					<c:choose>
						<c:when test="${empty hdlmain }">
							<tr height="10">
								<td colspan="4">
									<p align="center">
										<b> <span style="font-size: 9pt;">등록된 헤드라인이 없습니다.</span></b>
									</p>
								</td>
							</tr>
						</c:when>
						<c:when test="${!empty hdlmain }">

							<tr align="center">
								<a class="headline-img"
									href="${contextPath}/news/viewArticle.do?articlenum=${hdlmain.articlenum}">
									<img
									src="${contextPath }/download.do?&title=${hdlmain.articlenum}&imgFileName=${hdlmain.imgFileName}"
									id="preview" style="width: 100%; height: 100%;" />
								</a>
								
								<div class="headline-info" >
									<div class="headline-info-t">
										<h1 class="headline-info-text">${hdlmain.title}</h1>
									</div>
								</div>

							</tr>

						</c:when>

					</c:choose>

				</div>

			</div>

			<p></p>
			<div class="card-list">
				<div class="latest-list">
					<div class="articleText">

						&nbsp새로 올라온 기사
						<hr>
					</div>
					<table align="center" border="2" width="80%">

						<c:choose>
							<c:when test="${empty articlesList }">
								<tr height="10">
									<td colspan="4">
										<p align="center">
											<b> <span style="font-size: 9pt;">등록된 기사가 없습니다.</span></b>
										</p>
									</td>
								</tr>
							</c:when>

							<c:when test="${!empty articlesList }">
								<c:forEach var="article" items="${articlesList}"
									varStatus="articleNum" begin="0" end="5" step="1">
											
										
									
									<tr align="center">
										<div class="card">
											<a
												href="${contextPath}/news/viewArticle.do?articlenum=${article.articlenum}">
												<img
												src="${contextPath }/download.do?&title=${article.articlenum}&imgFileName=${article.imgFileName}"
												class="card-img-top" alt="...">
											</a>
											<div class="card-body">
												<h5 class="card-title">${article.title}</h5>
												<p style="font-size: 15px; color: gray">${article.id }${type }</p>
												<p class="card-text">${article.content }</p>
											</div>
										</div>

									</tr>
								</c:forEach>
							</c:when>
						</c:choose>
					</table>
				</div>
			</div>
			
			<!-- 추천 기사 -->
			<div class="card-list">
				<div class="latest-list">
					<div class="articleText" style="color:#40bfb7">

						&nbsp 추천 기사
						<hr style="width:100px">
					</div>
					<table align="center" border="2" width="80%">

						<c:choose>
							<c:when test="${empty hotarticlesList }">
								<tr height="10">
									<td colspan="4">
										<p align="center">
											<b> <span style="font-size: 9pt;">등록된 기사가 없습니다.</span></b>
										</p>
									</td>
								</tr>
							</c:when>

							<c:when test="${!empty hotarticlesList }">
								<c:forEach var="recommandarticle" items="${hotarticlesList}"
									varStatus="articleNum" begin="0" end="5" step="1">
											
										
									
									<tr align="center">
										<div class="card">
											<a
												href="${contextPath}/news/viewArticle.do?articlenum=${recommandarticle.articlenum}">
												<img
												src="${contextPath }/download.do?&title=${recommandarticle.articlenum}&imgFileName=${recommandarticle.imgFileName}"
												class="card-img-top" alt="...">
											</a>
											<div class="card-body">
												<h5 class="card-title">${recommandarticle.title}</h5>
												<p style="font-size: 15px; color: gray">${recommandarticle.id }${type }</p>
												<p class="card-text">${recommandarticle.content }</p>
											</div>
										</div>

									</tr>
								</c:forEach>
							</c:when>
						</c:choose>
					</table>
				</div>
			</div>
		</div>
	</section>

	<aside>
		<jsp:include page="${contextPath }/common/aside.jsp"></jsp:include>
	</aside>
	<footer class="footer">
		<jsp:include page="${contextPath }/common/footer.jsp"></jsp:include>
	</footer>
</body>
</html>