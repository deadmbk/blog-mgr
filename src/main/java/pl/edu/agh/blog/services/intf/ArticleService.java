package pl.edu.agh.blog.services.intf;

import java.util.List;

import org.springframework.security.access.prepost.PostFilter;

import pl.edu.agh.blog.models.Article;
import pl.edu.agh.blog.models.Comment;

public interface ArticleService extends AbstractService {

	public void addArticle(Article article);
	
	//@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_EDITOR') AND #article.author.username == principal.username")
	public void updateArticle(Article article);
	
	// unused method
	public Article getArticle(int id);
	
	// isAuthenticated()
	public Article getArticleBySlug(String slug);
	
	// unused method
	public void deleteArticle(int id);
	public void deleteArticle(String slug);
	
	//  OR hasPermission(filterObject, 'READ')
	@PostFilter("filterObject.access == 'PUB' or hasPermission(filterObject, 'READ')")
	public List<Article> getArticles();
	
//	// isAuthenticated()
	public void addComment(String slug, Comment comment);
//	
//	// isAuthenticated() AND (#comment.author.username == authentication.name)
	public void updateComment(Comment comment);
//	
//	// isAuthenticated() or nothing
	public Comment getComment(int id);
//	
//	// isAuthenticated() and (hasRole('ROLE_ADMIN') or (hasPermission(obj, id, 'DELETE')
	public void deleteComment(int id);
	
}
