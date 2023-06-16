package article;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.sql.Date;

public class ArticleVO {
	// article 테이블 컬럼들
	private String title; // 제목
	private Date writedate; // 작성날짜
	private Date updatedate; // 수정날짜
	private String content; // 내용
	private int articlenum; // 기사 번호(PK 예정?)
	private int type; // 기사 종류
	private int recCount; // 추천 수
	private int hotissue; // 특종여부, headline?
	private String imgFileName; // 이미지파일 -> 저장위치에서 불러오기냐 DB자체 저장이냐?
	private String id;
	
	// react 테이블 컬럼들(getter/setter 미생성)
		private String actype; // 리액션 종류(type) 비고: article 테이블에서 똑같은 컬럼명이 있음
		private int rcount; // 각 리액션 갯수
		


	public ArticleVO() {
		System.out.println("===> ArticleVO 생성자 호출");
	}

	public ArticleVO(String title, Date writedate, Date updatedate, String content, int articlenum, int type,
			int recCount, int hotissue, String imgFileName, String id, String actype, int rcount) {
		super();
		this.title = title;
		this.writedate = writedate;
		this.updatedate = updatedate;
		this.content = content;
		this.articlenum = articlenum;
		this.type = type;
		this.recCount = recCount;
		this.hotissue = hotissue;
		this.imgFileName = imgFileName;
		this.id = id;
		this.actype=actype;
		this.rcount=rcount;
		
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Date getWritedate() {
		return writedate;
	}

	public void setWritedate(Date writedate) {
		this.writedate = writedate;
	}

	public Date getUpdatedate() {
		return updatedate;
	}

	public void setUpdatedate(Date updatedate) {
		this.updatedate = updatedate;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getArticlenum() {
		return articlenum;
	}

	public void setArticlenum(int articlenum) {
		this.articlenum = articlenum;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getRecCount() {
		return recCount;
	}

	public void setRecCount(int recCount) {
		this.recCount = recCount;
	}

	public int getHotissue() {
		return hotissue;
	}

	public void setHotissue(int hotissue) {
		this.hotissue = hotissue;
	}

	public String getImgFileName() {
		try {
			if(imgFileName != null && imgFileName.length()!=0) {
				imgFileName = URLDecoder.decode(imgFileName,"UTF-8");
			}
		}catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return imgFileName;
	}

	public void setImgFileName(String imgFileName) {
		this.imgFileName= imgFileName;
		
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	
	public String getActype() {
		return actype;
	}

	public void setActype(String actype) {
		this.actype = actype;
	}

	public int getRcount() {
		return rcount;
	}

	public void setRcount(int rcount) {
		this.rcount = rcount;
	}

	
	
	
	
}
