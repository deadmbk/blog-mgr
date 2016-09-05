package pl.edu.agh.blog.test.mock.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pl.edu.agh.blog.dao.intf.ArticleDao;
import pl.edu.agh.blog.models.Article;
import pl.edu.agh.blog.models.Comment;

@Service
@Transactional
public class MockArticleServiceImpl implements MockArticleService {

	@Autowired
	private ArticleDao articleDao;
	
	@Override
	public void addArticle(Article article) {
	}

	@Override
	public void updateArticle(Article article) {
	}

	@Override
	public Article getArticleBySlug(String slug) {
		return articleDao.getArticleBySlug(slug);
	}
	

	@Override
	public Article getArticleBySlugForTest(String slug) {
		return articleDao.getArticleBySlug(slug);
	}

	@Override
	public void deleteArticle(int id) {
	}

	@Override
	public List<Article> getArticles() {
		return articleDao.getArticles();
	}

	@Override
	public void addComment() {
	}

	@Override
	public void updateComment(Comment comment) {
	}

	@Override
	public Comment getComment(int id) {
		return articleDao.getComment(id);
	}

	@Override
	public void deleteComment(int id) {
	}

}
