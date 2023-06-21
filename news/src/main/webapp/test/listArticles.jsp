<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

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


<div>
		<c:if test="${totArticles != null }">
			<c:choose>
				<c:when test="${totArticles >100 }">
				<c:forEach var="page" begin="1" end="10" step="1">
					<c:if test="${section > 1 && page==1 }">
						<a class="no-uline" href="${contextPath }/board8/listArticle.do?section=${section-1}&pageNum=${section*10-1}">&nbsp;pre</a>
					</c:if>
					<a class="no-uline" href="${contextPath }/board8/listArticle.do?section=${section}&pageNum=${page}"> ${(section-1)*10 + page} </a>
					<c:if test="${page==10 }">
						<a class="no-uline" href="${contextPath }/board8/listArticle.do?section=${section+1}&pageNum=${section*10+1}">&nbsp;next</a>
					</c:if>
				</c:forEach>
				</c:when>
				<c:when test="${totArticles==100 }">
					<c:forEach var="page" begin="1" end="10" step="1">
						<a class="no-uline" href="#">${page }</a>
					</c:forEach>
				</c:when>
				<c:when test="${totArticles<100 }">
					<c:forEach var="page" begin="1" end="${totArticles/10+1 }" step="1">
						<c:choose>
							<c:when test="${page==pageNum }">
								<a class="sel-page" href="${contextPath }/board8/listArticle.do?section=${section}&pageNum=${page}"> ${page } </a>
							</c:when>
							<c:otherwise>
								<a class="no-uline" href="${contextPath }/board8/listArticle.do?section=${section}&pageNum=${page}">${page }</a>
							</c:otherwise>
						</c:choose>
					</c:forEach>
				</c:when>
			</c:choose>
		</c:if>
	</div>
</body>
</html>