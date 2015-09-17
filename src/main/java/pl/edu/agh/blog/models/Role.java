package pl.edu.agh.blog.models;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.OrderBy;

@Entity
@Table(name="roles")
@SequenceGenerator(name = AbstractModel.sequenceGeneratorName, sequenceName = "roles_id_seq", allocationSize = 1)
public class Role extends AbstractModel {

	private static final long serialVersionUID = 1195420392489983154L;
	
	@Column(length = 50)
	private String name;
	
	@OrderBy(clause = "id asc")
	@OneToMany(mappedBy = "role", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<User> users = new HashSet<User>();
	
	public Role() {}
	public Role(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}
	
	@Override
	public String toString() {
		return "Role [id=" + id + ", name=" + name + "]";
	}
}
