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

<link href="style.css" rel="stylesheet" type="text/css" />
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-rbsA2VBKQhggwzxH7pPCaAqO46MgnOM80zW1RWuH61DGLwZJEdK2Kadq2F9CUG65"
	crossorigin="anonymous">

<script src="http://code.jquery.com/jquery-latest.min.js"></script>
<script type="text/javascript">
	function readURL(input) {
		if (input.files && input.files[0]) {
			var reader = new FileReader();
			reader.onload = function(e) {
				$('#preview').attr('src', e.target.result);
			}
			reader.readAsDataURL(input.files[0]);
		}
	}
	function backToList(obj) {
		obj.action = "${contextPath}/news/";
		obj.submit();
	}
</script>

<style>
div {
	padding: 5px;
	margin-top: 5px;
	margin-bottom: 5px;
}
</style>

<head>
<meta charset="UTF-8">
<title>기사 작성 페이지</title>
</head>
<body>
	<h1 style="text-align: center">기사 작성하는 페이지</h1>
	<form name="addArticleForm" method="post"
		action="${contextPath }/news/addArticle.do"
		enctype="multipart/form-data">
		<div style="width: 500px; margin: 10px auto">
			<div class="form-floating mb-3">
				<input type="text" class="form-control" id="floatingInput"
					placeholder="기사 제목 입력 칸" name="title"> <label
					for="floatingInput">기사 제목</label>
			</div>
			<div class="form-floating">
				<textarea class="form-control" placeholder="기사 내용 입력 칸"
					id="floatingTextarea2" name="content"
					style="height: 200px; resize: none"></textarea>
				<label for="floatingTextarea">기사 내용</label>
			</div>
			<div>
				<select class="form-select" aria-label="Default select examcleType" name="articleType">
					<option selected>기사 타입 선택</option>
					<option value="1">경제/정치</option>
					<option value="2">산업</option>
					<option value="3">사회/문화</option>
					<option value="4">부동산</option>
					<option value="5">글로벌</option>
					<option value="6">블록체인</option>
					<option value="7">스포츠/연예</option>
				</select>
			</div>
			<div>
				<select class="form-select" name="hotissue">
					<option value="0">일반 기사</option>
					<option value="1">특종</option>
				</select>
			</div>

			<div class="input-group mb-3">
				<label class="input-group-text" for="inputGroupFile01">Upload</label>
				<input class="form-control" placeholder=""
					aria-label="Example text with button addon"
					aria-describedby="button-addon1" type="file" name="imgFileName"
					onchange="readURL(this);" width=100%>

			</div>
			<div>

				<img id="preview" src="#" width=100% height=400px />

			</div>
			<div style="text-align: center">
				<input type="submit" value="글쓰기" /> <input type=button value="목록보기"
					onClick="backToList(this.form)" />
			</div>

		</div>
	</form>
</body>
</html>