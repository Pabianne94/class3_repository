package news;

import static db.JdbcUtil.close;
import static db.JdbcUtil.getConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class NewsDAO {
	Connection con;
	PreparedStatement pstmt;
	PreparedStatement pstmt2;
	ResultSet rs;

	public int insertNews(NewsBean news) {
		int insertCount = 0;
		int num = 1; // 새 글 번호를 저장할 변수, 초기값 1

		try {
			con = getConnection();

			String sql = "SELECT MAX(num) FROM news";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();

			if (rs.next()) { // 등록된 게시물이 하나라도 있을 경우
				num = rs.getInt(1) + 1;
			}

			sql = "INSERT INTO news VALUES (?,?,?,?,?,now(),0)";
			pstmt2 = con.prepareStatement(sql);
			pstmt2.setInt(1, num);
			pstmt2.setString(2, news.getName());
			pstmt2.setString(3, news.getPass());
			pstmt2.setString(4, news.getSubject());
			pstmt2.setString(5, news.getContent());

			insertCount = pstmt2.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("SQL 구문 오류 - insertNews");
		} finally {
			close(rs);
			close(pstmt);
			close(con);
		}

		return insertCount;
	}

	public ArrayList<NewsBean> selectNewsList(int pageNum, int listLimit) {
		ArrayList<NewsBean> newsList = null;

		con = getConnection();

		int startRow = (pageNum - 1) * listLimit;

		try {
			String sql = "SELECT * FROM news ORDER BY num DESC LIMIT ?,?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, startRow);
			pstmt.setInt(2, listLimit);


			rs = pstmt.executeQuery();

			newsList = new ArrayList<NewsBean>();


			while (rs.next()) {

				NewsBean news = new NewsBean();

				news.setNum(rs.getInt("num"));
				news.setName(rs.getString("name"));
				news.setPass(rs.getString("pass"));
				news.setSubject(rs.getString("subject"));
				news.setContent(rs.getString("content"));
				news.setDate(rs.getDate("date"));
				news.setReadcount(rs.getInt("readcount"));

				newsList.add(news);

			}

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("SQL 구문 오류 - selectNewsList");
		} finally {
			close(rs);
			close(pstmt);
			close(con);
		}

		return newsList;
	}

	public int selectListCount() {
		int listCount = 0;

		con = getConnection();

		try {
			String sql = "SELECT COUNT(num) FROM news";
			pstmt = con.prepareStatement(sql);

			rs = pstmt.executeQuery();

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

	public NewsBean selectNews(int num) {
		NewsBean news = null;

		con = getConnection();

		try {
			String sql = "SELECT * FROM news WHERE num=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, num);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				news = new NewsBean();
				news.setNum(rs.getInt("num"));
				news.setName(rs.getString("name"));
				news.setPass(rs.getString("pass"));
				news.setSubject(rs.getString("subject"));
				news.setContent(rs.getString("content"));
				news.setDate(rs.getDate("date"));
				news.setReadcount(rs.getInt("readcount"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("SQL 구문 오류 - selectNews");
		} finally {
			close(rs);
			close(pstmt);
			close(con);
		}

		return news;
	}

	public void updateReadcount(int num) {

		con = getConnection();

		try {
			String sql = "UPDATE news SET readcount=readcount+1 WHERE num=?";
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

	public int updateNews(NewsBean news) {
		int updateCount = 0;

		con = getConnection();

		try {
			String sql = "SELECT pass FROM news WHERE num=?"; // 패스워드 조회 후 별도로
																// 번호 조회
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, news.getNum());

			rs = pstmt.executeQuery();

			if (rs.next()) { // 조회 결과가 존재할 경우 패스워드 판별 작업 수행
				if (rs.getString("pass").equals(news.getPass())) { // 패스워드 일치 시

					// 2. 수정(UPDATE) 작업
					// >> 번호에 해당하는 작성자, 제목, 내용 수정
					sql = "UPDATE news SET name=?, subject=?, content=? WHERE num=?";
					pstmt2 = con.prepareStatement(sql);
					pstmt2.setString(1, news.getName());
					pstmt2.setString(2, news.getSubject());
					pstmt2.setString(3, news.getContent());
					pstmt2.setInt(4, news.getNum());

					updateCount = pstmt2.executeUpdate();
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("SQL 구문 오류 - updateNews");
		} finally {
			close(rs);
			close(pstmt);
			close(pstmt2);
			close(con);
		}

		return updateCount;
	}

	public int deleteNews(int num, String pass) {
		int deleteCount = 0;

		con = getConnection();

		try {
			String sql = "SELECT pass FROM news WHERE num=?";

			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, num);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				if (rs.getString("pass").equals(pass)) {
					// DELETE 작업
					sql = "DELETE FROM news WHERE num=?";
					pstmt2 = con.prepareStatement(sql);
					pstmt2.setInt(1, num);

					deleteCount = pstmt2.executeUpdate();
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("SQL 구문 오류 - deleteNews");
		} finally {
			close(rs);
			close(pstmt);
			close(pstmt2);
			close(con);
		}

		return deleteCount;
	}

	public ArrayList<NewsBean> selectRecentNewsList() {
		ArrayList<NewsBean> newsList = null;

		con = getConnection();

		try {
			String sql = "SELECT * FROM news ORDER BY num DESC limit 5";
			pstmt = con.prepareStatement(sql);

			rs = pstmt.executeQuery();

			newsList = new ArrayList<NewsBean>();

			while (rs.next()) {
				NewsBean news = new NewsBean();

				// BoardBean 객체에 조회된 1개 레코드 정보를 모두 저장
				news.setNum(rs.getInt("num"));
				news.setName(rs.getString("name"));
				news.setPass(rs.getString("pass"));
				news.setSubject(rs.getString("subject"));
				news.setContent(rs.getString("content"));
				news.setDate(rs.getDate("date"));
				news.setReadcount(rs.getInt("readcount"));

				// 전체 레코드를 저장하는 ArrayList 객체에 1개 레코드가 저장된 BoardBean 객체 추가
				newsList.add(news);
			}

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("SQL 구문 오류 - selectRecentNewsList");
		}

		return newsList;
	}

	// 검색어에 해당하는 게시물 목록 갯수를 조회하는 selectListCount
	// 파라미터 : 검색어(search), 리턴타입 : int(listCount)
	public int selectSearchListCount(String search, String searchType) {
		int listCount = 0;

		con = getConnection();

		try {
			String sql = "SELECT COUNT(num) FROM news WHERE " + searchType
					+ " LIKE ?";
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

	public ArrayList<NewsBean> selectSearchNewsList(int pageNum,
			int listLimit, String search, String searchType) {
		ArrayList<NewsBean> newsList = null;

		con = getConnection();

		// 현재 페이지에서 불러올 목록(레코드)의 첫번째(시작) 행번호 계산
		int startRow = (pageNum - 1) * listLimit;

		// board 테이블의 모든 레코드 조회(글번호-num 기준으로 내림차순 정렬)
		// >> SELECT 컬럼명 FROM 테이블명 ORDER BY 정렬할컬럼명 정렬방식;
		// (정렬 방식 - 오름차순 : ASC, 내림차순 : DESC)
		// SELECT 컬럼명 FROM 테이블명 LIMIT 시작행번호, 목록갯수;
		// (pageNum - 1) * listLimit
		try {
			String sql = "SELECT * FROM news WHERE " + searchType
					+ " LIKE ? ORDER BY num DESC LIMIT ?,?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, "%" + search + "%");
			pstmt.setInt(2, startRow);
			pstmt.setInt(3, listLimit);

			// >> 목록갯수는 파라미터로 전달받은 listLimit값 사용
			// >> 시작행번호는 (페이지번호 - 1) * 목록갯수값 사용

			// 4단계. SQL 구문 실행 및 결과 처리
			rs = pstmt.executeQuery();

			newsList = new ArrayList<NewsBean>();

			// 다음레코드가 존재할 동안 반복하면서
			// 1개 레코드 정보를 BoardBean 객체에 저장 후
			// 다시 BoardBean 객체를 전체 레코드에 저장하는 ArrayList<BoardBean> 객체에 추가
			while (rs.next()) {
				// 1개 레코드를 저장할 BoardBean 객체 생성
				NewsBean news = new NewsBean();
				// BoardBean 객체에 조회된 1개 레코드 정보를 모두 저장
				news.setNum(rs.getInt("num"));
				news.setName(rs.getString("name"));
				news.setPass(rs.getString("pass"));
				news.setSubject(rs.getString("subject"));
				news.setContent(rs.getString("content"));
				news.setDate(rs.getDate("date"));
				news.setReadcount(rs.getInt("readcount"));

				// 전체 레코드를 저장하는 ArrayList 객체에 1개 레코드가 저장된 BoardBean 객체 추가
				newsList.add(news);

			}

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("SQL 구문 오류 - selectNewsList");
		} finally {
			close(rs);
			close(pstmt);
			close(con);
		}

		// 전체 레코드를 저장할 ArrayList<BoardBean> 객체 생성
		// >> 주의! 반복문 시작 전에 미리 생성해야함.

		return newsList;
	}

}
