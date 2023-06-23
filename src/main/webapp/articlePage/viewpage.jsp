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
	}
%>

<%
String id = getCookieValue(cookies, "loginId");
String aNum = getCookieValue(cookies, "articleNum");
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
<script type="text/javascript" src="${contextPath }/articleJS.js"></script>

<title>오렌지 뉴스</title>

<c:set var="id" value="<%=id%>" />

</head>



<body id="body">
	<jsp:include page="${contextPath }/common/header.jsp"></jsp:include>

	<menu class="menu">
		<jsp:include page="${contextPath }/common/menu.jsp"></jsp:include>
	</menu>

	<section>
	
		<div class="articleViewSection">
			<div class="articleTitle">
				<h1>${article.title }</h1>
			</div>
			<div class="articleInfo">${article.id } &nbsp&nbsp ${article.writedate}</div>
			<div class="articleImg">
			<img src="${contextPath }/download.do?&title=${article.title}&imgFileName=${article.imgFileName}"
						id="preview" />
			</div>
			<div class="articleContent">${article.content }</div>
			
			<form method="post" action="${contextPath}/news/updateReact.do" name ="reactform">
			<table align="center">
				<!-- checkbox로 바꾸기 -->
				<tr>
				
					<td><input type="radio" name="react" value="좋아요"></input></td>
					<td><input type="radio" name="react" value="훈훈해요"></input></td>
					<td><input type="radio" name="react" value="슬퍼요"></input></td>
					<td><input type="radio" name="react" value="화나요"></input></td>
					<td><input type="radio" name="react" value="후속기사 원해요"></input></td>

				</tr>
				<tr>
					<td><a>좋아요 : ${article.rcount} </a></td>
				</tr>

				<tr>

					<td colspan=5 align=center><input type=submit value="반응남기기" />
					</td>

					<td colspan=5 align=center><input type=reset value="Reset"></td>
				</tr>
				
				<tr>
				<td colspan=5 align=center><input type=button value="리스트로 돌아가기"
					onClick="backToList(this.form)"></td>
			</tr>
			</table>
			
		</form>
			
			
		</div>

		<div class="reply-section">
			<div class="reply-insert">
				<div class="re-write-space">
					<div class="id-space">&nbsp&nbsp&nbsp&nbsp ${id }</div>
					<hr style="margin:0;">
					<textarea class="text-space"></textarea>
					<br/>	
					<button style="float: right" type="button"
						class="btn btn-warning active" class="writebutton"
						onclick="writeReply()">댓글 작성</button>
				</div>
			</div>
			<p></p>
			<hr>
			<div class="sort-list">
				<span onclick="resetList('0')">인기순</span>&nbsp&nbsp&nbsp <span
					onclick="resetList('1')">최신순</span>&nbsp&nbsp&nbsp <span
					onclick="resetList('2')">내댓글</span>
			</div>
			<hr>
			<p></p>
			<div class="reply-list">
				<!-- 댓글 예시 -->

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