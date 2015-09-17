package pl.edu.agh.blog.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBuilder;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import pl.edu.agh.blog.models.Article;
import pl.edu.agh.blog.models.Comment;
import pl.edu.agh.blog.models.Role;
import pl.edu.agh.blog.models.User;

@Configuration
@ComponentScan("pl.edu.agh.blog")
@EnableTransactionManagement
@Import({ SecurityConfig.class, WebAppConfig.class })
public class ApplicationContextConfig {
	
	@Bean(name = "dataSource")
	public DataSource getDataSource() {
		BasicDataSource dataSource = new BasicDataSource();
	    dataSource.setDriverClassName("org.postgresql.Driver");
	    dataSource.setUrl("jdbc:postgresql://localhost:5432/blog-mgr");
	    dataSource.setUsername("postgres");
	    dataSource.setPassword("root");
	 
	    return dataSource;
	}
	
	@Autowired
	@Bean(name = "sessionFactory")
	public SessionFactory getSessionFactory(DataSource dataSource) {
	 
	    LocalSessionFactoryBuilder sessionBuilder = new LocalSessionFactoryBuilder(dataSource);	 
	    sessionBuilder.addAnnotatedClasses(Role.class, User.class, Article.class, Comment.class);	 
	    //sessionBuilder.scanPackages("pl.edu.agh.blog.models");
	    sessionBuilder.addProperties(getHibernateProperties());	    
	    return sessionBuilder.buildSessionFactory();
	    
	}
	
	private Properties getHibernateProperties() {
	    Properties properties = new Properties();
	    properties.put("hibernate.show_sql", "true");
	    properties.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
	    properties.put("jadira.usertype.autoRegisterUserTypes", "true");
	    //properties.put("dateTime", "org.joda.time.contrib.hibernate.PersistentDateTime");
	    return properties;
	}
	
	@Autowired
	@Bean(name = "transactionManager")
	public HibernateTransactionManager getTransactionManager(SessionFactory sessionFactory) {
	    HibernateTransactionManager transactionManager = new HibernateTransactionManager(sessionFactory);	 
	    return transactionManager;
	}

}
