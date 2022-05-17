<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>company/welcome.jsp</title>
<link href="../css/default.css" rel="stylesheet" type="text/css">
<link href="../css/subpage.css" rel="stylesheet" type="text/css">
</head>
<body>
	<div id="wrap">
		<!-- 헤더 들어가는곳 -->
		<jsp:include page="../inc/top.jsp" />
		<!-- 헤더 들어가는곳 -->

		<!-- 본문들어가는 곳 -->
		<!-- 본문 메인 이미지 -->
		<div id="sub_img"></div>
		<!-- 왼쪽 메뉴 -->
		<nav id="sub_menu">
			<ul>
				<li><a href="../company/welcome.jsp">Welcome</a></li>
				<li><a href="../company/history.jsp">History</a></li>
				<li><a href="../company/newsroom.jsp">Newsroom</a></li>
				<li><a href="../company/games.jsp">Games</a></li>
			</ul>
		</nav>
		<!-- 본문 내용 -->
		<article>
			<h1>그는 일이 하고싶다.</h1>
			<figure class="jcp">
				<img src="../images/company/박종찬35.jpg" width="350" height="400">
				<figcaption>수업듣는 박종찬(27)씨</figcaption>
			</figure>
			<p>할 줄 아는 독일어 : Guten Tag <br>
			어떻게든 국립대를 가려고 생전 들어보지도 못한 독문과를 선택한 종찬씨 <br>
			이 길은 아닌 것을 1학년때 깨닫고 이것저것 해봤지만 전부 흐지부지.. <br>
			결국 중간에 잠깐 들었었던 컴공과 전공수업의 기억을 되살려서 국비 코딩 수업에 지원! <br>
			나름 ㅈ빠지게 뛰어서 따라는 가고 있는 것 같지만.... 과연 그의 미래는?
			</p>
		</article>


		<div class="clear"></div>
		<!-- 푸터 들어가는곳 -->
		<jsp:include page="../inc/bottom.jsp" />
		<!-- 푸터 들어가는곳 -->
	</div>
</body>
</html>


