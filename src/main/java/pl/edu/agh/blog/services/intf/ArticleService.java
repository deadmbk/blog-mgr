package pl.edu.agh.blog.services.intf;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;

import pl.edu.agh.blog.models.Article;
import pl.edu.agh.blog.models.Comment;
import pl.edu.agh.blog.models.User;

public interface ArticleService extends AbstractService {

	public static final String commentCanonicalName = Comment.class.getCanonicalName();
	public static final String articleCanonicalName = Article.class.getCanonicalName();
		
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_EDITOR')")
	public void addArticle(Article article, List<User> permittedUsers);
	
	// Alternatywnie, gdy chcemy autoryzowaæ ownera: #article.author.username == principal.username
	// U¿yta wersja jest bardziej ogólna, gdyby zabraæ komuœ rolê edytora to nie bêdzie móg³ edytowaæ swojego artyku³u
	@PreAuthorize("hasRole('ROLE_ADMIN') or ( hasRole('ROLE_EDITOR') and hasPermission(#article, 'WRITE') )")
	public void updateArticle(Article article, List<User> permittedUsers);
	
	// unused method
	public Article getArticle(int id);
	
	@PostAuthorize("returnObject.access == 'PUB' or hasPermission(returnObject, 'READ')")
	public Article getArticleBySlug(String slug);
	
	// TODO sprawdziæ czy dzia³a
	@PreAuthorize("hasRole('ROLE_ADMIN') or ( hasRole('ROLE_EDITOR') and hasPermission(#id, this.articleCanonicalName, 'DELETE') )")
	public void deleteArticle(int id);

	@PostFilter("filterObject.access == 'PUB' or hasPermission(filterObject, 'READ')")
	public List<Article> getArticles();
	
	@PreAuthorize("isAuthenticated()")
	public void addComment(String slug, Comment comment);

	@PreAuthorize("hasRole('ROLE_ADMIN') or hasPermission(#comment, 'WRITE')")
	public void updateComment(Comment comment);

	// only for editting page, so "write" permission
	@PostAuthorize("hasRole('ROLE_ADMIN') or hasPermission(returnObject, 'WRITE')")
	public Comment getComment(int id);

	@PreAuthorize("hasRole('ROLE_ADMIN') or hasPermission(#id, this.commentCanonicalName, 'DELETE')")
	public void deleteComment(int id);
	
	public ArrayList<String> getPermittedUsers(Article article);
}
