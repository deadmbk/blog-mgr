package pl.edu.agh.blog.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pl.edu.agh.blog.dao.intf.RoleDao;
import pl.edu.agh.blog.models.Role;
import pl.edu.agh.blog.services.intf.RoleService;

@Service
@Transactional
public class RoleServiceImpl implements RoleService {

	@Autowired
	private RoleDao dao;
	
	@Override
	public void addRole(Role role) {
		dao.addRole(role);
	}

	@Override
	public void updateRole(Role role) {
		dao.updateRole(role);
	}

	@Override
	public Role getRole(int id) {
		return dao.getRole(id);
	}
	
	@Override
	public Role getRoleByName(String name) {
		return dao.getRoleByName(name);
	}

	@Override
	public void deleteRole(int id) {
		dao.deleteRole(id);
	}

	@Override
	public List<Role> getRoles() {
		return dao.getRoles();
	}

}
