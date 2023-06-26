package reply;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class replyDAO {
	private PreparedStatement pstmt;
	private DataSource dataFactory;

	public replyDAO() {
		try {
			Context ctx = new InitialContext();
			Context envContext = (Context) ctx.lookup("java:/comp/env");
			dataFactory = (DataSource) envContext.lookup("jdbc/oracle");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public List<replyVO> showList(String articleNum, String type) {

		List<replyVO> list = new ArrayList<>();

		try {
			Connection con = dataFactory.getConnection();
			//type은 정렬 타입
			// 상위 타입 댓글들만 select
			if (type.equals("0")) {
				// 인기순 정렬
				String query = "select ID, rcomment, articlenum, good, bad, parentNum, renum, wdate "
						+ " from reply where articlenum = ? and parentNum = 0 order by good-bad desc";

				pstmt = con.prepareStatement(query);

				int artNum = Integer.parseInt(articleNum);
				System.out.println(artNum);
				pstmt.setInt(1, artNum);

			} else if (type.equals("1")) {
				// 최신순 정렬
				String query = "select ID, rcomment, articlenum, good, bad, parentNum, renum, wdate "
						+ " from reply where articlenum = ? and parentNum = 0 order by wdate desc";

				pstmt = con.prepareStatement(query);

				int artNum = Integer.parseInt(articleNum);
				System.out.println(artNum);
				pstmt.setInt(1, artNum);

			} else {
				// 내 댓글만 보기
				String query = "select ID, rcomment, articlenum, good, bad, parentNum, renum, wdate "
						+ " from reply where articlenum = ? and id = ? and parentNum = 0 order by wdate desc";

				pstmt = con.prepareStatement(query);

				int artNum = Integer.parseInt(articleNum);
				System.out.println(artNum);
				pstmt.setInt(1, artNum);
				pstmt.setString(2, type);

			}

			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				replyVO vo = new replyVO();

				vo.setUserID(rs.getString("ID"));
				vo.setContents(rs.getString("rcomment"));
				vo.setArticleNum(rs.getInt("articlenum"));
				vo.setGood(rs.getInt("good"));
				vo.setBad(rs.getInt("bad"));
				vo.setParentNum(rs.getInt("parentNum"));
				vo.setReplyNum(rs.getInt("renum"));
				vo.setDate(rs.getString("wdate"));
				System.out.println(rs.getString("ID"));
				list.add(vo);
			}

			rs.close();
			pstmt.close();
			con.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;

	}

	public int addReply0(String articleNum, String id, String comment) {

		try {
			Connection con = dataFactory.getConnection();

			// 상위 타입 댓글들만 select
			String query = "INSERT INTO REPLY values (reply_SEQ.nextval, 0 ,  to_char(sysdate,'YYYY/MM/DD HH24:MI:SS'), ? , 0, 0, ?, ?)";

			pstmt = con.prepareStatement(query);

			int artNum = Integer.parseInt(articleNum);
			System.out.println(artNum);

			pstmt.setString(1, comment);
			pstmt.setString(2, id);
			pstmt.setInt(3, artNum);

			pstmt.executeUpdate();

			pstmt.close();
			con.close();

		} catch (Exception e) {
			e.printStackTrace();
			return 1;
		}

		return 0;

	}

	public int addReply1(String articleNum, String replyNum, String id, String comment) {

		try {
			Connection con = dataFactory.getConnection();

			// 상위 타입 댓글들만 select
			String query = "INSERT INTO REPLY values (reply_SEQ.nextval , ? ,  to_char(sysdate,'YYYY/MM/DD HH24:MI:SS'), ? , 0, 0, ?, ?)";

			pstmt = con.prepareStatement(query);

			int artNum = Integer.parseInt(articleNum);
			int parentNum = Integer.parseInt(replyNum);
			
			pstmt.setInt(1, parentNum);
			pstmt.setString(2, comment);
			pstmt.setString(3, id);
			pstmt.setInt(4, artNum);

			pstmt.executeUpdate();

			pstmt.close();
			con.close();

		} catch (Exception e) {
			e.printStackTrace();
			return 1;
		}

		return 0;

	}

	public int getGood(String reNum) {

		try {
			Connection con = dataFactory.getConnection();

			// 상위 타입 댓글들만 select
			String query = "update reply set good = good + 1 where renum = ? ";

			pstmt = con.prepareStatement(query);

			int replyNum = Integer.parseInt(reNum);

			pstmt.setInt(1, replyNum);

			pstmt.executeUpdate();

			pstmt.close();
			con.close();

		} catch (Exception e) {
			e.printStackTrace();
			return 1;
		}

		return 0;

	}

	public int getBad(String reNum) {
		try {
			Connection con = dataFactory.getConnection();

			// 상위 타입 댓글들만 select
			String query = "update reply set bad = bad + 1 where renum = ? ";

			pstmt = con.prepareStatement(query);

			int replyNum = Integer.parseInt(reNum);

			pstmt.setInt(1, replyNum);

			pstmt.executeUpdate();

			pstmt.close();
			con.close();

		} catch (Exception e) {
			e.printStackTrace();
			return 1;
		}

		return 0;
	}

	public List<replyVO> showListType1(String articleNum, String parentNum) {
		List<replyVO> list = new ArrayList<>();

		try {
			Connection con = dataFactory.getConnection();

			// 상위 타입 댓글들만 select
			// 인기순 정렬
			String query = "select ID, rcomment, articlenum, good, bad, parentNum, renum, wdate "
					+ " from reply where articlenum = ? and parentNum = ? order by wdate desc";

			pstmt = con.prepareStatement(query);

			int artNum = Integer.parseInt(articleNum);
			int pNum = Integer.parseInt(parentNum);
			pstmt.setInt(1, artNum);
			pstmt.setInt(2, pNum);
			System.out.println("부모 댓글 번호 : " + pNum);

			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				replyVO vo = new replyVO();

				vo.setUserID(rs.getString("ID"));
				vo.setContents(rs.getString("rcomment"));
				vo.setArticleNum(rs.getInt("articlenum"));
				vo.setParentNum(rs.getInt("parentNum"));
				vo.setReplyNum(rs.getInt("renum"));
				vo.setDate(rs.getString("wdate"));
				
				list.add(vo);
			}

			
			System.out.println(list);
			rs.close();
			pstmt.close();
			con.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;

	}

	public int deleteType0(String reNum) {
		
		try {
			
			Connection con = dataFactory.getConnection();

			// 상위 타입 댓글들만 select
			String query = "delete from reply where renum = ? or parentNum = ?";

			pstmt = con.prepareStatement(query);

			int replyNum = Integer.parseInt(reNum);

			System.out.println(replyNum);
			pstmt.setInt(1, replyNum);
			pstmt.setInt(2, replyNum);

			pstmt.executeUpdate();

			pstmt.close();
			con.close();

		} catch (Exception e) {
			e.printStackTrace();
			return 1;
		}

		return 0;
	}

	public int deleteType1(String reNum, String pNum) {
try {
			
			Connection con = dataFactory.getConnection();

			// 상위 타입 댓글들만 select
			String query = "delete from reply where renum = ? and parentNum = ?";

			pstmt = con.prepareStatement(query);

			int replyNum = Integer.parseInt(reNum);
			int parentNum = Integer.parseInt(pNum);

			System.out.println(replyNum);
			pstmt.setInt(1, replyNum);
			pstmt.setInt(2, parentNum);

			pstmt.executeUpdate();

			pstmt.close();
			con.close();

		} catch (Exception e) {
			e.printStackTrace();
			return 1;
		}

		return 0;
	}
}
