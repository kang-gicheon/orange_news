package weather;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

@WebServlet("/twc/*")
public class TestWeatherController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	public void init(ServletConfig config) throws ServletException {
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doHandle(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doHandle(request, response);
	}

	protected void doHandle(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String nextPage = "";
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8");
		
		String action = request.getPathInfo();
		System.out.println("action: " + action);
		
		WeatherDAO wDao = new WeatherDAO();
		
		try {
			String mapName = null;
			
			if (action==null) {
				
				nextPage="/weatherPage/WeatherPage.jsp";
			}else if (action.equals("/getco.do")) {
				
				JSONObject totalObject;
			
				response.setContentType("application/x-json; charset=UTF-8");
		        PrintWriter out = response.getWriter();
				mapName = request.getParameter("mapName");
				System.out.println(mapName);
				
				// API 구하는 부분
				
				WeatherVO coXY = wDao.findCoordinate(mapName);
				
				Map<String, String> map = new HashMap<>();
				
				map.put("coX", coXY.getCoX());
				map.put("coY", coXY.getCoY());
				map.put("nowTime", coXY.getCurrentTime());
				map.put("nowDate", coXY.getCurrentDate());
				
				totalObject = new JSONObject(map);
				
				System.out.println(totalObject);
				
				out.print(totalObject);
				
				
				
				return;
				
				
			} else if (action.equals("/getco2.do")) {
				
				JSONObject totalObject;
			
				response.setContentType("application/x-json; charset=UTF-8");
		        PrintWriter out = response.getWriter();
				mapName = request.getParameter("lev1");
				String lev2 = request.getParameter("lev2");
				
				System.out.println(mapName + " " + lev2);
				// API 구하는 부분
				
				WeatherVO coXY = wDao.findCoordinate(mapName, lev2);
				
				Map<String, String> map = new HashMap<>();
				
				map.put("coX", coXY.getCoX());
				map.put("coY", coXY.getCoY());
				map.put("nowTime", coXY.getCurrentTime());
				map.put("nowDate", coXY.getCurrentDate());
				
				totalObject = new JSONObject(map);
				
				System.out.println(totalObject);
				
				out.print(totalObject);
				
				return;
				
				
			} else if (action.equals("/lev2list.do")) {
				
				response.setContentType("application/x-json; charset=UTF-8");
		        PrintWriter out = response.getWriter();
				mapName = request.getParameter("mapName");
				
				System.out.println(mapName);
				
				// 리스트 맴 json 생성
				JSONObject totalObject2;
				
				List<WeatherVO>	weatherList = new ArrayList<WeatherVO>();
				
				Map<Integer, WeatherVO> map2 = new HashMap<>();
				
				//하위 주소 리스트 JSON
				weatherList =  wDao.lev2List(mapName);
				
				System.out.println(weatherList.get(0));
				
				for(int i = 0; i<weatherList.size(); i++) {
					map2.put(i, weatherList.get(i));
				}
				
				totalObject2 = new JSONObject(map2);
				//하위 주소 리스트 JSON 생성
				
				System.out.println(totalObject2);
				
				out.print(totalObject2);
				
				return;
				
			} else if (action.equals("/getdetail.do")) {
				
				
				response.setContentType("application/x-json; charset=UTF-8");
		        PrintWriter out = response.getWriter();
				String lev1 = request.getParameter("lev1");
				String lev2 = request.getParameter("lev2");

				
				List<WeatherVO>	detailList = new ArrayList<WeatherVO>();
				
				//하위 주소 리스트 JSON
				detailList =  wDao.sortList(lev1, lev2);

				if(detailList.size() > 1) {
					// 사이즈가 1 이상이라면 lev2_1 이 존재한다는 말 이기 때문에 하위구역 리스트로 반환
					Map<Integer, WeatherVO> map3 = new HashMap<>();
					
					for(int i = 0; i<detailList.size(); i++) {
						map3.put(i, detailList.get(i));
						
					}	
					
					JSONObject totalObject3 = new JSONObject(map3);
					
					out.print(totalObject3);
					
				} else {
					// 사이즈가 1이면 좌표와 시간 반환
					Map<String, String> map4 = new HashMap<>();
					
					map4.put("coX", detailList.get(0).getCoX());
					map4.put("coY", detailList.get(0).getCoY());
					map4.put("nowTime", detailList.get(0).getCurrentTime());
					map4.put("nowDate", detailList.get(0).getCurrentDate());
					
					JSONObject totalObject4 = new JSONObject(map4);
					
					out.print(totalObject4);
					
				}
				
				return;
				
			} 
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		RequestDispatcher dispatch = request.getRequestDispatcher(nextPage);
		dispatch.forward(request, response);	
	}
	


}
