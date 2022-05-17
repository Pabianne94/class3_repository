<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%
request.setCharacterEncoding("UTF-8");
String id = request.getParameter("id");
String isDuplicate = request.getParameter("duplicate");

String roadPost = request.getParameter("roadAddress"); // 도로명주소
String jibunPost = request.getParameter("jibunAddress"); // 지번
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>member/join.jsp</title>
<link href="../css/default.css" rel="stylesheet" type="text/css">
<link href="../css/subpage.css" rel="stylesheet" type="text/css">
<script type="text/javascript">
	// 아이디 중복확인 여부와 패스워드 확인 결과, 패스워드 검사를 저장할 전역변수 선언
	var checkRetypePassResult = false, checkIdResult = false, checkPassResult = false;
	
	/* function checkPass(pass) {
		var regex = /[A-za-z0-9!@#$%^&*]{4~16}/;
		if(regex==false){
			alert("비밀번호 규칙 확인 필수!");
			document.fr.pass.focus();
		}
	} */
	
	function checkPass(pass) {
		var spanElem = document.getElementById("checkPassResult");
		var legnthRegex = /^[A-Za-z0-9!@#$%]{8,16}$/;
		var engUpperRegex = /[A-Z]/;
		var engLowerRegex = /[a-z]/;
		var numRegex = /[0-9]/;
		var specRegex = /[!@#$%]/;
		var count=0;
		
		
		
	/*	if(lengthRegex.exec(pass)) {
		 	spanElem.innerHTML = "사용 가능한 패스워드";
			spanElem.style.color = "GREEN";
			checkPassResult = true;
		} else {
			spanElem.innerHTML = "사용 불가능한 패스워드";
			spanElem.style.color = "RED";
			checkPassResult = false;
		} */
		if(engUpperRegex.exec(pass)) count++;
		if(engLowerRegex.exec(pass)) count++;
		if(numRegex.exec(pass)) count++;
		if(specRegex.exec(pass)) count++;
		
		switch(count) {
			case 4 :
				spanElem.innerHTML="사용 가능한 패스워드(안전)";
				spanElem.style.color = "GREEN";
				checkPassResult = true;
				break;
			case 3 :
				spanElem.innerHTML="사용 가능한 패스워드(보통)";
				spanElem.style.color = "YELLOW";
				checkPassResult = true;
				break;
			case 2 :
				spanElem.innerHTML="사용 가능한 패스워드(위험)";
				spanElem.style.color = "ORANGE";
				checkPassResult = true;
				break;
			default: 
					spanElem.innerHTML="영문자, 숫자, 특수문자 중 2가지 이상 조합 필수!";
				spanElem.style.color = "RED";
				checkPassResult = false;
		}
	}

	function checkRetypePass(pass2) {
		var pass = document.fr.pass.value;
		var spanElem = document.getElementById("checkRetypePassResult");

		if (pass == pass2) {
			spanElem.innerHTML = "패스워드 일치";
			spanElem.style.color = "GREEN";
			checkRetypePassResult = true;
		} else {
			spanElem.innerHTML = "패스워드 불일치";
			spanElem.style.color = "RED";
			checkRetypePassResult = false;
		}
	}

	function openCheckIdWindow() {
		window.open("check_id.jsp", "", "width=400, height=250");
	}
	
	function openPostCodeWindow() {
		window.open("post_code.jsp", "", "width=800, height=250");
	}
	
	

	
	function checkSubmit() {
		/* 	if (isDuplicate != null && isDuplicate.equals("true") && pass == pass2) {
				return true;
			} else {
				return false;
			} */
			
		// 아이디 중복 확인 작업을 통해 아이디를 입력받았고, >> != null
		// 정규표현식을 통해 패스워드 규칙이 올바르고(checkPassResult == true),
		// 패스워드 확인을 통해 두 패스워드가 동일하면 true 리턴, 아니면 false 리턴
		// 다른 친구들 활용하기? ..숙제
		if(checkIdResult == false) { // 아이디 중복확인을 수행하지 않았을 경우
			// alert() 함수를 통해 "중복 확인 필수!" 메세지 출력 후 아이디 창에 포커스 요청
			alert("아이디 중복확인 필수!");
			document.fr.id.focus();
			return false; // 현재 함수 종료
		} else if(checkRetypePassResult == false) { // 패스워드 확인을 수행하지 않았을 경우
			// alert() 함수를 통해 "패스워드 확인 필수!" 메세지 출력 후 아이디 창에 포커스 요청
			alert("패스워드 확인 필수!");
			document.fr.pass2.focus();
			return false; // 현재 함수 종료
		}
	}
</script>
</head>
<body>
	<div id="wrap">
		<!-- 헤더 들어가는곳 -->
		<jsp:include page="../inc/top.jsp"></jsp:include>
		<!-- 헤더 들어가는곳 -->

		<!-- 본문들어가는 곳 -->
		<!-- 본문 메인 이미지 -->
		<div id="sub_img_member"></div>
		<!-- 왼쪽 메뉴 -->
		<nav id="sub_menu">
			<ul>
				<li><a href="#">Join us</a></li>
				<li><a href="#">Privacy policy</a></li>
			</ul>
		</nav>
		<!-- 본문 내용 -->
		<article>
			<h1>Join Us</h1>
			<form action="joinPro.jsp" method="post" id="join" name="fr"
			onsubmit="return checkSubmit()">
				<fieldset>
					<legend>Basic Info</legend>
					<label>User Id</label> 
					<input type="text" name="id" class="id" id="id" 
					readonly required placeholder="중복체크 버튼 클릭"> 
					<input type="button" value="dup. check" class="dup" id="btn" 
					onclick="openCheckIdWindow()" ><br>
					
					<label>Password</label> 
					<input type="password" name="pass" id="pass" required 
					placeholder="영문, 숫자, 특수문자 8~16글자" onkeyup="checkPass(this.value)">
					<span id="checkPassResult"></span><br>
			<label>Retype Password</label> 
					<input type="password" name="pass2" required onkeyup="checkRetypePass(this.value)">
					<span id="checkRetypePassResult"> 
					</span><br>
					<!-- 패스워드 일치 여부 -->
					<!-- pass2 항목에 텍스트 입력할 때마다 자바스크립트의 checkPass 함수 호출 -->
					<!-- 함수에서 pass와 pass2를 비교하여 일치하면 초록색, 일치하지 않으면 빨간색 표시 -->
					
					<label>Name</label> <input type="text" name="name" id="name" required><br> 
					
					<label>E-Mail</label> <input type="email" name="email" id="email" required><br> 
					<label>Retype E-Mail</label> <input type="email" name="email2" required><br>
				</fieldset>
				
				<fieldset>
		  			<legend>Optional</legend>
		  			<label>Post code</label>
		  			<input type="text" name="post_code" class="post_code" id="post_code"
		  			readonly placeholder="검색 버튼 클릭">
		  			<input type="button" value="search" class="dup" 
		  			onclick="openPostCodeWindow()"><br>
		  			<label>Address</label>
		  			<input type="text" name="address" >
		  			<input type="text" name="address2" placeholder="상세주소 입력"><br>
		  			<label>Phone Number</label>
		  			<input type="text" name="phone" ><br>
		  			<label>Mobile Phone Number</label>
		  			<input type="text" name="mobile" ><br>
		  		</fieldset>

				<div class="clear"></div>
				<div id="buttons">
					<input type="submit" value="Submit" class="submit"> 
					<input type="reset" value="Cancel" class="cancel">
				</div>
			</form>
		</article>


		<div class="clear"></div>
		<!-- 푸터 들어가는곳 -->
		<jsp:include page="../inc/bottom.jsp"></jsp:include>
		<!-- 푸터 들어가는곳 -->
	</div>
</body>
</html>


