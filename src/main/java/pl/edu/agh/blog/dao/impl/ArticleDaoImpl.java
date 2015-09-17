package pl.edu.agh.blog.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.joda.time.DateTime;
import org.springframework.stereotype.Repository;

import pl.edu.agh.blog.dao.intf.ArticleDao;
import pl.edu.agh.blog.models.Article;
import pl.edu.agh.blog.models.Comment;

@Repository()
public class ArticleDaoImpl extends AbstractDaoImpl implements ArticleDao {

	@Override
	public void addArticle(Article article) {
		
		article.setCreatedAt(new DateTime());
		article.setSlug(generateSlugBasedOnTitle(article.getTitle()));
		
		save(article);
	}

	@Override
	public void updateArticle(Article article) {
		
		Article articleToUpdate = getArticle(article.getId());
		
		articleToUpdate.setTitle(article.getTitle());
		
		// TODO to powinno siê zmieniaæ?
		articleToUpdate.setSlug(generateSlugBasedOnTitle(article.getTitle()));
		articleToUpdate.setContent(article.getContent());
		articleToUpdate.setAccess(article.getAccess());
		articleToUpdate.setUpdatedAt(new DateTime());
		
		update(articleToUpdate);
	}

	@Override
	public Article getArticle(int id) {
		Criteria criteria = getSession().createCriteria(Article.class);
		criteria.add(Restrictions.eq("id", id));
		
		Article toReturn = (Article) criteria.uniqueResult();
		if (toReturn != null) {
			Hibernate.initialize(toReturn.getAuthor());
			Hibernate.initialize(toReturn.getComments());
			for (Comment comment : toReturn.getComments()) {
				Hibernate.initialize(comment.getAuthor());
			}
		}
		
		return toReturn;
	}

	@Override
	public Article getArticleBySlug(String slug) {
		Criteria criteria = getSession().createCriteria(Article.class);
		criteria.add(Restrictions.eq("slug", slug));
		
		Article toReturn = (Article) criteria.uniqueResult();
		if (toReturn != null) {
			Hibernate.initialize(toReturn.getAuthor());
			Hibernate.initialize(toReturn.getComments());
			for (Comment comment : toReturn.getComments()) {
				Hibernate.initialize(comment.getAuthor());
			}
		}
		
		return toReturn;
	}

	@Override
	public void deleteArticle(int id) {
		Article toDelete = new Article();
		toDelete.setId(id);
		delete(toDelete);
	}

	@Override
	public void deleteArticle(String slug) {
		Article toDelete = getArticleBySlug(slug);
		if (toDelete != null) {
			delete(toDelete);
		}
		
//		Query query = getSession().createQuery("delete Article where slug = :slug");
//		query.setParameter("slug", slug);
//		
//		query.executeUpdate();
	}

	@Override
	public List<Article> getArticles() {
		@SuppressWarnings("unchecked")
		List<Article> list = (List<Article>) getSession().createCriteria(Article.class).addOrder(Order.desc("created_at")).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
		for (Article article : list) {
			Hibernate.initialize(article.getAuthor());
			// TODO zmieniæ, ¿eby tylko liczba komentarzy by³a znana, bez inicjalizacji
			Hibernate.initialize(article.getComments());
		}
		return list;
	}

	@Override
	public void addComment(String slug, Comment comment) {
		
		comment.setCreatedAt(new DateTime());
				
		Article article = getArticleBySlug(slug);
		if (article != null) {			
			comment.setArticle(article);			
		}
		
		save(comment);
	}
	
	@Override
	public void updateComment(Comment comment) {
		
		Comment toUpdate = getComment(comment.getId());
		
		toUpdate.setContent(comment.getContent());
		toUpdate.setUpdatedAt(new DateTime());

		save(toUpdate);
	}
	
	@Override
	public Comment getComment(int id) {
		
		Criteria criteria = getSession().createCriteria(Comment.class);
		criteria.add(Restrictions.eq("id", id));
		
		Comment toReturn = (Comment) criteria.uniqueResult();
		if (toReturn != null) {
			Hibernate.initialize(toReturn.getAuthor());
			Hibernate.initialize(toReturn.getArticle());
		}
		
		return toReturn;
	}
	
	@Override
	public void deleteComment(int id) {
		Comment toDelete = new Comment();
		toDelete.setId(id);
		delete(toDelete);
	}

	private String generateSlugBasedOnTitle(String title) {
		
		String slug = title.toLowerCase().trim().replaceAll("[^a-zA-Z0-9 ]", "").replaceAll(" ", "-");
		
		// find duplicates
		Article article = getArticleBySlug(slug);
		String slugCopy = slug;
		int i = 2;
		
		while (article != null) {			
			slugCopy = slug + "-" + i++;
			article = getArticleBySlug(slugCopy);			
		}

		return slugCopy;
	}
}
