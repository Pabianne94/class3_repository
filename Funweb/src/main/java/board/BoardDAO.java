package board;

// static import : 특정 클래스의 static 메소드를 클래스명 없이 접근하기 위한 import
// <기본 문법> import static 패키지명.클래스명.메소드명;
// 		   or  import static 패키지명.클래스명.메소드명;
import static db.JdbcUtil.close;
import static db.JdbcUtil.getConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class BoardDAO {
	Connection con;
	PreparedStatement pstmt;
	PreparedStatement pstmt2;
	ResultSet rs;

	public int insertBoard(BoardBean board) {
		int insertCount = 0;
		int num = 1; // 새 글 번호를 저장할 변수(초기값 1)

		// JdbcUtil 클래스의 getConnection 메소드를 호출하여 Connection 객체 가져오기
		try {
			con = getConnection();

			// 3단계. SQL 구문 작성 및 전달
			// 새 글 번호(num) 계산
			// 현재 게시물들 중 가장 큰 num값을 조회하여 조회 결과가 없을 경우 num = 1
			// 아니면 조회된 num값 + 1을 수행한 값을 num으로 설정
			String sql = "SELECT MAX(num) FROM board";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();



			// rs.next 메소드를 통해 다음 레코드 존재 여부 확인
			// >> 다음 레코드가 존재하는 경우 기존 게시물이 있다는 의미이므로
			// 조회된 값(num의 최대값) + 1을 num 변수에 저장
			if (rs.next()) { // 등록된 게시물이 하나라도 존재할 경우(= 최대값이 조회될 경우)
				num = rs.getInt(1) + 1;
			}

			// 2) 글 쓰기 작업 수행(글번호는 num, 작성일은 now, 조회수는 0)
			sql = "INSERT INTO board VALUES (?,?,?,?,?,now(),0)";
			pstmt2 = con.prepareStatement(sql);
			pstmt2.setInt(1, num);
			pstmt2.setString(2, board.getName());
			pstmt2.setString(3, board.getPass());
			pstmt2.setString(4, board.getSubject());
			pstmt2.setString(5, board.getContent());

			insertCount = pstmt2.executeUpdate();

			// 4단계. SQL 구문 실행 및 결과 처리
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("SQL 구문 오류 - insertBoard");
		} finally {
			close(rs);
			close(pstmt);
			close(con);
		}

		return insertCount;
	}

	// 게시물 목록 조회작업을 수행하는 selectBoardList
	// >> 파라미터 : 현재 페이지 번호(pageNum), 표시할 목록 갯수(listLimit)
	// >> 리턴타입 : ArrayList<BoardBean>(boardList)
	public ArrayList<BoardBean> selectBoardList(int pageNum, int listLimit) {
		ArrayList<BoardBean> boardList = null;

		con = getConnection();

		// 현재 페이지에서 불러올 목록(레코드)의 첫번째(시작) 행번호 계산
		int startRow = (pageNum - 1) * listLimit;

		// board 테이블의 모든 레코드 조회(글번호-num 기준으로 내림차순 정렬)
		// >> SELECT 컬럼명 FROM 테이블명 ORDER BY 정렬할컬럼명 정렬방식;
		// (정렬 방식 - 오름차순 : ASC, 내림차순 : DESC)
		// SELECT 컬럼명 FROM 테이블명 LIMIT 시작행번호, 목록갯수;
		// (pageNum - 1) * listLimit
		try {
			String sql = "SELECT * FROM board ORDER BY num DESC LIMIT ?,?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, startRow);
			pstmt.setInt(2, listLimit);

			// >> 목록갯수는 파라미터로 전달받은 listLimit값 사용
			// >> 시작행번호는 (페이지번호 - 1) * 목록갯수값 사용

			// 4단계. SQL 구문 실행 및 결과 처리
			rs = pstmt.executeQuery();

			boardList = new ArrayList<BoardBean>();

			// 다음레코드가 존재할 동안 반복하면서
			// 1개 레코드 정보를 BoardBean 객체에 저장 후
			// 다시 BoardBean 객체를 전체 레코드에 저장하는 ArrayList<BoardBean> 객체에 추가
			while (rs.next()) {
				// 1개 레코드를 저장할 BoardBean 객체 생성
				BoardBean board = new BoardBean();
				// BoardBean 객체에 조회된 1개 레코드 정보를 모두 저장
				board.setNum(rs.getInt("num"));
				board.setName(rs.getString("name"));
				board.setPass(rs.getString("pass"));
				board.setSubject(rs.getString("subject"));
				board.setContent(rs.getString("content"));
				board.setDate(rs.getDate("date"));
				board.setReadcount(rs.getInt("readcount"));

				// 전체 레코드를 저장하는 ArrayList 객체에 1개 레코드가 저장된 BoardBean 객체 추가
				boardList.add(board);

			}

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("SQL 구문 오류 - selectBoardList");
		} finally {
			close(rs);
			close(pstmt);
			close(con);
		}

		// 전체 레코드를 저장할 ArrayList<BoardBean> 객체 생성
		// >> 주의! 반복문 시작 전에 미리 생성해야함.



		return boardList;
	}

	public int selectListCount() {
		int listCount = 0;

		con = getConnection();

		try {
			String sql = "SELECT COUNT(num) FROM board";
			pstmt = con.prepareStatement(sql);

			// 4단계. SQL 구문 실행 및 결과 처리
			rs = pstmt.executeQuery();

			// 조회된 결과값의 첫번째 값(1번 인덱스)을 listCount 변수에 저장
			if (rs.next()) {
				listCount = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("SQL 구문 오류 - selectListCount");
		} finally {
			close(rs);
			close(pstmt);
			close(con);
		}

		return listCount;
	}

	public BoardBean selectBoard(int num) {
		BoardBean board = null;

		con = getConnection();

		try {
			String sql = "SELECT * FROM board WHERE num=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, num);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				// BoardBean 객체 생성 후 조회된 컬럼 데이터를 저장
				board = new BoardBean();
				board.setNum(rs.getInt("num"));
				board.setName(rs.getString("name"));
				board.setPass(rs.getString("pass"));
				board.setSubject(rs.getString("subject"));
				board.setContent(rs.getString("content"));
				board.setDate(rs.getDate("date"));
				board.setReadcount(rs.getInt("readcount"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("SQL 구문 오류 - selectBoard");
		} finally {
			close(rs);
			close(pstmt);
			close(con);
		}

		return board;
	}

	public void updateReadcount(int num) {

		con = getConnection();

		try {
			String sql = "UPDATE board SET readcount=readcount+1 WHERE num=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, num);
			pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("SQL 구문 오류 - updateReadcount");
		} finally {
			close(pstmt);
			close(con);
		}
	}

	public int updateBoard(BoardBean board) {
		int updateCount = 0;

		con = getConnection();

		try {
			String sql = "SELECT pass FROM board WHERE num=?"; // 패스워드 조회 후 별도로
																// 번호 조회
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, board.getNum());

			rs = pstmt.executeQuery();

			if (rs.next()) { // 조회 결과가 존재할 경우 패스워드 판별 작업 수행
				if (rs.getString("pass").equals(board.getPass())) { // 패스워드 일치 시

					// 2. 수정(UPDATE) 작업
					// >> 번호에 해당하는 작성자, 제목, 내용 수정
					sql = "UPDATE board SET name=?, subject=?, content=? WHERE num=?";
					pstmt2 = con.prepareStatement(sql);
					pstmt2.setString(1, board.getName());
					pstmt2.setString(2, board.getSubject());
					pstmt2.setString(3, board.getContent());
					pstmt2.setInt(4, board.getNum());

					updateCount = pstmt2.executeUpdate();
				}
			}


		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("SQL 구문 오류 - updateBoard");
		} finally {
			close(rs);
			close(pstmt);
			close(pstmt2);
			close(con);
		}

		return updateCount;
	}

	public int deleteBoard(int num, String pass) {
		int deleteCount = 0;

		con = getConnection();

		try {
			String sql = "SELECT pass FROM board WHERE num=?";

			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, num);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				if (rs.getString("pass").equals(pass)) {
					// DELETE 작업
					sql = "DELETE FROM board WHERE num=?";
					pstmt2 = con.prepareStatement(sql);
					pstmt2.setInt(1, num);

					deleteCount = pstmt2.executeUpdate();
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("SQL 구문 오류 - deleteBoard");
		} finally {
			close(rs);
			close(pstmt);
			close(pstmt2);
			close(con);
		}

		return deleteCount;
	}

	public ArrayList<BoardBean> selectRecentBoardList() {
		ArrayList<BoardBean> boardList = null;

		con = getConnection();

		try {
			String sql = "SELECT * FROM board ORDER BY num DESC limit 5";
			pstmt = con.prepareStatement(sql);

			rs = pstmt.executeQuery();

			boardList = new ArrayList<BoardBean>();

			while (rs.next()) {
				BoardBean board = new BoardBean();

				// BoardBean 객체에 조회된 1개 레코드 정보를 모두 저장
				board.setNum(rs.getInt("num"));
				board.setName(rs.getString("name"));
				board.setPass(rs.getString("pass"));
				board.setSubject(rs.getString("subject"));
				board.setContent(rs.getString("content"));
				board.setDate(rs.getDate("date"));
				board.setReadcount(rs.getInt("readcount"));

				// 전체 레코드를 저장하는 ArrayList 객체에 1개 레코드가 저장된 BoardBean 객체 추가
				boardList.add(board);
			}

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("SQL 구문 오류 - selectRecentBoardList");
		}

		return boardList;
	}
	
	// 검색어에 해당하는 게시물 목록 갯수를 조회하는 selectListCount
	// 파라미터 : 검색어(search), 리턴타입 : int(listCount)
	public int selectSearchListCount(String search, String searchType) {
		int listCount = 0;

		con = getConnection();

		try {
			String sql = "SELECT COUNT(num) FROM board WHERE " + searchType + " LIKE ?";
			pstmt = con.prepareStatement(sql);
			// 검색어 생성을 위해서는 검색 키워드 앞뒤로 "%" 문자열 결합 필요
			pstmt.setString(1, "%" + search + "%");

			// 4단계. SQL 구문 실행 및 결과 처리
			rs = pstmt.executeQuery();

			// 조회된 결과값의 첫번째 값(1번 인덱스)을 listCount 변수에 저장
			if (rs.next()) {
				listCount = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("SQL 구문 오류 - selectListCount");
		} finally {
			close(rs);
			close(pstmt);
			close(con);
		}

		return listCount;
	}

	public ArrayList<BoardBean> selectSearchBoardList(int pageNum, int listLimit,
			String search, String searchType) {
		ArrayList<BoardBean> boardList = null;

		con = getConnection();

		// 현재 페이지에서 불러올 목록(레코드)의 첫번째(시작) 행번호 계산
		int startRow = (pageNum - 1) * listLimit;

		// board 테이블의 모든 레코드 조회(글번호-num 기준으로 내림차순 정렬)
		// >> SELECT 컬럼명 FROM 테이블명 ORDER BY 정렬할컬럼명 정렬방식;
		// (정렬 방식 - 오름차순 : ASC, 내림차순 : DESC)
		// SELECT 컬럼명 FROM 테이블명 LIMIT 시작행번호, 목록갯수;
		// (pageNum - 1) * listLimit
		try {
			String sql = "SELECT * FROM board WHERE " + searchType + " LIKE ? ORDER BY num DESC LIMIT ?,?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, "%" + search + "%");
			pstmt.setInt(2, startRow);
			pstmt.setInt(3, listLimit);

			// >> 목록갯수는 파라미터로 전달받은 listLimit값 사용
			// >> 시작행번호는 (페이지번호 - 1) * 목록갯수값 사용

			// 4단계. SQL 구문 실행 및 결과 처리
			rs = pstmt.executeQuery();

			boardList = new ArrayList<BoardBean>();

			// 다음레코드가 존재할 동안 반복하면서
			// 1개 레코드 정보를 BoardBean 객체에 저장 후
			// 다시 BoardBean 객체를 전체 레코드에 저장하는 ArrayList<BoardBean> 객체에 추가
			while (rs.next()) {
				// 1개 레코드를 저장할 BoardBean 객체 생성
				BoardBean board = new BoardBean();
				// BoardBean 객체에 조회된 1개 레코드 정보를 모두 저장
				board.setNum(rs.getInt("num"));
				board.setName(rs.getString("name"));
				board.setPass(rs.getString("pass"));
				board.setSubject(rs.getString("subject"));
				board.setContent(rs.getString("content"));
				board.setDate(rs.getDate("date"));
				board.setReadcount(rs.getInt("readcount"));

				// 전체 레코드를 저장하는 ArrayList 객체에 1개 레코드가 저장된 BoardBean 객체 추가
				boardList.add(board);

			}

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("SQL 구문 오류 - selectBoardList");
		} finally {
			close(rs);
			close(pstmt);
			close(con);
		}

		// 전체 레코드를 저장할 ArrayList<BoardBean> 객체 생성
		// >> 주의! 반복문 시작 전에 미리 생성해야함.



		return boardList;
	}
}

