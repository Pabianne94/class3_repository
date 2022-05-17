<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>company/history.jsp</title>
<link href="../css/default.css" rel="stylesheet" type="text/css">
<link href="../css/subpage.css" rel="stylesheet" type="text/css">
</head>
<body>
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
			<h1>그는 맛있는 것이 먹고싶다.</h1>
			<figure class="food">
				<img src="../images/company/food/food_rs (1).jpg" width="150" height="150">
				<img src="../images/company/food/food_rs (2).jpg" width="150" height="150">
				<img src="../images/company/food/food_rs (3).jpg" width="150" height="150">
				<img src="../images/company/food/food_rs (4).jpg" width="150" height="150">
				<img src="../images/company/food/food_rs (5).jpg" width="150" height="150">
				<img src="../images/company/food/food_rs (6).jpg" width="150" height="150">
				<img src="../images/company/food/food_rs (7).jpg" width="150" height="150">
				<img src="../images/company/food/food_rs (8).jpg" width="150" height="150">
				<img src="../images/company/food/food_rs (9).jpg" width="150" height="150">
				<img src="../images/company/food/food_rs (10).jpg" width="150" height="150">
				<img src="../images/company/food/food_rs (11).jpg" width="150" height="150">
				<img src="../images/company/food/food_rs (12).jpg" width="150" height="150">
				<figcaption><부산 수업 들으러 다니면서 먹은 음식들></figcaption>
			</figure>
			<p><br>
			그가 원하는 맛은 '괜찮네' 가 아닌 '와 맛있네!' <br>
			'진짜' 맛집을 찾아서 점심시간에 음식점을 찾아 헤맸지만... <br>
			코로나 이슈로 인한 잠정 중단.
			</p>
		</article>


		<div class="clear"></div>
		<!-- 푸터 들어가는곳 -->
		<jsp:include page="../inc/bottom.jsp" />
		<!-- 푸터 들어가는곳 -->
	</div>
</body>
</html>