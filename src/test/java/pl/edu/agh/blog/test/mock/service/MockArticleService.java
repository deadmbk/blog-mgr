package pl.edu.agh.blog.test.mock.service;

import java.util.List;

import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;

import pl.edu.agh.blog.models.Article;
import pl.edu.agh.blog.models.Comment;

public interface MockArticleService {

	public static final String commentCanonicalName = Comment.class.getCanonicalName();
	public static final String articleCanonicalName = Article.class.getCanonicalName();
		
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_EDITOR')")
	public void addArticle(Article article);

	@PreAuthorize("hasRole('ROLE_ADMIN') or ( hasRole('ROLE_EDITOR') and hasPermission(#article, 'WRITE') )")
	public void updateArticle(Article article);
	
	@PostAuthorize("returnObject.access == 'PUB' or hasPermission(returnObject, 'READ')")
	public Article getArticleBySlug(String slug);

	public Article getArticleBySlugForTest(String slug);

	@PreAuthorize("hasRole('ROLE_ADMIN') or ( hasRole('ROLE_EDITOR') and hasPermission(#id, this.articleCanonicalName, 'DELETE') )")
	public void deleteArticle(int id);

	@PostFilter("filterObject.access == 'PUB' or hasPermission(filterObject, 'READ')")
	public List<Article> getArticles();
	
	@PreAuthorize("isAuthenticated()")
	public void addComment();

	@PreAuthorize("hasRole('ROLE_ADMIN') or hasPermission(#comment, 'WRITE')")
	public void updateComment(Comment comment);

	@PostAuthorize("hasRole('ROLE_ADMIN') or hasPermission(returnObject, 'WRITE')")
	public Comment getComment(int id);

	@PreAuthorize("hasRole('ROLE_ADMIN') or hasPermission(#id, this.commentCanonicalName, 'DELETE')")
	public void deleteComment(int id);
}
