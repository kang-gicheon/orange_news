package news.member;

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
	
	public void join(MemberVO memberVO) {			//회원 가입 메서드
		try {
			conn=dataFactory.getConnection();
			String id = memberVO.getId();
			String pwd = memberVO.getPwd();
			String name = memberVO.getName();
			String pnum = memberVO.getPnum();
			String email = memberVO.getEmail();
			String query= "INSERT INTO memeber(id, pwd, name, pnum, email)"
					+ " VALUE(?, ?, ?, ?, ?)";
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
		}
	}
	
	public boolean login(MemberVO memberVO) {			//로그인 메서드. 로그인 성공:1 실페:0
		String id;
		String pwd;
		String name;
		int rep;
		String email;
		try {
			conn=dataFactory.getConnection();
			id = memberVO.getId();
			pwd = memberVO.getPwd();
			System.out.println("id: " + id);
			System.out.println("pwd: " + pwd);
			String query = "SELECT id, name, rep, email FROM member WHERE id=? AND pwd=?";
			System.out.println(query);
			pstmt=conn.prepareStatement(query);
			pstmt.setString(1, id);
			pstmt.setString(2, pwd);
			ResultSet rs = pstmt.executeQuery();
			if(rs.next()) {
				id=rs.getString("id");
				name=rs.getString("name");
				rep=rs.getInt("rep");
				email=rs.getString("email");
				
				memberVO.setId(id);
				memberVO.setName(name);
				memberVO.setReporter(rep);
				memberVO.setEmail(email);
			}
			rs.close();
			pstmt.close();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public void logout() {			//로그아웃 메서드
		
	}
	
	public void personalData(String id) {			//개인정보조회 메서드
		MemberVO memberVO = new MemberVO();
		try {
			conn=dataFactory.getConnection();
			String query= "SELECT id, name, rep, pnum, email"
					+ " FROM member WHERE id=?";
			System.out.println(query);
			pstmt=conn.prepareStatement(query);
			pstmt.setString(1, id);
			ResultSet rs = pstmt.executeQuery();
			if(rs.next()) {
				id = rs.getString("id");
				String name = rs.getString("name");
				int rep = rs.getInt("rep");
				String pnum = rs.getString("pnum");
				String email = rs.getString("email");
				
				memberVO.setId(id);
				memberVO.setName(name);;
				memberVO.setReporter(rep);;
				memberVO.setPnum(pnum);
				memberVO.setEmail(email);
			}
			rs.close();
			pstmt.close();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void changeInfo(MemberVO memberVO) {				//회원 정보 수정 메서드
		try {
			conn=dataFactory.getConnection();
			String query= "UPDATE member SET name=?, pnum=?, email=?";
			System.out.println(query);
			pstmt=conn.prepareStatement(query);
			String name = memberVO.getName();
			String pnum = memberVO.getPnum();
			String email = memberVO.getEmail();
			pstmt.setString(1, name);
			pstmt.setString(2, pnum);
			pstmt.setString(3, email);
			pstmt.executeUpdate();
			pstmt.close();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void withdraw(String id) {				//회원 탈퇴 메서드(로그인 값을 매개변수)
		try {
			conn=dataFactory.getConnection();
			String query= "DELETE FROM member WHERE id=?";
			System.out.println(query);
			pstmt=conn.prepareStatement(query);
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
