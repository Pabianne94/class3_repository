<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%
//세션 객체 초기화(invalidate())를 수행 후 메인페이지로 이동
session.invalidate();

// 세션의 특정 속성을 지움 (위와 같음)
// session.removeAttribute("sId");

response.sendRedirect("../main/main.jsp");
%>