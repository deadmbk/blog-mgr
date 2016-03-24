package pl.edu.agh.blog.models;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.OrderBy;
import org.hibernate.validator.constraints.NotEmpty;
import org.joda.time.DateTime;

@Entity
@Table(name="articles")
@SequenceGenerator(name = AbstractModel.sequenceGeneratorName, sequenceName = "articles_id_seq", allocationSize = 1)
public class Article extends AbstractModel {

	private static final long serialVersionUID = 6815122236810641480L;
	
	@NotEmpty
	@Column(length = 50)
	private String title;
	
	//@NotNull
	@Column(length = 70)
	private String slug;
	
	//@NotNull
	private String content;
	
	private String access;
	
	@ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY)
	private User author;
	
	@OrderBy(clause = "created_at desc")
	@OneToMany(mappedBy = "article", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<Comment> comments = new HashSet<Comment>();
	
	private DateTime created_at;
	private DateTime updated_at;
	
	public Article() {}
	public Article(String title, String slug, String content, String access, User author) {
		this(title, slug, content, access, author, new DateTime());
	}
	
	public Article(String title, String slug, String content, String access, User author, DateTime created_at) {
		this.title = title;
		this.slug = slug;
		this.content = content;
		this.author = author;
		this.created_at = created_at;
	}

	// getters
	public String getTitle() {
		return title;
	}
	
	public String getSlug() {
		return slug;
	}
	
	public String getContent() {
		return content;
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
	
	public Set<Comment> getComments() {
		return comments;
	}
	
	public String getAccess() {
		return access;
	}
	
	// setters
	public void setTitle(String title) {
		this.title = title;
	}
	
	public void setSlug(String slug) {
		this.slug = slug;
	}
	
	public void setContent(String content) {
		this.content = content;
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

	public void setComments(Set<Comment> comments) {
		this.comments = comments;
	}

	public void setAccess(String access) {
		this.access = access;
	}
	
	@Override
	public String toString() {
		return "Article [title=" + title + ", content=" + content + ", author=" + author + ", created_at=" + created_at
				+ ", updated_at=" + updated_at + "]";
	}
	
}
