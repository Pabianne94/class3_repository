<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>company/newsroom.jsp</title>
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
			<h1>충격! 박종찬(27)씨 코로나 확진!</h1>
			<figure class="corona">
				<img src="../images/company/corona.jpg" width="350" height="600">
				<figcaption>양성 선고</figcaption>
			</figure>
			<p>집-학원-집-학원만 반복하던 종찬씨... <br>
			도저히 참지 못하고 딱 한번 동전노래방에 방문! <br>
			그 이후 증상을 보이더니.. 결국 코로나 확진! <br>
			노래방에서 걸렸다는 확증은 없지만 정말 의심스럽다. <br>
			</p>
		</article>


		<div class="clear"></div>
		<!-- 푸터 들어가는곳 -->
		<jsp:include page="../inc/bottom.jsp" />
		<!-- 푸터 들어가는곳 -->
	</div>
</body>
</html>