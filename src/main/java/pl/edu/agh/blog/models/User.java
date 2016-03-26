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
import javax.validation.constraints.Size;

import org.hibernate.annotations.OrderBy;
import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name="users")
@SequenceGenerator(name = AbstractModel.sequenceGeneratorName, sequenceName = "users_id_seq", allocationSize = 1)
public class User extends AbstractModel {

	private static final long serialVersionUID = 3177723440035291803L;
	
	@NotEmpty(message = "{user.username.notEmpty}")
	@Size(min = 3, max = 30, message = "{user.username.size}")
	@Column(length = 30)
	private String username;
	
	@NotEmpty(message = "{user.password.notEmpty}")
	@Size(min = 3, max = 60, message = "{user.password.size}")
	private String password;
	
	@ManyToOne(targetEntity = Role.class, fetch = FetchType.LAZY)
	private Role role;
	
	public User() {}
	
	public User(String username, String password, Role role) {
		this.username = username;
		this.password = password;
		this.role = role;
	}
	
	@OrderBy(clause = "created_at desc")
	@OneToMany(mappedBy = "author", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<Article> articles = new HashSet<Article>();
	
	@OrderBy(clause = "created_at desc")
	@OneToMany(mappedBy = "author", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<Comment> comments = new HashSet<Comment>();

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + "]";
	}
	
}
