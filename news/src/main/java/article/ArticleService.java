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
	
	public List<ArticleVO> listArticles(){
		List<ArticleVO> articlesList = articleDAO.selectAllArticles();
		return articlesList;
	}

}
