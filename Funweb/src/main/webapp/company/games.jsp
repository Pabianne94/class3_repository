<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>company/games.jsp</title>
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
			<h1>코딩하는 종찬(27)씨의 삶의 낙?</h1>
			<figure class="pubg">
				<img src="../images/company/pubg.png" width="600" height="300">
				<figcaption>배틀그라운드 최근 전적</figcaption>
			</figure>
			<figure class="la">
				<img src="../images/company/lostark.png" width="600" height="300">
				<figcaption>로스트아크 최종 컨텐츠 정복</figcaption>
			</figure>
			<p>코딩러의 삶을 살기로 결정한 종찬씨... <br>
			하지만 그런 그에게도 유일한 취미가 있었으니 그것은 게임! <br>
			주말마다 한두판씩 하는 배틀그라운드와 RPG 로스트아크가 유일한 삶의 낙이다.
			</p>
		</article>


		<div class="clear"></div>
		<!-- 푸터 들어가는곳 -->
		<jsp:include page="../inc/bottom.jsp" />
		<!-- 푸터 들어가는곳 -->
	</div>
</body>
</html>