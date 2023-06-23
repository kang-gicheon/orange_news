<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
    <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	request.setCharacterEncoding("UTF-8");
%>
<c:set var="contextPath" value="${pageContext.request.contextPath }" />

<!-- 사용자 또는 기자가 로그인 하는 페이지 -->

<!DOCTYPE html>
<html>
<style>

/* 글씨 폰트 링크 */
@import url(https://fonts.googleapis.com/css?family=Open+Sans:400);

/* 폰트 참고 링크 */
@import url(http://weloveiconfonts.com/api/?family=fontawesome);

[class*="fontawesome-"]:before {
	font-family: 'FontAwesome', sans-serif;
}

/* 기본 레이아웃 */
* {
	margin: 0;
	padding: 0;
	box-sizing: border-box;
}

/* body 영역 시작 */
body {
	background: #e9e9e9;
	color: #5e5e5e;
	font: 400 87.5%/1.5em 'Open Sans', sans-serif;
}

/* 레이아웃 영역 구성  */
.form-wrapper {
	background: #fafafa;
	margin: 3em auto;
	padding: 0 1em;
	max-width: 370px;
}

.form-wrapper-text {
	color: black;
	text-align: right;
	padding: 10px;
}

.form-wrapper-text a {
	color: black;
	text-decoration: none;
	text-font: bold;
	font-size: 20px;
}

.form-wrapper-text a:hover {
	color: #8c8c8c;
	text-decoration: none;
}

/*  로그인 창 닫기 영역(링크) */

/* 레이아웃 제목 텍스트 영역 시작 */

h1 {
	text-align: center;
	padding: 1em 0;
}

form {
	padding: 0 1.5em;
}

.form-item {
	margin-bottom: 0.75em;
	width: 100%;
}

.form-item input {
	background: #fafafa;
	border: none;
	border-bottom: 2px solid #e9e9e9;
	color: #666;
	font-family: 'Open Sans', sans-serif;
	font-size: 1em;
	height: 50px;
	transition: border-color 0.3s;
	width: 100%;
}

/* 레이아웃 제목 텍스트 영역 끝 */

/* 로그인 버튼 영역 시작 */

.form-item input:focus {
	border-bottom: 2px solid #c0c0c0;
	outline: none;
}

.button-panel { /* 버튼 영역 */
	margin: 2em 0 0;
	width: 100%;
}

.button-panel .button {
	background: #ff761a; /* 로그인 버튼 색 지정 */
	border: none;
	color: #fff;
	cursor: pointer;
	height: 50px;
	font-family: 'Open Sans', sans-serif;
	font-size: 1.2em;
	letter-spacing: 0.05em;
	text-align: center;
	text-transform: uppercase;
	transition: background 0.3s ease-in-out;
	width: 100%;
}

.button:hover {
	background: #ee3e52;
}

.form-footer {
	font-size: 1em;
	padding: 2em 0;
	text-align: center;
}

.form-footer a {
	color: black;
	text-decoration: none;
	transition: border-color 0.3s;
}

.form-footer a:hover {
	border-bottom: 1px dotted #8c8c8c;
}
</style>
<head>
<meta charset="UTF-8">
<title>로그인 페이지</title>
</head>
<body>

	<div class="form-wrapper">
		<div class="form-wrapper-text">
			<p>
				<a href='#'>X</a>
			</p>
		</div>

		<h1>로그인</h1>
		<form name="logindata" method="post"
			action="${contextPath }/member/logintest.do">
			<div class="form-item">
				<!-- id 입력창 -->
				<label for="id"></label> <input type="text" name="id"
					required="required" placeholder="아이디"></input>
			</div>
			<div class="form-item">
				<label for="pwd"></label>
				<!-- 비밀번호 입력창 -->
				<input type="password" name="pwd" required="required"
					placeholder="비밀번호"></input>
			</div>
			<div class="button-panel">
				<input type="submit" class="button" title="Sign In" value="로그인"></input>
			</div>
		</form>
		
			<!-- 계정 생성을 위한 임력 정보 form 생성 영역 종료 -->

			<!-- 사용자가 이미 계정이 없는 경우, 회원가입으로 넘어가는 링크 영역 -->
			
			<!-- 사용자가 계정을 잊은경우, 계정 찾기로 넘어가는 링크 영역 -->
		
		<div class="form-footer">
			<p>
				<a href="${contextPath }/member/join.do">계정을 만드시겠습니까?</a>
			</p>
			<p>
				<a href="${contextPath }/member/findid.do">계정을 잊으셨나요?</a>
			</p>
		</div>
	</div>

</body>
</html>