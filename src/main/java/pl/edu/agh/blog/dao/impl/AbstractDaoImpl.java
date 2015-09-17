package pl.edu.agh.blog.dao.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractDaoImpl {

	@Autowired
	private SessionFactory sessionFactory;
	
	protected Session getSession() {
		return sessionFactory.getCurrentSession();
	}
	
	public void save(Object entity) {
		getSession().save(entity);
	}
	
	public void delete(Object entity) {
		getSession().delete(entity);
	}
	
	public void update(Object entity) {
		getSession().update(entity);
	}
	
}
