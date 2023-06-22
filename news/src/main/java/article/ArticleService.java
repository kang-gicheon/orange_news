package article;

import java.util.List;

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
	
	public List<ArticleVO> listTypeArticles(){
		List<ArticleVO> articlesofTypeList = articleDAO.selectArticlesofType();
		return articlesofTypeList;
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
}
