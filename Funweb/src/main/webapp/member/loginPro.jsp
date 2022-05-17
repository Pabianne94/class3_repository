<%@page import="member.MemberDAO"%>
<%@page import="member.MemberBean"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%
String id = request.getParameter("id");
String pass = request.getParameter("pass");

MemberBean member = new MemberBean();
member.setId(id);
member.setPass(pass);

// MemberDAO 객체 생성 후 checkUser() 메소드를 호출하여 로그인 여부 판별 요청
// >> 파라미터 : MemberBean 객체, 리턴타입 : boolean(isLoginSuccess)
MemberDAO memberDAO = new MemberDAO();
boolean isLoginSuccess = memberDAO.checkUser(member);

// 로그인 판별 결과에 따른 처리
// >> 성공(isLoginSuccess가 true)시 세션 객체에 "sId" 속성으로 아이디 저장 후 메인페이지로 포워딩
// >> 실패 시 자바스크립트를 통해 "아이디 또는 패스워드 틀림" 출력 후 이전페이지로 돌아가기

if (isLoginSuccess) {
	session.setAttribute("sId", id);
	response.sendRedirect("../main/main.jsp");
} else {
%>
<script>
	alert("아이디 또는 패스워드 틀림!")
	history.back();
</script>
<%
}
%>
<!DOCTYPE html>