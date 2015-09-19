package pl.edu.agh.blog.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.acls.domain.BasePermission;
import org.springframework.security.acls.domain.ObjectIdentityImpl;
import org.springframework.security.acls.domain.PrincipalSid;
import org.springframework.security.acls.model.AccessControlEntry;
import org.springframework.security.acls.model.MutableAcl;
import org.springframework.security.acls.model.ObjectIdentity;
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
	public void addArticle(Article article, String [] permittedUsers) {
		
		articleDao.addArticle(article);
		
		createAcl(article.getClass(), article.getId());
		if (permittedUsers != null) createReaderPermission(article, permittedUsers);
		
		
	}
		
	private void createReaderPermission(Article article, String [] permittedUsers) {
		
		ObjectIdentity oid = new ObjectIdentityImpl(article);
		MutableAcl acl = (MutableAcl) mutableAclService.readAclById(oid);
		
		for (String username : permittedUsers) {
			this.insertAclEntry(acl, BasePermission.READ, username, true);
		}
		
		mutableAclService.updateAcl(acl);		
	}
	
	@Override
	public void updateArticle(Article article, String [] permittedUsers) {
		articleDao.updateArticle(article);
		
		updateAcl(article, permittedUsers);
	}
	
	private void updateAcl(Article article, String [] permittedUsers) {
		
		ObjectIdentity oid = new ObjectIdentityImpl(article);
		MutableAcl acl = (MutableAcl) mutableAclService.readAclById(oid);
		
		this.deleteAclEntries(acl);
		if (permittedUsers != null) {
			for (String username : permittedUsers) {
				this.insertAclEntry(acl, BasePermission.READ, username, true);
			}
		}
				
		mutableAclService.updateAcl(acl);
	}
	
	public ArrayList<String> getPermittedUsers(Article article) {
		
		ArrayList<String> permittedUsers = new ArrayList<String>();
		ObjectIdentity oid = new ObjectIdentityImpl(article);
		MutableAcl acl = (MutableAcl) mutableAclService.readAclById(oid);
		
		for (AccessControlEntry ace : acl.getEntries()) {
			
			PrincipalSid sid = (PrincipalSid) ace.getSid();
			if (sid.getPrincipal().equals(article.getAuthor().getUsername())) // skip owner
				continue;
			
			permittedUsers.add(sid.getPrincipal());
		}
		
		return permittedUsers;		
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
		
		
		for (Comment comment : article.getComments()) {
			deleteAcl(comment.getClass(), comment.getId());
		}
		
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
		deleteAcl(Comment.class, id);
	}

}
