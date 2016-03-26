package pl.edu.agh.blog.models;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotEmpty;
import org.joda.time.DateTime;

@Entity
@Table(name="comments")
@SequenceGenerator(name = AbstractModel.sequenceGeneratorName, sequenceName = "comments_id_seq", allocationSize = 1)
public class Comment extends AbstractModel {

	private static final long serialVersionUID = 2524981079107498913L;
	
	@NotEmpty(message = "{comment.content.notEmpty}")
	private String content;
	
	@ManyToOne(targetEntity = Article.class, fetch = FetchType.LAZY)
	private Article article;
	
	@ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY)
	private User author;
	
	private DateTime created_at;
	private DateTime updated_at;
	
	public Comment() {}
	public Comment(String content, Article article, User author) {
		this(content, article, author, new DateTime());
	}
	
	public Comment(String content, Article article, User author, DateTime created_at) {
		this.content = content;
		this.article = article;
		this.author = author;
		this.created_at = created_at;
	}
	
	public String getContent() {
		return content;
	}
	
	public Article getArticle() {
		return article;
	}
	
	public User getAuthor() {
		return author;
	}
	
	public DateTime getCreatedAt() {
		return created_at;
	}
	
	public DateTime getUpdatedAt() {
		return updated_at;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
	
	public void setArticle(Article article) {
		this.article = article;
	}
	
	public void setAuthor(User author) {
		this.author = author;
	}
	
	public void setCreatedAt(DateTime created_at) {
		this.created_at = created_at;
	}
	
	public void setUpdatedAt(DateTime updated_at) {
		this.updated_at = updated_at;
	}

	@Override
	public String toString() {
		return "Comment [content=" + content + ", article=" + article + ", author=" + author + ", created_at="
				+ created_at + ", updated_at=" + updated_at + "]";
	}
	
}
