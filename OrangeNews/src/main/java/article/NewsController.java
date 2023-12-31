package article;

import java.io.File;
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
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileUtils;

import member.MemberVO;

@WebServlet("/news/*")
public class NewsController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static String ARTICLE_IMAGE_REPO = "C:\\news\\article_image";
	// ArticleDAO articleDAO;
	ArticleVO articleVO;
	ArticleService articleService;
	MemberVO memberVO;

	public void init(ServletConfig config) throws ServletException {
		articleVO = new ArticleVO();
		memberVO = new MemberVO();
		articleService = new ArticleService();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doHandle(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doHandle(request, response);
	}

	private void doHandle(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String nextPage = "";
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		String action = request.getPathInfo();

		// 쿠키
		Cookie[] cookies = request.getCookies();
		Cookie articlenumCookie;

		System.out.println("action: " + action); // 어떤 액션인지 콘솔에서 확인용 (나중에 지워짐)

		try {
			ArticleVO headarticle = new ArticleVO();
			List<ArticleVO> articlesList = new ArrayList<ArticleVO>();
			List<ArticleVO> hotarticlesList = new ArrayList<ArticleVO>();
			List<ArticleVO> hdlArticlesList = new ArrayList<ArticleVO>();
			List<ArticleVO> ReactRankingList = new ArrayList<ArticleVO>();

			ReactRankingList = articleService.listReact();
			request.setAttribute("reactList", ReactRankingList);

			if (action == null) {

				System.out.println("기본 화면");
				articlesList = articleService.listArticles(0); // 0: 기사 번호에 따라 정렬 / 1: 추천 수에 따라 정렬
				request.setAttribute("articlesList", articlesList);

				hotarticlesList = articleService.listArticles(1);
				request.setAttribute("hotarticlesList", hotarticlesList);

				articleService.displayHDLarticlesList(hdlArticlesList);
				request.setAttribute("headarticlesList", hdlArticlesList);

				articleService.displayHDLarticle(headarticle);
				System.out.println("헤드라인 기사 번호: " + headarticle.getArticlenum());
				request.setAttribute("hdlmain", headarticle);

				nextPage = "/main.jsp";

			}

			else if (action.equals("/type1List.do")) {
				int type = 1;

				String _section = request.getParameter("section");
				String _pageNum = request.getParameter("pageNum");
				int section = Integer.parseInt(_section == null ? "1" : _section);
				int pageNum = Integer.parseInt(_pageNum == null ? "1" : _pageNum);

				Map<String, Integer> pagingMap = new HashMap<String, Integer>();
				pagingMap.put("section", section);
				pagingMap.put("pageNum", pageNum);
				Map articlesMap = articleService.listTypeArticles(pagingMap, type);
				articlesMap.put("section", section);
				articlesMap.put("pageNum", pageNum);

				request.setAttribute("articlesMap", articlesMap);
				nextPage = "/articlelistPage/ListType1.jsp";
			}

			else if (action.equals("/type2List.do")) {
				int type = 2;

				String _section = request.getParameter("section");
				String _pageNum = request.getParameter("pageNum");
				int section = Integer.parseInt(_section == null ? "1" : _section);
				int pageNum = Integer.parseInt(_pageNum == null ? "1" : _pageNum);

				Map<String, Integer> pagingMap = new HashMap<String, Integer>();
				pagingMap.put("section", section);
				pagingMap.put("pageNum", pageNum);
				Map articlesMap = articleService.listTypeArticles(pagingMap, type);
				articlesMap.put("section", section);
				articlesMap.put("pageNum", pageNum);

				request.setAttribute("articlesMap", articlesMap);
				nextPage = "/articlelistPage/ListType2.jsp";
			}

			else if (action.equals("/type3List.do")) {
				int type = 3;

				String _section = request.getParameter("section");
				String _pageNum = request.getParameter("pageNum");
				int section = Integer.parseInt(_section == null ? "1" : _section);
				int pageNum = Integer.parseInt(_pageNum == null ? "1" : _pageNum);

				Map<String, Integer> pagingMap = new HashMap<String, Integer>();
				pagingMap.put("section", section);
				pagingMap.put("pageNum", pageNum);
				Map articlesMap = articleService.listTypeArticles(pagingMap, type);
				articlesMap.put("section", section);
				articlesMap.put("pageNum", pageNum);

				request.setAttribute("articlesMap", articlesMap);
				nextPage = "/articlelistPage/ListType3.jsp";
			}

			else if (action.equals("/type4List.do")) {
				int type = 4;

				String _section = request.getParameter("section");
				String _pageNum = request.getParameter("pageNum");
				int section = Integer.parseInt(_section == null ? "1" : _section);
				int pageNum = Integer.parseInt(_pageNum == null ? "1" : _pageNum);

				Map<String, Integer> pagingMap = new HashMap<String, Integer>();
				pagingMap.put("section", section);
				pagingMap.put("pageNum", pageNum);
				Map articlesMap = articleService.listTypeArticles(pagingMap, type);
				articlesMap.put("section", section);
				articlesMap.put("pageNum", pageNum);

				request.setAttribute("articlesMap", articlesMap);
				nextPage = "/articlelistPage/ListType4.jsp";
			}

			else if (action.equals("/type5List.do")) {
				int type = 5;

				String _section = request.getParameter("section");
				String _pageNum = request.getParameter("pageNum");
				int section = Integer.parseInt(_section == null ? "1" : _section);
				int pageNum = Integer.parseInt(_pageNum == null ? "1" : _pageNum);

				Map<String, Integer> pagingMap = new HashMap<String, Integer>();
				pagingMap.put("section", section);
				pagingMap.put("pageNum", pageNum);
				Map articlesMap = articleService.listTypeArticles(pagingMap, type);
				articlesMap.put("section", section);
				articlesMap.put("pageNum", pageNum);

				request.setAttribute("articlesMap", articlesMap);
				nextPage = "/articlelistPage/ListType5.jsp";
			}

			else if (action.equals("/type6List.do")) {
				int type = 6;

				String _section = request.getParameter("section");
				String _pageNum = request.getParameter("pageNum");
				int section = Integer.parseInt(_section == null ? "1" : _section);
				int pageNum = Integer.parseInt(_pageNum == null ? "1" : _pageNum);

				Map<String, Integer> pagingMap = new HashMap<String, Integer>();
				pagingMap.put("section", section);
				pagingMap.put("pageNum", pageNum);
				Map articlesMap = articleService.listTypeArticles(pagingMap, type);
				articlesMap.put("section", section);
				articlesMap.put("pageNum", pageNum);

				request.setAttribute("articlesMap", articlesMap);
				nextPage = "/articlelistPage/ListType6.jsp";
			}

			else if (action.equals("/type7List.do")) {
				int type = 7;

				String _section = request.getParameter("section");
				String _pageNum = request.getParameter("pageNum");
				int section = Integer.parseInt(_section == null ? "1" : _section);
				int pageNum = Integer.parseInt(_pageNum == null ? "1" : _pageNum);

				Map<String, Integer> pagingMap = new HashMap<String, Integer>();
				pagingMap.put("section", section);
				pagingMap.put("pageNum", pageNum);
				Map articlesMap = articleService.listTypeArticles(pagingMap, type);
				articlesMap.put("section", section);
				articlesMap.put("pageNum", pageNum);

				request.setAttribute("articlesMap", articlesMap);
				nextPage = "/articlelistPage/ListType7.jsp";
			}

			else if (action.equals("/addarticleForm.do")) { // 기사 작성 폼 요청 액션

				System.out.println("기사 작성 폼 요청");

				manageCookieId(cookies, memberVO);
				manageCookieRep(cookies, memberVO);
				String loginId = memberVO.getId();
				int report = memberVO.getReporter();
				if (loginId == null) { // 로그인이 안 되어있을 경우
					PrintWriter pw = response.getWriter();
					pw.print("<script>" + " alert('로그인이 필요합니다.');" + " location.href='" + request.getContextPath()
							+ "/news/login.do';" + "</script>");
					return;
				} else if (loginId != null && report == 0) { // 로그인은 되어있으나 기자 계정이 아닌 경우
					PrintWriter pw = response.getWriter();
					pw.print("<script>" + " alert('기자 계정이 아닙니다.');" + " location.href='" + request.getContextPath()
							+ "/news/';" + "</script>");
					return;
				}
				nextPage = "/test/addArticlePage.jsp";

			}

			else if (action.equals("/addArticle.do")) { // 기사 작성 (기자로 로그인시)
				System.out.println("기사작성액션받음");

				Map<String, String> articleMap = upload(request, response);
				String title = articleMap.get("title");
				String content = articleMap.get("content");
				String type = articleMap.get("articleType");
				String hio = articleMap.get("hotissue");
				String imgFileName = articleMap.get("imgFileName");

				manageCookieId(cookies, memberVO);
				String id = memberVO.getId();

				articleVO.setTitle(title);
				articleVO.setContent(content);
				articleVO.setType(Integer.parseInt(type));
				articleVO.setHotissue(Integer.parseInt(hio));
				articleVO.setImgFileName(imgFileName);
				articleVO.setId(id);

				int Anum = articleService.addArticle(articleVO);
				System.out.println(Anum);

				if (imgFileName != null) {
					File srcFile = new File(ARTICLE_IMAGE_REPO + "\\" + "temp" + "\\" + imgFileName);
					File destDir = new File(ARTICLE_IMAGE_REPO + "\\" + Anum); // 제목 기준으로 디렉토리 생성
					destDir.mkdirs();
					FileUtils.moveFileToDirectory(srcFile, destDir, true);
					srcFile.delete();
				}

				PrintWriter pw = response.getWriter();
				pw.print("<script>" + " alert('새 기사를 작성했습니다.');" + " location.href='" + request.getContextPath()
						+ "/news';" + "</script>");
				return;

			} else if (action.equals("/viewArticle.do")) {
				// 쿠키(path:/news/viewArticle.do)값 삭제
				deleteCookie(cookies, response);

				int articlenum = Integer.parseInt(request.getParameter("articlenum"));
				System.out.println(articlenum + " <= articlenumString입니다");

				articlenumCookie = new Cookie("articlenum", Integer.toString(articlenum));
				articlenumCookie.setPath("/news/viewArticle.do");
				articlenumCookie.setMaxAge(60 * 60 * 24);
				response.addCookie(articlenumCookie);

				articleVO.setArticlenum(articlenum);
				articleService.viewArticle(articleVO);
				request.setAttribute("article", articleVO);

				nextPage = "/articlePage/viewpage.jsp";
			}

			else if (action.equals("/updateReact.do")) {
				System.out.println("반응 업데이트");

				String type = request.getParameter("react");
				System.out.println(type + " <= type입니다");

				articleVO.setActype(type);
				articleService.updateAction(articleVO);

				int articlenum = articleVO.getArticlenum();
				PrintWriter pw = response.getWriter();
				pw.print("<script>" + " alert('반응이 등록되었습니다.');" + " location.href='" + request.getContextPath()
						+ "/news/viewArticle.do?articlenum=" + articlenum + "'; </script>");
				return;
			}

			else if (action.equals("/updateRec.do")) {
				System.out.println("추천 업데이트");
				String recOX = request.getParameter("recommand");
				System.out.println(recOX + "<= 추천 받았는지");

				articleService.updateReccount(articleVO);

				int articlenum = articleVO.getArticlenum();
				PrintWriter pw = response.getWriter();
				pw.print("<script>" + " alert('기사가 추천되었습니다.');" + " location.href='" + request.getContextPath()
						+ "/news/viewArticle.do?articlenum=" + articlenum + "'; </script>");
				return;
			}

			else {

				System.out.println("그 외");
				PrintWriter pw = response.getWriter();
				pw.print("<script> location.href='" + request.getContextPath() + "/news';" + "</script>");
				return;

			}

			RequestDispatcher dispatch = request.getRequestDispatcher(nextPage);
			dispatch.forward(request, response);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private Map<String, String> upload(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		Map<String, String> articleMap = new HashMap<String, String>();
		String encoding = "utf-8";
		File currentDirPath = new File(ARTICLE_IMAGE_REPO); // 파일 객체 생성
		DiskFileItemFactory factory = new DiskFileItemFactory();
		factory.setRepository(currentDirPath);
		factory.setSizeThreshold(1024 * 1024);
		ServletFileUpload upload = new ServletFileUpload(factory);
		try {
			List<FileItem> items = upload.parseRequest(request);
			for (int i = 0; i < items.size(); i++) {
				FileItem fileItem = (FileItem) items.get(i);
				if (fileItem.isFormField()) {
					System.out.println(fileItem.getFieldName() + "=" + fileItem.getString(encoding));
					articleMap.put(fileItem.getFieldName(), fileItem.getString(encoding));
					// 파일 업로드로 같이 전송된 새 글 관련 매개변수를 Map에 저장&반환
				} else {
					System.out.println("파라미터이름: " + fileItem.getFieldName());
					System.out.println("파일 이름:" + fileItem.getName());
					System.out.println("파일 크기:" + fileItem.getSize() + "bytes");

					if (fileItem.getSize() > 0) { // 파일 크기가 0보다 크면 (크기가 잡히면 존재한다는 뜻) 저장소에 업로드
						int idx = fileItem.getName().lastIndexOf("\\");
						if (idx == -1) {
							idx = fileItem.getName().lastIndexOf("/");
						}
						String fileName = fileItem.getName().substring(idx + 1);
						articleMap.put(fileItem.getFieldName(), fileName); // 업로드된 파일 이름 저장
						File uploadFile = new File(currentDirPath + "\\temp\\" + fileName); // 여기도 temp에 업로드
						fileItem.write(uploadFile);
					}

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return articleMap;
	}

	private void manageCookieId(Cookie cookies[], MemberVO memberVO) {
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				String cookieName = cookie.getName();
				if (cookieName.equals("loginId")) {
					System.out.println("로그인 상태 확인 중");
					System.out.println(cookieName + ": " + cookie.getValue());
					memberVO.setId(cookie.getValue());
				}
			}
		}
	}

	private void manageCookieRep(Cookie cookies[], MemberVO memberVO) {
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				String cookieName = cookie.getName();
				if (cookieName.equals("reporter")) {
					System.out.println("로그인 상태 확인 중");
					System.out.println(cookieName + ": " + cookie.getValue());
					memberVO.setReporter(Integer.parseInt(cookie.getValue()));
				}
			}
		}
	}

	private void deleteCookie(Cookie cookies[], HttpServletResponse response) throws ServletException, IOException {
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				cookie.setPath("/news");
				cookie.setMaxAge(0);
				response.addCookie(cookie);

			}
		}
	}

	// 안쓸 메서드일 확률이 높습니다. 참고
	private void clearData(MemberVO memberVO) {
		articleVO.setTitle(null);
		articleVO.setContent(null);
		articleVO.setType(0);
	}

}
