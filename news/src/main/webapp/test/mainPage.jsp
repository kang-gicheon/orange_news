<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
request.setCharacterEncoding("UTF-8");
%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>뉴스 플랫폼</title>

</head>

<body>
	<h1>Orange News</h1>
	<a href="${contextPath }/news/addarticleForm.do">
		<p>기사 작성</p>
	</a>
	<a href="${contextPath }/member/loginForm.do">
		<p>로그인</p>
	</a>
	<a href="${contextPath }/member/logout.do">
		<p>로그아웃</p>
	</a>
	<a href="${contextPath }/member/mypage.do">
		<p>마이페이지</p>
	</a>
	<a href="${contextPath }/twc">
		<p>(날씨)</p>
	</a>


	<!-- 헤드라인 기사 시작 -->
	<table id="hot" align="center" border="2" width="80%">
		<c:choose>
			<c:when test="${!empty articlesList }">

				<c:forEach var="article" items="${articlesList}"
					varStatus="articleNum">


					<c:if test="${article.hotissue == 1 }">
						<tr align="center">
							<td width="5%">${articleNum.count }</td>
							<td width="30%"><a
								href="${contextPath}/news/viewArticle.do?articlenum=${article.articlenum}">
									<img
									src="${contextPath }/download.do?&title=${article.title}&imgFileName=${article.imgFileName}"
									id="preview" style="width: 200px; height: 150px;" />
							</a><br> <a>${article.title} - ${article.id }</a></td>
						</tr>
					</c:if>

				</c:forEach>

			</c:when>
		</c:choose>
	</table>
	<!-- 헤드라인 기사 끝 -->



	<!-- 인기 기사 시작 -->
	<table align="center" border="2" width="80%">
		<c:choose>
			<c:when test="${empty hotarticlesList}">
				<tr height="10">
					<td colspan="4">
						<p align="center">
							<b> <span style="font-size: 9pt;">등록된 기사가 없습니다.</span></b>
						</p>
					</td>
				</tr>
			</c:when>

			<c:when test="${!empty hotarticlesList }">
				<c:forEach var="article" items="${hotarticlesList}"
					varStatus="articleNum">
					<c:if test="${article.hotissue == 0 }">
						<tr align="center">
							<td width="5%">${articleNum.count }</td>
							<td width="30%"><a
								href="${contextPath}/news/viewArticle.do?articlenum=${article.articlenum}">
									<img
									src="${contextPath }/download.do?&title=${article.title}&imgFileName=${article.imgFileName}"
									id="preview" style="width: 200px; height: 150px;" />
							</a><br> <a>${article.title} - ${article.id }</a></td>
						</tr>
					</c:if>
				</c:forEach>
			</c:when>
		</c:choose>
	</table>
	<!-- 인기 기사 끝 -->



	<!-- 메인 화면 최근 기사 시작 -->
	<table align="center" border="2" width="80%">
		<c:choose>
			<c:when test="${empty articlesList}">
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
					varStatus="articleNum">
					<c:if test="${article.hotissue == 0 }">
						<tr align="center">
							<td width="5%">${articleNum.count }</td>
							<td width="30%"><a
								href="${contextPath}/news/viewArticle.do?articlenum=${article.articlenum}">
									<img
									src="${contextPath }/download.do?&title=${article.title}&imgFileName=${article.imgFileName}"
									id="preview" style="width: 200px; height: 150px;" />
							</a><br> <a>${article.title} - ${article.id }</a></td>
						</tr>
					</c:if>
				</c:forEach>
			</c:when>
		</c:choose>
	</table>
	<!-- 메인 화면 최근 기사 끝 -->

</body>
</html>