<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>

<style>

.Link{

	text-align: left;
	transition: color 0.3s ease-in-out;
	text-decoration: none;

}

.Link a {

	color: white;
	transition: color 0.3s ease-in-out;
	text-decoration: none;

}

.Link a:hover {

	color: red;

}

body {
  background: #001628;
  color: white;	/* 뉴스 타이틀 색 지정 */
  padding: 4em;
  font-family: sans-serif;
  line-height: 1.15;
}

hr { /*dummy content*/
  height: 6px;
  border: none;
  background: rgba(0, 0, 0, 0.1);
} 

hr:last-child {  /*dummy content*/
  margin-right: 60%;
}

hr.image { /*dummy content*/
  padding-bottom: 50%;
} 

.page {
  padding: 2em;
  background: orange;	/* 기사 배경색 */
  max-width: 1100px;
  text-align:center;
}

.archive {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(180px, 1fr));
  grid-gap: 1em;
  grid-auto-flow: dense;
}

.article {
  padding: 1em;
  background: black;
  box-shadow:
    0 5px 10px rgba(0, 0, 0, 0.1),
    0 20px 20px rgba(0, 0, 0, 0.05);
}

.article:nth-child(1) {
  grid-column: 1 / 5;
  background: rgba(200,200,255,0.3)
}

.article:nth-child(1)>hr.image {
  padding-bottom: 30%;
}

.article:nth-child(1)>hr:last-child {
  margin-right: 20%;
  height: 28px
}

.article:nth-child(1):after {
  content: "grid-column: 1 / 5";
}

.article:nth-child(8) {
  grid-column: span 3 / -1;
  background: rgba(255,200,255,0.3)
}
.article:nth-child(8):after {
  content: "grid-column: span 3 / -1";
}

.article:nth-child(12) {
  grid-column: 1 / 4;
  background: rgba(255,200,255,0.3)
}
.article:nth-child(12):after {
  content: "grid-column: 1 / 4";
}

.article:after {
  color: #808;
  font-weight: bold;
  font-family: courier;
}

</style>

<meta charset="UTF-8">
<title>뉴스 메인페이지</title>
</head>
<body>
<div class="page">

<div class = "Link">
  <a href = #>기사 상세보기</a>
</div>

  <img src="../images/test.png" alt="로고">
  <h1>Orange NEWS</h1>
  
  <div class="archive">
    <article class="article">1
      <hr class="image">
      <hr>
    </article>
    <article class="article">2
      <hr>
      <hr>
      <hr>
      <hr>
      <hr>
    </article>
    <article class="article">3
      <hr>
      <hr>
      <hr>
    </article>

    <article class="article">6
      <hr>
      <hr>
      <hr>
    </article>
     <article class="article">7
      <hr>
      <hr>
      <hr>
      <hr>
      <hr>
    </article>
  
    
    <article class="article">17
      <hr>
      <hr>
      <hr>
      <hr>
      <hr>
      <hr>
    </article>
    
     <article class="article">4
      <hr>
      <hr>
      <hr>
    </article>
    
      <article class="article">2
      <hr>
      <hr>
      <hr>
      <hr>
      <hr>
    </article>
   
    <article class="article">12
      <hr class="image"><hr>
      <hr>
      <hr>
    </article>
   
  </div>
</div>
</body>
</html>