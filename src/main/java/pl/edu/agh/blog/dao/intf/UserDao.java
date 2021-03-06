package pl.edu.agh.blog.dao.intf;

import java.util.List;

import pl.edu.agh.blog.models.User;

public interface UserDao {

	public void addUser(User user);
	public void updateUser(User user);
	public User getUser(int id);
	public User getUserByUsername(String username);
	public void deleteUser(int id);
	public List<User> getUsers();
	public List<User> getUsersWithRole();
	
}
