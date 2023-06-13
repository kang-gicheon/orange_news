package news.member;

import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class MemberDAO {
	private PreparedStatement pst;
	private Connection conn;
	private DataSource data;
	
	public MemberDAO() {		//생성자, DB연결 메서드
		try {
			Context ctx = new InitialContext();
			Context envCtx = (Context) ctx.lookup("java:/comp/env");
			data = (DataSource) envCtx.lookup("jdbc/oracle");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void join() {
		try {
			conn=data.getConnection();
			String query= "INSERT INTO memeber()"
					+ " VALUE()";
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void personalData() {
		try {
			conn=data.getConnection();
			String query= "SELECT FROM ";
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void changeInfo() {
		try {
			conn=data.getConnection();
			String query= "UPDATE FROM ";
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void withdraw() {
		try {
			conn=data.getConnection();
			String query= "DELETE FROM";
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
