package pl.edu.agh.blog.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import pl.edu.agh.blog.dao.intf.RoleDao;
import pl.edu.agh.blog.models.Role;

@Repository()
public class RoleDaoImpl extends AbstractDaoImpl implements RoleDao {

	@Override
	public void addRole(Role role) {
		save(role);
	}

	@Override
	public void updateRole(Role role) {
		update(role);
	}

	@Override
	public Role getRole(int id) {
		Criteria criteria = getSession().createCriteria(Role.class);
		criteria.add(Restrictions.eq("id", id));
		
		Role toReturn = (Role) criteria.uniqueResult();
		return toReturn;
	}
	
	@Override
	public Role getRoleByName(String name) {
		Criteria criteria = getSession().createCriteria(Role.class);
		criteria.add(Restrictions.eq("name", name));
		
		Role toReturn = (Role) criteria.uniqueResult();
		return toReturn;
	}

	@Override
	public void deleteRole(int id) {
		Role toDelete = new Role();
		toDelete.setId(id);
		delete(toDelete);
	}

	@Override
	public List<Role> getRoles() {
		@SuppressWarnings("unchecked")
		List<Role> list = (List<Role>) getSession().createCriteria(Role.class).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();	
		return list;
	}

	
}
