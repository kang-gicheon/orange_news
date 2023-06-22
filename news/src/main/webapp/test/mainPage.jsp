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

	<h2>헤드라인 테스트</h2>
	<h3>헤드라인 기준 : 가장 최근 특종O 기사인것</h3>
	<table align="center" border="2" width="80%">
		<c:choose>
			<c:when test="${empty headline }">
				<tr height="10">
					<td colspan="4">
						<p align="center">
							<b> <span style="font-size: 9pt;">등록된 헤드라인이 없습니다.</span></b>
						</p>
					</td>
				</tr>
			</c:when>
			<c:when test="${!empty headline }">

				<tr align="center">

					<c:choose>
						<c:when test="${headline.type==1}">
							<td>경제/정치</td>
						</c:when>
						<c:when test="${headline.type==2}">
							<td>산업</td>
						</c:when>
						<c:when test="${headline.type==3}">
							<td>사회/문화</td>
						</c:when>
						<c:when test="${headline.type==4}">
							<td>부동산</td>
						</c:when>
						<c:when test="${headline.type==5}">
							<td>글로벌</td>
						</c:when>
						<c:when test="${headline.type==6}">
							<td>블록체인</td>
						</c:when>
						<c:when test="${headline.type==7}">
							<td>스포츠/연예</td>
						</c:when>
						<c:otherwise>
							<td>오류</td>
						</c:otherwise>

					</c:choose>
					<td width=5%>${headline.title }</td>

					<td width="30%"><a
						href="${contextPath}/news/viewArticle.do?articlenum=${headline.articlenum}">
							<img
							src="${contextPath }/download.do?&title=${headline.title}&imgFileName=${headline.imgFileName}"
							id="preview" style="width: 200px; height: 150px;" />
					</a></td>

				</tr>

			</c:when>


		</c:choose>



	</table>

	<h2>반응순 정렬</h2>
	<h3>기준 : 반응별 가장 높은 기사 하나씩 출력 (수 조절 가능)</h3>


	<table align="center" border="2" width="80%">

		<c:choose>
			<c:when test="${empty reactList }">
				<tr height="10">
					<td colspan="4">
						<p align="center">
							<b> <span style="font-size: 9pt;">등록된 기사가 없습니다.</span></b>
						</p>
					</td>
				</tr>
			</c:when>
			
			<c:when test="${!empty reactList }">
			<c:forEach var="article" items="${reactList}"
					varStatus="articleNum">
					<tr align="center">
						<td width="5%">${article.actype} - 반응 수 : ${article.rcount }</td>
						<td width="30%"><a
							href="${contextPath}/news/viewArticle.do?articlenum=${article.articlenum}">
								<img
								src="${contextPath }/download.do?&title=${article.title}&imgFileName=${article.imgFileName}"
								id="preview" style="width: 200px; height: 150px;" />
						</a><br> <a>${article.title} - ${article.id }</a></td>

					</tr>
				</c:forEach>
			
			</c:when>

		</c:choose>
	</table>


	<h2>추천순 정렬</h2>
	<h3>실시간 반응O</h3>
	<table align="center" border="2" width="80%">

		<c:choose>
			<c:when test="${empty articlesHotList }">
				<tr height="10">
					<td colspan="4">
						<p align="center">
							<b> <span style="font-size: 9pt;">등록된 기사가 없습니다.</span></b>
						</p>
					</td>
				</tr>
			</c:when>

			<c:when test="${!empty articlesHotList }">
				<c:forEach var="article" items="${articlesHotList}"
					varStatus="articleNum">
					<tr align="center">
						<c:if test="${article.hotissue==1 }">
							<td width="5%">특종</td>
						</c:if>
						<c:if test="${article.hotissue==0 }">
							<td width="5%">일반기사</td>
						</c:if>
						<td width="5%">추천 수 : ${article.recCount }</td>
						<td width="30%"><a
							href="${contextPath}/news/viewArticle.do?articlenum=${article.articlenum}">
								<img
								src="${contextPath }/download.do?&title=${article.title}&imgFileName=${article.imgFileName}"
								id="preview" style="width: 200px; height: 150px;" />
						</a><br> <a>${article.title} - ${article.id }</a></td>

					</tr>
				</c:forEach>
			</c:when>


		</c:choose>

	</table>

	<h2>최신순 정렬</h2>
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
					varStatus="articleNum">
					<tr align="center">
						<c:if test="${article.hotissue==1 }">
							<td width="5%">특종</td>
						</c:if>
						<c:if test="${article.hotissue==0 }">
							<td width="5%">일반기사</td>
						</c:if>
						<td width="30%"><a
							href="${contextPath}/news/viewArticle.do?articlenum=${article.articlenum}">
								<img
								src="${contextPath }/download.do?&title=${article.title}&imgFileName=${article.imgFileName}"
								id="preview" style="width: 200px; height: 150px;" />
						</a><br> <a>${article.title} - ${article.id }</a></td>

					</tr>
				</c:forEach>
			</c:when>
		</c:choose>

	</table>

</body>
</html>