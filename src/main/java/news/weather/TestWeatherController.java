package weather;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;

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
		HttpSession session;
		
		String action = request.getPathInfo();
		
		WeatherDAO wDao = new WeatherDAO();
		
		try {
			
			if (action.equals("/getco.do")) {
				
				JSONObject totalObject;
				
				response.setContentType("application/x-json; charset=UTF-8");
		        PrintWriter out = response.getWriter();
				String mapName = request.getParameter("mapName");
				
				System.out.println(mapName);
				
				WeatherVO coXY = wDao.findCoordinate(mapName);
				
				System.out.println(coXY.getCoX());
				System.out.println(coXY.getCoY());
				System.out.println(coXY.getCurrentTime());
				System.out.println(coXY.getCurrentDate());
				
				Map<String, String> map = new HashMap<>();
				
				map.put("coX", coXY.getCoX());
				map.put("coY", coXY.getCoY());
				map.put("nowTime", coXY.getCurrentTime());
				map.put("nowDate", coXY.getCurrentDate());
				
				totalObject = new JSONObject(map);
				
				System.out.println(totalObject);
				
				out.print(totalObject);
				return;
			}
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		RequestDispatcher dispatch = request.getRequestDispatcher(nextPage);
		dispatch.forward(request, response);	
	}
	


}
