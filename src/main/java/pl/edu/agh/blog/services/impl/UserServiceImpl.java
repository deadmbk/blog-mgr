package pl.edu.agh.blog.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pl.edu.agh.blog.dao.intf.UserDao;
import pl.edu.agh.blog.models.User;
import pl.edu.agh.blog.services.intf.UserService;

@Service
@Transactional
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao dao;
	
	@Override
	public void addUser(User user) {
		dao.addUser(user);
	}

	@Override
	public void updateUser(User user) {
		dao.updateUser(user);
	}

	@Override
	public User getUser(int id) {
		return dao.getUser(id);
	}
	
	@Override
	public User getUserByUsername(String username) {
		return dao.getUserByUsername(username);
	}

	@Override
	public void deleteUser(int id) {
		dao.deleteUser(id);
	}

	@Override
	public List<User> getUsers() {
		return dao.getUsers();
	}

}
