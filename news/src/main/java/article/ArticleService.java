package article;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ArticleService {
	
	ArticleDAO articleDAO;
	
	
	public ArticleService() {
		articleDAO = new ArticleDAO();
	}
	
	public void addArticle (ArticleVO article) {
		articleDAO.insertNewArticle(article);
	}
	
	public ArticleVO updateAction(ArticleVO article) {
		articleDAO.updateReact(article);
		return article;
	}
	
	public ArticleVO updateReccount(ArticleVO article) {
		articleDAO.updateReccount(article);
		return article;
	}
	
	public List<ArticleVO> listArticles(int a){
		List<ArticleVO> articlesList = articleDAO.selectAllArticles(a);	//0: 기사 번호에 따라 정렬 / 1: 추천 수에 따라 정렬
		return articlesList;
	}
	
	public Map listTypeArticles(Map<String, Integer> pagingMap, int type){
		Map articlesMap = new HashMap();
		List<ArticleVO> articlesofTypeList = articleDAO.selectArticlesofType(pagingMap, type);
		int totArticles = articleDAO.selectTotArticles(type);
		if(type==1) {
			articlesMap.put("articleType1", articlesofTypeList);
		} else if(type==2) {
			articlesMap.put("articleType2", articlesofTypeList);
		} else if(type==3) {
			articlesMap.put("articleType3", articlesofTypeList);
		} else if(type==4) {
			articlesMap.put("articleType4", articlesofTypeList);
		} else if(type==5) {
			articlesMap.put("articleType5", articlesofTypeList);
		} else if(type==6) {
			articlesMap.put("articleType6", articlesofTypeList);
		} else if(type==7) {
			articlesMap.put("articleType7", articlesofTypeList);
		}
//		articlesMap.put("totArticles", totArticles);	//a안 - 기사 글 갯수에 따라 페이징 넘버 생성
		articlesMap.put("totArticles", 130);			//b안 - 고정된 갯수에 떄라 페이징 넘버 생성
		return articlesMap;
	}
	
	public void viewArticle(ArticleVO article) {
		articleDAO.selectArticle(article, 0);
	}
	
	public void displayHDLarticlesList(List<ArticleVO> hdlArticlesList) {
		articleDAO.HDLarticles(hdlArticlesList);		
	}
	
	public void displayHDLarticle(ArticleVO article) {
		articleDAO.selectArticle(article, 1);
	}
	
	public List<ArticleVO> listReact(){
		List<ArticleVO> articlesList = articleDAO.selectReactArticles();
		return articlesList;
	}
}
