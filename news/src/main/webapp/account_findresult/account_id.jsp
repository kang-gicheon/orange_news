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

h3 {
  text-align: center;
  padding: 1em 0;
}

</style>
<script>
	function backTologin(obj){
		obj.action="${contextPath }/member/login.do";
		obj.submit();
	}
</script>
<head>
<meta charset="UTF-8">
<title>아이디 탐색결과</title>
</head>
<body>

<div class="form-wrapper">
	<div class = "form-wrapper-text">
	
		<p><a href='#'>X</a></p>
	
	</div>
  <h1>아이디 탐색결과</h1>
  <form>
    <div class="form-item">
      <label for="email"></label>
      <h3>${memberVO.id }</h3>
    </div>
    <div class="button-panel">
      <input type="button" class="button" title="Sign In" value="로그인" onClick="backTologin(this.form)"></input>
    </div>
  </form>
  <div class="form-footer">
    <p><a href="${contextPath }/member/findpwd.do">비밀번호 찾기</a></p>
  </div>
</div>

</body>
</html>