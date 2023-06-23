<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
    <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	request.setCharacterEncoding("UTF-8");
%>
<c:set var="contextPath" value="${pageContext.request.contextPath }" />

<!-- 찾고자 하는 아이디를 조회하기 위해 계정 생성시에 입력한 이름, 휴대폰 번호를 입력하는 페이지 -->

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
* { margin: 0; padding: 0; box-sizing: border-box; }

/* body 영역 시작 */

body {
  background: #e9e9e9;
  color: #5e5e5e;
  font: 400 87.5%/1.5em 'Open Sans', sans-serif;
}

/* 레이아웃 영역 구성 */

.form-wrapper {
  background: #fafafa;
  margin: 3em auto;
  padding: 0 1em;
  max-width: 370px;
}

.form-wrapper-text {

	color:black;
	text-align: right;
	padding: 10px

}

.form-wrapper-text a {

	color: black;
	text-decoration: none;
	text-font: bold;
	font-size: 20px

}

/

.form-wrapper-text a:hover {

	color: #8c8c8c;
	text-decoration: none;

/* 아이디 찾기 창 닫기 영역(링크) */

/* 레이아웃 제목 텍스트 영역 시작 */

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

.button-panel .button {		/* 아이디 찾기 버튼 크기, 색상 조정 */
  background: #ff761a;
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

.button:hover {		/* 아이디 찾기 버튼을 마우스로 가져다댈시 나타나는 효과 지정  */
  background: #ee3e52;
}

/* 아이디 찾기 버튼 영역 끝 */

/* 아이디 찾기 링크 영역 시작 */

.form-footer {		/* footer 영역 폰트 크기, 안쪽 여백 지정, 글씨 정렬 지정 */
  font-size: 1em;
  padding: 2em 0;
  text-align: center;
}

.form-footer a {		/* footer 링크 영역 색, 밑줄 생성, 딜레이 여부 지정 */
  color: #8c8c8c;
  text-decoration: none;
  transition: border-color 0.3s;
}

.form-footer a:hover {		/* 링크를 마우스로 가져다댈시 나타나는 효과 지정  */
  border-bottom: 1px dotted #8c8c8c;
}

</style>
<head>
<meta charset="UTF-8">

<!-- 제목 영역 이름 지정 -->

<title>계정 분실창 아이디</title>
</head>
<body>

<div class="form-wrapper">

<!-- 아이디 찾기 창 종료 링크 영역 -->

<div class = "form-wrapper-text">
<p><a href='#'>X</a></p>
</div>
  <h1>아이디 찾기</h1>
  
   <!-- 아이디 찾기 전 임력 정보 form 생성 영역 -->
  
  <form name="abc" method="post" action="${contextPath }/member/resultid.do">
    <div class="form-item">
      <label for="name"></label>
      <input type="text" name="name" required="required" placeholder="이름"></input>
    </div>
    <div class="form-item">
      <label for="pnum"></label>
      <input type="text" name="pnum" required="required" placeholder="휴대폰 번호"></input>
    </div>
    <div class="button-panel">
      <input type="submit" class="button" title="Sign In" value="아이디 찾기"></input>
    </div>
  </form>
  
    <!-- 아이디 찾기 전 임력 정보 form 생성 영역 종료 -->
  
   <!-- 사용자가 계정을 기억해 로그인 하는 경우 또는 비밀번호 생각이 나지 않는 경우를 대비한 링크 생성 영역 -->
  
  <div class="form-footer">
    <p><a href="${contextPath }/member/login.do">로그인하기</a></p>
    <p><a href="${contextPath }/member/findpwd.do">비밀번호를 잊으셨나요?</a></p>
  </div>
</div>

</body>
</html>