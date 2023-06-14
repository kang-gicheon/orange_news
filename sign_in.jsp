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
	/* CSS 적용예정 */
</style>
<head>
<meta charset="UTF-8">
<title>로그인창</title>
</head>
<body>

	<form method = "post" action = ""}>	<!-- 받은 데이터 경로 지정  -->
	
		<h1 style = "text-align:center">로그인 창</h1>
		<table align = "center">
		
			<tr>
			
				<td width = "200">
				
					<p align = "right">아이디
				
				</td>
				
					<td width = "400"><input type = "text" name = ""></td> <!-- 아이디 컬럼 지정 -->
			
			</tr>
			
				<tr>
			
				<td width = "200">
				
					<p align = "right">비밀번호
				
				</td>
				
					<td width = "400"><input type = "password" name = ""></td> <!-- 비밀번호 컬럼 지정 -->
			
			</tr>
				
			<tr>
				
				<td width = "200"> 
					
					<p> </p>	<!-- 로그인, 다시하기 입력칸 색 지정 -->
					
				</td>
	
				
				
			
				<td width = "400">
				
					<input type = "submit" value = "로그인 하기">
					<input type = "submit" value = "다시 입력하기">
						
				</td>
			
			</tr>		
		
		</table>
	
	</form>

</body>
</html>