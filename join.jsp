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
<head>
<meta charset="UTF-8">
<title>회원가입 창</title>
</head>
<body>
	


<div id="signup">
<form action="">
    <div id="signup">	
        <div>
            <a>Log In</a>		<!-- c:set 활용 예정, 로그인 페이지 이동  -->
        </div>
        <h1>Sign Up</h1>ㄴ
        <div class="field-wrap">	<!-- autocomplete = 자동완성 기능 비활성화 -->
            <table>
                <tr>
                    <td>
                        이름
                    </td>
                    <td>
                        <input type="text" required autocomplete="off" />
                    </td>
                </tr>
                
                <tr>
                    <td>
                        Email주소
                    </td>
                    <td><input type="email"required autocomplete="off"/></td>
                </tr>


                <tr>
                    <td>
                        아이디
                    </td>
                    <td><input type=""required autocomplete="off"></td>
                </tr>


                <tr>
                    <td>
                        비밀번호
                    </td>
                    <td><input type="password"required autocomplete="off"/></td>
                </tr>

                <tr>
                    <td>
                        비밀번호 재입력
                    </td>
                    <td><input type=""required autocomplete="off"></td>
                </tr>
            
                <tr>
                    <td>
                        휴대폰 번호
                    </td>
                    <td>
                        <input type=""required autocomplete="off"></td>
                </tr>
            
                <tr>
                    <td>
                        주소
                    </td>
                    <td><input type=""required autocomplete="off"></td>
                </tr>
            </table>
        </div>


        
        <button type="submit" class="button button-block"/>회원가입</button>

    
</form>

</body>
</html>