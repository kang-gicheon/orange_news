package cookie;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import member.MemberDAO;
import member.MemberVO;

/**
 * Servlet implementation class Cookies
 */
//@WebServlet("/*")
public class Cookies extends HttpServlet {
	private static final long serialVersionUID = 1L;
	MemberDAO memberDAO;
	
	@Override
	public void init(ServletConfig config) throws ServletException {
		memberDAO = new MemberDAO();
		System.out.println("Cookies Servlet initiated");
	}


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doHandle(request, response);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doHandle(request, response);
	}
	
	private void doHandle(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String nextPage= "";
		Cookie[] cookies = request.getCookies();
		Cookie loginCookie;
		
		HttpSession session;
		
		MemberVO memberVO = new MemberVO();
		
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8");
		String action = request.getPathInfo();
		System.out.println("action: " + action);
		
		try {
			if(action.equals("/login.do")) {
				String id=request.getParameter("id");
				String pwd=request.getParameter("pwd");
				
				memberVO.setId(id);
				memberVO.setPwd(pwd);
				boolean loginValue=memberDAO.login(memberVO);
				System.out.println("로그인 값: " + loginValue);
				if(loginValue) {			//로그인 성공시
					request.setAttribute("memberVO", memberVO);
					
					loginCookie = new Cookie("loginId", memberVO.getId());
					loginCookie.setMaxAge(60 * 60 * 24);
					response.addCookie(loginCookie);
					
					session=request.getSession();
					session.setAttribute("loginIdSess", memberVO.getId());
					nextPage="/interchange/tonews.jsp";
					
//					nextPage="/interchange/tologincookie";
				}
				
				else {						//로그인 실패시
					memberVO.setId(null);
					memberVO.setName(null);
					memberVO.setPwd(null);
					memberVO.setPnum(null);
					memberVO.setEmail(null);
					PrintWriter pw = response.getWriter();
					pw.print("<script>" + "alert('로그인에 실패하였습니다');"
										+ " location.href='"
										+ request.getContextPath()
										+ "/member/login.do"
										+ "';"
										+ "</script>");
					return;
				}
			}else if(action.equals("/")) {		//틀
				
				nextPage="/";
			}else if(action.equals("/")) {		//틀
				
				nextPage="/";
			}else if(action.equals("/logoutCookie.do")) {
				deleteCookie(cookies, response);
				System.out.println("logOut confirm - id value: " + memberVO.getId());
				
				PrintWriter pw = response.getWriter();
				pw.print("<script>" + " alert('로그아웃 되었습니다.');"
						+ " location.href='"
						+ request.getContextPath()
						+ "/news"
						+ "';"
						+ "</script>");
				return;
				
			}else if(action.equals("/")) {		//틀
				
				nextPage="/";
			}else if(action.equals("/")) {		//틀
				
				nextPage="/";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		response.sendRedirect(nextPage);
	}

	private void manageCookieId(Cookie cookies[], MemberVO memberVO) {
		if (cookies != null) {
		    for (Cookie cookie : cookies) {
		    	String cookieName = cookie.getName();
		    	if(cookieName.equals("loginId")) {
		    		System.out.println("로그인 상태 확인 중");
		    		System.out.println(cookieName+": "+cookie.getValue());
		    		memberVO.setId(cookie.getValue());
		    	}	
		    }
		}
	}
	
	
	
	private void deleteCookie(Cookie cookies[], HttpServletResponse response) throws ServletException, IOException {
		if(cookies != null) {
			for(Cookie cookie : cookies) {
				cookie.setMaxAge(0);
				response.addCookie(cookie);
			}
		}
	}
}
