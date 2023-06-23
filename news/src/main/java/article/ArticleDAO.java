package article;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class ArticleDAO {
	
	//PrepareStatement를 여유롭게 선언해준다... 
	private PreparedStatement pstmt;
	private PreparedStatement pstmt0;
	private PreparedStatement pstmt1;
	private PreparedStatement pstmt2;
	private PreparedStatement pstmt3;
	private PreparedStatement pstmt4;
	private PreparedStatement pstmt5;
	
	//Connection도... 
	private Connection conn;
	private Connection conn2;
	private DataSource dataFactory;
	
	//초기화 
	private int rcount=0;
	
	public ArticleDAO() {	//ArticleDAO 객체 생성(DB연결)
		try {
			Context ctx = new InitialContext();
			Context envCtx = (Context) ctx.lookup("java:/comp/env");
			dataFactory = (DataSource) envCtx.lookup("jdbc/oracle");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public int selectTotArticles(int type) {	//type 값 기사들의 총 갯수를 산출하는 메서드
		try {
			conn=dataFactory.getConnection();
			String query="SELECT COUNT(articlenum) FROM member WHERE type=?";
			pstmt=conn.prepareStatement(query);
			pstmt.setInt(1, type);
			ResultSet rs = pstmt.executeQuery();
			if(rs.next())
				return (rs.getInt(1));
			rs.close();
			pstmt.close();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	public List<ArticleVO> selectAllArticles(int a){	//모든 기사의 거의 모든 컬럼 데이터를
		System.out.println("selectAllArticles 들어옴");
		List<ArticleVO> articlesList = new ArrayList<ArticleVO>();
		
		try {
			conn=dataFactory.getConnection();
			String query = "SELECT title, updatedate, content, articlenum, type, reccount, hotissue, img, id "+
			" FROM ARTICLE ORDER by ";
			if(a==0) {
				query +="articlenum";
			}else if(a==1) {
				query += "reccount";
			}
			 //기사 번호 기준으로 select 하는지, reccount(추천순) 기준으로 select 하는지 한번에 처리 
			query += " DESC";
			pstmt = conn.prepareStatement(query);
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()) { //rs.next()를 while안에서 돌아가게 하고 articles리스트를 만들어준다. 
				String title = rs.getString("title");
				Date updatedate = rs.getDate("updatedate");
				String content = rs.getString("content");
				int articleNum = rs.getInt("articlenum");
				int type = rs.getInt("type");
				int reccount = rs.getInt("reccount");
				int hotissue = rs.getInt("hotissue");
				String imageFileName = rs.getString("img");
				String id = rs.getString("id");
				
				ArticleVO article = new ArticleVO();
				article.setTitle(title);
				article.setUpdatedate(updatedate);
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
		return articlesList; //원하는 정렬 조건의 기사들이 정렬된 리스트가 반환됨
	}
	
	public List<ArticleVO> selectArticlesofType(Map<String, Integer> pagingMap, int type){
		List<ArticleVO> articlesofTypeList = new ArrayList<ArticleVO>();
		int section = (Integer) pagingMap.get("section");
		int pageNum = (Integer) pagingMap.get("pageNum");
		try {
			conn=dataFactory.getConnection();
			String query="SELECT * FROM ("
					+ "SELECT ROWNUM as recnum, title, updatedate, content, articlenum,type, reccount, hotissue,img, id "
					+ "FROM(SELECT title, updatedate, content, articlenum, type, reccount, hotissue, img, id"
					+ " FROM article WHERE type=? ORDER by articlenum DESC)) "
					+ "WHERE recNum between(?-1)*100+(?-1)*10+1 and (?-1)*100+?*10";
			pstmt=conn.prepareStatement(query);
			pstmt.setInt(1, type);
			pstmt.setInt(2, section);
			pstmt.setInt(3, pageNum);
			pstmt.setInt(4, section);
			pstmt.setInt(5, pageNum);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				ArticleVO article = new ArticleVO();
				String title = rs.getString("title");
				Date updatedate = rs.getDate("updatedate");
				String content = rs.getString("content");
				int articleNum = rs.getInt("articlenum");
				type = rs.getInt("type");
				int reccount = rs.getInt("reccount");
				int hotissue = rs.getInt("hotissue");
				String imageFileName = rs.getString("img");
				String id = rs.getString("id");
				
				article.setTitle(title);
				article.setUpdatedate(updatedate);
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
	
	
	public List<ArticleVO> selectReactArticles() { //각 반응 순서대로 정렬한 리스트를 반환하는 메소드 
		System.out.println("selectReactArticles 메소드 들어옴");
		List<ArticleVO> articlesList = new ArrayList<ArticleVO>();

		try {
			conn = dataFactory.getConnection();

			String query = "SELECT articlenum, rcount FROM REACT WHERE r_type=? "
					+ "and rcount = (SELECT max(rcount) FROM REACT WHERE r_type=?) "; //max를 뽑는다.
			String query2 = "SELECT title, id, img FROM ARTICLE WHERE articlenum=?"; 
			//그 max인 articlenum을 기준으로 article 테이블에서 정보를 가져온다. (일단은 출력에 필요한 부분만)

			
			//각 반응 5가지를 전부 확인해야함 
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
				rcount = rs.getInt("rcount");
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

		return articlesList;
	}
	
	
	public void insertNewArticle(ArticleVO article) { //새로운 기사 작성하는 메소드 
		
		try {
			
			conn = dataFactory.getConnection();
			//jsp에서 작성한 article의 정보를 받아온다. 
			String title = article.getTitle();
			String content = article.getContent();
			int type = article.getType();
			int hotissue = article.getHotissue();
			String img = article.getImgFileName();
			
			//insert문 작성
			String query = "INSERT INTO ARTICLE (title, writedate, updatedate, content, articlenum, type, reccount, hotissue, img, id)"
					+ " values(?, sysdate, sysdate, ?, seq_anum.nextval, ?, 0, ?, ?, 'reporter1')";
			System.out.println(query); //확인용
			pstmt=conn.prepareStatement(query);
			pstmt.setString(1, title);
			pstmt.setString(2, content);
			pstmt.setInt(3, type);
			pstmt.setInt(4, hotissue);
			pstmt.setString(5,img);
			//각 정보들을 set해주고 쿼리를 업데이트함 
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
				//방금 작성한 기사의 articlenum을 제목 기준으로 받아온다. 
			}
			
			rsrs.close();
			ps.close();
			
			conn2 = dataFactory.getConnection();
			
			//초기에 반응 테이블을 하나씩 초기화시켜 채워둔다. 
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
	
	public ArticleVO selectArticle(ArticleVO article, int a) { //기사 조회 메소드 
		//a==0 기사 선택 / a==1 헤드라인 기사 전시
		
		try {
			conn=dataFactory.getConnection();
			String query ="SELECT * FROM (SELECT title, writedate, content, articlenum, type, reccount, hotissue, img, id from ARTICLE";	
			if(a==0) {
				query += " WHERE articlenum=?)";
			} else if(a==1) {
				query += " WHERE hotissue=? ORDER BY articlenum DESC) WHERE ROWNUM = 1";
			}
			pstmt=conn.prepareStatement(query);
			if(a==0) {
				pstmt.setInt(1, article.getArticlenum());
			} else if (a==1) {
				pstmt.setInt(1, a);
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
			
			//select문 실행 > article에 받아온 데이터 set > article 반환 
			
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
	
	public ArticleVO viewReact(ArticleVO article) { //반응 개수 확인용 메소드 
		try {
			conn=dataFactory.getConnection();
			String query = "SELECT r_type, rcount from REACT where articlenum=?";
			//기사 번호를 기준으로 type과 count를 받아온다. 
			pstmt= conn.prepareStatement(query);
			pstmt.setInt(1, article.getArticlenum());
			ResultSet rs  = pstmt.executeQuery();
			rs.next(); 
			//받아온 정보 
			String r_type = rs.getString("r_type");
			rcount = rs.getInt("rcount");
			//정보를 article에 set해준후 반환
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
	
	
	public ArticleVO updateReact (ArticleVO article) { //반응 업데이트 
		
		//받아온 article의 정보를 받아온다. 
		String r_type = article.getActype();
		int articlenum =article.getArticlenum();
			
		try {
			conn = dataFactory.getConnection();
			String query = "SELECT rcount FROM REACT WHERE r_type=? and articlenum = ?";
			//받아온 정보를 기준으로 rcount 가져오기 
			//ex) 기사 번호가 2, type이 "화나요"일 경우 조건에 맞는 rcount가 select됨.
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, r_type);
			pstmt.setInt(2, articlenum);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				rcount = rs.getInt("rcount"); //rcount 받아옴
			}
			
			rcount=rcount+1;
			//한 번 실행될 때마다 1씩 올라간다. 
			
			conn2 = dataFactory.getConnection();
			String query2 = "UPDATE REACT SET rcount=? WHERE r_type=? and articlenum=? ";
			pstmt2 = conn2.prepareStatement(query2);
			pstmt2.setInt(1, rcount);
			pstmt2.setString(2, r_type);
			pstmt2.setInt(3, articlenum);
			pstmt2.executeUpdate();
			//1 올라간 rcount값을 update 해준다. 
			
			article.setActype(r_type);
			article.setRcount(rcount);
			article.setArticlenum(articlenum);
			//jsp에서 출력해야 하기 때문에 출력용 정보들을 article에 set해주고 반환한다. 
			
			pstmt2.close();
			conn2.close();
			pstmt.close();
			conn.close();
		
		} catch(Exception e) {
			e.printStackTrace();
		}
		return article;
	}
	
	public ArticleVO updateReccount(ArticleVO article) { //추천 업데이트 
		int articlenum = article.getArticlenum();
		//받아온 article의 정보
		
		try {
			conn=dataFactory.getConnection();
			String query = "SELECT reccount FROM ARTICLE WHERE articlenum=?";
			//reccount가 몇인지 select문으로 확인 
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, articlenum);
			ResultSet rs = pstmt.executeQuery();
			rs.next();
			int rrcount = rs.getInt("reccount");
			System.out.println(rrcount+"reccount 현재 개수");
			
			rrcount = rrcount+1;
			//위 메소드와 마찬가지로 한 번 실행될때마다 1씩 늘어나게 한다. 
			
			conn2 = dataFactory.getConnection();
			String query2 = "UPDATE ARTICLE SET reccount =? WHERE articlenum =?";
			//1 늘어난 reccount를 update 한다. 
			pstmt2 = conn2.prepareStatement(query2);
			pstmt2.setInt(1, rrcount);
			pstmt2.setInt(2, articlenum);
			pstmt2.executeUpdate();
			
			//그 정보를 article에 기록해 반환
			
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
		pstmt.setInt(1, 1);
		ResultSet rs = pstmt.executeQuery();
		
		while(rs.next()) {
			ArticleVO article = new ArticleVO();
			title=rs.getString("title");
			articlenum=rs.getInt("articlenum");
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
