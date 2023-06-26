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
			<div class="articleInfo">${article.id }&nbsp&nbsp
				${article.writedate}</div>
			<div class="articleImg">
				<img
					src="${contextPath }/download.do?&title=${article.articlenum}&imgFileName=${article.imgFileName}"
					id="preview" />
			</div>
			<div class="articleContent">${article.content }</div>
			<hr>
			<form method="post" action="${contextPath}/news/updateReact.do"
				name="reactform">
				<table class="react-section" align="center">
					<!-- checkbox로 바꾸기 -->
					
					<tr class="react-icon">
						<td>
							<svg xmlns="http://www.w3.org/2000/svg" width="32" height="32" fill="currentColor" class="bi bi-emoji-heart-eyes" viewBox="0 0 16 16">
  							<path d="M8 15A7 7 0 1 1 8 1a7 7 0 0 1 0 14zm0 1A8 8 0 1 0 8 0a8 8 0 0 0 0 16z"/>
  							<path d="M11.315 10.014a.5.5 0 0 1 .548.736A4.498 4.498 0 0 1 7.965 13a4.498 4.498 0 0 1-3.898-2.25.5.5 0 0 1 .548-.736h.005l.017.005.067.015.252.055c.215.046.515.108.857.169.693.124 1.522.242 2.152.242.63 0 1.46-.118 2.152-.242a26.58 26.58 0 0 0 1.109-.224l.067-.015.017-.004.005-.002zM4.756 4.566c.763-1.424 4.02-.12.952 3.434-4.496-1.596-2.35-4.298-.952-3.434zm6.488 0c1.398-.864 3.544 1.838-.952 3.434-3.067-3.554.19-4.858.952-3.434z"/>
							</svg>
						</td>
						<td>
							<svg xmlns="http://www.w3.org/2000/svg" width="32" height="32" fill="currentColor" class="bi bi-emoji-smile" viewBox="0 0 16 16">
  							<path d="M8 15A7 7 0 1 1 8 1a7 7 0 0 1 0 14zm0 1A8 8 0 1 0 8 0a8 8 0 0 0 0 16z"/>
  							<path d="M4.285 9.567a.5.5 0 0 1 .683.183A3.498 3.498 0 0 0 8 11.5a3.498 3.498 0 0 0 3.032-1.75.5.5 0 1 1 .866.5A4.498 4.498 0 0 1 8 12.5a4.498 4.498 0 0 1-3.898-2.25.5.5 0 0 1 .183-.683zM7 6.5C7 7.328 6.552 8 6 8s-1-.672-1-1.5S5.448 5 6 5s1 .672 1 1.5zm4 0c0 .828-.448 1.5-1 1.5s-1-.672-1-1.5S9.448 5 10 5s1 .672 1 1.5z"/>
							</svg>
						</td>
						<td>
							<svg xmlns="http://www.w3.org/2000/svg" width="32" height="32" fill="currentColor" class="bi bi-emoji-frown" viewBox="0 0 16 16">
 						 	<path d="M8 15A7 7 0 1 1 8 1a7 7 0 0 1 0 14zm0 1A8 8 0 1 0 8 0a8 8 0 0 0 0 16z"/>
  							<path d="M4.285 12.433a.5.5 0 0 0 .683-.183A3.498 3.498 0 0 1 8 10.5c1.295 0 2.426.703 3.032 1.75a.5.5 0 0 0 .866-.5A4.498 4.498 0 0 0 8 9.5a4.5 4.5 0 0 0-3.898 2.25.5.5 0 0 0 .183.683zM7 6.5C7 7.328 6.552 8 6 8s-1-.672-1-1.5S5.448 5 6 5s1 .672 1 1.5zm4 0c0 .828-.448 1.5-1 1.5s-1-.672-1-1.5S9.448 5 10 5s1 .672 1 1.5z"/>
							</svg>
						</td>
						<td>
							<svg xmlns="http://www.w3.org/2000/svg" width="32" height="32" fill="currentColor" class="bi bi-emoji-angry" viewBox="0 0 16 16">
							<path d="M8 15A7 7 0 1 1 8 1a7 7 0 0 1 0 14zm0 1A8 8 0 1 0 8 0a8 8 0 0 0 0 16z"/>
	 					 	<path d="M4.285 12.433a.5.5 0 0 0 .683-.183A3.498 3.498 0 0 1 8 10.5c1.295 0 2.426.703 3.032 1.75a.5.5 0 0 0 .866-.5A4.498 4.498 0 0 0 8 9.5a4.5 4.5 0 0 0-3.898 2.25.5.5 0 0 0 .183.683zm6.991-8.38a.5.5 0 1 1 .448.894l-1.009.504c.176.27.285.64.285 1.049 0 .828-.448 1.5-1 1.5s-1-.672-1-1.5c0-.247.04-.48.11-.686a.502.502 0 0 1 .166-.761l2-1zm-6.552 0a.5.5 0 0 0-.448.894l1.009.504A1.94 1.94 0 0 0 5 6.5C5 7.328 5.448 8 6 8s1-.672 1-1.5c0-.247-.04-.48-.11-.686a.502.502 0 0 0-.166-.761l-2-1z"/>
							</svg>
						</td>
						<td>
							<svg xmlns="http://www.w3.org/2000/svg" width="32" height="32" fill="currentColor" class="bi bi-file-plus" viewBox="0 0 16 16">
 							<path d="M8.5 6a.5.5 0 0 0-1 0v1.5H6a.5.5 0 0 0 0 1h1.5V10a.5.5 0 0 0 1 0V8.5H10a.5.5 0 0 0 0-1H8.5V6z"/>
  							<path d="M2 2a2 2 0 0 1 2-2h8a2 2 0 0 1 2 2v12a2 2 0 0 1-2 2H4a2 2 0 0 1-2-2V2zm10-1H4a1 1 0 0 0-1 1v12a1 1 0 0 0 1 1h8a1 1 0 0 0 1-1V2a1 1 0 0 0-1-1z"/>
							</svg>
						</td>
					</tr>
					<tr class="react-text">
						<td>좋아요</td>
						<td>훈훈해요</td>
						<td>슬퍼요</td>
						<td>화나요</td>
						<td style="line-height: 12px">후속기사<br/> 원해요</td>
					</tr>
					<tr class="react-count">
						<td><input type="radio" name="react" value="좋아요"></input></td>
						<td><input type="radio" name="react" value="훈훈해요"></input></td>
						<td><input type="radio" name="react" value="슬퍼요"></input></td>
						<td><input type="radio" name="react" value="화나요"></input></td>
						<td><input type="radio" name="react" value="후속기사원해요"></input></td>
					</tr>
					
					<tr style="height:45px">

						<td colspan="5" align=center><input type=submit value="반응남기기" />
						</td>
						
					</tr>

					<tr style="height:45px">
						<td colspan="5" align=center><input type=button
							value="리스트로 돌아가기" onClick="backToList(this.form)"></td>
					</tr>
				</table>
			</form>
			<hr>
			<form method="post" action="${contextPath}/news/updateRec.do"
				name="recform">
				<table style="margin:0 auto">
					<tr style="align: center">
						<td align=center><input type=submit name="recommand"
							value="추천 ${article.recCount }" /> </td>
						<td style="width:30px"></td>
						<td align=center ><input type=button
							value="리스트로 돌아가기" onClick="backToList(this.form)"></td>
					</tr>
				</table>
			</form>

		</div>

		<div class="reply-section">
			<div class="reply-insert">
				<div class="re-write-space">
					<div class="id-space">&nbsp&nbsp&nbsp&nbsp ${id }</div>
					<hr style="margin: 2px; width:100%">
					<textarea class="text-space"></textarea>
					<br />
					<button style="float: right" type="button"
						class="btn btn-warning active" class="writebutton"
						onclick="writeReply()">댓글 작성</button>
				</div>
			</div>
			
			<div >
				<div class="login_need">
					<p>로그인 하시면 답글을 다실수 있습니다.</p>
					<button type="button"
						onclick="location.href='${contextPath }/member/loginForm.do'"
						class="btn btn-outline-warning btn-lg">로그인 하기</button>
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