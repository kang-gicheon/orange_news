<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%
request.setCharacterEncoding("UTF-8");
String id = request.getParameter("id");
%>

<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>reply demo</title>
<script  src="http://code.jquery.com/jquery-3.6.0.min.js"></script>

<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-rbsA2VBKQhggwzxH7pPCaAqO46MgnOM80zW1RWuH61DGLwZJEdK2Kadq2F9CUG65"
	crossorigin="anonymous">


<style type="text/css">
.reply-section {
	width: 650px;
	border: 1px solid;
}

.reply-insert {
	width: 620px;
	height: 250px;
	border: 1px solid;
	border-radius: 4px;
	border-color: orange;
	background-color: #fff;
	margin: 5px auto;
}

.reply-insert textarea {
	width: 100%;
	height: 150px;
	border-radius: 4px;
	resize: none;
	margin: 5px auto;
	border-color: orange;
	background-color: #fff;
}

hr {
	width: 95%;
	margin: 2px auto 2px;
	background-color: #fff;
	margin: 2px auto 2px;
}

hr.type1 {
	width: 100%;
	margin: 2px auto 2px;
	background-color: #fff;
	margin: 2px auto 2px;
}

.re-write-space {
	width: 95%;
	margin: 5px auto 9px;
}

.id-space {
	color: #000;
	font-size: 15px;
}

.writebutton {
	background-color: #fff;
	font-size: 3px;
	float: right;
	margin: 0 0 0 100px;
}
#type1writebutton {
	background-color: #fff;
	font-size: 3px;
	float: right;
	margin: 0 29px 0 ;
	
}

.card {
	margin: 10px auto 10px;
}

.sort-list {
	text-align: center;
}

.type1-reply {
	display: none;
}

.type1-text-space {
	width : 100%;
	resize : none;
}

</style>


<script type="text/javascript">

	$(document).ready(function() {
		
		//댓글 초기화
			resetList(0);

	}); 
	
	function resetList(type) {
		
		if(type == 2) {
			
			$.ajax({
				type : 'post',	
				
				// 경로를 바꿔야 한다면 이 변수를 바꾸기
				url : '/trc/type0list.do',			
				//
				
				//articleNum을 viewPage에서 받을수 있도록 한다.
				//일단 임시로 1을 전달 
				///////////////////////////sortType에 아이디 넣어야 함
				dataType : 'json',   
				data : {"articleNum" : "1", "sortType" : "kang"},
				
				success : function(result) { // 결과 성공 콜백함수
					$(".reply-list").empty();
					for(l et i = 0; i < Object.keys(result).length ; i++ ){
						
						makeReply(result[i].userID,
								result[i].date,
								result[i].contents,
								result[i].good,
								result[i].bad,
								result[i].replyNum);
						
					}
					console.log(result);
					
				},
				
				error : function(request, status, error) { // 결과 에러 콜백함수
					console.log(error)
				}
			});	

			
		} else {
		
			$.ajax({
				type : 'post',	
				
				// 경로를 바꿔야 한다면 이 변수를 바꾸기
				url : '/trc/type0list.do',			
				//
				
				//articleNum을 viewPage에서 받을수 있도록 한다.
				//일단 임시로 1을 전달
				dataType : 'json',   
				data : {"articleNum" : "1", "sortType" : type},
				
				success : function(result) { // 결과 성공 콜백함수
					$(".reply-list").empty();
					for(let i = 0; i < Object.keys(result).length ; i++ ){
						
						makeReply(result[i].userID,
								result[i].date,
								result[i].contents,
								result[i].good,
								result[i].bad,
								result[i].replyNum);
						
					}
					console.log(result);
					
				},
				
				error : function(request, status, error) { // 결과 에러 콜백함수
					console.log(error)
				}
			});
		}
	}	
	
	function makeReply(id, date, comment, good, bad, renum) {
		
		$(".reply-list").append(`
				<div id="reply`+ renum + `" class="card" style="width: 95%">
					<div class="card-body">
						<h5 class="card-title">`+ id + `</h5>
						<h6 class="card-subtitle mb-2 text-muted">`+ date +`</h6>
						<p class="card-text">`+ comment +`</p>
						<a href="#" onclick="showType1(`+renum+`)" class="card-link">답글</a>
						<a href="#"	onclick="getGood(`+renum+`)" class="card-link">추천 : ` + good + `</a>
						<a href="#" onclick="getBad(`+renum+`)"	class="card-link">비추천 : ` + bad + `</a>
						<a href="#" onclick="deleteType0(`+renum+`)"	class="card-link">삭제</a>
						
						<div class="type1-reply">
						<p></p>
						<hr>
							<div class="type1-reply-write">
								<div class="type1-id-space">&nbsp&nbsp&nbsp&nbsp <%= id %></div>
								<hr>
								<div style="width : 90%; margin-left : 30px; margin-top: 10px">
								<textarea class="type1-text-space"></textarea><br />
								</div>
								<button type="button"
									class="btn btn-warning active btn-sm" id="type1writebutton"
									onclick="writeReplyType1(` + renum + `)">등록</button>
							</div>
							<p style="margin-bottom: 50px"></p>
							<hr>
							<div class="type1-reply-list">
								답글이 없습니다.
							</div>
						</div>
					</div>
				</div>
		`);
		
	}
	
	
		
	function writeReply() {
		
		let content = $(".text-space").val();
		
		if( content != null) {
			
			$.ajax({
				type : 'post',	
				
				// 경로를 바꿔야 한다면 이 변수를 바꾸기
			    url : '/trc/addreply0.do',			
			    //
			    
			    //articleNum을 viewPage에서 받을수 있도록 한다. id 또한 마찬가지
			    //일단 임시로 1과 kang 을 전달
			    dataType : 'text',   
			    data : {"articleNum" : "1" , "id" : "kang", "comment" : content},
			    
			    success : function(result) { // 결과 성공 콜백함수

			        console.log(result);
			    	if(result == "success"){
			    		alert("답변이 등록되었습니다.");
			    		//새로고침
			    		location.reload();
			    		
			    	} else {
			    		alert("다시 시도해 주십시오");
			    	}
					
			    },
			    
			    error : function(request, status, error) { // 결과 에러 콜백함수
			        console.log(error)
			    }
			});
			
		} else {
			alert("내용을 입력해 주심시오");
		}
		
	}
	
	function getGood(renum) {
		
		$.ajax({
			type : 'post',	
			
			// 경로를 바꿔야 한다면 이 변수를 바꾸기
		    url : '/trc/getgood.do',			
		    //
		    
		    //articleNum을 viewPage에서 받을수 있도록 한다.
		    //일단 임시로 1을 전달
		    dataType : 'text',   
		    data : {"replyNum" : renum},
		    
		    success : function(result) { // 결과 성공 콜백함수
		    	
		        console.log(result);
		        if(result == "success"){
		    		alert("해당 댓글을 추천하셨습니다.");
		    		//새로고침
		    		location.reload();
		    		
		    	} else {
		    		alert("다시 시도해 주십시오");
		    	}
				
		    },
		    
		    error : function(request, status, error) { // 결과 에러 콜백함수
		        console.log(error)
		    }
		});
	}
		
	function getBad(renum) {
		
		$.ajax({
			type : 'post',	
			
			// 경로를 바꿔야 한다면 이 변수를 바꾸기
			url : '/trc/getbad.do',			
			//
			
			//articleNum을 viewPage에서 받을수 있도록 한다.
			//일단 임시로 1을 전달
			dataType : 'text',   
			data : {"replyNum" : renum},
			
			success : function(result) { // 결과 성공 콜백함수
				
				console.log(result);
				if(result == "success"){
					alert("해당 댓글을 비추천하셨습니다.");
					//새로고침
					location.reload();
						
				} else {
					alert("다시 시도해 주십시오");
				}
				
			},
			
			error : function(request, status, error) { // 결과 에러 콜백함수
				console.log(error)
			}
		});
			
	}
	
	function deleteType0(renum) {
		
		$.ajax({
			type : 'post',	
			
			// 경로를 바꿔야 한다면 이 변수를 바꾸기
			url : '/trc/deletetype0.do',			
			//
			
			//articleNum을 viewPage에서 받을수 있도록 한다.
			//일단 임시로 1을 전달
			dataType : 'text',   
			data : {"replyNum" : renum},
			
			success : function(result) { // 결과 성공 콜백함수
				
				console.log(result);
				if(result == "success"){
					alert("해당 댓글을 삭제하였습니다.");
					//새로고침
					location.reload();
						
				} else {
					alert("다시 시도해 주십시오");
				}
				
			},
			
			error : function(request, status, error) { // 결과 에러 콜백함수
				console.log(error)
			}
		});
		
	}
	
	function deleteType1(renum, parentnum) {
		
		$.ajax({
			type : 'post',	
			
			// 경로를 바꿔야 한다면 이 변수를 바꾸기
			url : '/trc/deletetype1.do',			
			//
			
			//articleNum을 viewPage에서 받을수 있도록 한다.
			//일단 임시로 1을 전달
			dataType : 'text',   
			data : {"replyNum" : renum, "parentNum" : parentnum},
			
			success : function(result) { // 결과 성공 콜백함수
				
				console.log(result);
				if(result == "success"){
					
					alert("해당 댓글을 삭제하였습니다.");
					showType1(parentnum);
						
				} else {
					alert("다시 시도해 주십시오");
				}
				
			},
			
			error : function(request, status, error) { // 결과 에러 콜백함수
				console.log(error)
			}
		});
		
	}
	
	function makeReply1(id, date, comment, replyNum, parentNum) {
		let replyId = "reply" + replyNum;
		let parentId = "reply"+ parentNum;
		console.log("makeR1 : "+replyId);	
		$('#'+ parentId +' > div > div > .type1-reply-list').append(`
				<div id="`+ replyId +`">
					<div class="card-body sm">
						<h6 class="card-title">`+ id + `&nbsp&nbsp<a href="#" onclick="deleteType1(`+replyNum+`,`+parentNum+`)" class="card-link" style="font-size:8px">삭제</a></h6>  
						<h7 class="card-subtitle mb-2 text-muted">`+ date +`</h7>
						<p class="card-text">`+ comment +`</p>
					<div>
					<hr  class="type1">
				</div>
		`);
	}

	function showType1(parentnum) {
		
		let id = "reply"+parentnum;
		
		console.log(id);
		
		$('#'+id+' > div > .type1-reply').show();
		
		$.ajax({
			type : 'post',	
			
			// 경로를 바꿔야 한다면 이 변수를 바꾸기
			url : '/trc/type1list.do',			
			//
			
			//articleNum을 viewPage에서 받을수 있도록 한다.
			//일단 임시로 1을 전달
			dataType : 'json',   
			data : {"articleNum": "1", "parentNum" : parentnum},
			
			success : function(result) { // 결과 성공 콜백함수
				
				console.log("111");
				console.log(result);
			
				$('#'+id+' > div > div > div.type1-reply-list').empty();
				
				console.log(Object.keys(result).length);
				
				for(let i = 0; i < Object.keys(result).length ; i++ ){
					
					makeReply1(result[i].userID,
							result[i].date,
							result[i].contents,
							result[i].replyNum,
							result[i].parentNum);
					
				}
				
			},
			
			error : function(request, status, error) { // 결과 에러 콜백함수
				console.log(error);
			}
		});	
		
	}
	
	function writeReplyType1(parentNum) {
		
		let id = "reply"+parentNum;
		
		let comment = $('#'+id+' > div > div > div > div > .type1-text-space').val();
		
		$.ajax({
			type : 'post',	
			
			// 경로를 바꿔야 한다면 이 변수를 바꾸기
			url : '/trc/addreply1.do',			
			//
			
			//articleNum을 viewPage에서 받을수 있도록 한다.
			//일단 임시로 1을 전달 id 는 임시로 kang
			dataType : 'text',   
			data : {"articleNum": "1", "replyNum" : parentNum, "id" : 'kang', "comment" : comment},
			
			success : function(result) { // 결과 성공 콜백함수
				if(result == "success"){
					
					alert("답변을 등록 하셨습니다.");
					
					showType1(parentNum);
					
				} else {
					alert("다시 시도해 주십시오");
				}
				
				
			},
			
			error : function(request, status, error) { // 결과 에러 콜백함수
				console.log(error)
			}
			
		});
		
	}
</script>
</head>
<body>
	<div class="reply-section">
		<div class="reply-insert">
			<div class="re-write-space">
				<div class="id-space">&nbsp&nbsp&nbsp&nbsp <%= id %></div>
				<hr>
				<textarea class="text-space"></textarea>
				<br />
				<button style="float: right" type="button"
					class="btn btn-warning active" class="writebutton"
					onclick="writeReply()">댓글 작성</button>
			</div>
		</div>
		<p></p>
		<hr>
		<div class="sort-list">
			<span onclick="resetList('0')">인기순</span>&nbsp&nbsp&nbsp
			<span onclick="resetList('1')">최신순</span>&nbsp&nbsp&nbsp
			<span onclick="resetList('2')">내댓글</span>
		</div>
		<hr>
		<p></p>
		<div class="reply-list">
			<!-- 댓글 예시 -->
		
		</div>
			
	</div>
</body>
</html>