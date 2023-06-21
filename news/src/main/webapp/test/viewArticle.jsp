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
		<table border=1 align="center">
			<tr>

				<td width="5%" align="center" bgcolor="cyan">기사 종류</td>
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
						<td>오류</td>
					</c:otherwise>

				</c:choose>

			</tr>
			<tr>
				<td width="50" align="center" bgcolor="cyan">기자</td>
				<td align="center">${article.id }</td>
			</tr>
			<tr>
				<td width="50" align="center" bgcolor="cyan">기사 제목</td>
				<td align="center">${article.title }</td>
			</tr>

			<tr>
				<td width="50" align="center" bgcolor="cyan">기사 내용</td>
				<td align="center">${article.content }</td>
			</tr>

			<c:if
				test="${not empty article.imgFileName && article.imgFileName!='null' }">
				<tr>
					<td width="50" align="center" bgcolor="cyan">기사 이미지</td>

					<td><img
						src="${contextPath }/download.do?&title=${article.title}&imgFileName=${article.imgFileName}"
						id="preview" /></td>
				</tr>

			</c:if>


			<tr>
				<td rowspan="2" align="center" bgcolor="cyan">등록일자</td>
				<td>${article.writedate}</td>
			</tr>



		</table>
		</form>
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
					<td colspan="5">현재 ${article.actype }의 개수 : ${article.rcount} </td>
				</tr>

				<tr>

					<td colspan=5 align=center><input type=submit value="반응남기기" />
					</td>

				</tr>
				</table>
				</form>
				
				<form method="post"  action="${contextPath}/news/updateRec.do" name ="recform">
				
				<table>
				
				<tr>
					<td colspan ="5">현재 추천 개수 : ${article.recCount }</td>
					</tr>
					<tr>
					<td colspan="5">이 기사 추천하기 </td>
					<td colspan=5 align=center><input type=submit name="react" value="O"/>
					</td>
				</tr>
				
				<tr>
				<td colspan=5 align=center><input type=button value="리스트로 돌아가기"
					onClick="backToList(this.form)"></td>
			</tr>
			</table>
		</form>
		

</body>
</html>