package news.article;

import java.sql.Blob;
import java.sql.Date;

public class ArticleVO {
	//article 테이블 컬럼들
	private String title;		//제목
	private Date writedate;		//작성날짜
	private Date updatedate;	//수정날짜
	private String content;		//내용
	private int articlenum;		//기사 번호(PK)
	private int type;			//기사 종류
	private int recCount;		//추천 수
	private int hotissue;		//특종여부, headline?
	private Blob img;			//이미지파일 -> 저장위치에서 불러오기냐 DB자체 저장이냐?
	private String name;		//기자 이름(FK)
	
	//react 테이블 컬럼들(getter/setter 미생성)
	private String actype;		//리액션 종류(type) 비고: article 테이블에서 똑같은 컬럼명이 있음
	private int rcount;			//각 리액션 갯수
	
	
	//reply 테이블 컬럼들 (getter/setter 미생성)
	private Date wdate;			//작성 날짜
	private String rcomment;	//댓글
	private int good;			//반응(좋아요)
	private int bad;			//반응(싫어요)
	
	
	
	public ArticleVO() {
		System.out.println("===> ArticleVO 생성자 호출");
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

	public Blob getImg() {
		return img;
	}

	public void setImg(Blob img) {
		this.img = img;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	

}
