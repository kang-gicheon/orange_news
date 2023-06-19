package cookie;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import member.MemberVO;

public class Cookies {
	
	public Cookies() {
		
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
