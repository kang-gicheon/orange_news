<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%
request.setCharacterEncoding("UTF-8");
%>

<c:set var="contextPath" value="${pageContext.request.contextPath}" />


<!DOCTYPE html>
<html>
<head>
<script src="//code.jquery.com/jquery-3.6.0.min.js"></script>
<meta charset="UTF-8">
<title>Insert title here</title>
<script>
	
	$(document).ready(function() {
		
		$("g").click(function() {
			
			let mapId = "";
			let mapName = "";
			
			mapId = $(this).attr('id');
			mapName = $(this).attr('name');
			
			console.log(mapName);
			
			$.ajax({
				
				type : 'post',			 // 타입 (get, post, put 등등)
			    url : '/twc/getco.do',			 // 요청할 서버url
			    dataType : 'json',       // 데이터 타입 (html, xml, json, text 등등)
			    data : {"mapName" : mapName},
			    
			    success : function(result) { // 결과 성공 콜백함수
			        console.log(result);
			    	getAPI(result.coX, result.coY, result.nowTime, result.nowDate);
			    	
			    	$("g > *").attr('fill', '#118ac9'); 

					let getFill1 = document.querySelector("#"+mapId);
					let getFill2 = getFill1.querySelectorAll("path");

					for(let i = 0; i < getFill2.length; i++){
						getFill2[i].setAttribute('fill','#FF761A');
					}
					
					document.getElementById("wArea").innerHTML = mapName + "<br/> 날씨정보";
					
			    },
			    
			    error : function(request, status, error) { // 결과 에러 콜백함수
			        console.log(error)
			    }
			})
			
		});
		
	});

	function getAPI( coX, coY, time, date) {
		// API 받아오기	
		//받아올 단어를 담아줄 배열
		var words = new Array();
		//json object 를 담아줄 객체
		var obj = "";

		var xhr = new XMLHttpRequest();
		var url = 'http://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/getUltraSrtNcst'; /*URL*/
		var queryParams = '?'
				+ encodeURIComponent('serviceKey')
				+ '='
				+ 'vDvgfXzS%2BoF6c1fJwn8CH205yaaoug7RDKTuX3milX7tMPCWG9%2BHpCOxRMTuCXfWRFkaigYqfU6PjTZe7DtNkA%3D%3D'; /*Service Key*/
				
		queryParams += '&' + encodeURIComponent('pageNo') + '='
				+ encodeURIComponent('1'); /*페이지 넘버*/
		queryParams += '&' + encodeURIComponent('numOfRows') + '='
				+ encodeURIComponent('1000'); /**/
		queryParams += '&' + encodeURIComponent('dataType') + '='
				+ encodeURIComponent('json'); /**/

		// 초 단기 실황일경우 1시간 내의 데이터만 조회 가능
		queryParams += '&' + encodeURIComponent('base_date') + '='
				+ encodeURIComponent(date); /**/
		queryParams += '&' + encodeURIComponent('base_time') + '='
				+ encodeURIComponent(time); /**/

		// 코드 정보는 엑셀에 저장됨
		queryParams += '&' + encodeURIComponent('nx') + '='
				+ encodeURIComponent(coX); /**/
		queryParams += '&' + encodeURIComponent('ny') + '='
				+ encodeURIComponent(coY); /**/

		xhr.open('GET', url + queryParams);

		xhr.onreadystatechange = function() {
			if (this.readyState == 4) {
				obj = JSON.parse(xhr.responseText);

				for (let i = 0; i < obj.response.body.items.item.length; i++) {
					console.log(obj.response.body.items.item[i]);
						
				}
				
				let RN1 = obj.response.body.items.item[2].obsrValue;
				let T1H = obj.response.body.items.item[3].obsrValue;
				let REH = obj.response.body.items.item[1].obsrValue;
				let PTY = obj.response.body.items.item[0].obsrValue;
				let PTYString;
				let WSD = obj.response.body.items.item[7].obsrValue;
				
				/* 없음(0), 비(1), 비/눈(2), 눈(3), 빗방울(5), 빗방울눈날림(6), 눈날림(7)  */
				if( PTY =='0') PTYString = "없음";
				else if ( PTY == '1') PTYString = "비"
				else if ( PTY == '2') PTYString = "비/눈";
				else if ( PTY == '3')PTYString = "눈";
				else if ( PTY == '5') PTYString = "빗방울";
				else if ( PTY == '6') PTYString = "빗방울눈날림";
				else if ( PTY == '7') PTYString = "눈날림";
				
				if(PTY != '0') {
					document.getElementById("PTY").innerHTML= "강수형태 :" + PTYString;
				}
				
				document.getElementById("T1H").innerHTML= T1H;
				document.getElementById("RN1").innerHTML= RN1;
				document.getElementById("REH").innerHTML= REH;
				document.getElementById("WSD").innerHTML= WSD;
			}
		};

		xhr.send('');
		// API 받기 끝
	}
	
	
</script>
<style>
</style>
</head>

<body>

	<table border=1>
		<tr>
			<td rowspan="2"><jsp:include page="Map.jsp"></jsp:include></td>
			<td id="wArea">날씨 정보</td>
		</tr>
		<tr>

			<td>
				<div>기온 :<span id="T1H" value="기온"></span></div>	
				<div>강수량 : <span id="RN1" value="강수량"></span></div>
				<div>습도 : <span id="REH" value="습도"></span></div>	
				<div><span id="PTY" value="강수형태"></span></div>	
				<div>풍속 : <span id="WSD" value="풍속"></span></div>	
			</td>
		</tr>

		<tr>
			<td colspan="2">지역별 검색</td>
		</tr>
	</table>


</body>
</html>