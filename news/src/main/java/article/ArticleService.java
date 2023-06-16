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
	
	public void addReact(ArticleVO article) {
		articleDAO.firstAddReact(article);
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

}
