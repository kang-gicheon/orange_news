let id = getCookie("loginId");
let aNum = getCookie("articlenum");

$(document).ready(function() {

	//댓글 초기화

	resetList(0);
	needLogin();

	//반응 초기화
	getReact();
});

function getReact() {
	console.log("겟리엑트 아직 안만듦");
	/*$.ajax({
		type: 'post',

		// 경로를 바꿔야 한다면 이 변수를 바꾸기
		url: '/news/getReact.do',
		//

		//articleNum을 viewPage에서 받을수 있도록 한다.
		dataType: 'json',
		data: { "articleNum": aNum },

		success: function(result) { // 결과 성공 콜백함수
		
			console.log(result);

		},

		error: function(request, status, error) { // 결과 에러 콜백함수
			console.log(error)
		}
	});*/
}

function resetList(type) {
	
	if (type == 2) {

		$.ajax({
			type: 'post',

			// 경로를 바꿔야 한다면 이 변수를 바꾸기
			url: '/trc/type0list.do',
			//

			//articleNum을 viewPage에서 받을수 있도록 한다.
			//일단 임시로 1을 전달 
			///////////////////////////sortType에 아이디 넣어야 함
			dataType: 'json',
			data: { "articleNum": aNum , "sortType": id },

			success: function(result) { // 결과 성공 콜백함수
				$(".reply-list").empty();
				for (let i = 0; i < Object.keys(result).length; i++) {

					makeReply(result[i].userID,
						result[i].date,
						result[i].contents,
						result[i].good,
						result[i].bad,
						result[i].replyNum);

				}
				console.log(result);

			},

			error: function(request, status, error) { // 결과 에러 콜백함수
				console.log(error)
			}
		});


	} else {

		$.ajax({
			type: 'post',

			// 경로를 바꿔야 한다면 이 변수를 바꾸기
			url: '/trc/type0list.do',
			//

			//articleNum을 viewPage에서 받을수 있도록 한다.
			//일단 임시로 1을 전달
			dataType: 'json',
			data: { "articleNum": aNum , "sortType": type },

			success: function(result) { // 결과 성공 콜백함수
				$(".reply-list").empty();
				for (let i = 0; i < Object.keys(result).length; i++) {

					makeReply(result[i].userID,
						result[i].date,
						result[i].contents,
						result[i].good,
						result[i].bad,
						result[i].replyNum);

				}
				console.log(result);

			},

			error: function(request, status, error) { // 결과 에러 콜백함수
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
						<h6 class="card-subtitle mb-2 text-muted">`+ date + `</h6>
						<p class="card-text">`+ comment + `</p>
						<a href="#" onclick="showType1(`+ renum + `)" class="card-link">답글</a>
						<a href="#"	onclick="getGood(`+ renum + `)" class="card-link">추천 : ` + good + `</a>
						<a href="#" onclick="getBad(`+ renum + `)"	class="card-link">비추천 : ` + bad + `</a>
						<a href="#" onclick="deleteType0(`+ renum + `)"	class="card-link">삭제</a>
						
						<div class="type1-reply">
						<p></p>
						<hr>
							<div class="type1-reply-write">
								<div class="type1-id-space">&nbsp&nbsp&nbsp&nbsp` + id + `</div>
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

	if (content != null) {

		$.ajax({
			type: 'post',

			// 경로를 바꿔야 한다면 이 변수를 바꾸기
			url: '/trc/addreply0.do',
			//

			//articleNum을 viewPage에서 받을수 있도록 한다. id 또한 마찬가지
			//일단 임시로 1과 kang 을 전달
			dataType: 'text',
			data: { "articleNum": aNum , "id": id , "comment": content },

			success: function(result) { // 결과 성공 콜백함수

				console.log(result);
				if (result == "success") {
					alert("답변이 등록되었습니다.");
					//새로고침
					location.reload();

				} else {
					alert("다시 시도해 주십시오");
				}

			},

			error: function(request, status, error) { // 결과 에러 콜백함수
				console.log(error)
			}
		});

	} else {
		alert("내용을 입력해 주심시오");
	}

}

function getGood(renum) {

	$.ajax({
		type: 'post',

		// 경로를 바꿔야 한다면 이 변수를 바꾸기
		url: '/trc/getgood.do',
		//

		//articleNum을 viewPage에서 받을수 있도록 한다.
		//일단 임시로 1을 전달
		dataType: 'text',
		data: { "replyNum": renum },

		success: function(result) { // 결과 성공 콜백함수

			console.log(result);
			if (result == "success") {
				alert("해당 댓글을 추천하셨습니다.");
				//새로고침
				location.reload();

			} else {
				alert("다시 시도해 주십시오");
			}

		},

		error: function(request, status, error) { // 결과 에러 콜백함수
			console.log(error)
		}
	});
}

function getBad(renum) {

	$.ajax({
		type: 'post',

		// 경로를 바꿔야 한다면 이 변수를 바꾸기
		url: '/trc/getbad.do',
		//

		//articleNum을 viewPage에서 받을수 있도록 한다.
		//일단 임시로 1을 전달
		dataType: 'text',
		data: { "replyNum": renum },

		success: function(result) { // 결과 성공 콜백함수

			console.log(result);
			if (result == "success") {
				alert("해당 댓글을 비추천하셨습니다.");
				//새로고침
				location.reload();

			} else {
				alert("다시 시도해 주십시오");
			}

		},

		error: function(request, status, error) { // 결과 에러 콜백함수
			console.log(error)
		}
	});

}

function deleteType0(renum) {

	$.ajax({
		type: 'post',

		// 경로를 바꿔야 한다면 이 변수를 바꾸기
		url: '/trc/deletetype0.do',
		//

		//articleNum을 viewPage에서 받을수 있도록 한다.
		//일단 임시로 1을 전달
		dataType: 'text',
		data: { "replyNum": renum },

		success: function(result) { // 결과 성공 콜백함수

			console.log(result);
			if (result == "success") {
				alert("해당 댓글을 삭제하였습니다.");
				//새로고침
				location.reload();

			} else {
				alert("다시 시도해 주십시오");
			}

		},

		error: function(request, status, error) { // 결과 에러 콜백함수
			console.log(error)
		}
	});

}

function deleteType1(renum, parentnum) {

	$.ajax({
		type: 'post',

		// 경로를 바꿔야 한다면 이 변수를 바꾸기
		url: '/trc/deletetype1.do',
		//

		//articleNum을 viewPage에서 받을수 있도록 한다.
		//일단 임시로 1을 전달
		dataType: 'text',
		data: { "replyNum": renum, "parentNum": parentnum },

		success: function(result) { // 결과 성공 콜백함수

			console.log(result);
			if (result == "success") {

				alert("해당 댓글을 삭제하였습니다.");
				showType1(parentnum);
				$('#reply' + parentnum + ' > div > .type1-reply').show();

			} else {
				alert("다시 시도해 주십시오");
			}

		},

		error: function(request, status, error) { // 결과 에러 콜백함수
			console.log(error)
		}
	});

}

function makeReply1(id, date, comment, replyNum, parentNum) {
	let replyId = "reply" + replyNum;
	let parentId = "reply" + parentNum;
	console.log("makeR1 : " + replyId);
	$('#' + parentId + ' > div > div > .type1-reply-list').append(`
				<div id="`+ replyId + `">
					<div class="card-body sm">
						<h6 class="card-title">`+ id + `&nbsp&nbsp<a href="#" onclick="deleteType1(` + replyNum + `,` + parentNum + `)" class="card-link" style="font-size:8px">삭제</a></h6>  
						<h7 class="card-subtitle mb-2 text-muted">`+ date + `</h7>
						<p class="card-text">`+ comment + `</p>
					<div>
					<hr  class="type1">
				</div>
		`);
}

function showType1(parentnum) {

	let id = "reply" + parentnum;

	console.log(id);

	$('#' + id + ' > div > .type1-reply').toggle();

	$.ajax({
		type: 'post',

		// 경로를 바꿔야 한다면 이 변수를 바꾸기
		url: '/trc/type1list.do',
		//

		//articleNum을 viewPage에서 받을수 있도록 한다.
		//일단 임시로 1을 전달
		dataType: 'json',
		data: { "articleNum": aNum, "parentNum": parentnum },

		success: function(result) { // 결과 성공 콜백함수

			console.log("111");
			console.log(result);

			$('#' + id + ' > div > div > div.type1-reply-list').empty();

			console.log(Object.keys(result).length);

			for (let i = 0; i < Object.keys(result).length; i++) {

				makeReply1(result[i].userID,
					result[i].date,
					result[i].contents,
					result[i].replyNum,
					result[i].parentNum);

			}

		},

		error: function(request, status, error) { // 결과 에러 콜백함수
			console.log(error);
		}
	});

}

function writeReplyType1(parentNum) {

	let replyid = "reply" + parentNum;

	let comment = $('#' + replyid + ' > div > div > div > div > .type1-text-space').val();

	$.ajax({
		type: 'post',

		// 경로를 바꿔야 한다면 이 변수를 바꾸기
		url: '/trc/addreply1.do',
		//

		//articleNum을 viewPage에서 받을수 있도록 한다.
		//일단 임시로 1을 전달 id 는 임시로 kang
		dataType: 'text',
		data: { "articleNum": aNum, "replyNum": parentNum, "id": id , "comment": comment },

		success: function(result) { // 결과 성공 콜백함수
			if (result == "success") {

				alert("답변을 등록 하셨습니다.");

				showType1(parentNum);
				$('#'+ replyid + ' > div > .type1-reply').show();

			} else {
				alert("다시 시도해 주십시오");
			}


		},

		error: function(request, status, error) { // 결과 에러 콜백함수
			console.log(error)
		}

	});

}

function needLogin() {

	if (id == null) {
		$(".reply-insert").hide();
		$(".login_need").show();
		console.log("비로그인 상태");
	} else {
		$(".reply-insert").show();
		$(".login_need").hide();
		console.log("로그인 상태");
	}
}

function getCookie(name) { //가져올 쿠키의 이름을 파라미터 값으로 받고

	var nameOfCookie = name + "="; //쿠키는 "쿠키=값" 형태로 가지고 있어서 뒤에 있는 값을 가져오기 위해 = 포함

	var x = 0;

	while (x <= document.cookie.length) {  //현재 세션에 가지고 있는 쿠키의 총 길이를 가지고 반복

		var y = (x + nameOfCookie.length); //substring으로 찾아낼 쿠키의 이름 길이 저장

		if (document.cookie.substring(x, y) == nameOfCookie) { //잘라낸 쿠키와 쿠키의 이름이 같다면

			if ((endOfCookie = document.cookie.indexOf(";", y)) == -1) //y의 위치로부터 ;값까지 값이 있으면 

				endOfCookie = document.cookie.length; //쿠키의 길이로 적용하고

			return unescape(document.cookie.substring(y, endOfCookie)); //--쿠키의 시작점과 끝점을 찾아서 값을 반환

		}

		x = document.cookie.indexOf(" ", x) + 1; //--다음 쿠키를 찾기 위해 시작점을 반환

		if (x == 0) //--쿠키 마지막이면 

			break; //--반복문 빠져나오기

	}

	return null; //--빈값 반환

}

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

