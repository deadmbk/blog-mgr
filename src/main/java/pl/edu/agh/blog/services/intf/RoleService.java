package pl.edu.agh.blog.services.intf;

import java.util.List;

import pl.edu.agh.blog.models.Role;

public interface RoleService {

	// unused method
	public void addRole(Role role);
	
	// unused method
	public void updateRole(Role role);
	
	// unused method
	public Role getRole(int id);
	
	public Role getRoleByName(String name);
	
	// unused method
	public void deleteRole(int id);
	
	public List<Role> getRoles();
	
}
