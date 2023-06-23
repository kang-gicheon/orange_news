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
		String nextPage = "";		//action(request한 url) 값 받아내서 dispatch 방식으로 인클루드하기 위한 String 변수 
		
		MemberVO memberVO = new MemberVO();

		//쿠키와 세션
		Cookie[] cookies = request.getCookies(); // 클라이언트로부터 전송된 모든 쿠키 가져오기
		Cookie loginCookieId = null;		//로그인 값을 저장하는 Cookie 변수
		Cookie loginCookieRep = null;		//member 레코드(계정)의 rep 컬럼값(기자/일반 계정 판별)을 저장하는 Cookie 변수
		HttpSession session = request.getSession();	//세션
		
		request.setCharacterEncoding("utf-8");	//request 인코딩
		response.setContentType("text/html; charset=utf-8");	//response할 데이터 인코딩 처리
		String action = request.getPathInfo();				//request한 url
		System.out.println("action: " + action);
		
		
		/* 주의: ;;.jsp;;이 입력된 주석은 해당 jsp페이지에서 요청되었음을 표시 */
		try {
			if(action==null) {			//개인 계정 페이지 (/mypage.do로 이동)
				PrintWriter pw = response.getWriter();		//script문을 response하여 웹에서 실행
				pw.print("<script>"	+ " location.href='"
									+ request.getContextPath()
									+ "/member/mypage.do"	//dispatch가 아닌 리다이렉트와 유사한 방식으로 action 값에 url 부여
									+ "';"
									+ "</script>");
				return;
			}else if(action.equals("/mypage.do")) {			//개인 계정 페이지 ;;/test/mainPage.jsp;;
				manageCookieId(cookies, memberVO);	//로그인 확인
				if(memberVO.getId() == null) {		//로그아웃 상태일 시 실행되는 조건문
					PrintWriter pw = response.getWriter();
					pw.print("<script>" + " alert('로그인 화면으로 이동합니다');"	//로그아웃 상태이므로 로그인 화면으로 넘어간다는 것을 알려주는 알림창
										+ " location.href='"		
										+ request.getContextPath()
										+ "/member/loginForm.do"		//로그인 페이지로 이동
										+ "';"
										+ "</script>");
					return;
				}
				memberDAO.getMemberInfo(memberVO);	//로그인 된 계정의 정보를 불러옴
				request.setAttribute("memberVO", memberVO);		//request에 불러온 계정 데이터 입력
				nextPage="/myPage/myPage.jsp";
			}else if(action.equals("/join.do")) {		//회원가입 페이지
				manageCookieId(cookies, memberVO);	//로그인 확인
				if(memberVO.getId() != null ) {		//로그인 상태일 경우 실행되는 조건문
					PrintWriter pw = response.getWriter();
					pw.write("<script>" + "alert('로그인 중 입니다.');"	//로그인 상태를 알리는 알림창
									+ " location.href='"
									+ request.getContextPath()
									+ "/member/mypage.do"		//개인 계정 페이지로 이동
									+ "';"
									+ "</script>");
					return;
				}
				nextPage="/sign_in/join.jsp";	//회원가입 페이지로 이동
			}else if(action.equals("/joining.do")) {		//회원가입 절차 진행		;;/sign_in/join.jsp;;
				String id=request.getParameter("id");		//jsp페이지에서 보낸 id 속성의 데이터
				String pwd=request.getParameter("pwd");		//jsp페이지에서 보낸 pwd 속성의 데이터
				String name=request.getParameter("name");	//jsp페이지에서 보낸 name 속성의 데이터
				int rep=0;		//rep컬럼 값 부여 = 회원가입시 일반 계정이 디폴트
				String pnum=request.getParameter("pnum");	//jsp페이지에서 보낸 pnum 속성의 데이터
				String email=request.getParameter("email");	//jsp페이지에서 보낸 pnum 속성의 데이터
				System.out.println("아이디: "+id);
				System.out.println("비밀번호: "+pwd);
				System.out.println("이름: "+ name);
				System.out.println("기자계정값: "+rep);
				System.out.println("휴대폰번호: "+pnum);
				System.out.println("이메일: "+email);
				memberVO.setId(id);			//id 값 부여
				memberVO.setPwd(pwd);		//pwd 값 부여
				memberVO.setName(name);		//name 값 부여
				memberVO.setReporter(rep);	//reporter 값 부여
				memberVO.setPnum(pnum);		//pnum 값 부여
				memberVO.setEmail(email);	//email 값 부여
				
				boolean isjoined = memberDAO.join(memberVO);	//회원가입 실행하여 그 값을 isjoined에 부여
				
				//값이 false인 경우
				if(!isjoined) {
					PrintWriter pw = response.getWriter();
					pw.print("<script>" + "alert('이미 존재하는 계정입니다');"	//이미 존재하는 계정임을 알리는 알림창
										+ " location.href='"
										+ request.getContextPath()
										+ "/member/join.do"			//회원가입 페이지로 다시 이동
										+ "';"
										+ "</script>");
					return;
				}
				
				//값이 true인 경우
				PrintWriter pw = response.getWriter();
				pw.print("<script>" + "alert('회원가입을 축하드립니다.');"		//회원가입이 완료되었다는 알림창
									+ " location.href='"				
									+ request.getContextPath()
									+ "/member/loginForm.do"			//로그인 페이지로 이동
									+ "';"
									+ "</script>");
				return;
				
			}else if(action.equals("/loginForm.do")) {		//로그인 입력 페이지
				clearData(memberVO);
				manageCookieId(cookies, memberVO);	//쿠키로 구운 로그인 값 확인
				System.out.println("로그인 중인 아이디: " + memberVO.getId());
				if(memberVO.getId() != null ) {		//로그인 중인 경우
					PrintWriter pw = response.getWriter();
					pw.write("<script>" + "alert('로그인 중 입니다.');"	//로그인 중이라는 알림창 보내기
										+ " location.href='"
										+ request.getContextPath()
										+ "/news"					//메인페이지로 이동
										+ "';"
										+ "</script>");
					return;
				}
				nextPage="/sign_in/sign_in.jsp";	//로그인 페이지로 이동
			}else if(action.equals("/login.do")) {		//로그인 데이터 받기
				String id=request.getParameter("id");
				String pwd=request.getParameter("pwd");
				
				memberVO.setId(id);
				memberVO.setPwd(pwd);
				boolean loginValue=memberDAO.login(memberVO);
				System.out.println("로그인 값: " + loginValue);
				
				if(loginValue) {			//로그인 성공시
					request.setAttribute("memberVO", memberVO);
					
					loginCookieId = new Cookie("loginId", memberVO.getId());
					loginCookieRep = new Cookie("reporter", String.valueOf(memberVO.getReporter()));
					loginCookieId.setPath("/");
					loginCookieRep.setPath("/");
					loginCookieId.setMaxAge(60 * 60 * 24);
					loginCookieRep.setMaxAge(60 * 60 * 24);
					response.addCookie(loginCookieId);
					response.addCookie(loginCookieRep);
					
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
				loginCookieId = null;
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
				System.out.println("id:"+id + " / new password:" + pwd);
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
	
	//쿠키로 구운 id값을 확인하고 불러오는 메서드(로그인 확인)
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
	
	//접속 포트에 있는 모든 쿠키값을 삭제하는 메서드 
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
	
	//member의 VO 데이터를 비우는 메서드, 코드 실행 시 의도한 데이터 처리를 위한 안전장치
	private void clearData(MemberVO memberVO){
		memberVO.setId(null);
		memberVO.setName(null);
		memberVO.setPwd(null);
		memberVO.setPnum(null);
		memberVO.setEmail(null);
	}
}
