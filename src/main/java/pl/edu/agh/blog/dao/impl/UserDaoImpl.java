package pl.edu.agh.blog.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.criterion.Restrictions;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

import pl.edu.agh.blog.dao.intf.UserDao;
import pl.edu.agh.blog.models.User;

@Repository()
public class UserDaoImpl extends AbstractDaoImpl implements UserDao {

	@Override
	public void addUser(User user) {		
		String password = encodePassword(user.getPassword());
		user.setPassword(password);
		save(user);
	}

	@Override
	public void updateUser(User user) {
		
		User userToUpdate = getUser(user.getId());
		
		userToUpdate.setUsername(user.getUsername());
		userToUpdate.setRole(user.getRole());
		
		if (!user.getPassword().isEmpty()) {
			
			String password = this.encodePassword(user.getPassword());
			userToUpdate.setPassword(password);
		
		}
		
		update(userToUpdate);
	}

	@Override
	public User getUser(int id) {		
		Criteria criteria = getSession().createCriteria(User.class);
		criteria.add(Restrictions.eq("id", id));
		
		User toReturn = (User) criteria.uniqueResult();
		if (toReturn != null) {
			Hibernate.initialize(toReturn.getRole());
		}
		
		return toReturn;
	}
	

	@Override
	public User getUserByUsername(String username) {
		Criteria criteria = getSession().createCriteria(User.class);
		criteria.add(Restrictions.eq("username", username));
		
		User toReturn = (User) criteria.uniqueResult();
		// Nie ma potrzeby pobierania roli
		/*if (toReturn != null) {
			Hibernate.initialize(toReturn.getRole());
		}*/
		
		return toReturn;
	}

	@Override
	public void deleteUser(int id) {		
		User toDelete = new User();
		toDelete.setId(id);
		delete(toDelete);
	}
	
	@Override
	public List<User> getUsers() {
		@SuppressWarnings("unchecked")
		List<User> list = (List<User>) getSession().createCriteria(User.class).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();		
		return list;
	}

	@Override
	public List<User> getUsersWithRole() {
		@SuppressWarnings("unchecked")
		List<User> list = (List<User>) getSession().createCriteria(User.class).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
		for (User entity : list) {
			Hibernate.initialize(entity.getRole());
		}
		
		return list;
	}

	private String encodePassword(String rawPassword) {
		if (rawPassword == null || "".equals(rawPassword))
			return "";
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		return (passwordEncoder.encode(rawPassword));
	}

}
