<%@page import="board.BoardDAO"%>
<%@page import="board.BoardBean"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%
request.setCharacterEncoding("UTF-8");

BoardBean board = new BoardBean();
board.setName(request.getParameter("name"));
board.setPass(request.getParameter("pass"));
board.setSubject(request.getParameter("subject"));
board.setContent(request.getParameter("content"));

// BoardDAO 객체의 insertBoard() 메소드를 호출하여 게시물 등록 작업 요청
// >> 파라미터 : BoardBean 객체, 리턴타입 : int(insertCount)
BoardDAO dao = new BoardDAO();

int insertCount = dao.insertBoard(board);

// 게시물 등록 작업 요청 결과 처리
// >> 성공 시 notice.jsp 페이지로 포워딩
// >> 실패 시 자바스크립트 사용하여 "글쓰기 실패!" 출력 후 이전페이지로 돌아가기
if (insertCount > 0) {
	response.sendRedirect("notice.jsp");
} else {
%>
<script>
	alert("글쓰기 실패!");
	history.back();
</script>
<%
}
%>

