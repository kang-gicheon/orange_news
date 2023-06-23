package news.member;

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
		Cookie loginCookie;
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
			}else if(action.equals("/findid.do")) {		//아이디 찾기
				
				nextPage="/find_account/find_id.jsp";
			}else if(action.equals("/resultid.do")) {		//아이디찾기 결과
				String name=request.getParameter("name");
				String pnum=request.getParameter("pnum");
				memberVO.setName(name);
				memberVO.setPnum(pnum);
				boolean isId = memberDAO.findId(memberVO);
				if(!isId) {
					PrintWriter pw = response.getWriter();
					pw.write("<script>" + "alert('회원정보가 없습니다.');"
							+ " location.href='"
							+ request.getContextPath()
							+ "/member/login.do"
							+ "';"
							+ "</script>");
					return;
				}
				request.setAttribute("memberVO", memberVO);
				nextPage="/account_findresult/account_id.jsp";
			}else if(action.equals("/findpwd.do")) {		//비번 찾기
				
				nextPage="/find_account/find_password.jsp";
			}else if(action.equals("/resultpwd.do")) {		//비번 찾기 결과
				String id=request.getParameter("id");
				String pnum=request.getParameter("pnum");
				memberVO.setId(id);
				memberVO.setPnum(pnum);
				boolean isPwd = memberDAO.findPwd(memberVO);
				if(!isPwd) {
					PrintWriter pw = response.getWriter();
					pw.write("<script>" + "alert('입력하신 정보가 다릅니다.');"
							+ " location.href='"
							+ request.getContextPath()
							+ "/member/login.do"
							+ "';"
							+ "</script>");
					return;
				}
				request.setAttribute("memberVO", memberVO);
				nextPage="/account_findresult/account_pwd.jsp";
			}else if(action.equals("/")) {		//틀
				
				nextPage="/";
			}else if(action.equals("/join.do")) {		//회원가입페이지로 이동
				
				request.setAttribute("memberVO", memberVO);
				nextPage="/sign_in/join.jsp";
			}else if(action.equals("/joining.do")) {		//회원가입 절차 진행
				String id=request.getParameter("id");
				String pwd=request.getParameter("pwd");
				String name=request.getParameter("name");
				int rep=0;		//기자 계정 부여에 대해서
				String pnum=request.getParameter("pnum");
				String email=request.getParameter("email");
				System.out.println("아이디: "+id);
				System.out.println("비밀번호: "+pwd);
				System.out.println("이름: "+ name);
				System.out.println("기자계정값: "+rep);
				System.out.println("휴대폰번호: "+pnum);
				System.out.println("이메일: "+email);
				memberVO.setId(id);
				memberVO.setPwd(pwd);
				memberVO.setName(name);
				memberVO.setReporter(rep);
				memberVO.setPnum(pnum);
				memberVO.setEmail(email);
				
				boolean isjoined = memberDAO.join(memberVO);
				if(!isjoined) {
					PrintWriter pw = response.getWriter();
					pw.print("<script>" + "alert('정보를 잘못 입력하였습니다');"
										+ " location.href='"
										+ request.getContextPath()
										+ "/member/join.do"
										+ "';"
										+ "</script>");
					return;
				}
				
				PrintWriter pw = response.getWriter();
				pw.print("<script>" + "alert('회원가입을 축하드립니다.');"
									+ " location.href='"
									+ request.getContextPath()
									+ "/member/login.do"
									+ "';"
									+ "</script>");
				return;
				
//				nextPage="/sign_in/sign_in.jsp";
			}else if(action.equals("/modmember.do")) {		//회원수정
				
				request.setAttribute("memberVO", memberVO);
				nextPage="/";
			}else if(action.equals("/login.do")) {		//로그인입력 창 보내기
				Cookie[] cookies = request.getCookies(); // 클라이언트로부터 전송된 모든 쿠키 가져오기

				if (cookies != null) {
				    for (Cookie cookie : cookies) {
				    	memberVO.setId(cookie.getName());
				    	String id = memberDAO.searchsession(memberVO);
				    	memberVO.setId(id);
				    	System.out.println("쿠키 확인용");
				        if (memberVO.getId() != null) {
				            break;
				        }
				    }
				}
				System.out.println("로그인 중인 아이디: "+memberVO.getId());
				if(memberVO.getId() != null ) {
					PrintWriter pw = response.getWriter();
					pw.write("<script>" + "alert('로그인 중 입니다.');"
									+ " location.href='"
									+ request.getContextPath()
									+ "/member/mypage.do"
									+ "';"
									+ "</script>");
					return;
				}

				nextPage="/sign_in/sign_in.jsp";
			}else if(action.equals("/logintest.do")) {		//로그인 데이터 받기
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
					nextPage="/myPage/myPage.jsp";	//메인 페이지로 보낼 예정
				}else {
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
			}else if(action.equals("/mypage.do")) {
				System.out.println("id: " + memberVO.getId());
//				if(loginCookie == null) {
//					PrintWriter pw = response.getWriter();
//					pw.print("<script>" + " alert('로그인 화면으로 이동합니다');"
//							+ " location.href='"
//							+ request.getContextPath()
//							+ "/member/login.do"
//							+ "';"
//							+ "</script>");
//					return;
//				}
				request.setAttribute("memberVO", memberVO);
				nextPage="/myPage/myPage.jsp";
			}else if(action.equals("/logout.do")) {		//로그아웃
				
				memberVO.setId(null);
				memberVO.setName(null);
				memberVO.setPwd(null);
				memberVO.setPnum(null);
				memberVO.setEmail(null);
				
				
				System.out.println("logOut confirm - id: ");
				
				PrintWriter pw = response.getWriter();
				pw.print("<script>" + " alert('로그아웃 되었습니다.');"
						+ " location.href='"
						+ request.getContextPath()
						+ "/member/login.do"
						+ "';"
						+ "</script>");
				return;
			}
			
			RequestDispatcher dispatch = request.getRequestDispatcher(nextPage);
			dispatch.forward(request, response);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
