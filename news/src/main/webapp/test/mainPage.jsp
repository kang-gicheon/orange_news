<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
request.setCharacterEncoding("UTF-8");
%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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

	<table align="center" border="2" width="80%">
		<tr height="10" align="center" bgcolor="orange">

			<td>기사번호</td>
			<td>기자</td>
			<td>제목</td>
			<td>내용</td>
			<td>이미지</td>
		</tr>

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
						<td width="5%">${articleNum.count}</td>
						<td width="5%">${article.id }</td>
						<td align = "left" width ="30%">${article.title }</td>
						<td align = "left" width ="60%">${article.content }</td>
						
					</tr>
				</c:forEach>
			</c:when>
		</c:choose>

	</table>

</body>
</html>