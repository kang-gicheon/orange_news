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

		WeatherVO vo = new WeatherVO();

		try {
			Connection con = dataFactory.getConnection();

			String query = "select co_X, co_Y from area_coordinate where lev2 is null and lev1 = ?";

			pstmt = con.prepareStatement(query);
			pstmt.setString(1, value);

			ResultSet rs = pstmt.executeQuery();

			// 좌표 get

			while (rs.next()) {

				String coX = rs.getString("co_X");
				String coY = rs.getString("co_Y");

				System.out.print(rs.getString("co_X"));
				System.out.println(rs.getString("co_Y"));

				vo.setCoX(coX);
				vo.setCoY(coY);

			}
			;

			// 현재시간 날짜 측정
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

	public WeatherVO findCoordinate(String mapName, String _lev2) {
		
		WeatherVO vo = new WeatherVO();
		
		try {
			Connection con = dataFactory.getConnection();
			String query = "select co_X, co_Y from area_coordinate where lev1 = ? and lev2 = ? and lev3 is null ";

			pstmt = con.prepareStatement(query);
			pstmt.setString(1, mapName);
			pstmt.setString(2, _lev2);

			ResultSet rs = pstmt.executeQuery();

			// 좌표 get
			System.out.println(_lev2);
			
			while (rs.next()) {

				String coX = rs.getString("co_X");
				String coY = rs.getString("co_Y");

				vo.setCoX(coX);
				vo.setCoY(coY);

			}

			// 현재시간 날짜 측정
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

	public List<WeatherVO> findCoordinateList(String _lev1) {

		List<WeatherVO> list = new ArrayList<>();

		try {

			Connection con = dataFactory.getConnection();

			String query = "select * " + " from "
					+ " (select lev1, SUBSTR(lev2, 0, INSTR(lev2,'시', 3, 1)) lev2, SUBSTR(lev2, INSTR(lev2,'시', 3, 1)+1) lev2_1 "
					+ " from area_coordinate " + " where lev3 is null and lev1 = ? " + " union "
					+ " select lev1, lev2, null lev2_1 " + " from area_coordinate "
					+ " where lev3 is null and lev2 like '%군' and lev1 = ? ) " + " where lev2 is not null "
					+ " order by lev2, lev2_1 ";

			pstmt = con.prepareStatement(query);

			System.out.println(_lev1);

			pstmt.setString(1, _lev1);
			pstmt.setString(2, _lev1);

			ResultSet rs = pstmt.executeQuery();

			// 좌표 get

			while (rs.next()) {
				WeatherVO vo = new WeatherVO();

				String lev1 = rs.getString("lev1");
				String lev2 = rs.getString("lev2");
				String lev2_1 = rs.getString("lev2_1");

				vo.setLev1(lev1);
				vo.setLev2(lev2);
				vo.setLev2_1(lev2_1);

				System.out.println(lev1 + " _" + lev2 + " " + lev2_1);
				list.add(vo);
			}
			;

			System.out.println(list);

			rs.close();
			pstmt.close();
			con.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}

	public List<WeatherVO> lev2List(String _lev1) {

		// 리스트용 중복없는 lev2 리스트 출력기능
		List<WeatherVO> list = new ArrayList<>();

		try {
			Connection con = dataFactory.getConnection();
			String query = null;
			
			if(_lev1.endsWith("도") == true) {
				query = "select * " + " from " + " (select lev1, SUBSTR(lev2, 0, INSTR(lev2,'시', 3, 1)) lev2 "
						+ " from area_coordinate " + " where lev3 is null and lev1 = ? " + " union " + " select lev1, lev2 "
						+ " from area_coordinate " + " where lev3 is null and lev2 like '%군' and lev1 = ? ) "
						+ " where lev2 is not null " + " order by lev2 ";
				
				pstmt = con.prepareStatement(query);
				
				pstmt.setString(1, _lev1);
				pstmt.setString(2, _lev1);
				
			} else {
				query = "select lev1, lev2, co_X, co_Y from area_coordinate where lev3 is null and lev2 is not null and lev1 = ? ";
				
				pstmt = con.prepareStatement(query);
				
				pstmt.setString(1, _lev1);
			}
			
			System.out.println(query);
			
			ResultSet rs = pstmt.executeQuery();

			// 좌표 get
			while (rs.next()) {

				WeatherVO vo = new WeatherVO();

				String lev2 = rs.getString("lev2");
				
				vo.setLev2(lev2);

				list.add(vo);

				System.out.println(lev2);
			}

			System.out.println(list);

			rs.close();
			pstmt.close();
			con.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}

	public List<WeatherVO> sortList(String _lev1, String _lev2) {

		List<WeatherVO> list = new ArrayList<>();

		try {
			Connection con = dataFactory.getConnection();

			String query = "select co_X, co_Y, lev1, SUBSTR(lev2, 0, INSTR(lev2,'시', 3, 1)) lev2, SUBSTR(lev2, INSTR(lev2,'시', 3, 1)+1) lev2_1 "
					+ " from area_coordinate where lev1 = ? and lev2 like ? and lev3 is null";

			pstmt = con.prepareStatement(query);

			pstmt.setString(1, _lev1);
			pstmt.setString(2, _lev2 + "%");

			System.out.println(_lev1);
			System.out.println(_lev2);

			System.out.println(query);

			ResultSet rs = pstmt.executeQuery();

			// 좌표 get
			WeatherVO vo = null;

			while (rs.next()) {

				if (rs.getString("lev2_1") != null && rs.getString("lev2") != null) {

					vo = new WeatherVO();

					String lev1 = rs.getString("lev1");
					String lev2 = rs.getString("lev2");
					String lev2_1 = rs.getString("lev2_1");

					vo.setLev1(lev1);
					vo.setLev2(lev2);
					vo.setLev2_1(lev2_1);

				} else {

					String coX = rs.getString("co_X");
					String coY = rs.getString("co_Y");

					vo = new WeatherVO();

					vo.setCoX(coX);
					vo.setCoY(coY);
					vo.setLev2_1(null);

					LocalDate nowDate = LocalDate.now();
					DateTimeFormatter formatterDate = DateTimeFormatter.ofPattern("yyyyMMdd");
					String formatedDate = nowDate.format(formatterDate);

					LocalTime nowTime = LocalTime.now();
					LocalTime beforeOneHour = nowTime.minusHours(1);
					DateTimeFormatter formatterTime = DateTimeFormatter.ofPattern("HHmm");
					String formatedTime = beforeOneHour.format(formatterTime);

					vo.setCurrentDate(formatedDate);
					vo.setCurrentTime(formatedTime);

				}

				list.add(vo);

			}

			rs.close();
			pstmt.close();
			con.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}

}
