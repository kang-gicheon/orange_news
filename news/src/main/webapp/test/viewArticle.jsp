<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
request.setCharacterEncoding("UTF-8");
%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>기사 정보</title>

<script src="http://code.jquery.com/jquery-latest.min.js"></script>
<script type="text/javascript">
	function backToList(obj) {
		obj.action = "${contextPath}/news";
		obj.submit();
	}

	function readURL(input) {
		if (input.files && input.files[0]) {
			var reader = new FileReader();
			reader.onload = function(e) {
				$('#preview').attr('src', e.target.result);
			}
			reader.readAsDataURL(input.files[0]);
		}
	}
</script>
</head>
<body>
	<h1>기사 클릭시 나오는 기사 페이지</h1>
	<form name="frmArticle" method="post" action="${contextPath }">
		<table border="1" align="center">
			<tr>
				<td width="50" align="center" bgcolor="cyan">글번호</td>
				<td><input type="text" value="${article.articlenum }" disabled />
					<input type="hidden" name="articleNO" value="${article.articlenum}" />
				</td>
			</tr>
			<tr>
				<td width="50" align="center" bgcolor="cyan">기자</td>
				<td><input type="text" value="${article.id }" name="id"
					disabled /></td>
			</tr>
			<tr>
				<td width="50" align="center" bgcolor="cyan">기사 제목</td>
				<td><input type="text" value="${article.title }" name="title"
					id="i_title" disabled /></td>
			</tr>

			<tr>
				<td width="50" align="center" bgcolor="cyan">기사 내용</td>
				<td><textarea rows="20" cols="60" name="content" id="i_content"
						disabled> ${article.content }</textarea></td>
			</tr>

			<c:if
				test="${not empty article.imgFileName && article.imgFileName!='null' }">
				<tr>
					<td width="50" align="center" bgcolor="cyan">기사
						이미지</td>
					<td>
					<%-- <input type="hidden" name="originalFileName" value="${article.imgFileName }"> --%>
					<img src= "${contextPath }/download.do?
							imgFileName=${article.imgFileName}
							&articlenum=${article.articlenum}" id="preview" /><br>
					</td>
				</tr>

			</c:if>

			<tr>

				<td width="5%" align="center" bgcolor="cyan" >기사 종류</td>
				<c:choose>
					<c:when test="${article.type==1}">
						<td>경제/정치</td>
					</c:when>
					<c:when test="${article.type==2}">
						<td>산업</td>
					</c:when>
					<c:when test="${article.type==3}">
						<td>사회/문화</td>
					</c:when>
					<c:when test="${article.type==4}">
						<td>부동산</td>
					</c:when>
					<c:when test="${article.type==5}">
						<td>글로벌</td>
					</c:when>
					<c:when test="${article.type==6}">
						<td>블록체인</td>
					</c:when>
					<c:when test="${article.type==7}">
						<td>스포츠/연예</td>
					</c:when>
					<c:otherwise>
						<td>Exception</td>
					</c:otherwise>

				</c:choose>

			</tr>


			<tr>
				<td rowspan="2" align="center" bgcolor="cyan">등록일자</td>
				<td><input type=text
					value="<fmt:formatDate value="${article.writedate}"/>" /></td>
			</tr>



		</table>

		<tr id="tr_btn">
			<td colspan=2 align=center><input type=button value="리스트로 돌아가기"
				onClick="backToList(this.form)"></td>
		</tr>
	</form>
</body>
</html>