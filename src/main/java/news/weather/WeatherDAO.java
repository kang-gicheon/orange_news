package weather;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class WeatherDAO {
	private PreparedStatement pstmt;
	private DataSource dataFactory;

	public WeatherDAO() {
		try {
			Context ctx = new InitialContext();
			Context envContext = (Context) ctx.lookup("java:/comp/env");
			dataFactory = (DataSource) envContext.lookup("jdbc/oracle");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// value 값에 따른 largArea 좌표 검색
	// value 값은 도시의 이름이다. ex) 서울특별시
	public WeatherVO findCoordinate(String value) {
		
		WeatherVO vo = null;
		try {
			Connection con = dataFactory.getConnection();
			
			String query = "select lev1, co_X, co_Y from area_coordinate where lev2 is null and lev1 = ?";
			
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, value);
			
			ResultSet rs = pstmt.executeQuery();
			
			//좌표 get
			vo = new WeatherVO();	
			
			while(rs.next()){
				
				String coX = rs.getString("co_X");
				String coY = rs.getString("co_Y");
				String lev1 = rs.getString("lev1");
				
				vo.setCoX(coX);
				vo.setCoY(coY);
				vo.setLev1(lev1);
				
			};
			
			
			
			//현재시간 날짜 측정
			LocalDate nowDate = LocalDate.now();
			DateTimeFormatter formatterDate = DateTimeFormatter.ofPattern("yyyyMMdd");
			String formatedDate = nowDate.format(formatterDate);
			
			LocalTime nowTime = LocalTime.now();
			LocalTime beforeOneHour = nowTime.minusHours(1);
			DateTimeFormatter formatterTime = DateTimeFormatter.ofPattern("HHmm");
			String formatedTime = beforeOneHour.format(formatterTime);
			
			// api 검색 포맷에 맟준 날짜와 시간
			vo.setCurrentDate(formatedDate);
			vo.setCurrentTime(formatedTime);
			
			rs.close();
			pstmt.close();
			con.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return vo;
	}

	
	
	
}
