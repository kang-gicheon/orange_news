package member;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class MemberDAO {
	PreparedStatement pstmt;
	Connection conn;
	private DataSource dataFactory;
	
	public MemberDAO() {				//생성자, DB연결 메서드
		try {
			Context ctx = new InitialContext();
			Context envCtx = (Context) ctx.lookup("java:/comp/env");
			dataFactory = (DataSource) envCtx.lookup("jdbc/oracle");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public boolean insertMember(MemberVO memberVO) {			//회원 가입 메서드
		try {
			conn=dataFactory.getConnection();
			String id = memberVO.getId();			//입력한 아이디
			String pwd = memberVO.getPwd();			//입력한 비밀번호
			String name = memberVO.getName();		//입력한 이름
			String pnum = memberVO.getPnum();		//입력한 휴대폰 번호
			String email = memberVO.getEmail();		//입력한 이메일
			String query= "INSERT INTO member(id, pwd, name, pnum, email)"
					+ " VALUES(?, ?, ?, ?, ?)";
			System.out.println(query);
			pstmt=conn.prepareStatement(query);
			pstmt.setString(1, id);			//아이디 대입
			pstmt.setString(2, pwd);		//비밀번호 대입
			pstmt.setString(3, name);		//이름 대입
			pstmt.setString(4, pnum);		//휴대폰 번호 대입
			pstmt.setString(5, email);		//이메일 대입
			pstmt.executeUpdate();
			pstmt.close();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();	//입력한 데이터가 입력이 불가능한 경우(ex. DB 이슈)
			return false;		//false 반환
		} finally {
			memberVO.setId(null);
			memberVO.setPwd(null);
			memberVO.setName(null);
			memberVO.setPnum(null);
			memberVO.setEmail(null);
		}
		return true;	//정상적으로 연산할 경우 true를 return
	}
	
	public boolean selectMember(MemberVO memberVO, int a) {		//로그인[a==1], 회원조회[a==0] 메서드
		try {
			conn=dataFactory.getConnection();
			String id = memberVO.getId();		//아이디
			String pwd = memberVO.getPwd();		//비밀번호
			System.out.println("id: " + id);
			System.out.println("pwd: " + pwd);
			//해당 레코드의 아이디, 이름, 계정정보(일반/기자), 핸드폰번호, 이메일 값 요청
			String query = "SELECT id, name, rep, pnum, email FROM member WHERE id=?";
			if(a==1) {		//Service에 의해 요청된 것이 로그인 방식일 경우
				query += " AND pwd=?";	//pwd 컬럼값 요구 (비밀번호)
			}
			System.out.println(query);
			pstmt=conn.prepareStatement(query);
			pstmt.setString(1, id);
			if(a==1) {		//로그인 Service에 의해 요청된 것이 로그인 방식일 경우
				pstmt.setString(2, pwd);	//pwd 컬럼값 입력
			}
			ResultSet rs = pstmt.executeQuery();
			rs.next();
			id=rs.getString("id");
			String name=rs.getString("name");
			int rep=rs.getInt("rep");
			String pnum=rs.getString("pnum");
			String email=rs.getString("email");
			
			memberVO.setId(id);
			memberVO.setName(name);
			memberVO.setReporter(rep);
			memberVO.setPnum(pnum);
			memberVO.setEmail(email);
			
			rs.close();
			pstmt.close();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
			return false;	//입력한 id, pwd 값이 알맞지 않을 경우 SQLException을 유도, false를 return
		}
		return true;	//정상적으로 연산할 경우 true를 return
	}
	
	public void updateMember(MemberVO memberVO) {		//회원 정보 수정 메서드
		try {
			conn=dataFactory.getConnection();
			String query= "UPDATE member SET name=?, pwd=?, pnum=?, email=?"
					+ " WHERE id=?";
			System.out.println(query);
			pstmt=conn.prepareStatement(query);
			String name = memberVO.getName();		//(수정한) 회원 이름
			String pwd = memberVO.getPwd();			//(수정한) 회원 비밀번호
			String pnum = memberVO.getPnum();		//(수정한) 회원 전화번호
			String email = memberVO.getEmail();		//(수정한) 회원 이메일
			String id = memberVO.getId();			//레코드(계정) pk명
			pstmt.setString(1, name);	//(수정한) 회원 이름 대입
			pstmt.setString(2, pwd);	//(수정한) 회원 비밀번호 대입
			pstmt.setString(3, pnum);	//(수정한) 회원 전화번호 대입
			pstmt.setString(4, email);	//(수정한) 회원 이메일 대입
			pstmt.setString(5, id);	//레코드 찾기
			pstmt.executeUpdate();
			pstmt.close();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public boolean selectId(MemberVO memberVO) {	//분실한 계정 아이디 찾기
		try {
			conn=dataFactory.getConnection();
			//클라이언트가 입력한 name, pnum 값을 가진 레코드의 id 컬럼값 조회
			String query = "SELECT id FROM member WHERE name=? AND pnum=?";
			System.out.println(query);
			pstmt=conn.prepareStatement(query);
			String name = memberVO.getName();	//찾는 계정의 이름
			String pnum = memberVO.getPnum();	//찾는 계정의 핸드폰 번호
			pstmt.setString(1, name);
			pstmt.setString(2, pnum);
			ResultSet rs = pstmt.executeQuery();
			rs.next();
			String id = rs.getString("id");		//조회한 아이디 대입
			memberVO.setId(id);
			System.out.println("조회한 아이디: " + memberVO.getId());
			pstmt.close();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();	//클라이언트가 입력한 데이터가 조회되지 않을 경우
			return false;			//false 반환
		}
		return true;		//정상적으로 연산할 경우 true를 return
	}
	
	public boolean selectPwd(MemberVO memberVO) {	//분실한 계정 비밀번호 찾기
		try {
			conn=dataFactory.getConnection();
			//클라이언트가 입력한 id, pnum 값을 가진 레코드의 pwd 컬럼값 조회
			String query = "SELECT pwd FROM member WHERE id=? AND pnum=?";
			System.out.println(query);
			pstmt=conn.prepareStatement(query);
			String id = memberVO.getId();		//찾는 계정의 아이디
			String pnum = memberVO.getPnum();	//찾는 계정의 핸드폰 번호
			pstmt.setString(1, id);
			pstmt.setString(2, pnum);
			ResultSet rs = pstmt.executeQuery();
			rs.next();
			String pwd = rs.getString("pwd");	//조회한 비밀번호 대입
			memberVO.setPwd(pwd);
			pstmt.close();
			pstmt.close();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();	//클라이언트가 입력한 데이터가 조회되지 않을 경우
			return false;			//false 반환
		}
		return true;		//정상적으로 연산할 경우 true를 return
	}
	
	public void deleteMember(String id) {		//회원 탈퇴 메서드(로그인 값을 매개변수)
		try {
			conn=dataFactory.getConnection();
			//해당 id 컬럼값을 지닌 레코드를 삭제
			String query= "DELETE FROM member WHERE id=?";
			System.out.println(query);
			pstmt=conn.prepareStatement(query);
			pstmt.setString(1, id);	//id 값 대입
			pstmt.executeUpdate();
			pstmt.close();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
