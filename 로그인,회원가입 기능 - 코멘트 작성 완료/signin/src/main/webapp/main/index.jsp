<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link href="style.css" rel="stylesheet" type="text/css" />
<title>임시 페이지</title>
</head>
<body>

	<jsp:include page="./header.jsp"></jsp:include>
	<menu>menu
	</menu>
	<section>
		<article class="headline">
			기사제목
			<hr class="image">
			<hr>
		</article>
	</section>

	<aside>
		<div class="login">로그인</div>
		<div class="weather">오늘의 날씨</div>
		<div class="hot-issue-rank">핫이슈 랭크</div>
	</aside>
	<footer>footer</footer>

</body>
</html>