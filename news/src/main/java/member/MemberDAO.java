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
	
	public boolean join(MemberVO memberVO) {			//회원 가입 메서드
		try {
			conn=dataFactory.getConnection();
			String id = memberVO.getId();
			String pwd = memberVO.getPwd();
			String name = memberVO.getName();
			String pnum = memberVO.getPnum();
			String email = memberVO.getEmail();
			String query= "INSERT INTO member(id, pwd, name, pnum, email)"
					+ " VALUES(?, ?, ?, ?, ?)";
			System.out.println(query);
			pstmt=conn.prepareStatement(query);
			pstmt.setString(1, id);
			pstmt.setString(2, pwd);
			pstmt.setString(3, name);
			pstmt.setString(4, pnum);
			pstmt.setString(5, email);
			pstmt.executeUpdate();
			pstmt.close();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			memberVO.setId(null);
			memberVO.setPwd(null);
			memberVO.setName(null);
			memberVO.setPnum(null);
			memberVO.setEmail(null);
		}
		return true;
	}
	
	public boolean login(MemberVO memberVO) {			//로그인 메서드. 로그인 성공:1 실페:0
		try {
			conn=dataFactory.getConnection();
			String id = memberVO.getId();
			String pwd = memberVO.getPwd();
			System.out.println("id: " + id);
			System.out.println("pwd: " + pwd);
			String query = "SELECT id, name, rep, pnum, email FROM member WHERE id=? AND pwd=?";
			System.out.println(query);
			pstmt=conn.prepareStatement(query);
			pstmt.setString(1, id);
			pstmt.setString(2, pwd);
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
			return false;
		}
		return true;
	}
	
	public void getMemberInfo(MemberVO memberVO) {			//로그인 메서드. 로그인 성공:1 실페:0
		String id;
		String name;
		int rep;
		String pnum;
		String email;
		try {
			conn=dataFactory.getConnection();
			id = memberVO.getId();
			System.out.println("id: " + id);
			String query = "SELECT id, name, rep, pnum, email FROM member WHERE id=?";
			System.out.println(query);
			pstmt=conn.prepareStatement(query);
			pstmt.setString(1, id);
			ResultSet rs = pstmt.executeQuery();
			rs.next();
			id=rs.getString("id");
			name=rs.getString("name");
			rep=rs.getInt("rep");
			pnum=rs.getString("pnum");
			email=rs.getString("email");
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
		}
	}
	
	public void editInfo(MemberVO memberVO) {				//회원 정보 수정 메서드
		try {
			conn=dataFactory.getConnection();
			String query= "UPDATE member SET name=?, pwd=?, pnum=?, email=?"
					+ " WHERE id=?";
			System.out.println(query);
			pstmt=conn.prepareStatement(query);
			String name = memberVO.getName();
			String pwd = memberVO.getPwd();
			String pnum = memberVO.getPnum();
			String email = memberVO.getEmail();
			String id = memberVO.getId();
			pstmt.setString(1, name);
			pstmt.setString(2, pwd);
			pstmt.setString(3, pnum);
			pstmt.setString(4, email);
			pstmt.setString(5, id);
			pstmt.executeUpdate();
			pstmt.close();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public boolean findId(MemberVO memberVO) {
		try {
			conn=dataFactory.getConnection();
			String query = "SELECT id FROM member WHERE name=? AND pnum=?";
			System.out.println(query);
			pstmt=conn.prepareStatement(query);
			String name = memberVO.getName();
			String pnum = memberVO.getPnum();
			pstmt.setString(1, name);
			pstmt.setString(2, pnum);
			ResultSet rs = pstmt.executeQuery();
			rs.next();
			String id = rs.getString("id");
			memberVO.setId(id);
			System.out.println("조회한 아이디: " + memberVO.getId());
			pstmt.close();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public boolean findPwd(MemberVO memberVO) {
		try {
			conn=dataFactory.getConnection();
			String query = "SELECT pwd FROM member WHERE id=? AND pnum=?";
			System.out.println(query);
			pstmt=conn.prepareStatement(query);
			String id = memberVO.getId();
			String pnum = memberVO.getPnum();
			pstmt.setString(1, id);
			pstmt.setString(2, pnum);
			ResultSet rs = pstmt.executeQuery();
			rs.next();
			String pwd = rs.getString("pwd");
			memberVO.setPwd(pwd);
			pstmt.close();
			pstmt.close();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public void withdraw(String id) {				//회원 탈퇴 메서드(로그인 값을 매개변수)
		try {
			conn=dataFactory.getConnection();
			String query= "DELETE FROM member WHERE id=?";
			System.out.println(query);
			pstmt=conn.prepareStatement(query);
			pstmt.setString(1, id);
			pstmt.executeUpdate();
			pstmt.close();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			id=null;
		}
	}
}
