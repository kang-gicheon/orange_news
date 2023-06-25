package member;

public class MemberVO {
	//member 테이블 컬럼들
	private String id;			//아이디(PK)
	private String pwd;			//비밀번호
	private String name;		//이름
	private int reporter=0;		//기자 계정 유효성 검사?
	private String pnum;		//휴대폰 번호
	private String email;		//주소
	
	
	public MemberVO() {
		System.out.println("===> MemberVO 생성자 호출");
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getReporter() {
		return reporter;
	}

	public void setReporter(int reporter) {
		this.reporter = reporter;
	}

	public String getPnum() {
		return pnum;
	}

	public void setPnum(String pnum) {
		this.pnum = pnum;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}	
	
}
