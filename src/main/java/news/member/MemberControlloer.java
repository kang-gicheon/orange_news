package news.member;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class MemberControlloer
 */
@WebServlet("/member/*")
public class MemberControlloer extends HttpServlet {
	private static final long serialVersionUID = 1L;
	MemberDAO memberDAO;
	
	@Override
	public void init(ServletConfig config) throws ServletException {
		memberDAO = new MemberDAO();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doHandle(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doHandle(request, response);
	}
	
	private void doHandle(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String nextPage = "";
		MemberVO memberVO = new MemberVO();
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8");
		String action = request.getPathInfo();
		System.out.println("action: " + action);
		try {
			if(action==null) {			//개인 계정 페이지
				
				nextPage="/";
			}else if(action.equals("/")) {		//틀
				
				nextPage="/";
			}else if(action.equals("/")) {		//틀
				
				nextPage="/";
			}else if(action.equals("/")) {		//틀
				
				nextPage="/";
			}else if(action.equals("/")) {		//회원가입
				
				nextPage="/";
			}else if(action.equals("/")) {		//회원수정
				
				nextPage="/";
			}else if(action.equals("/login.do")) {		//로그인입력 창 보내기
				
				nextPage="/sign_in/sign_in.jsp";
			}else if(action.equals("/login.do?id=*")) {		//로그인 데이터 받기
				System.out.println("testlogintest");
				String id=request.getParameter("id");
				String pwd=request.getParameter("pwd");
				
				memberVO.setId(id);
				memberVO.setPwd(pwd);
				boolean loginValue=memberDAO.login(memberVO);
				System.out.println("loginValue: " + loginValue);
				if(loginValue==true) {			//로그인 성공시
					request.setAttribute("memberVO", memberVO);
					nextPage="/";	//로그인 성공시 articleController에 넘길 예정
				}else if(loginValue == false) {
					memberVO.setId(null);
					memberVO.setName(null);
					memberVO.setPwd(null);
					memberVO.setEmail(null);
					nextPage="/sign_in/sign_in.jsp";
					PrintWriter pw = response.getWriter();
					pw.print("<script>" + "alert('로그인에 실패하였습니다');"
										+ " location.href='"
										+ request.getContextPath()
										+ "/member/login.do"
										+ "';"
										+ "</script>");
					return;
				}
			}else if(action.equals("/mypage.do")) {
				if(memberVO.getId()==null) {
					PrintWriter pw = response.getWriter();
					pw.print("<script>" + " alert('로그인 화면으로 이동합니다');"
							+ " location.href='"
							+ request.getContextPath()
							+ "/member/login.do"
							+ "';"
							+ "</script>");
					return;
				}
				nextPage="/myPage/myPage.jsp";
				
			
			}
			
			RequestDispatcher dispatch = request.getRequestDispatcher(nextPage);
			dispatch.forward(request, response);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
