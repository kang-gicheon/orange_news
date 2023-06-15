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
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileUtils;

import news.member.MemberVO;		//<-임시 임포트

@WebServlet("/news/*")
public class NewsController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static String ARTICLE_IMAGE_REPO = "C:\\news\\article_image";
	//ArticleDAO articleDAO;
	ArticleVO articleVO;
	ArticleService articleService;
	MemberVO memberVO;		//MemberVO 변수 추가

	public void init(ServletConfig config) throws ServletException {
		articleVO = new ArticleVO();
		memberVO = new MemberVO();
		articleService = new ArticleService();
		System.out.println("===> init() 호출");
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
		String loginId=memberVO.getId();		//테스트용 변수
//		boolean loginValue=memberDAO.login();
		int report=memberVO.getReporter();		//테스트용 변수
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		String action = request.getPathInfo();
		System.out.println("action: " + action); // 어떤 액션인지 콘솔에서 확인용 (나중에 지워짐)

		try {
			List<ArticleVO> articlesList = new ArrayList<ArticleVO>();
			if (action == null) {
				System.out.println("기본 화면");
				articlesList = articleService.listArticles();
				request.setAttribute("articlesList", articlesList);
				nextPage = "/test/mainPage.jsp";
				
			} else if (action.equals("/member.do")) { // 회원가입
				System.out.println("회원가입");
			}

			else if (action.equals("/login.do")) { // 로그인
				System.out.println("로그인");
				nextPage = "/test/loginPage.jsp";
			}

			else if (action.equals("/articleForm.do")) { // 기사 클릭 (기사 조회)
				System.out.println("기사");

			}

			else if (action.equals("/addarticleForm.do")) { // 기사 작성 폼 요청 액션

				System.out.println("기사 작성 폼 요청");

				loginId="asd"; report=1; //임시데이터
				if (loginId==null) { // 로그인이 안 되어있을 경우
					PrintWriter pw = response.getWriter();
					pw.print("<script>" + " alert('로그인이 필요합니다.');" + " location.href='" + request.getContextPath()
							+ "/news/login.do';" + "</script>");
					return;
				}

				if (loginId != null && report==0) { // 로그인은 되어있으나 기자 계정이 아닌 경우
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
				System.out.println("이미지 파일 상태 "+imgFileName);
				articleVO.setTitle(title);
				articleVO.setContent(content);
				articleVO.setType(Integer.parseInt(type));
				articleVO.setHotissue(Integer.parseInt(hio));
				articleVO.setImgFileName(imgFileName);
				articleService.addArticle(articleVO);
				
				if(imgFileName != null ) {
					System.out.println("들어오는지");
					File srcFile = new File(ARTICLE_IMAGE_REPO+"\\"+"temp"+"\\"+imgFileName);
					File destDir = new File(ARTICLE_IMAGE_REPO+"\\"+title); //제목 기준으로 디렉토리 생성
					destDir.mkdirs();
					FileUtils.moveFileToDirectory(srcFile, destDir, true);
					srcFile.delete();
				}
				
				PrintWriter pw = response.getWriter();
				pw.print("<script>"+" alert('새 기사를 작성했습니다.');"+" location.href='"
						+request.getContextPath()+"/news/';" +"</script>");
						return; 
				
			} 
			else if(action.equals("/viewArticle.do")) {
				int articlenum = Integer.parseInt(request.getParameter("articlenum"));
				System.out.println(articlenum);
				System.out.println(articlenum+" <= int articlenum 입니다");
				articleVO.setArticlenum(articlenum);
				articleService.viewArticle(articleVO);
				System.out.println("articleVO.getType: "+ articleVO.getType());
				request.setAttribute("article", articleVO);
				nextPage="/test/viewArticle.jsp";
			}

			else {
				System.out.println("그 외");
				PrintWriter pw = response.getWriter();
				pw.print("<script> location.href='"
						+request.getContextPath()+"/news';" +"</script>");
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

}
