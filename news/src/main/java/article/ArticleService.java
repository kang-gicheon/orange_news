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
	
	public List<ArticleVO> listArticles(){
		List<ArticleVO> articlesList = articleDAO.selectAllArticles();
		return articlesList;
	}
	
	public void viewArticle(ArticleVO article) {
		
		articleDAO.selectArticle(article);
	}
	
	public ArticleVO headline () {
		ArticleVO article = articleDAO.HDLarticle();
		return article;
		
	}
	
	public ArticleVO updateReccount(ArticleVO article) {
		articleDAO.updateReccount(article);
		return article;
	}
	
	public List<ArticleVO> listHot(){
		List<ArticleVO> articlesHotList = articleDAO.selectHotAllArticles();
		return articlesHotList;
	}
	

}
