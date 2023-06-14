<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
request.setCharacterEncoding("UTF-8");
%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<script type="text/javascript">
	function backToList(obj) {
		obj.action = "${contextPath}/news/";
		obj.submit();
	}
</script>

<head>
<meta charset="UTF-8">
<title>기사 작성 페이지</title>
</head>
<body>
	<h1 style="text-align: center">기사 작성하는 페이지</h1>
	<form name="addArticleForm" method="post"
		action="${contextPath }/news/addArticle.do"
		enctype="multipart/form-data">

		<table border=0 align="center">
			<tr>
				<td align="center">기사 제목</td>
				<td colspan="2"><input type="text" size="50" maxlength="30"
					name="title" /></td>
			</tr>

			<tr>
				<td align="center">기사 내용</td>
				<td colspan="2"><input type="text" size="50" maxlength="50"
					name="content" /></td>
			</tr>

			<tr>
				<td align="center">기사 타입</td>
				<td colspan="2"><select name="articleType">
						<option value="">기사 타입 선택</option>
						<option value="1">경제/정치</option>
						<option value="2">산업</option>
						<option value="3">사회/문화</option>
						<option value="4">부동산</option>
						<option value="5">글로벌</option>
						<option value="6">블록체인</option>
						<option value="7">스포츠/연예</option>
				</select></td>
			</tr>

			<tr>
				<td align="center">특종 여부</td>
				<td colspan="2"><select name="hotissue">
					<option value="0">일반 기사</option>
					<option value="1">특종</option>
				</select>
					</td>

			</tr>
			
			<tr>
				<td align="right"></td>
				<td colspan="2">
				<input type="submit" value="글쓰기" />
				<input type=button value="목록보기"onClick="backToList(this.form)" />
				</td>
			</tr>
			


		</table>
		
		<h4 align="center">이미지 보류</h4>

	</form>
</body>
</html>