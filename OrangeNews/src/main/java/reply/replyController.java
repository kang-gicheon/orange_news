package reply;

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
import javax.servlet.http.HttpSession;

import org.json.JSONObject;

@WebServlet("/trc/*")
public class replyController extends HttpServlet {
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
		
		replyDAO rDao = new replyDAO();
		
		try {
			
			if (action.equals("/type0list.do")) {
				//댓글을 저장할 JSON과 그것을 UTF-8로 설정
				JSONObject totalObject;
				response.setContentType("application/x-json; charset=UTF-8");
				
		        PrintWriter out = response.getWriter();
		        
		       
				String articleNum = request.getParameter("articleNum"); //받은 기사 번호
				String type = request.getParameter("sortType"); // 받은 정렬 방법
				System.out.println(articleNum);
				
				List<replyVO> replyList  = new ArrayList<replyVO>(); // 댓글 리스트 선언
				
				// 기사번호에 맞는 댓글 리스트를 정렬하여 저장해주는 기능
				replyList = rDao.showList(articleNum, type);
				
				// 댓글리스트를 저장할 해쉬맵
				Map<Integer, replyVO> map = new HashMap<>();
				
				//번호와 댓글 내용을 저장
				for(int i = 0; i<replyList.size(); i++) {
					map.put(i, replyList.get(i));
				}
				
				//맵을 JSON에 저장
				totalObject = new JSONObject(map);
				
				System.out.println(totalObject);
				//JSON 전달
				out.print(totalObject);
				
				return;
				
				
			}if (action.equals("/type1list.do")) {
				
				JSONObject totalObject;
			
				response.setContentType("application/x-json; charset=UTF-8");
		        PrintWriter out = response.getWriter();
				String articleNum = request.getParameter("articleNum");
				String parentNum = request.getParameter("parentNum");
				System.out.println(articleNum);
				
				// API 구하는 부분
				List<replyVO> replyListType1  = new ArrayList<replyVO>();
				
				replyListType1 = rDao.showListType1(articleNum, parentNum);
				
				Map<Integer, replyVO> map = new HashMap<>();
				
				for(int i = 0; i<replyListType1.size(); i++) {
					map.put(i, replyListType1.get(i));
				}
				
				totalObject = new JSONObject(map);
				
				System.out.println(totalObject);
				
				out.print(totalObject);
				
				return;
				
				
			}  else if (action.equals("/myreply.do")) {
				
				JSONObject totalObject;
				
				response.setContentType("application/x-json; charset=UTF-8");
		        PrintWriter out = response.getWriter();
				String articleNum = request.getParameter("articleNum");
				String id = request.getParameter("id");
				System.out.println(articleNum);
				
				// API 구하는 부분
				List<replyVO> replyList  = new ArrayList<replyVO>();
				
				replyList = rDao.showList(articleNum, id);
				
				Map<Integer, replyVO> map = new HashMap<>();
				
				for(int i = 0; i<replyList.size(); i++) {
					map.put(i, replyList.get(i));
				}
				
				totalObject = new JSONObject(map);
				
				System.out.println(totalObject);
				
				out.print(totalObject);
				
				return;

			} else if (action.equals("/addreply0.do")) {
				
				int result;
				response.setContentType("application/x-json; charset=UTF-8");
		        PrintWriter out = response.getWriter();
				String articleNum = request.getParameter("articleNum");
				String id = request.getParameter("id");
				String comment = request.getParameter("comment");
				
				System.out.println(articleNum);
				System.out.println(id);
				System.out.println(comment);
				
				result = rDao.addReply0(articleNum, id, comment);
				
				if( result == 0) {
					out.print("success");
				} else {
					out.print("fail");
				}
				return;
				
			} else if (action.equals("/addreply1.do")) {
				
				int result;
				response.setContentType("application/x-json; charset=UTF-8");
		        PrintWriter out = response.getWriter();
		        // 기사번호 댓글번호 아이디 내용을 받음
				String articleNum = request.getParameter("articleNum");
				String replyNum = request.getParameter("replyNum");
				String id = request.getParameter("id");
				String comment = request.getParameter("comment");
				// 댓글을 추가함
				result = rDao.addReply1(articleNum, replyNum, id, comment);
				
				if( result == 0) {
					out.print("success");
				} else {
					out.print("fail");
				}
				return;
				
			} else if (action.equals("/getgood.do")) {
				
				int result;
				response.setContentType("application/x-json; charset=UTF-8");
		        PrintWriter out = response.getWriter();
				String reNum = request.getParameter("replyNum");
				
				result = rDao.getGood(reNum);
				
				if( result == 0) {
					out.print("success");
				} else {
					out.print("fail");
				}
				return;
				
			} else if (action.equals("/getbad.do")) {
				
				int result;
				response.setContentType("application/x-json; charset=UTF-8");
		        PrintWriter out = response.getWriter();
				String reNum = request.getParameter("replyNum");
				
				result = rDao.getBad(reNum);
				
				if( result == 0) {
					out.print("success");
				} else {
					out.print("fail");
				}
				return;
			} else if (action.equals("/deletetype0.do")) {
				
				int result;
				response.setContentType("application/x-json; charset=UTF-8");
		        PrintWriter out = response.getWriter();
				String reNum = request.getParameter("replyNum");
				
				result = rDao.deleteType0(reNum);
				
				System.out.println(result);
				
				if( result == 0) {
					out.print("success");
				} else {
					out.print("fail");
				}
				return;
			} else if (action.equals("/deletetype1.do")) {
				
				int result;
				response.setContentType("application/x-json; charset=UTF-8");
		        PrintWriter out = response.getWriter();
		        
				String reNum = request.getParameter("replyNum");
				String parentNum = request.getParameter("parentNum");
				
				result = rDao.deleteType1(reNum, parentNum);
				
				System.out.println(result);
				
				if( result == 0) {
					out.print("success");
				} else {
					out.print("fail");
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
