

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<c:set var="articlesList" value="${articlesMap.articleType2 }" />
<c:set var="totArticles" value="${articlesMap.totArticles }" />
<c:set var="section" value="${articlesMap.section }" />
<c:set var="pageNum" value="${articlesMap.pageNum }" />
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
		<div class="type-section">
			<div class="type-section-title">산 업</div>
			<hr>
			<c:choose>
				<c:when test="${empty articlesList }">
					<table align="center" border="2" width="80%">
						<tr height="10">
							<td colspan="4">
								<p align="center">
									<b> <span style="font-size: 9pt;">등록된 기사가 없습니다.</span></b>
								</p>
							</td>
						</tr>
					</table>
				</c:when>

				<c:when test="${!empty articlesList }">
					<c:forEach var="article" items="${articlesList}"
						varStatus="articleNum">

						<div class="type-article"
							onclick="location.href='${contextPath}/news/viewArticle.do?articlenum=${article.articlenum}'"
							style="padding: 8px;">
							<table>
								<tr>
									<td rowspan='2' style="width: 120px">
										<div class="type-article-img">
											<img
												src="${contextPath }/download.do?&title=${article.articlenum}&imgFileName=${article.imgFileName}"
												class="card-img-top" alt="...">
										</div>
									</td>
									<td style="height: 55px">
										<div class="type-article-title">${article.title}</div>
									</td>
								</tr>
								<tr>
									<td>
										<div class="type-article-content">${article.content }</div>
									</td>
								</tr>
							</table>
						</div>

						<hr style="width: 90%; margin: 10px auto;">
					</c:forEach>
				</c:when>
			</c:choose>


		</div>

		<!-- 페이징 시작 -->
		<div style="margin: 0 auto;">

			<nav aria-label="Page navigation example" style="text-align: center">
				<ul class="pagination">

					<c:if test="${totArticles != null }">
						<c:choose>
							<c:when test="${totArticles >100 }">
								<c:forEach var="page" begin="1" end="10" step="1">
									<c:if test="${section > 1 && page==1 }">

										<li class="page-item"><a class="page-link"
											href="${contextPath }/news/type2list.do?section=${section-1}&pageNum=${section*10-1}">Pre</a></li>

									</c:if>
									<a class="no-uline"
										href="${contextPath }/news/type2list.do?section=${section}&pageNum=${page}">
										${(section-1)*10 + page} </a>
									<c:if test="${page==10 }">
										<li class="page-item"><a class="page-link" href="#">Next</a></li>
										<a class="no-uline"
											href="${contextPath }/news/type2list.do?section=${section+1}&pageNum=${section*10+1}">&nbsp;next</a>
									</c:if>
								</c:forEach>
							</c:when>
							<c:when test="${totArticles==100 }">
								<c:forEach var="page" begin="1" end="10" step="1">
									<a class="no-uline" href="#">${page }</a>
								</c:forEach>
							</c:when>
							<c:when test="${totArticles<100 }">
								<c:forEach var="page" begin="1" end="${totArticles/10+1 }"
									step="1">
									<c:choose>
										<c:when test="${page==pageNum }">
											<li class="page-item"><a class="page-link"
												href="${contextPath }/news/type2list.do?section=${section}&pageNum=${page}">${page }</a></li>
										</c:when>
										<c:otherwise>
											<li class="page-item"><a class="page-link"
												href="${contextPath }/news/type2list.do?section=${section}&pageNum=${page}">${page }</a></li>
										</c:otherwise>
									</c:choose>
								</c:forEach>
							</c:when>
						</c:choose>
					</c:if>
				</ul>
			</nav>
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