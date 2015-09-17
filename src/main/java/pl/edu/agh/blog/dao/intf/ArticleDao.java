package pl.edu.agh.blog.dao.intf;

import java.util.List;

import pl.edu.agh.blog.models.Article;
import pl.edu.agh.blog.models.Comment;

public interface ArticleDao {

	public void addArticle(Article article);
	public void updateArticle(Article article);
	public Article getArticle(int id);
	public Article getArticleBySlug(String slug);
	public void deleteArticle(int id);
	public void deleteArticle(String slug);
	public List<Article> getArticles();
	
	public void addComment(String slug, Comment comment);
	public void updateComment(Comment comment);
	public Comment getComment(int id);
	public void deleteComment(int id);
	/*public List<Comment> getComments();*/
	
}
