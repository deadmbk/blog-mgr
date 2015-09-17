package pl.edu.agh.blog.dao.intf;

import java.util.List;

import pl.edu.agh.blog.models.Role;

public interface RoleDao {

	public void addRole(Role role);
	public void updateRole(Role role);
	public Role getRole(int id);
	public Role getRoleByName(String name);
	public void deleteRole(int id);
	public List<Role> getRoles();
	
}
