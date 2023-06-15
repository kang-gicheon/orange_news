package weather;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
				String mapName = request.getParameter("mapName");
				
				System.out.println(mapName);
				
				// mapName의 값을 받아서 weatherDAO
				//coXY에 좌표와 현재시간을 set!
				WeatherVO coXY = wDao.findCoordinate(mapName);
				
				//coXY를 바인딩
				request.setAttribute("coXY", coXY);
				nextPage = "/WeatherPage.jsp";	
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		RequestDispatcher dispatch = request.getRequestDispatcher(nextPage);
		dispatch.forward(request, response);
	}
	


}
