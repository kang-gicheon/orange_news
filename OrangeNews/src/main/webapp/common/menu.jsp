<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<div class="navibar">
	<ul class="navi">
		<li>
			<div class="navi-text">
				<a href="${contextPath }/news/type1List.do">경제 / 정치</a>
			</div>
		</li>
		<li>
			<div>
				<a href="${contextPath }/news/type2List.do">산 업</a>
			</div>
		</li>
		<li>
			<div>
				<a href="${contextPath }/news/type3List.do">사회 / 문화</a>
			</div>
		</li>
		<li>
			<div>
				<a href="${contextPath }/news/type4List.do">부 동 산</a>
			</div>
		</li>
		<li>
			<div>
				<a href="${contextPath }/news/type5List.do">글 로 벌</a>
			</div>
		</li>
		<li>
			<div>
				<a href="${contextPath }/news/type6List.do">블록 체인</a>
			</div>
		</li>
		<li>
			<div style="border-right: solid 1px">
				<a href="${contextPath }/news/type7List.do">연예 / 스포츠</a>
			</div>
		</li>

	</ul>
</div>