<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>

<style>

body{
		padding: 0;
		margin: 0;
		font-family: Helvetica, Arial, sans-serif;
	}	
	#topbar{
		width: 1000px;
		margin:0 auto;
		height: 80px;
	}
	
	#empty1{
		margin-top 8px;
		width: 300px;
		height: 1px;
		float: left;
	}
	
	#logoimage{
		margin-top: 8px; 
	}
	
	#menu-bar-container{
		background-color:#BB1919;
		width: 100%;
		height: 70px; 
	}
	#menu-bar{
		width: 1000px;
		margin: auto;
	}
	h1{
		padding: 0;
		margin: 0;
		color: white;
		font-size: 40px;
		font-weight: normal;
		padding-top: 10px;
		float: left;
	}
	#loginbox{
		float: right;
		border: 1px solid #884545;
		width: 190px;
		margin-top: 15px;
		padding: 5px 0 0 5px;
		
	}

	#loginbox a{
		color: white;
		text-decoration: none;
		font-size: 20px;
		position: relative;
		top: -13px;
	}
	#location-img{
		width: 40px;
		height: 30px;
		margin: 0;
	}


		#menu-bar-2{
			background-color: #A91717;
			
		}
		#menubar-text{
			width: 1000px;
			margin: auto;
			height: 40px;
		}

		#menubar-text a{
			color: white;
			text-decoration: none;
			border: 2px soild blue;
			padding: 0 10px;
			border-right: 0.5px solid #BB4545;
			position: relative;
			top: 9px;
		}
		#menubar-text a:hover{
			text-decoration: underline;
		}
		#no-border{
			border:none !important;
			margin-top: 50px;
		}

		#page-container{
			width: 1000px;
			margin: 0 auto;
		}
		#article-container{
			float: left;
			width: 700px;
			border-right: 1px solid #DBDBDB;
			margin-right: 20px;
		}
		}
		h2{
			font-weight: normal;
			font-size: 20px;
			border-bottom: 3px solid #BB1919;
			width: 100px;
		}
		h3 a{
			font-size: 36px;
			color: black;
			text-decoration: none;
		}
		h3 a:hover{
			color: #1167A8;
		}

		#article-summary{
			width: 40%;
			float: left;
			margin-right: 15px;
			color: #5a5a5a;
		}
		#article-summary img{
			position: relative;
			top: 2px;
		}
		#topic-link{
			text-decoration: none;
			color :#BB1919;
			border-left: 1px solid #CCCCCC;
			padding-left: 5px;
		}
		#article-hr{
			border-color: #BB1919;
		}
		.article-link{
			text-decoration: none;
			color: black;

		}
		#teac-row-2{
			margin-top: 10px;
		}
		#article-summary-1{
			width: 250px;
			float: left;
			margin-right: 5px;
			border-right: 1px solid #DBDBDB;
		}
		#article-summary-2{
			width: 250px;
			float: left;
			margin-right: 5px;
			margin-left: 5px;
			border-right: 1px solid #DBDBDB;
		}
		#article-summary-3{
			width: 150px;
			float: left;
			margin-left: 10px;
		}
		.article-summary-3-hr{
			border-color: #DBDBDB;
		}
		
		#watch-listen{
			margin-left: 20px;
		}
		#loginbox{
			height: 60px;
		}
		#hotissue{
			position: absoulte;
		}
		h4{
			font-weight: normal;
			font-size: 20px;
		}
		p{
			color: #9D9D9D;
			font-size: 14px;
		}
		#watch-listen img{
			float: left;
			margin-right: 7px;
		}

</style>

<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

<!-- TOP Bar -->
	<div id="topbar">
		<div id="empty1">
		
		</div>
		
		<div id="logo">
			<a href="#"><img id="logoimage" src="#">로고이미지</a> <!-- 로고 -->
		</div>
		
	</div>
	<!-- Clear the right and left float so then they not intrapt in next divesion -->
	
	<!-- Menu Bar -->
	<div id="menu-bar-container">
		<div id="menu-bar">
			<h1>NEWS</h1>
			<div id="loginbox">
				<a href=#>로그인</a>
			</div>
		</div>
	</div>
	<!-- Manu Bar 2 -->
	<div id="menu-bar-2">
		<div id="menubar-text">
			<a href="#">Home</a>
			<!-- <a href="#">Video</a>
			<a href="#">World</a>
			<a href="#">Asia</a>
			<a href="#">UK</a>
			<a href="#">Business</a>
			<a href="#">Tech</a>
			<a href="#">Science</a>
			<a href="#">Magazine</a>
			<a href="#">Entertainment & Arts</a>
			<a href="#">Health</a>
			<a id="no-border" href="#">More
				<img id="more-arrow" src="https://www.materialui.co/materialIcons/navigation/arrow_drop_down_white_192x192.png">
			</a> -->
		</div>  
	</div>

	<div class="clear"></div>
	<!-- Page Container Starts from here. -->
	<div id="page-container">
	<!-- left page container -->
		<div id="main-containr">
			<!-- Main Headin Strats from here -->
			<h2>Technology</h2>

			<div id="article-container">
				<h3><a href="#">FCC votes to end net neutrality rules</h3></a>
			
				<div id="article-summary">
					<p>Critics say changing the rules may mean some data travels on "fast lanes".</p>
					<p>
					<img src="https://cdn0.iconfinder.com/data/icons/feather/96/clock-128.png" height="15" width="15">
					19 May 2017 <a id="topic-link" href="#">Technology</a></p>
					
					<hr id="article-hr" width="50" align="left">
					
					<a class="article-link" href="#"><p>FCC website 'targeted by DDoS attack'.</p></a>
					
					<a class="article-link" href="#"><p>'Bots' spam FCC over net neutrality</p></a>
				</div>
			
				<img src="https://ichef-1.bbci.co.uk/news/410/cpsprodpb/E963/production/_90974795_mediaitem90974794.jpg" width="400">
			
				<div class="clear"></div>
				<hr width="690" align="left">
			
				<!-- Nes in second row in left colom. -->
				<div id="tech-row-2">
					<div id="article-summary-1">	
						<img src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcShVK8wzC9L_512hATO2uGzAO50Kab_CyuBq94PzLsHJ-UnZU4C7w" width="230">
						<h4>French robot Tech Tent: Les robots arrivent</h4>
						<p>	This week the radio show's focus falls on France and its high-tech ambitions</p>
						<p>Rory Cellan-Jones <br>
						Technology <br> correspondent</p>
						<p><img src="https://cdn0.iconfinder.com/data/icons/feather/96/clock-128.png" height="15" width="15">
						19 May 2017</p>
 						<p>Global manhunt for WannaCry creators.</p>
						<p>French candidates in last push for votes</p>
					</div>

					<div id="article-summary-2">	
						<img src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcShVK8wzC9L_512hATO2uGzAO50Kab_CyuBq94PzLsHJ-UnZU4C7w" width="230">
						<h4>French robot Tech Tent: Les robots arrivent</h4>
						<p>	This week the radio show's focus falls on France and its high-tech ambitions</p>
					.	<p>Rory Cellan-Jones <br>
						Technology <br> correspondent</p>
						<p><img src="https://cdn0.iconfinder.com/data/icons/feather/96/clock-128.png" height="15" width="15">
						19 May 2017</p>
 						<p>Global manhunt for WannaCry creators.</p>
						<p>French candidates in last push for votes</p>
					</div>

					<div id="article-summary-3">	
						<div id="article-summary-3-1">
						<h4>The five big announcements from Google I/O</h4>
						<p><img src="https://cdn0.iconfinder.com/data/icons/feather/96/clock-128.png" height="15" width="15">
						19 May 2017 <a id="topic-link" href="#">Technology</a></p>
						</div>

						<hr align="center" class="article-summary-3-hr">

						<div id="article-summary-3-2">
						<h4>UK illegal content 'sanctions' threat</h4>
						<p><img src="https://cdn0.iconfinder.com/data/icons/feather/96/clock-128.png" height="15" width="15">
						19 May 2017 <a id="topic-link" href="#">Technology</a></p>
						</div>

						<hr align="center" class="article-summary-3-hr">

						<div id="article-summary-3-3">
 						<h4>UK illegal content 'sanctions' threat</h4>
						<p><img src="https://cdn0.iconfinder.com/data/icons/feather/96/clock-128.png" height="15" width="15">
						19 May 2017 <a id="topic-link" href="#">Technology</a></p>
						</div>
					</div>
				</div>	

			</div>
		</div>

<!-- Right Colom -->
		<div id="watch-listen">
			
			
			<div id="hotissue">
				<h4>핫이슈</h4>
				<img src="#" width="75" height="60">
				<h5>Virtual prison fence to deter drones
				<p><img src="https://cdn0.iconfinder.com/data/icons/feather/96/clock-128.png" height="15" width="15">
				19 May 2017 <a id="topic-link" href="#">Technology</a></p>
				</h5>
				
				<img src=# width="75" height="60">
				<h5>가나다라마바사아자ㅏㅊ카타타타탙타타타ㅏ타타ㅏㅏㅏ라나안아</h5>
			</div>
			
			
		</div>
		
			
	</div>

</body>
</html>