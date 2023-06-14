package news.member;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class MemberDAO {
	private PreparedStatement pst;
	private Connection conn;
	private DataSource data;
	
	public MemberDAO() {				//생성자, DB연결 메서드
		try {
			Context ctx = new InitialContext();
			Context envCtx = (Context) ctx.lookup("java:/comp/env");
			data = (DataSource) envCtx.lookup("jdbc/oracle");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void join(MemberVO memberVO) {			//회원 가입 메서드
		try {
			conn=data.getConnection();
			String id = memberVO.getId();
			String pwd = memberVO.getPwd();
			String name = memberVO.getName();
			String pnum = memberVO.getPnum();
			String address = memberVO.getAddress();
			String query= "INSERT INTO memeber(id, pwd, name, pnum, address)"
					+ " VALUE(?, ?, ?, ?, ?)";
			System.out.println(query);
			pst=conn.prepareStatement(query);
			pst.setString(1, id);
			pst.setString(2, pwd);
			pst.setString(3, name);
			pst.setString(4, pnum);
			pst.setString(5, address);
			pst.executeUpdate();
			pst.close();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void login() {			//로그인 메서드
		try {
			conn=data.getConnection();
			String query = "SELECT FROM member WHERE id=?, pwd=?";
			String id;
			pst=conn.prepareStatement(query);
			pst.executeQuery();
			pst.close();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void logout() {			//로그아웃 메서드
		
	}
	
	public void personalData(String id) {			//개인정보조회 메서드
		MemberVO memberVO = new MemberVO();
		try {
			conn=data.getConnection();
			String query= "SELECT id, name, rep, pnum, address"
					+ " FROM member WHERE id=?";
			System.out.println(query);
			pst=conn.prepareStatement(query);
			pst.setString(1, id);
			ResultSet rs = pst.executeQuery();
			rs.next();
			id = rs.getString("id");
			String name = rs.getString("name");
			int rep = rs.getInt("rep");
			String pnum = rs.getString("pnum");
			String address = rs.getString("address");
			
			memberVO.setId(id);
			memberVO.setName(name);;
			memberVO.setReporter(rep);;
			memberVO.setPnum(pnum);
			memberVO.setAddress(address);
			rs.close();
			pst.close();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void changeInfo(MemberVO memberVO) {				//회원 정보 수정 메서드
		try {
			conn=data.getConnection();
			String query= "UPDATE member SET name=?, pnum=?, address=?";
			System.out.println(query);
			pst=conn.prepareStatement(query);
			String name = memberVO.getName();
			String pnum = memberVO.getPnum();
			String address = memberVO.getAddress();
			pst.setString(1, name);
			pst.setString(2, pnum);
			pst.setString(3, address);
			pst.executeUpdate();
			pst.close();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void withdraw(String id) {				//회원 탈퇴 메서드
		try {
			conn=data.getConnection();
			String query= "DELETE FROM member WHERE id=?";
			System.out.println(query);
			pst=conn.prepareStatement(query);
			pst.executeUpdate();
			pst.close();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			id=null;
		}
	}
}
