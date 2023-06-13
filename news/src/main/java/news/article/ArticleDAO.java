package news.article;

import java.sql.Blob;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class ArticleDAO {
	private PreparedStatement pst;
	private Connection conn;
	private DataSource data;
	
	public ArticleDAO() {		//생성자, DB연결 메서드
		try {
			Context ctx = new InitialContext();
			Context envCtx = (Context) ctx.lookup("java:/comp/env");
			data = (DataSource) envCtx.lookup("jdbc/oracle");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void articlesList() {		//기사 목록 메서드
		try {
			conn=data.getConnection();
			String query = "SELECT title, writedate, updatedate, articlenum, type, reccount, hotissue FROM article";
							//무엇을 리스로에서 보여줄 것인지?
							//확정: title, updatedate, type
			System.out.println(query);
			pst = conn.prepareStatement(query);
			ResultSet rs = pst.executeQuery();
			while(rs.next()) {
				String title = rs.getString("title");
				Date writeDate = rs.getDate("writeDate");
				Date updatedate = rs.getDate("updatedate");
				int articlenum = rs.getInt("articlenum");
				int type = rs.getInt("type");
				int reccount = rs.getInt("reccount");
				int hotissue = rs.getInt("hotissue");
				String id = rs.getString("id");
			}
			rs.close();
			pst.close();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void selectArticle() {		//기사 선택 메서드
		try {
			conn=data.getConnection();
			String query="SELECT title, writedate, updatedate, content articlenum, type, reccount, hotissue FROM article";
						//무엇을 게시판에서 보여줄 것인지?
			System.out.println(query);
			pst = conn.prepareStatement(query);
			ResultSet rs = pst.executeQuery();
			rs.next();
			String title = rs.getString("title");
			Date writeDate = rs.getDate("writeDate");
			Date updatedate = rs.getDate("updatedate");
			String content = rs.getString("content");
			int articlenum = rs.getInt("articlenum");
			int type = rs.getInt("type");
			int reccount = rs.getInt("reccount");
			int hotissue = rs.getInt("hotissue");
			String id = rs.getString("id");
			
			rs.close();
			pst.close();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void createArticle(ArticleVO articleVO) {	//기사 작성 메서드
		try {
			conn=data.getConnection();
			String title = articleVO.getTitle();
			String content = articleVO.getContent();
			int articlenum = articleVO.getArticlenum();
			int type = articleVO.getType();
			Blob img = articleVO.getImg();
			String query = "INSERT INTO article (title, writedate, updatedate, content, articlenum, type, img) "
					+ "VALUE (?, SYSDATE, SYSDATE, ?, SEQ_ANUM.NEXTVAL, ?, ?)";
			System.out.println(query);
			pst=conn.prepareStatement(query);
			pst.setString(1, title);
			pst.setString(2, content);
			pst.setInt(3, type);
			pst.setBlob(4, img);
			pst.executeUpdate();
			pst.close();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void updateArticle(ArticleVO articleVO) {	//기사 수정 메서드
		String title = articleVO.getTitle();
		String content = articleVO.getContent();
		Blob img = articleVO.getImg();
		int articlenum = articleVO.getArticlenum();
		try {
			conn=data.getConnection();
			String query="UPDATE article SET title=?, updatedate=SYSDATE, content=?";
			if(img != null) {
				query += ", img=?";
				query += " WHERE articlenum=?";
			} else {
				query += " WHERE articlenum=?";
			}
			
			System.out.println(query);
			pst = conn.prepareStatement(query);
			pst.setString(1, title);
			pst.setString(2, content);
			if(img != null) {
				pst.setBlob(3, img);
				pst.setInt(4, articlenum);
			}else {
				pst.setInt(3, articlenum);				
			}
			pst.executeUpdate();
			pst.close();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void deleteArticle(int articlenum) {		//기사 삭제 메서드
		try {
			conn=data.getConnection();
			String query = "DELETE FROM article WHERE articlenum=?";
			System.out.println(query);
			pst=conn.prepareStatement(query);
			pst.setInt(1, articlenum);
			pst.executeUpdate();
			pst.close();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
