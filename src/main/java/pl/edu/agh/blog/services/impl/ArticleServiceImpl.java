package pl.edu.agh.blog.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pl.edu.agh.blog.dao.intf.ArticleDao;
import pl.edu.agh.blog.models.Article;
import pl.edu.agh.blog.models.Comment;
import pl.edu.agh.blog.services.intf.ArticleService;

@Service
@Transactional
public class ArticleServiceImpl extends AbstractServiceImpl implements ArticleService {

	@Autowired
	private ArticleDao articleDao;
	
	@Override
	public void addArticle(Article article) {
		articleDao.addArticle(article);
		this.createAcl(article.getClass(), article.getId());
	}

	@Override
	public void updateArticle(Article article) {
		articleDao.updateArticle(article);
	}

	@Override
	public Article getArticle(int id) {
		return articleDao.getArticle(id);
	}

	@Override
	public Article getArticleBySlug(String slug) {
		return articleDao.getArticleBySlug(slug);
	}

	@Override
	public void deleteArticle(int id) {
		articleDao.deleteArticle(id);
	}
	
	@Override
	public void deleteArticle(String slug) {
		// TODO temporary
		Article article = articleDao.getArticleBySlug(slug);
		articleDao.deleteArticle(slug);
		deleteAcl(article.getClass(), article.getId());
	}

	@Override
	public List<Article> getArticles() {
		return articleDao.getArticles();
	}

	@Override
	public void addComment(String slug, Comment comment) {
		articleDao.addComment(slug, comment);
		this.createAcl(comment.getClass(), comment.getId());
	}

	@Override
	public void updateComment(Comment comment) {
		articleDao.updateComment(comment);
		
	}

	@Override
	public Comment getComment(int id) {
		return articleDao.getComment(id);
	}

	@Override
	public void deleteComment(int id) {
		articleDao.deleteComment(id);		
	}

}
