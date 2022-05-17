<%@page import="news.NewsDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%
request.setCharacterEncoding("UTF-8");

int num = Integer.parseInt(request.getParameter("num"));
String pageNum = request.getParameter("page");
String pass = request.getParameter("pass");

// BoardDAO 객체의 deleteBoard 메소드를 호출하여 게시물 삭제 작업 요청
NewsDAO dao = new NewsDAO();

// >> 파라미터 : 글번호(num), 패스워드(pass), 리턴타입 : int(deleteCount)
int deleteCount = dao.deleteNews(num, pass);

// >> 패스워드를 조회하여 패스워드가 일치할 경우에만 레코드 삭제




if (deleteCount > 0) {
	response.sendRedirect("news_main.jsp?page=" + pageNum);
} else {
%>
<script>
	alert("글 삭제 실패!");
	history.back();
</script>
<%
}
%>