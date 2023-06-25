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
	MemberService memberService;
	
	@Override
	public void init(ServletConfig config) throws ServletException {
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
		Cookie loginCookieRep = null;		//member 레코드(계정)의 rep 컬럼값(일반[0]/기자[1] 계정 판별)을 저장하는 Cookie 변수
		HttpSession session = request.getSession();	//세션
		
		request.setCharacterEncoding("utf-8");	//request 인코딩
		response.setContentType("text/html; charset=utf-8");	//response할 데이터 인코딩 처리
		String action = request.getPathInfo();				//request한 url
		System.out.println("action: " + action);
		
		/* 주의: ;;.jsp;;이 입력된 주석은 해당 줄의 action값이 해당 jsp페이지의 요청에 의해 부여되었음 표시 */
		try {
			if(action==null) {			//action> 개인 계정 페이지 (/mypage.do로 이동)
				PrintWriter pw = response.getWriter();		//script문을 response하여 웹에서 실행
				pw.print("<script>"	+ " location.href='"
									+ request.getContextPath()
									+ "/member/mypage.do"	//dispatch가 아닌 리다이렉트와 유사한 방식으로 action 값에 url 부여 유도
									+ "';"
									+ "</script>");
				return;
			}else if(action.equals("/mypage.do")) {		//action> 개인 계정 페이지 ;;/main.jsp;;
				manageCookieId(cookies, memberVO);	//쿠키로 구운 로그인 값 확인
				
				//로그아웃 상태일 시 실행되는 조건문
				if(memberVO.getId() == null) {	
					PrintWriter pw = response.getWriter();
					pw.print("<script>" + " alert('로그인 화면으로 이동합니다');"	//로그아웃 상태이므로 로그인 화면으로 넘어간다는 것을 알려주는 알림창
										+ " location.href='"		
										+ request.getContextPath()
										+ "/member/loginForm.do"		//로그인 페이지로 이동
										+ "';"
										+ "</script>");
					return;
				}
				
				//로그인 상태일 떄
				memberService.getMemberInfo(memberVO);	//로그인 된 계정의 정보를 불러옴
				request.setAttribute("memberVO", memberVO);		//request에 불러온 계정 데이터 입력
				nextPage="/myPage/myPage.jsp";		//개인 계정 페이지로 dispatch
			}else if(action.equals("/join.do")) {		//action> 회원가입 페이지	;;/sign_in/sign_in.jsp;;
				manageCookieId(cookies, memberVO);	//쿠키로 구운 로그인 값 확인
				//로그인 상태일 경우 실행되는 조건문
				if(memberVO.getId() != null ) {		
					PrintWriter pw = response.getWriter();
					pw.write("<script>" + "alert('로그인 중 입니다.');"	//로그인 상태를 알리는 알림창
									+ " location.href='"
									+ request.getContextPath()
									+ "/member/mypage.do"		//개인 계정 페이지로 이동
									+ "';"
									+ "</script>");
					return;
				}
				
				//로그아웃 상태일 때
				nextPage="/sign_in/join.jsp";	//회원가입 페이지로 dispatch
			}else if(action.equals("/joining.do")) {		//action> 회원가입 절차 진행		;;/sign_in/join.jsp;;
				String id=request.getParameter("id");		//jsp페이지에서 보낸 name=id의 데이터
				String pwd=request.getParameter("pwd");		//jsp페이지에서 보낸 name=pwd의 데이터
				String name=request.getParameter("name");	//jsp페이지에서 보낸 name=name의 데이터
				int rep=0;		//rep컬럼 값 부여 = 회원가입시 일반[0] 계정이 디폴트
				String pnum=request.getParameter("pnum");	//jsp페이지에서 보낸 name=pnum의 데이터
				String email=request.getParameter("email");	//jsp페이지에서 보낸 name=pnum의 데이터
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
				
				boolean isjoined = memberService.join(memberVO);	//회원가입 실행하여 그 값을 isjoined에 부여
				
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
									+ "/news/mainpage.do"			//로그인 페이지로 이동
									+ "';"
									+ "</script>");
				return;
				
			}else if(action.equals("/loginForm.do")) {		//action> 로그인 입력 페이지		;;/main.jsp;;
				clearData(memberVO);
				manageCookieId(cookies, memberVO);	//쿠키로 구운 로그인 값 확인
				System.out.println("로그인 중인 아이디: " + memberVO.getId());
				
				//로그인 상태인 경우
				if(memberVO.getId() != null ) {
					PrintWriter pw = response.getWriter();
					pw.write("<script>" + "alert('로그인 중 입니다.');"	//로그인 중이라는 알림창 보내기
										+ " location.href='"
										+ request.getContextPath()
										+ "/news"					//메인페이지로 이동
										+ "';"
										+ "</script>");
					return;
				}
				nextPage="/sign_in/sign_in.jsp";	//로그인 페이지로 dispatch
			}else if(action.equals("/login.do")) {		//action> 로그인 데이터 받기		;;/main.jsp;;
				String id=request.getParameter("id");	//jsp페이지에서 보낸 name=id의 데이터
				String pwd=request.getParameter("pwd");	//jsp페이지에서 보낸 name=pwd의 데이터
				String refer =request.getParameter("refer");	//jsp페이지에서 보낸 name=refer
				
				System.out.println("refer: "+refer);
				
				memberVO.setId(id);		//id값 부여
				memberVO.setPwd(pwd);	//pwd값 부여 - 비밀번호
				boolean loginValue=memberService.login(memberVO);	//로그인 시도와 그 결과값을 loginValue 변수에 부여
				System.out.println("로그인 값: " + loginValue);	
				
				//로그인 성공시 (loginValue==true)
				if(loginValue) {			
					String _id=memberVO.getId();	//쿠키값 전송을 위한 _id
					String _rep=String.valueOf(memberVO.getReporter());		//쿠키값 전송을 위한 _rep #쿠키는 오로지 문자열만 받으므로 String으로 파싱
					loginCookieId = new Cookie("loginId", _id);		//loginCookieId(아이디 쿠키) 값 부여 {key: loginId, value: (_id 값)}
					loginCookieRep = new Cookie("reporter", _rep);	//loginCookieRep(일반[0]/기자[1] 계정 쿠키) 값 부여 {key: reporter, value: (_rep 값)}
					loginCookieId.setPath("/");		//아이디 쿠키를 저장할 path 설정(접속포트의 모든 url에 공유)
					loginCookieRep.setPath("/");	//계정 쿠키를 저장할 path 설정(접속포트의 모든 url에 공유)
					loginCookieId.setMaxAge(60 * 60 * 24);	//쿠키 유지 시간 설정
					loginCookieRep.setMaxAge(60 * 60 * 24);
					response.addCookie(loginCookieId);		//아이디 쿠키 굽기
					response.addCookie(loginCookieRep);		//계정 쿠키 쿠키
					
					session=request.getSession();	//세션 객체 생성
					session.setAttribute("loginIdSess", _id);	//key가 loginIdSess이고 value가 _id 값인 세션 생성, 적용
					PrintWriter pw = response.getWriter();
					pw.write("<script>"	+ " location.href='"	//dispatch 방식이 아닌 페이지 연결을 위한 스크립트문 실행
										+ refer				//이전에 방문했던 페이지 url
										+ "';"
										+ "</script>");
					return;
				}
				//로그인 실패시(loginValue==false)
				else {						
					clearData(memberVO);
					PrintWriter pw = response.getWriter();
					pw.print("<script>" + "alert('로그인에 실패하였습니다');"	//로그인 실패 알림창 보내기
										+ " location.href='"
										+ request.getContextPath()
										+ "/member/loginForm.do"	//로그인 페이지로 이동
										+ "';"
										+ "</script>");
					return;
				}
			}else if(action.equals("/logout.do")) {		//action> 로그아웃		;;/main.jsp;;, ;;/myPage/myPage.jsp;;
				clearData(memberVO);
				deleteCookie(cookies, response);		//쿠키 삭제 메서드 실행
				loginCookieId = null;
				System.out.println("logOut confirm - id value: " + memberVO.getId());
				
				session.removeAttribute("loginIdSess");	//세션에 있는 로그인 값 삭제
				PrintWriter pw = response.getWriter();
				pw.print("<script>" + " alert('로그아웃 되었습니다.');"	//로그아웃을 알리는 알림창
									+ " location.href='"
									+ request.getContextPath()
									+ "/news"					//메인 페이지로 이동
									+ "';"
									+ "</script>");
				return;
			}else if(action.equals("/modmember.do")) {		// 회원정보 수정을 위한 계정 정보 확인	;;/myPage/myPage.jsp;;
				clearData(memberVO);
				manageCookieId(cookies, memberVO);		//쿠키로 구운 로그인 값 확인
				String id=memberVO.getId();			//manageCookieId() 메서드로 얻은 값 id에 부여
				System.out.println("로그인 중인 아이디: "+ id);
				if(id == null ) {		//로그아웃 상태일 시
					PrintWriter pw = response.getWriter();
					pw.write("<script>" + "alert('로그아웃 상태입니다.');"	//로그아웃 상태를 알리는 알림창 보내기
									+ " location.href='"
									+ request.getContextPath()
									+ "/member/loginForm.do"		//로그인 페이지로 이동
									+ "';"
									+ "</script>");
					return;
				}
				request.setAttribute("memberVO", memberVO);
				nextPage="/modconfirm/confirmForm.jsp";			//계정 확인 페이지로 dispatch
			}else if(action.equals("/modmemberForm.do")) {		//회원정보 수정 페이지	;;/modconfirm/confirmForm.jsp;;
				manageCookieId(cookies, memberVO);	//쿠키로 아이디값 확인
				String id = memberVO.getId();		//id에 값 부여
				String pwd = request.getParameter("pwd");	//jsp페이지로부터 name=pwd 값 받기(클라이언트가 입력한 비밀번호)
				memberVO.setPwd(pwd);	//VO의 전역변수 pwd(String)에 pwd 값 부여
				System.out.println("id:"+id + "/ password:" + pwd);
				boolean loginValue=memberService.login(memberVO);	//login() 메서드 리턴값을 loginValue에 대입 -> 계정 정보 확인
				
				//입력한 정보가 계정 정보와 불일치할 경우(loginValue==false)
				if(!loginValue) {
					PrintWriter pw = response.getWriter();
					pw.write("<script>"	+ "alert('비밀번호가 맞지 않습니다.');"	//입력한 정보가 맞지 않다는 알람창 보내기
										+ "location.href='"
										+ request.getContextPath()
										+ "/member/mypage.do"			//메인페이지로 이동
										+ "';"
										+ "</script>");
					return;
				}
				//입력한 정보가 계정 정보와 일차할 경우(loginValue==true)
				request.setAttribute("memberVO", memberVO);
				nextPage="/modconfirm/modmemberForm.jsp";	//회원정보 수정 페이지로 dispatch
			}else if(action.equals("/mod.do")) {		//회원정보 수정 데이터 처리	;;/modconfirm/modmemberForm;;
				manageCookieId(cookies, memberVO);		//현재 로그인되어 있는 쿠키값 가져오기
				String id = memberVO.getId();			//id에 로그인 id 대입
				String pwd = request.getParameter("pwd");		//jsp페이지에서 보낸 name=pwd의 데이터
				String name = request.getParameter("name");		//jsp페이지에서 보낸 name=name의 데이터
				String pnum = request.getParameter("pnum");		//jsp페이지에서 보낸 name=pnum의 데이터
				String email = request.getParameter("email");	//jsp페이지에서 보낸 name=email의 데이터
				memberVO.setPwd(pwd);		//pwd값 부여
				memberVO.setName(name);		//name값 부여
				memberVO.setPnum(pnum);		//pnum값 부여
				memberVO.setEmail(email);	//email값 부여
				System.out.println("id:"+id + " / new password:" + pwd);
				
				//로그인 쿠키값이 없을 경우 -> 로그아웃 상태
				if(memberVO.getId() == null ) {
					PrintWriter pw = response.getWriter();
					pw.write("<script>" + "alert('로그아웃 상태입니다.');"	//로그아웃 상태를 알리는 알림창 보내기
										+ " location.href='"
										+ request.getContextPath()
										+ "/member/loginForm.do"		//로그인 화면으로 보내기
										+ "';"
										+ "</script>");
					return;
				}
				
				//로그인 쿠키갑이 있을 경우(memberVO.getId()!=null) -> 로그인 상태
				memberService.editInfo(memberVO);			//jsp 페이지에서 입력한 데이터 반영
				request.setAttribute("memberVO", memberVO);	//수정된 정보 request에 담아내기

				PrintWriter pw = response.getWriter();
				pw.write("<script>" + "alert('회원정보가 수정되었습니다.');"	//회원정보가 수정이 정상적으로 수행되었음을 알리는 알림창 보내기
									+ " location.href='"
									+ request.getContextPath()
									+ "/member/mypage.do"				//개인 계정 페이지로 이동
									+ "';"
									+ "</script>");
				return;
			}else if(action.equals("/findid.do")) {		//아이디 찾기		;;/sign_in/sign_in.jsp;;, ;;/find_account/find_password.jsp;;
				manageCookieId(cookies, memberVO);		//현재 로그인 상태인지 쿠키값 확인
				System.out.println("로그인 중인 아이디: "+memberVO.getId());
				
				//로그인 상태일 경우
				if(memberVO.getId() != null ) {
					PrintWriter pw = response.getWriter();
					pw.write("<script>" + "alert('로그인 중 입니다.');"		//로그인 상태를 알리는 알림창 보내기
									+ " location.href='"
									+ request.getContextPath()
									+ "/member/mypage.do"				//개인 계정 페이지로 이동
									+ "';"
									+ "</script>");
					return;
				}
				
				//로그아웃 상태일 경우(memberVO.getId() == null)
				nextPage="/find_account/find_id.jsp";		//아이디 찾기 페이지로 dispatch
			}else if(action.equals("/resultid.do")) {		//아이디 찾기 결과		;;/find_account/find_id.jsp;;
				clearData(memberVO);
				manageCookieId(cookies, memberVO);		//현재 로그인 상태인지 쿠키값 확인
				System.out.println("로그인 중인 아이디: "+memberVO.getId());
				
				//로그인 상태일 경우
				if(memberVO.getId() != null ) {
					PrintWriter pw = response.getWriter();
					pw.write("<script>" + "alert('로그인 중 입니다.');"		//로그인 상태를 알리는 알림창 보내기
									+ " location.href='"
									+ request.getContextPath()
									+ "/member/mypage.do"				//개인 계정 페이지로 이동
									+ "';"
									+ "</script>");
					return;
				}
				
				//로그아웃 상태일 경우(memberVO.getId() == null)
				String name=request.getParameter("name");		//jsp페이지에서 보낸 name=name의 데이터
				String pnum=request.getParameter("pnum");		//jsp페이지에서 보낸 name=pnum의 데이터
				memberVO.setName(name);		//name 값 부여
				memberVO.setPnum(pnum);		//pnum 값 부여
				boolean isId = memberService.findId(memberVO);	//아이디 찾기 수행과 그 return 값을 idId에 부여
				
				//클라이언트가 찾는 회원 정보가 없을 경우(isId==false 인 경우)
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
				
				//클라이언트가 찾는 회원 정보가 있을 경우 (isId==true 인 경우)
				request.setAttribute("memberVO", memberVO);		//조회한 정보 request에 담아내기
				nextPage="/account_findresult/account_id.jsp";	//아이디 찾기 결과 페이지로 dispatch
			}else if(action.equals("/findpwd.do")) {		//비번 찾기		;;//account_findresult/account_id.jsp;;, ;;/find_account/find_id.jsp;;
				clearData(memberVO);
				manageCookieId(cookies, memberVO);		//현재 로그인 상태인지 쿠키값 확인
				System.out.println("로그인 중인 아이디: "+memberVO.getId());
				
				//로그인 상태인 경우
				if(memberVO.getId() != null ) {
					PrintWriter pw = response.getWriter();
					pw.write("<script>" + "alert('로그인 중 입니다.');"
									+ " location.href='"
									+ request.getContextPath()
									+ "/member/mypage.do"			//개인 계정 페이지로 이동
									+ "';"
									+ "</script>");
					return;
				}
				
				//로그아웃 상태인 경우
				nextPage="/find_account/find_password.jsp";	//비밀번호 찾기 페이지로 dispatch
			}else if(action.equals("/resultpwd.do")) {		//비번 찾기 결과		;;/find_account/find_password.jsp;;
				clearData(memberVO);
				manageCookieId(cookies, memberVO);		//현재 로그인 상태인지 쿠키값 확인
				System.out.println("로그인 중인 아이디: "+memberVO.getId());
				
				//로그인 상태인 경우
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
				
				//로그아웃 상태인 경우
				String id=request.getParameter("id");		//jsp페이지에서 보낸 name=id의 데이터
				String pnum=request.getParameter("pnum");	//jsp페이지에서 보낸 name=pnum의 데이터
				memberVO.setId(id);			//id 값 부여
				memberVO.setPnum(pnum);		//pnum 값 부여
				boolean isPwd = memberService.findPwd(memberVO);	//아이디 찾기 수행과 그 return값을 isPwd에 부여
				
				//클라이언트가 찾는 회원 정보가 없을 경우
				if(!isPwd) {
					PrintWriter pw = response.getWriter();
					pw.write("<script>" + "alert('입력하신 정보가 다릅니다.');"		//알림창
							+ " location.href='"
							+ request.getContextPath()
							+ "/member/loginForm.do"		//로그인 페이지로 이동
							+ "';"
							+ "</script>");
					return;
				}
				
				//클라이언트가 찾는 회원 정보가 있을 경우
				request.setAttribute("memberVO", memberVO);		//request에 데이터 담기
				nextPage="/account_findresult/account_pwd.jsp";	//비밀번호 찾기 결과 페이지로 dispatch
			}else if(action.equals("/deleteAccount.do")) {		//회원탈퇴			;;/myPage/myPage.jsp;;
				clearData(memberVO);
				manageCookieId(cookies, memberVO);		//로그인 쿠키 아이디 값 불러오기
				
				String id = memberVO.getId();		//id에 값 부여
				memberService.withdraw(id);		//삭제 작업 진행
				memberVO.setId(id);				//삭제 결과값 부여 (id==null) 
				session=request.getSession();	//세션 불러오기
				session.removeAttribute("loginIdSess");		//로그인 세션 삭제
				deleteCookie(cookies, response);		//로그인 쿠키 삭제
				PrintWriter pw = response.getWriter();
				pw.write("<script>" + "alert('회원탈퇴가 완료되었습니다.');"		//알림창
									+ " location.href='"
									+ request.getContextPath()
									+ "/member/loginForm.do"		//로그인 화면으로 이동
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
		//쿠키가 존재할 경우
		if (cookies != null) {
		    for (Cookie cookie : cookies) {		//배열에 있는 모든 쿠키값 대입
		    	String cookieName = cookie.getName();	//쿠키 key 받기
		    	if(cookieName.equals("loginId")) {		//쿠키 key가 loginId인 경우
		    		System.out.println("로그인 상태 확인 중");
		    		System.out.println(cookieName+": "+cookie.getValue());
		    		memberVO.setId(cookie.getValue());	//해당하는 쿠키의 value(로그인 중인 아이디)를 MemberVO의 전역변수 id에 부여
		    	}	
		    }
		}
	}
	
	//접속 포트에 있는 모든 쿠키값을 삭제하는 메서드 
	private void deleteCookie(Cookie cookies[], HttpServletResponse response) throws ServletException, IOException {
		//쿠키가 존재할 경우
		if(cookies != null) {
			for(Cookie cookie : cookies) {		//배열에 있는 모든 쿠키값 대입
				System.out.println("삭제할 쿠키: "+cookie.getName());
					cookie.setPath("/");		//path를 접속 포트의 모든 url로 설정하고
					cookie.setMaxAge(0);		//시간을 0으로 설정(삭제)
					response.addCookie(cookie);	//쿠키 추가 -> 삭제
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
