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
	
	private Connection conn;
	private Connection conn2;
	private DataSource dataFactory;
	
	private int rcount =0;
	
	public ArticleDAO() {
		try {
			Context ctx = new InitialContext();
			Context envCtx = (Context) ctx.lookup("java:/comp/env");
			dataFactory = (DataSource) envCtx.lookup("jdbc/oracle");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	//자바는 자동 커밋으로 알고 있는데 꼭 필요한 메서드인지 확인 요망
	// -> 필요하더라도 재사용되는 데가 하나 밖에 없는 데 꼭 메서드로 만들 필요가 있는지 논의 필요
	public void commitString() {
		try {
			conn = dataFactory.getConnection();
			String query = "commit";
			pstmt = conn.prepareStatement(query);
			pstmt.executeUpdate();
			System.out.println("커밋 함");
			
			pstmt.close();
			conn.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public List<ArticleVO> selectAllArticles(){
		System.out.println("selectAllArticles 들어옴");
		List<ArticleVO> articlesList = new ArrayList<ArticleVO>();
		
		try {
			conn=dataFactory.getConnection();
			String query = "SELECT title, content, articlenum,type, reccount, hotissue,img, id "+
			" FROM ARTICLE ORDER by ARTICLENUM DESC";
			
			System.out.println(query);
			pstmt = conn.prepareStatement(query);
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()) {
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
			
			
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		
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
		//	String id = article.getId();
			
			String query = "INSERT INTO ARTICLE (title, writedate, updatedate, content, articlenum, type, reccount, hotissue, img, id)"
					+ " values(?, sysdate, sysdate, ?, seq_anum.nextval, ?, 0, ?, ?, 'reporter1')";
			
		//	String query2 = "SELECT articlenum FROM ARTICLE WHERE title =?";
			
			System.out.println(query);
			pstmt=conn.prepareStatement(query);
			pstmt.setString(1, title);
			pstmt.setString(2, content);
			pstmt.setInt(3, type);
			pstmt.setInt(4, hotissue);
			pstmt.setString(5,img);
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
			conn2=dataFactory.getConnection();
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
			System.out.println(articlenum +" <= articlenum");
			
			
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
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public ArticleVO selectArticle(ArticleVO article) {
		try {
			conn=dataFactory.getConnection();
			String query ="SELECT title, writedate, content, articlenum, type, reccount, hotissue, img, id"
					+" from ARTICLE where articlenum=?";
			System.out.println(query);
			pstmt=conn.prepareStatement(query);
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
		}catch(Exception e) {
			e.printStackTrace();
		}
		return article;
	}
	
	public ArticleVO viewReact(ArticleVO article) {
		try {
			conn=dataFactory.getConnection();
			String query = "SELECT r_type, rcount from REACT where articlenum=?";
			System.out.println(query);
			pstmt= conn.prepareStatement(query);
			pstmt.setInt(1, article.getArticlenum());
			ResultSet rs  = pstmt.executeQuery();
			rs.next();
			String r_type = rs.getString("r_type");
			int rcount = rs.getInt("rcount");
			
			article.setActype(r_type);
			article.setRcount(rcount);
			article.setArticlenum(article.getArticlenum());
			
			rs.close();
			pstmt.close();
			conn.close();
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		return article;
	}
	
	//이거 어차피 insertNewArticle() 메서드에서 실행되는 쿼리문과 동일한데 필요한 메서드인지 확인 요망
	public void firstAddReact(ArticleVO article) {
		
		try {
			System.out.println("반응 넣기 메소드");
			conn = dataFactory.getConnection();
			System.out.println(article.getArticlenum() +" <= articlenum");
			int articlenum = article.getArticlenum();
			
			String query = "INSERT INTO REACT (r_type, rcount, articlenum) values(?,0,?) ";
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, "좋아요");
			pstmt.setInt(2, articlenum);
			pstmt.executeUpdate();
			pstmt.close();
			
			pstmt2 = conn.prepareStatement(query);
			pstmt2.setString(1, "훈훈해요");
			pstmt2.setInt(2, articlenum);
			pstmt2.executeUpdate();
			pstmt2.close();
			
			pstmt3 = conn.prepareStatement(query);
			pstmt3.setString(1, "슬퍼요");
			pstmt3.setInt(2, articlenum);
			pstmt3.executeUpdate();
			pstmt3.close();
			
			pstmt4 = conn.prepareStatement(query);
			pstmt4.setString(1, "화나요");
			pstmt4.setInt(2, articlenum);
			pstmt4.executeUpdate();
			pstmt4.close();
			
			pstmt5 = conn.prepareStatement(query);
			pstmt5.setString(1, "후속 기사 원해요");
			pstmt5.setInt(2, articlenum);
			pstmt5.executeUpdate();
			pstmt5.close();
			
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public ArticleVO updateReact (ArticleVO article) {
		System.out.println("updateReact 메소드 들어옴");
		String r_type = article.getActype();
		int articlenum =article.getArticlenum();
		
//		int rcount = 0;
//		try {
//		String query = "SELECT rcount FROM REACT where R_TYPE = ?";
//		pstmt= conn.prepareStatement(query);
//		pstmt.setString(1, r_type);
//		ResultSet rs = pstmt.executeQuery();
//		while(rs.next()) {
//			rcount = rs.getInt("rcount");
//		}
//		
		try {
			conn = dataFactory.getConnection();
			String query = "SELECT rcount FROM REACT WHERE r_type=? and articlenum = ?";
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, r_type);
			pstmt.setInt(2, articlenum);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				rcount = rs.getInt("rcount"); //rcount 받아옴
				System.out.println(r_type+"의 현재 개수 : "+rcount);
			}
			
			rcount=rcount+1;
			
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
			
			commitString();
		
			pstmt2.close();
			conn2.close();
			pstmt.close();
			conn.close();
		
		} catch(Exception e) {
			e.printStackTrace();
		}
		return article;
	}
}
