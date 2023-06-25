<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
    <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	request.setCharacterEncoding("UTF-8");
%>
<c:set var="contextPath" value="${pageContext.request.contextPath }" />

<!DOCTYPE html>
<html>
<style>

/* Fonts */
@import url(https://fonts.googleapis.com/css?family=Open+Sans:400);

/* fontawesome */
@import url(http://weloveiconfonts.com/api/?family=fontawesome);
[class*="fontawesome-"]:before {
  font-family: 'FontAwesome', sans-serif;
}

/* Simple Reset */
* { margin: 0; padding: 0; box-sizing: border-box; }

/* body */
body {
  background: #e9e9e9;
  color: #5e5e5e;
  font: 400 87.5%/1.5em 'Open Sans', sans-serif;
}

/* Form Layout */
.form-wrapper {
  background: #fafafa;
  margin: 3em auto;
  padding: 0 1em;
  max-width: 370px;
}

.form-wrapper-text {

	color:black;
	text-align: right;
	padding: 10px;
	
}

.form-wrapper-text a {

	color:black;
	text-decoration: none;
	text-font: bold;
	font-size: 20px;
	
}


.form-wrapper-text a:hover {
  
  color: #8c8c8c;
  text-decoration:none;
  
}

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

.form-item input:focus {
  border-bottom: 2px solid #c0c0c0;
  outline: none;
}

.button-panel {
  margin: 2em 0 0;
  width: 100%;
}

.button-panel .button {
  background: #ff761a;	/* 계정 생성하기 버튼 색 */
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
  color: #8c8c8c;
  text-decoration: none;
  transition: border-color 0.3s;
}

.form-footer a:hover {
  border-bottom: 1px dotted #8c8c8c;
}

</style>
<script>
	function setValue(elementName, defaultValue) {
		let elements = document.getElementsByName(elementName);
		for(let i = 0; i<elements.length; i++){
			let element = elements[i];
			if (element.value === '') {
				element.value = defaultValue;
			}
		}
	}
</script>

<head>
<meta charset="UTF-8">
<title>회원 정보 수정</title>
</head>
<body>

<div class="form-wrapper">
	<div class="form-wrapper-text">
		<p><a href="#">X</a></p>
		<!-- 메인페이지로 이동 -->
	</div>
	<h1>회원 정보 수정</h1>
	<form name="abc" method="post" action="${contextPath}/member/mod.do">
		<!-- 백 작업 진행 중 -->
		<div class="form-item">
			<span>아이디: ${memberVO.id}</span><br>
		</div>

		<div class="form-item">
			<input type="password" name="pwd" placeholder="*****" value="${empty param.pwd ? memberVO.pwd : param.pwd}" >
		</div>

		<div class="form-item">
			<input type="text" name="name" value="${empty param.name ? memberVO.name : param.name}" >
		</div>

		<div class="form-item">
			<input type="text" name="pnum" value="${empty param.pnum ? memberVO.pnum : param.pnum}" >
		</div>

		<div class="form-item">
			<input type="email" name="email" value="${empty param.email ? memberVO.email : param.email}">
		</div>

		<div class="button-panel">
			<input type="submit" class="button" title="Sign In" value="정보 수정하기">
		</div>
	</form>
	<div class="form-footer">
		<p><a href="${contextPath}/member/mypage.do">마이페이지로 돌아가기</a></p>
		<!-- 계정이 있다면 로그인 화면으로 이동 -->
	</div>
</div>

</body>
</html>