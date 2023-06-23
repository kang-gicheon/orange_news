<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
        <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
		<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	request.setCharacterEncoding("UTF-8");
%>
<c:set var="contextPath" value="${pageContext.request.contextPath }" />
    
<!-- 로그인 이후 로그인한 정보와 계정 수정, 삭제, 로그아웃 기능을 지원하는 마이페이지 -->
    
<!DOCTYPE html>
<html>
<style>

html, body, div, span, applet, object, iframe,
h1, h2, h3, h4, h5, h6, p, blockquote, pre,
a, abbr, acronym, address, big, cite, code,
del, dfn, em, img, ins, kbd, q, s, samp,
small, strike, strong, sub, sup, tt, var,
b, u, i, center,
dl, dt, dd, ol, ul, li,
fieldset, form, label, legend,
table, caption, tbody, tfoot, thead, tr, th, td,
article, aside, canvas, details, embed, 
figure, figcaption, footer, header, hgroup, 
menu, nav, output, ruby, section, summary,
time, mark, audio, video {
 margin: 0;
 padding: 0;
 border: 0;
 font-size: 100%;
/*  font: inherit; */
/*  vertical-align: baseline; */
}
/* HTML5 display-role reset for older browsers */
article, aside, details, figcaption, figure, 
footer, header, hgroup, menu, nav, section {
 display: block;
}
body {
 line-height: 1;
}
ol, ul {
 list-style: none;
}
blockquote, q {
 quotes: none;
}
blockquote:before, blockquote:after,
q:before, q:after {
 content: '';
 content: none;
}
table {
 border-collapse: collapse;
 border-spacing: 0;
}
*{
/*   border: solid 1px #999; */
  margin: 0px;
  padding: 0px;
  vertical-align: middle;
  font-family: "lato";
  box-sizing: border-box;
  -webkit-font-smoothing: antialiased;
}
.myprofile{		/* 마이 프로필 전체 크기 수정 칸 */
  height: 400px;
  width: 420px;
  vertical-align: top;
  float: right;
  margin-top: 20px;
  margin-right: 20px;
  position: relative;
  box-shadow: 0 2px 10px 0 rgba(0, 0, 0, 0.2), 2px 10px 30px 0 rgba(0, 0, 0, 0.2);
  font-size: 13px;
  color: #666;
}
.myprofile .icon {
  display: inline-block;
  width: 1.8em;
  height: 1.8em;
  stroke-width: 0;
}
.myprofile .header,.myprofile .personal,.myprofile .business{
  padding: 10px 30px;
}
.myprofile .header{
  height: 150px;
  background: linear-gradient(#ee7426, #ed722f)	/* 마이 페이지 헤더영역 지정 */
}
.myprofile .button {	/* X 버튼 크기 지정 */
  position: absolute;
  top: 10px;
  right: 20px;
}
.myprofile .button  a{	/* X 링크 바깥 여백 지정 */
  margin-left: 10px;
}

.myprofile .button  a:hover{	/* X 링크 색상 밑줄 여부 지정 */
  
  color: #8c8c8c;
  text-decoration:none;
  
}

.myprofile a.close{
  font-size: 32px;
  color: #ffffff;
  text-decoration: none;
}
.myprofile .personal svg, .myprofile .business svg {
  margin-right: 10px;
}
.myprofile svg.icon.icon-edit-profile{
  width: 1.5em;
  height: 1.5em;
  color: #fff;
}
.myprofile h1 {
  display: inline-block;
  font-size: 22px;
  color: white;
  margin: 0;
  padding-top: 5px;
}
.myprofile .info{
  margin-left: 20px;
  margin-top: 20px;
}
.myprofile .info div{
  display: inline-block;
  margin-left: 20px;
  color: #fff;
}
.myprofile .icon.icon-user{
  display: inline-block;
  border-radius:50px;
  width: 4.5em;
  height: 4.5em;
  fill: #333;
}
.myprofile .name{
  font-weight: bold;
  font-size: 17px;
  line-height: 28px;
}
.myprofile .personal{
  background-color: #f4f4f4;
}
.myprofile .item{
  height: 40px;
  line-height: 40px;
}
.myprofile .item > div{
  float: left;
  width: 50%;
}
.myprofile .company{
  display: block;
  margin-top: 10px;
}
.myprofile h3.companyname{
  display: inline-block;
  font-size: 15px;
  font-weight: bold;
  color: #00ABD1;
}
.myprofile .item02,.myprofile .item03{
  display: inline-block;
  padding: 10px 0;
}
.myprofile .item02{
  width: 50%;
  float: left;
}
.myprofile .item02,.myprofile .item03 .title{
  line-height: 20px;
  color: #333;
}
.myprofile .item02 .details{
  font-size: 13px;
  color: #666;
}
.myprofile .item03{ /* 계정 수정, 삭제, 로그아웃 구간 시작 */
  width: 100%;
}
.myprofile table {
  border-collapse: collapse;
  width: 100%;
  margin-top: 5px;
}
.myprofile tr,.myprofile td {
  padding-left: 10px;
  height: 28px;
}

	/* 계정 수정,삭제,로그아웃 색 지정 */

/* .myprofile td{	
 
}
.myprofile .section td{
 
} */

</style>
<script>
	function fn_mod_member(obj){
		obj.action="${contextPath}/member/login.do"
		obj.submit();
	}
	
	function fn_logOut(obj){
		obj.action="${contextPath}/member/logout.do"
		obj.submit();
	}
</script>
<head>
<meta charset="UTF-8">
<title>로그인 개인 페이지</title>
</head>
<body>

<form name="frmMember" method="post" action="${contextPath }" enctype="multipart/form-data" >
  <div class="myprofile">

    <div class="header profile">
        <h1>내 프로필</h1>
        <div class="button">

              <a href="#"><svg class="icon icon-edit-profile">
              <use xlink:href="#icon-edit-profile"></use></svg></a>
              <a class="close" href="#">&times;</a>

        </div>

          <div class="info">

              <svg class="icon icon-user"><use xlink:href="#icon-user"></use></svg>
          
              <div>

                <h2 class="name">${memberVO.name }</h2>	<!-- 사용자 이름 / 아이디 입력칸 -->

              </div>

          </div>

    </div>

    <div class="personal">

    <span class="title">사용자 정보</span>
      <!-- <div class="item">
        <svg class="icon icon-title"><use xlink:href="#icon-title"></use></svg>
        <span>UI Designer</span>
      </div> -->	<!-- 추가 항목을 넣기 위한 칸 -->
      <div class="item">

          <svg class="icon icon-email"><use xlink:href="#icon-email"></use></svg>
          <span><!-- 사용자 이메일 입력칸 -->${memberVO.email} </span>

      </div>

      <div class="item">
      
        <div class="phone01">

          <svg class="icon icon-phone01"><use xlink:href="#icon-phone01"></use></svg>
          <span><!-- 휴대폰 번호 입력칸 -->${memberVO.pnum }</span>
        
        </div> 

  
      </div>  <!-- 프로필 타이틀, 이름, 이메일, 직업 -->
      

    </div>
    <div class="business">
      <div class="company">
        <svg class="icon icon-company"><use xlink:href="#icon-company"></use></svg>

      </div>
    
        <table>
              <tr class="section">

                    <td><input type="button" class="button000" title="Sign In" value="계정 수정하기" onClick="fn_mod_member(this.form)"></input></td>
                    <td><input type="button" class="button000" title="Sign In" value="계정 삭제하기"></input></td>
                    <td><input type="button" class="button000" title="Sign In" value="로그아웃하기" onClick="fn_logOut(this.form)"></input></td>
              
                  </tr>
      
            <!--   <tr class="section">
                <td><input type="submit" class="button000" title="Sign In" value="계정 수정하기"></input></td>
                  <td><input type="submit" class="button000" title="Sign In" value="계정 삭제하기"></input></td>
                  <td><input type="submit" class="button000" title="Sign In" value="로그아웃하기"></input></td>
              </tr> -->
  
  
        </table>
      </div>

    </div>
    

</form>

</body>
</html>