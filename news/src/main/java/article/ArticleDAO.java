package article;

import java.sql.Blob;
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
	private Connection conn;
	private DataSource dataFactory;
	
	public ArticleDAO() {
		try {
			Context ctx = new InitialContext();
			Context envCtx = (Context) ctx.lookup("java:/comp/env");
			dataFactory = (DataSource) envCtx.lookup("jdbc/oracle");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public List<ArticleVO> selectAllArticles(){
		System.out.println("selectAllArticles 들어옴");
		List<ArticleVO> articlesList = new ArrayList<ArticleVO>();
		
		try {
			conn=dataFactory.getConnection();
			String query = "SELECT title, content, articlenum,type, reccount, hotissue, id "+
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
				String id = rs.getString("id");
				
				ArticleVO article = new ArticleVO();
				article.setTitle(title);
				article.setContent(content);
				article.setArticlenum(articleNum);
				article.setType(type);
				article.setRecCount(reccount);
				article.setHotissue(hotissue);
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
			String img = article.getimgFileName();
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
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public ArticleVO selectArticle(int articlenum) {
		ArticleVO article = new ArticleVO();
		try {
			conn=dataFactory.getConnection();
			String query ="SELECT title, writedate, content, articlenum, type, reccount, hotissue, img, id"
					+" from ARTICLE where articlenum=?";
			System.out.println(query);
			pstmt=conn.prepareStatement(query);
			pstmt.setInt(1, articlenum);
			ResultSet rs = pstmt.executeQuery();
			rs.next();
			String title = rs.getString("title");
			Date writedate = rs.getDate("writedate");
			String content = rs.getString("content");
			int articlenum2= rs.getInt("articlenum");
			String imageFileName = rs.getString("img");
			String id = rs.getString("id");
			
			
			article.setArticlenum(articlenum2);
			article.setTitle(title);
			article.setContent(content);
			article.setimgFileName(imageFileName);
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
}
