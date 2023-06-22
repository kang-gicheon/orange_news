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
	
	public ArticleDAO() {
		try {
			Context ctx = new InitialContext();
			Context envCtx = (Context) ctx.lookup("java:/comp/env");
			dataFactory = (DataSource) envCtx.lookup("jdbc/oracle");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public List<ArticleVO> selectAllArticles(int a){
		System.out.println("selectAllArticles 들어옴");
		List<ArticleVO> articlesList = new ArrayList<ArticleVO>();
		
		try {
			conn=dataFactory.getConnection();
			String query = "SELECT title, content, articlenum,type, reccount, hotissue,img, id "+
			" FROM ARTICLE ORDER by ";
			if(a==0) {
				query +="articlenum";
			}else if(a==1) {
				query += "reccount";
			}
			query += " DESC";
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
	
	public List<ArticleVO> selectArticlesofType(){
		List<ArticleVO> articlesofTypeList = new ArrayList<ArticleVO>();
		ArticleVO article = new ArticleVO();
		try {
			conn=dataFactory.getConnection();
			int type = article.getType();
			String query="SELECT title, content, articlenum,type, reccount, hotissue,img, id"
					+ " FROM article WHERE type=? ORDER by articlenum DESC";
			pstmt=conn.prepareStatement(query);
			pstmt.setInt(1, type);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				String title = rs.getString("title");
				String content = rs.getString("content");
				int articleNum = rs.getInt("articlenum");
				type = rs.getInt("type");
				int reccount = rs.getInt("reccount");
				int hotissue = rs.getInt("hotissue");
				String imageFileName = rs.getString("img");
				String id = rs.getString("id");
				
				article.setTitle(title);
				article.setContent(content);
				article.setArticlenum(articleNum);
				article.setType(type);
				article.setRecCount(reccount);
				article.setHotissue(hotissue);
				article.setImgFileName(imageFileName);
				article.setId(id);
				articlesofTypeList.add(article);	
			}
			rs.close();
			pstmt.close();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return articlesofTypeList;
	}
	
	public void insertNewArticle(ArticleVO article) {
		
		try {
			
			conn = dataFactory.getConnection();
			String title = article.getTitle();
			String content = article.getContent();
			int type = article.getType();
			int hotissue = article.getHotissue();
			String img = article.getImgFileName();
			
			String query = "INSERT INTO ARTICLE (title, writedate, updatedate, content, articlenum, type, reccount, hotissue, img, id)"
					+ " values(?, sysdate, sysdate, ?, seq_anum.nextval, ?, 0, ?, ?, 'reporter1')";
			System.out.println(query);
			pstmt=conn.prepareStatement(query);
			pstmt.setString(1, title);
			pstmt.setString(2, content);
			pstmt.setInt(3, type);
			pstmt.setInt(4, hotissue);
			pstmt.setString(5,img);
			pstmt.executeUpdate();
			pstmt.close();
			conn.close();
			
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
	
	public ArticleVO selectArticle(ArticleVO article, int a) {
		//a==0 기사 선택 / a==1 헤드라인 기사 전시
		
		try {
			conn=dataFactory.getConnection();
			String query ="SELECT * FROM (SELECT title, writedate, content, articlenum, type, reccount, hotissue, img, id from ARTICLE";	
			if(a==0) {
				query += " WHERE articlenum=?)";
			} else if(a==1) {
				query += " ORDER BY articlenum DESC) WHERE ROWNUM = 1";
			}
			pstmt=conn.prepareStatement(query);
			System.out.println(query);
			if(a==0) {
				pstmt.setInt(1, article.getArticlenum());
			}
			ResultSet rs = pstmt.executeQuery();
			rs.next();
			String title = rs.getString("title");
			Date writedate = rs.getDate("writedate");
			String content = rs.getString("content");
			int articlenum = rs.getInt("articlenum");
			int type = rs.getInt("type");
			int reccount = rs.getInt("reccount");
			int hotissue = rs.getInt("hotissue");
			String imageFileName = rs.getString("img");
			String id = rs.getString("id");
			
			article.setTitle(title);
			article.setWritedate(writedate);
			article.setContent(content);
			article.setArticlenum(articlenum);
			article.setType(type);
			article.setRecCount(reccount);
			article.setHotissue(hotissue);
			article.setImgFileName(imageFileName);
			article.setId(id);
			rs.close();
			pstmt.close();
			conn.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return article;
	}
	
//	public ArticleVO selectArticle(ArticleVO article) {
//		try {
//			conn=dataFactory.getConnection();
//			String query ="SELECT title, writedate, content, articlenum, type, reccount, hotissue, img, id"
//					+" from ARTICLE where articlenum=?";
//			pstmt=conn.prepareStatement(query);
//			pstmt.setInt(1, article.getArticlenum());
//			ResultSet rs = pstmt.executeQuery();
//			rs.next();
//			String title = rs.getString("title");
//			Date writedate = rs.getDate("writedate");
//			String content = rs.getString("content");
//			int type = rs.getInt("type");
//			int reccount = rs.getInt("reccount");
//			int hotissue = rs.getInt("hotissue");
//			String imageFileName = rs.getString("img");
//			String id = rs.getString("id");
//			
//			article.setTitle(title);
//			article.setContent(content);
//			article.setType(type);
//			article.setRecCount(reccount);
//			article.setHotissue(hotissue);
//			article.setImgFileName(imageFileName);
//			article.setId(id);
//			article.setWritedate(writedate);
//			rs.close();
//			pstmt.close();
//			conn.close();
//		}catch(Exception e) {
//			e.printStackTrace();
//		}
//		return article;
//	}
	
	public ArticleVO viewReact(ArticleVO article) {
		try {
			conn=dataFactory.getConnection();
			String query = "SELECT r_type, rcount from REACT where articlenum=?";
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
	
	
	public ArticleVO updateReact (ArticleVO article) {
		String r_type = article.getActype();
		int articlenum =article.getArticlenum();
		int rcount = 0;
			
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
			
			pstmt2.close();
			conn2.close();
			pstmt.close();
			conn.close();
		
		} catch(Exception e) {
			e.printStackTrace();
		}
		return article;
	}
	
	public ArticleVO updateReccount(ArticleVO article) {
		System.out.println("updateReccount 메소드 들어옴");
		int articlenum = article.getArticlenum();
		
		
		try {
			conn=dataFactory.getConnection();
			String query = "SELECT reccount FROM ARTICLE WHERE articlenum=?";
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, articlenum);
			ResultSet rs = pstmt.executeQuery();
			rs.next();
			int rrcount = rs.getInt("reccount");
			System.out.println(rrcount+"reccount 현재 개수");
			
			rrcount = rrcount+1;
			
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
			
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return article;
	}
	
	
	public void HDLarticles(List<ArticleVO> hdlArticlesList) { //헤드라인 기사 뽑기 
		
		String title;
		int articlenum;
		
		try {
		conn= dataFactory.getConnection();
		String query = "SELECT title, articlenum FROM article WHERE hotissue=? ORDER BY articlenum DESC";
		pstmt=conn.prepareStatement(query);
		System.out.println(query);
		pstmt.setInt(1, 1);
		ResultSet rs = pstmt.executeQuery();
		
		while(rs.next()) {
			ArticleVO article = new ArticleVO();
			title=rs.getString("title");
			articlenum=rs.getInt("articlenum");
			System.out.println("title: "+title);
			System.out.println("articlenum: "+articlenum);
			article.setTitle(title);
			article.setArticlenum(articlenum);
			hdlArticlesList.add(article);
		}
		
		rs.close();
		pstmt.close();
		conn.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
//	public List<ArticleVO> HDLarticles() { //헤드라인 기사 뽑기 
//		ArticleVO article = new ArticleVO();
//		List<ArticleVO> hdlArticlesList = new ArrayList<ArticleVO>();
//		String title;
//		String imageFileName;
//		int articlenum;
//		
//		try {
//		conn= dataFactory.getConnection();
//		String query = "SELECT title, img, articlenum FROM article WHERE hotissue=1 ORDER BY articlenum DESC";
//		pstmt=conn.prepareStatement(query);
//		ResultSet rs = pstmt.executeQuery();
//		
//		while(rs.next()) {
//			title=rs.getString("title");
//			imageFileName=rs.getString("img");
//			articlenum=rs.getInt("articlenum");
//			article.setTitle(title);
//			article.setImgFileName(imageFileName);
//			article.setArticlenum(articlenum);
//			hdlArticlesList.add(article);
//		}
//		
//		rs.close();
//		pstmt.close();
//		conn.close();
//		}catch(Exception e) {
//			e.printStackTrace();
//		}
//		return hdlArticlesList;
//	}
	
}
