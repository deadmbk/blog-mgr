package pl.edu.agh.blog.services.intf;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;

import pl.edu.agh.blog.models.Article;
import pl.edu.agh.blog.models.Comment;

public interface ArticleService extends AbstractService {

	public static final String canonicalName = Comment.class.getCanonicalName();
	
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_EDITOR')")
	public void addArticle(Article article, String [] permittedUsers);
	
	// #article.author.username == principal.username
	@PreAuthorize("hasRole('ROLE_ADMIN') or ( hasRole('ROLE_EDITOR') and hasPermission(#article, 'WRITE') )")
	public void updateArticle(Article article, String [] permittedUsers);
	
	// unused method
	public Article getArticle(int id);
	
	/* unprotected method */
	@PostAuthorize("returnObject.access == 'PUB' or hasPermission(returnObject, 'READ')")
	public Article getArticleBySlug(String slug);
	
	// TODO zabezpieczyæ
	// @PreAuthorize("hasRole('ROLE_ADMIN') or hasPermission(#id, this.canonicalName, 'WRITE')")
	public void deleteArticle(int id);

	@PostFilter("filterObject.access == 'PUB' or hasPermission(filterObject, 'READ')")
	public List<Article> getArticles();
	
	@PreAuthorize("isAuthenticated()")
	public void addComment(String slug, Comment comment);
//	
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasPermission(#comment, 'WRITE')")
	public void updateComment(Comment comment);
//	
	// only for editting page, so "write" permission
	@PostAuthorize("hasRole('ROLE_ADMIN') or hasPermission(returnObject, 'WRITE')")
	public Comment getComment(int id);

	// TODO write? nie delete?
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasPermission(#id, this.canonicalName, 'WRITE')")
	public void deleteComment(int id);
	
	public ArrayList<String> getPermittedUsers(Article article);
}
