package member;

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

/**
 * Servlet implementation class MemberControlloer
 */
@WebServlet("/member/*")
public class MemberController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	MemberDAO memberDAO;
	MemberService memberService;
	
	@Override
	public void init(ServletConfig config) throws ServletException {
		memberDAO = new MemberDAO();
		memberService = new MemberService();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doHandle(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doHandle(request, response);
	}
	
	private void doHandle(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		MemberVO memberVO = new MemberVO();

		//쿠키
		Cookie[] cookies = request.getCookies(); // 클라이언트로부터 전송된 모든 쿠키 가져오기
		Cookie loginCookie = null;
		
		//로그인 값을 가진 쿠키 전송 
		HttpSession session = request.getSession();
		
		String nextPage = "";
		
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8");
		String action = request.getPathInfo();
		System.out.println("action: " + action);
		
		try {
			if(action==null) {			//개인 계정 페이지
				
				nextPage="/myPage/myPage.jsp";
			}else if(action.equals("/mypage.do")) {			//개인 계정페이지
				manageCookieId(cookies, memberVO);
				if(memberVO.getId() == null) {
					PrintWriter pw = response.getWriter();
					pw.print("<script>" + " alert('로그인 화면으로 이동합니다');"
										+ " location.href='"
										+ request.getContextPath()
										+ "/member/loginForm.do"
										+ "';"
										+ "</script>");
					return;
				}
				memberDAO.getMemberInfo(memberVO);
				request.setAttribute("memberVO", memberVO);
				nextPage="/myPage/myPage.jsp";
			}else if(action.equals("/join.do")) {		//회원가입페이지로 이동
				manageCookieId(cookies, memberVO);
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
					pw.print("<script>" + "alert('이미 존재하는 계정입니다');"
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
									+ "/member/loginForm.do"
									+ "';"
									+ "</script>");
				return;
				
			}else if(action.equals("/loginForm.do")) {		//로그인입력 창 보내기
				clearData(memberVO);
				manageCookieId(cookies, memberVO);
				System.out.println("로그인 중인 아이디: " + memberVO.getId());
				if(memberVO.getId() != null ) {
					PrintWriter pw = response.getWriter();
					pw.write("<script>" + "alert('로그인 중 입니다.');"
										+ " location.href='"
										+ request.getContextPath()
										+ "/news"
										+ "';"
										+ "</script>");
					return;
				}
				nextPage="/sign_in/sign_in.jsp";
			}else if(action.equals("/login.do")) {		//로그인 데이터 받기
				String id=request.getParameter("id");
				String pwd=request.getParameter("pwd");
				
				memberVO.setId(id);
				memberVO.setPwd(pwd);
				boolean loginValue=memberDAO.login(memberVO);
				System.out.println("로그인 값: " + loginValue);
				
				if(loginValue) {			//로그인 성공시
					request.setAttribute("memberVO", memberVO);
					
					loginCookie = new Cookie("loginId", memberVO.getId());
					loginCookie.setPath("/");
					loginCookie.setMaxAge(60 * 60 * 24);
					response.addCookie(loginCookie);
					
					session=request.getSession();
					session.setAttribute("loginIdSess", memberVO.getId());
					
					PrintWriter pw = response.getWriter();
					pw.write("<script>"	+ " location.href='"
										+ request.getContextPath()
										+ "/news/mainpage.do"
										+ "';"
										+ "</script>");
					return;
				}
				else {						//로그인 실패시
					clearData(memberVO);
					PrintWriter pw = response.getWriter();
					pw.print("<script>" + "alert('로그인에 실패하였습니다');"
										+ " location.href='"
										+ request.getContextPath()
										+ "/member/loginForm.do"
										+ "';"
										+ "</script>");
					return;
				}
				
			}else if(action.equals("/logout.do")) {		//로그아웃
				clearData(memberVO);
				loginCookie = null;
				deleteCookie(cookies, response);
				
				System.out.println("logOut confirm - id value: " + memberVO.getId());
				
				session.removeAttribute("loginIdSess");
				PrintWriter pw = response.getWriter();
				pw.print("<script>" + " alert('로그아웃 되었습니다.');"
									+ " location.href='"
									+ request.getContextPath()
									+ "/news"
									+ "';"
									+ "</script>");
				return;
			}else if(action.equals("/modmember.do")) {		//회원수정 페이지로 이동
				clearData(memberVO);
				manageCookieId(cookies, memberVO);
				System.out.println("로그인 중인 아이디: "+memberVO.getId());
				if(memberVO.getId() == null ) {
					PrintWriter pw = response.getWriter();
					pw.write("<script>" + "alert('로그아웃 상태입니다.');"
									+ " location.href='"
									+ request.getContextPath()
									+ "/member/loginForm.do"
									+ "';"
									+ "</script>");
					return;
				}
				request.setAttribute("memberVO", memberVO);
				nextPage="/modconfirm/confirmForm.jsp";			//하기 전에 계정 확인
			}else if(action.equals("/modmemberForm.do")) {		//회원정보 수정 페이지
				manageCookieId(cookies, memberVO);
				String id = memberVO.getId();
				String pwd = request.getParameter("pwd");
				memberVO.setPwd(pwd);
				System.out.println("id:"+id + "/ password:" + pwd);
				boolean loginValue=memberDAO.login(memberVO);
				if(!loginValue) {
					PrintWriter pw = response.getWriter();
					pw.write("<script>"	+ "alert('비밀번호가 맞지 않습니다.');"
										+ "location.href='"
										+ request.getContextPath()
										+ "/member/mypage.do"
										+ "';"
										+ "</script>");
					return;
				}
				request.setAttribute("memberVO", memberVO);
				nextPage="/modconfirm/modmemberForm.jsp";
			}else if(action.equals("/mod.do")) {		//회원정보 수정 데이터 처리
				manageCookieId(cookies, memberVO);
				String id = memberVO.getId();
				String pwd = request.getParameter("pwd");
				String name = request.getParameter("name");
				String pnum = request.getParameter("pnum");
				String email = request.getParameter("email");
				memberVO.setPwd(pwd);
				memberVO.setName(name);
				memberVO.setPnum(pnum);
				memberVO.setEmail(email);
				System.out.println("id:"+id + "/ new password:" + pwd);
				if(memberVO.getId() == null ) {
					PrintWriter pw = response.getWriter();
					pw.write("<script>" + "alert('로그아웃 상태입니다.');"
										+ " location.href='"
										+ request.getContextPath()
										+ "/member/loginForm.do"
										+ "';"
										+ "</script>");
					return;
				}
				memberDAO.editInfo(memberVO);
				request.setAttribute("memberVO", memberVO);

				PrintWriter pw = response.getWriter();
				pw.write("<script>" + "alert('회원정보가 수정되었습니다.');"
									+ " location.href='"
									+ request.getContextPath()
									+ "/member/mypage.do"
									+ "';"
									+ "</script>");
				return;
			}else if(action.equals("/findid.do")) {		//아이디 찾기
				manageCookieId(cookies, memberVO);
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
				nextPage="/find_account/find_id.jsp";
			}else if(action.equals("/resultid.do")) {		//아이디 찾기 결과
				clearData(memberVO);
				manageCookieId(cookies, memberVO);
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
							+ "/member/loginForm.do"
							+ "';"
							+ "</script>");
					return;
				}
				request.setAttribute("memberVO", memberVO);
				nextPage="/account_findresult/account_id.jsp";
			}else if(action.equals("/findpwd.do")) {		//비번 찾기
				clearData(memberVO);
				manageCookieId(cookies, memberVO);
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
				
				nextPage="/find_account/find_password.jsp";
			}else if(action.equals("/resultpwd.do")) {		//비번 찾기 결과
				clearData(memberVO);
				manageCookieId(cookies, memberVO);
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
							+ "/member/loginForm.do"
							+ "';"
							+ "</script>");
					return;
				}
				request.setAttribute("memberVO", memberVO);
				nextPage="/account_findresult/account_pwd.jsp";
			}else if(action.equals("/deleteAccount.do")) {		//회원탈퇴
				clearData(memberVO);
				manageCookieId(cookies, memberVO);
				String id = memberVO.getId();
				memberDAO.withdraw(id);
				memberVO.setId(id);
				session=request.getSession();
				session.removeAttribute("loginIdSess");
				deleteCookie(cookies, response);
				PrintWriter pw = response.getWriter();
				pw.write("<script>" + "alert('회원탈퇴가 완료되었습니다.');"
									+ " location.href='"
									+ request.getContextPath()
									+ "/member/loginForm.do"
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
				
				System.out.println("삭제할 쿠키: "+cookie.getName());
					cookie.setPath("/");
					cookie.setMaxAge(0);
					response.addCookie(cookie);
					
			}
		}
	}
	
	private void clearData(MemberVO memberVO){
		memberVO.setId(null);
		memberVO.setName(null);
		memberVO.setPwd(null);
		memberVO.setPnum(null);
		memberVO.setEmail(null);
	}
}
