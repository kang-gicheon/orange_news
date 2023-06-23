package article;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class ArticleDAO {
	private PreparedStatement pstmt;
	private PreparedStatement pstmt1;
	private PreparedStatement pstmt2;
	private PreparedStatement pstmt3;
	private PreparedStatement pstmt4;
	private PreparedStatement pstmt5;
	private PreparedStatement pstmt0;

	private Connection conn;
	private Connection conn2;
	private DataSource dataFactory;

	private int rcount = 0;

	public ArticleDAO() {
		try {
			Context ctx = new InitialContext();
			Context envCtx = (Context) ctx.lookup("java:/comp/env");
			dataFactory = (DataSource) envCtx.lookup("jdbc/oracle");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public List<ArticleVO> selectAllArticles() {
		System.out.println("selectAllArticles 들어옴");
		List<ArticleVO> articlesList = new ArrayList<ArticleVO>();

		try {
			conn = dataFactory.getConnection();
			String query = "SELECT title, content, articlenum,type, reccount, hotissue,img, id "
					+ " FROM ARTICLE ORDER by ARTICLENUM DESC";

			System.out.println(query);
			pstmt = conn.prepareStatement(query);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				String title = rs.getString("title");
				String content = rs.getString("content");
				int articleNum = rs.getInt("articlenum");
				int type = rs.getInt("type");
				int reccount = rs.getInt("reccount");
				int hotissue = rs.getInt("hotissue");
				String imageFileName = rs.getString("img");
				String id = rs.getString("id");

				ArticleVO article = new ArticleVO();
				article.setTitle(title);
				article.setContent(content);
				article.setArticlenum(articleNum);
				article.setType(type);
				article.setRecCount(reccount);
				article.setHotissue(hotissue);
				article.setImgFileName(imageFileName);
				article.setId(id);
				articlesList.add(article);

			}

			rs.close();
			pstmt.close();
			conn.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return articlesList;
	}

	public List<ArticleVO> selectHotAllArticles() {
		System.out.println("selectHotAllArticles 들어옴");
		List<ArticleVO> articlesHotList = new ArrayList<ArticleVO>();

		try {
			conn = dataFactory.getConnection();
			String query = "SELECT title, content, articlenum, type, reccount, hotissue, img, id "
					+ " FROM ARTICLE ORDER by reccount DESC";

			pstmt = conn.prepareStatement(query);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				String title = rs.getString("title");
				String content = rs.getString("content");
				int articleNum = rs.getInt("articlenum");
				int type = rs.getInt("type");
				int reccount = rs.getInt("reccount");
				int hotissue = rs.getInt("hotissue");
				String imageFileName = rs.getString("img");
				String id = rs.getString("id");

				ArticleVO article = new ArticleVO();
				article.setTitle(title);
				article.setContent(content);
				article.setArticlenum(articleNum);
				article.setType(type);
				article.setRecCount(reccount);
				article.setHotissue(hotissue);
				article.setImgFileName(imageFileName);
				article.setId(id);
				articlesHotList.add(article);
			}

			rs.close();
			pstmt.close();
			conn.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return articlesHotList;
	}

	public List<ArticleVO> selectReactArticles() {
		System.out.println("selectReactArticles 메소드 들어옴");
		List<ArticleVO> articlesList = new ArrayList<ArticleVO>();

		try {
			conn = dataFactory.getConnection();

			String query = "SELECT articlenum, rcount FROM REACT WHERE r_type=? "
					+ "and rcount = (SELECT max(rcount) FROM REACT WHERE r_type=?) ";
			String query2 = "SELECT title, id, img FROM ARTICLE WHERE articlenum=?";

			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, "좋아요");
			pstmt.setString(2, "좋아요");
			pstmt2 = conn.prepareStatement(query);
			pstmt2.setString(1, "훈훈해요");
			pstmt2.setString(2, "훈훈해요");
			pstmt3 = conn.prepareStatement(query);
			pstmt3.setString(1, "슬퍼요");
			pstmt3.setString(2, "슬퍼요");
			pstmt4 = conn.prepareStatement(query);
			pstmt4.setString(1, "화나요");
			pstmt4.setString(2, "화나요");
			pstmt5 = conn.prepareStatement(query);
			pstmt5.setString(1, "후속기사원해요");
			pstmt5.setString(2, "후속기사원해요");

			ResultSet rs = pstmt.executeQuery();
			ResultSet rs2 = pstmt2.executeQuery();
			ResultSet rs3 = pstmt3.executeQuery();
			ResultSet rs4 = pstmt4.executeQuery();
			ResultSet rs5 = pstmt5.executeQuery();

			if (rs.next()) { // 좋아요
				int articlenum = rs.getInt("articlenum");
				int rcount = rs.getInt("rcount");
				rs.close();

				ArticleVO article = new ArticleVO();
			
				

				pstmt0 = conn.prepareStatement(query2);
				pstmt0.setInt(1, articlenum);

				ResultSet rs0 = pstmt0.executeQuery();
				rs0.next();
				String title = rs0.getString("title");
				String id = rs0.getString("id");
				String img = rs0.getString("img");
				
				article.setArticlenum(articlenum);
				article.setRcount(rcount);
				article.setActype("좋아요");
				article.setTitle(title);
				article.setId(id);
				article.setImgFileName(img);
				articlesList.add(article);
				System.out.println("1"+articlesList);
				rs0.close();

			}

			if (rs2.next()) { // 훈훈해요
				int articlenum = rs2.getInt("articlenum");
				rcount = rs2.getInt("rcount");
				rs2.close();

				ArticleVO article2 = new ArticleVO();
				
				
				pstmt0 = conn.prepareStatement(query2);
				pstmt0.setInt(1, articlenum);

				ResultSet rs0 = pstmt0.executeQuery();
				rs0.next();
				String title = rs0.getString("title");
				String id = rs0.getString("id");
				String img = rs0.getString("img");
				
				article2.setArticlenum(articlenum);
				article2.setRcount(rcount);
				article2.setActype("훈훈해요");
				article2.setTitle(title);
				article2.setId(id);
				article2.setImgFileName(img);
				
				
				articlesList.add(article2);
				System.out.println("2"+articlesList);
				rs0.close();
			}

			if (rs3.next()) {// 슬퍼요
				int articlenum = rs3.getInt("articlenum");
				rcount = rs3.getInt("rcount");
				rs3.close();

				ArticleVO article3 = new ArticleVO();
				
				pstmt0 = conn.prepareStatement(query2);
				pstmt0.setInt(1, articlenum);

				ResultSet rs0 = pstmt0.executeQuery();
				rs0.next();
				String title = rs0.getString("title");
				String id = rs0.getString("id");
				String img = rs0.getString("img");
				
				article3.setTitle(title);
				article3.setId(id);
				article3.setImgFileName(img);
				
				article3.setActype("슬퍼요");
				article3.setArticlenum(articlenum);
				article3.setRcount(rcount);
				articlesList.add(article3);
				System.out.println("3"+articlesList);
				rs0.close();
			}

			if (rs4.next()) { // 화나요
				int articlenum = rs4.getInt("articlenum");
				rcount = rs4.getInt("rcount");
				rs4.close();

				ArticleVO article4 = new ArticleVO();
				
				
				pstmt0 = conn.prepareStatement(query2);
				pstmt0.setInt(1, articlenum);

				ResultSet rs0 = pstmt0.executeQuery();
				rs0.next();
				String title = rs0.getString("title");
				String id = rs0.getString("id");
				String img = rs0.getString("img");
				
				
				article4.setActype("화나요");
				article4.setTitle(title);
				article4.setId(id);
				article4.setImgFileName(img);
				article4.setArticlenum(articlenum);
				article4.setRcount(rcount);
				articlesList.add(article4);
				System.out.println("4"+articlesList);
				rs0.close();
			}

			if (rs5.next()) { // 후속기사원해요
				int articlenum = rs5.getInt("articlenum");
				rcount = rs5.getInt("rcount");
				rs5.close();

				ArticleVO article5 = new ArticleVO();
				
				
				pstmt0 = conn.prepareStatement(query2);
				pstmt0.setInt(1, articlenum);

				ResultSet rs0 = pstmt0.executeQuery();
				rs0.next();
				String title = rs0.getString("title");
				String id = rs0.getString("id");
				String img = rs0.getString("img");
				
				
				article5.setActype("후속기사원해요");
				article5.setTitle(title);
				article5.setId(id);
				article5.setImgFileName(img);
				article5.setArticlenum(articlenum);
				article5.setRcount(rcount);
				articlesList.add(article5);
				System.out.println("5"+articlesList);
				rs0.close();

			}

			pstmt.close();
			pstmt2.close();
			pstmt3.close();
			pstmt4.close();
			pstmt5.close();
			conn.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("반응" + articlesList);
		return articlesList;
	}

	public void insertNewArticle(ArticleVO article) {

		try {

			System.out.println("insertNewArticle메소드");
			conn = dataFactory.getConnection();
			String title = article.getTitle();
			String content = article.getContent();
			int type = article.getType();
			int hotissue = article.getHotissue();
			String img = article.getImgFileName();
			// String id = article.getId();

			String query = "INSERT INTO ARTICLE (title, writedate, updatedate, content, articlenum, type, reccount, hotissue, img, id)"
					+ " values(?, sysdate, sysdate, ?, seq_anum.nextval, ?, 0, ?, ?, 'reporter1')";

			// String query2 = "SELECT articlenum FROM ARTICLE WHERE title =?";

			System.out.println(query);
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, title);
			pstmt.setString(2, content);
			pstmt.setInt(3, type);
			pstmt.setInt(4, hotissue);
			pstmt.setString(5, img);
			pstmt.executeUpdate();
			pstmt.close();

//			PreparedStatement pstmt2 = conn.prepareStatement(query2);
//			pstmt2.setString(1, title);
//			ResultSet rs = pstmt.executeQuery();
//			while(rs.next()) {
//				articlenum = rs.getInt("articlenum");
//			}
//			
//			
//			pstmt2.close();
//			rs.close();

			conn.close();

			System.out.println("으아아아아아아아아아");
			conn2 = dataFactory.getConnection();
			int articlenum = 0;
			String check = "SELECT articlenum FROM ARTICLE WHERE title =?";
			PreparedStatement ps = conn2.prepareStatement(check);
			ps.setString(1, title);
			ResultSet rsrs = ps.executeQuery();
			while (rsrs.next()) {
				articlenum = rsrs.getInt("articlenum");
			}

			rsrs.close();
			ps.close();

			System.out.println("반응 넣기 메소드");
			conn2 = dataFactory.getConnection();
			System.out.println(articlenum + " <= articlenum");

			String query2 = "INSERT INTO REACT (r_type, rcount, articlenum) values(?,0,?) ";
			pstmt1 = conn2.prepareStatement(query2);
			pstmt1.setString(1, "좋아요");
			pstmt1.setInt(2, articlenum);
			pstmt1.executeUpdate();
			pstmt1.close();

			pstmt2 = conn2.prepareStatement(query2);
			pstmt2.setString(1, "훈훈해요");
			pstmt2.setInt(2, articlenum);
			pstmt2.executeUpdate();
			pstmt2.close();

			pstmt3 = conn2.prepareStatement(query2);
			pstmt3.setString(1, "슬퍼요");
			pstmt3.setInt(2, articlenum);
			pstmt3.executeUpdate();
			pstmt3.close();

			pstmt4 = conn2.prepareStatement(query2);
			pstmt4.setString(1, "화나요");
			pstmt4.setInt(2, articlenum);
			pstmt4.executeUpdate();
			pstmt4.close();

			pstmt5 = conn2.prepareStatement(query2);
			pstmt5.setString(1, "후속기사원해요");
			pstmt5.setInt(2, articlenum);
			pstmt5.executeUpdate();
			pstmt5.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public ArticleVO selectArticle(ArticleVO article) {
		try {
			conn = dataFactory.getConnection();
			String query = "SELECT title, writedate, content, articlenum, type, reccount, hotissue, img, id"
					+ " from ARTICLE where articlenum=?";
			System.out.println(query);
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, article.getArticlenum());
			ResultSet rs = pstmt.executeQuery();
			rs.next();
			String title = rs.getString("title");
			Date writedate = rs.getDate("writedate");
			String content = rs.getString("content");
			int type = rs.getInt("type");
			int reccount = rs.getInt("reccount");
			int hotissue = rs.getInt("hotissue");
			String imageFileName = rs.getString("img");
			String id = rs.getString("id");

			article.setTitle(title);
			article.setContent(content);
			article.setType(type);
			article.setRecCount(reccount);
			article.setHotissue(hotissue);
			article.setImgFileName(imageFileName);
			article.setId(id);
			article.setWritedate(writedate);
			rs.close();
			pstmt.close();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return article;
	}

	public ArticleVO viewReact(ArticleVO article) {
		try {
			conn = dataFactory.getConnection();
			String query = "SELECT r_type, rcount from REACT where articlenum=?";
			System.out.println(query);
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, article.getArticlenum());
			ResultSet rs = pstmt.executeQuery();
			rs.next();
			String r_type = rs.getString("r_type");
			int rcount = rs.getInt("rcount");

			article.setActype(r_type);
			article.setRcount(rcount);
			article.setArticlenum(article.getArticlenum());

			rs.close();
			pstmt.close();
			conn.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return article;
	}

	public ArticleVO updateReact(ArticleVO article) {
		System.out.println("updateReact 메소드 들어옴");
		String r_type = article.getActype();
		int articlenum = article.getArticlenum();

		try {
			conn = dataFactory.getConnection();
			String query = "SELECT rcount FROM REACT WHERE r_type=? and articlenum = ?";
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, r_type);
			pstmt.setInt(2, articlenum);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				rcount = rs.getInt("rcount"); // rcount 받아옴
				System.out.println(r_type + "의 현재 개수 : " + rcount);
			}

			rcount = rcount + 1;

			conn2 = dataFactory.getConnection();
			String query2 = "UPDATE REACT SET rcount=? WHERE r_type=? and articlenum=? ";
			pstmt2 = conn2.prepareStatement(query2);
			pstmt2.setInt(1, rcount);
			pstmt2.setString(2, r_type);
			pstmt2.setInt(3, articlenum);
			pstmt2.executeUpdate();

			article.setActype(r_type);
			article.setRcount(rcount);
			article.setArticlenum(articlenum);

			pstmt2.close();
			conn2.close();
			pstmt.close();
			conn.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return article;
	}

	public ArticleVO updateReccount(ArticleVO article) {
		System.out.println("updateReccount 메소드 들어옴");
		int articlenum = article.getArticlenum();

		try {
			conn = dataFactory.getConnection();
			String query = "SELECT reccount FROM ARTICLE WHERE articlenum=?";
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, articlenum);
			ResultSet rs = pstmt.executeQuery();
			rs.next();
			int rrcount = rs.getInt("reccount");
			System.out.println(rrcount + "reccount 현재 개수");

			rrcount = rrcount + 1;

			conn2 = dataFactory.getConnection();
			String query2 = "UPDATE ARTICLE SET reccount =? WHERE articlenum =?";
			pstmt2 = conn2.prepareStatement(query2);
			pstmt2.setInt(1, rrcount);
			pstmt2.setInt(2, articlenum);
			pstmt2.executeUpdate();

			article.setRecCount(rrcount);
			article.setArticlenum(articlenum);

			pstmt2.close();
			pstmt.close();
			conn2.close();
			conn.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return article;
	}

	public ArticleVO HDLarticle() { // 헤드라인 기사 뽑기

		ArticleVO article = new ArticleVO();
		int articlenum = 0;

		try {
			conn = dataFactory.getConnection();
			String query = "SELECT max(articlenum) FROM ARTICLE where hotissue=1";
			pstmt = conn.prepareStatement(query);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				articlenum = rs.getInt("max(articlenum)");
			}

			System.out.println(articlenum + "<= 헤드라인 기사 번호");

			conn2 = dataFactory.getConnection();

			String query2 = "SELECT title, img, type FROM ARTICLE WHERE articlenum = ?";
			pstmt2 = conn2.prepareStatement(query2);
			pstmt2.setInt(1, articlenum);
			ResultSet rs2 = pstmt2.executeQuery();

			rs2.next();
			String title = rs2.getString("title");
			String imageFileName = rs2.getString("img");
			int type = rs2.getInt("type");

			article.setTitle(title);
			article.setImgFileName(imageFileName);
			article.setType(type);

			rs.close();
			rs2.close();
			pstmt.close();
			pstmt2.close();
			conn.close();
			conn2.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return article;
	}
}
