package pl.edu.agh.blog.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableWebSecurity
@EnableTransactionManagement
@ImportResource({"/WEB-INF/acl-context.xml"})
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	DataSource dataSource;
	
	@Autowired
	public void configureAuthentication(AuthenticationManagerBuilder auth) throws Exception {

		auth.jdbcAuthentication().dataSource(dataSource)
			.usersByUsernameQuery("SELECT username, password, 1 FROM users WHERE username=?")
			.authoritiesByUsernameQuery("SELECT username, name FROM users JOIN roles ON roles.id = users.role_id WHERE username=?")
			.passwordEncoder(passwordEncoder());
		
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		/*
		 * users	- URL-based security (only role ADMIN allowed)
		 * articles - URL-based security and later method security to determine author
		 * comments - URL-based security and later method security to determine author
		 */
		
		http.authorizeRequests()
				.antMatchers("/user/register", "/login").not().authenticated()	// not working for /login*	
				.antMatchers("/user/**").access("hasRole('ROLE_ADMIN')")
				.antMatchers("/article/list", "/article/show/*").permitAll()
				.antMatchers("/article/add", "/article/edit", "/article/edit/*", "/article/delete/*").access("hasAnyRole('ROLE_ADMIN', 'ROLE_EDITOR')")	
				.antMatchers("/article/*/comment/**").authenticated()
				.antMatchers("/resources/**").permitAll()
				.antMatchers("/", "/index").permitAll()
				.anyRequest().denyAll()
				.and()
			.formLogin()
	            .loginPage("/login") .failureUrl("/login?error")
	            .permitAll()
	            .and()
        	.logout()
        		.logoutSuccessUrl("/?logout")
        		.permitAll()
        		.and()
    		.exceptionHandling()
    			.accessDeniedPage("/access-denied");
					
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		PasswordEncoder encoder = new BCryptPasswordEncoder();
		return encoder;
	}
}
